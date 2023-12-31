package shay.space.station.infra.base;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import shay.space.station.infra.behavior.IRobotState;
import shay.space.station.infra.behavior.states.FailingState;
import shay.space.station.infra.behavior.states.RebootingState;
import shay.space.station.infra.behavior.states.WorkingState;
import shay.space.station.tools.Tool;
import shay.space.station.tools.ToolState;
import shay.space.station.tools.exception.ToolMalfunctionException;
import shay.space.station.ui.infra.Output;

import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class Robot implements IRobotDate, IRobotBuilder, IRobotActions, IRobotBehavior {

    private final ReadWriteLock lock = new ReentrantReadWriteLock(); // like ReentrantLock
    private final Lock m_readLock = lock.readLock();
    private final Lock m_writeLock = lock.writeLock();

    protected RobotData m_robotData;
    protected IRobotState m_stateRobot;
    protected final List<Tool> m_tools;

    @Autowired
    BeanFactory m_beanFactory;

    @Autowired
    Output m_output;


    public Robot(final List<Tool> tools, final IRobotState robotState) {
        m_tools = tools;
        setStates(robotState);
    }

    @Override
    public List<Tool> getTools() {
        return m_tools;
    }

    @Override
    public IRobotState getStates() {
        m_readLock.lock();
        try {
            return m_stateRobot;
        }
        finally {
            m_readLock.unlock();
        }
    }

    @Override
    public void setStates(final IRobotState statesRobot) {
        m_writeLock.lock();
        try {
            m_stateRobot = statesRobot;
        }
        finally {
            m_writeLock.unlock();
        }

    }

    @Override
    public void dispatch() {
        final IRobotActions actions = this;
        m_stateRobot.dispatchState(actions);

    }

    @Override
    public void reboot() {
        final IRobotActions actions = this;
        m_stateRobot.rebootState(actions);

    }

    @Override
    public void selfDiagnosis() {
        final IRobotActions actions = this;
        m_stateRobot.selfDiagnosisState(actions);
    }

    @Override
    public RobotData getData() {
        return m_robotData;
    }

    @Override
    public void build(final String name, final String callSign) {
        m_robotData = new RobotData(name, callSign, this.getClass().getSimpleName());
    }

    @Override
    public String toString() {
        return m_robotData.toString() + "| Tools" + m_tools + "| Robot State: " + m_stateRobot.toString();
    }


    @Override
    public void startSelfDiagnosis() {
        throw new UnsupportedOperationException();

    }

    @Override
    public void startFixingTools() {
        CompletableFuture<Boolean> selfHealingResult = CompletableFuture.completedFuture(true); // Default success

        for (Tool tool : m_tools) {
            if (tool.getToolState() == ToolState.MALFUNCTION) {
                selfHealingResult = tool.selfHealing(completion -> {
                    if (completion instanceof ToolMalfunctionException) {
                        m_output.print("Tool self-healing failed due to malfunction.");
                    }
                    else if (completion instanceof InterruptedException) {
                        m_output.print("Tool self-healing was interrupted.");
                    }
                    else {
                        m_output.print("Tool self-healing encountered an unexpected error.");
                    }
                });
            }
        }

        selfHealingResult.thenAccept(success -> {
            if (success) {
                m_output.print("All tools have been successfully healed.");
            }
            else {
                m_output.print("Some tools could not be healed.");
            }
        });
    }


    // @Override
    // public void startFixingTools() {
    //
    //     ForkJoinPool.commonPool().submit(() -> {
    //         for (Tool tool : m_tools) {
    //             m_output.print("Starting self-healing on tool " + tool.toString());
    //             if (tool.getToolState() == ToolState.MALFUNCTION) {
    //                 tool.selfHealing();
    //             }
    //         }
    //
    //         for (Tool tool : m_tools) {
    //             if (tool.getToolState() == ToolState.MALFUNCTION) {
    //                 return false;
    //             }
    //
    //         }
    //         // this.setState(new ActiveState(m_robot, m_output));
    //         return true;
    //     });
    //
    // }

    @Override
    public void startWork() {
        final var tool = randomJob(m_tools);
        m_output.print("Robot " + m_robotData.name() + " is in active duty." + tool.toString() + ".");
        m_output.print("Duty shift cycle will take a random time between 30-180 seconds.");
        final var working = m_beanFactory.getBean(WorkingState.class);
        setStates(working);
        try {
            tool.use(success -> {
                if (success) {
                    m_output.print("Tool usage successful.");
                }
                else {
                    m_output.print("Tool usage failed.");
                    final var failing = m_beanFactory.getBean(FailingState.class);
                    setStates(failing);
                }
            });

        }
        catch (ToolMalfunctionException e) {
            m_output.print("XX " + e.getMessage());
            final var failing = m_beanFactory.getBean(FailingState.class);
            setStates(failing);
        }


    }

    @Override
    public void startReboot() {
        m_output.print("Robot " + m_robotData.name() + " start Reboot.");

        final var rebooting = m_beanFactory.getBean(RebootingState.class);
        setStates(rebooting);

    }

    @Override
    public void notActive() {
        m_output.print("Robot " + m_robotData.name() + " is not active.");

    }

    private Tool randomJob(final List<Tool> tools) {
        // assert tools.size() == 0;
        final SecureRandom sr = new SecureRandom();
        return tools.get(sr.nextInt(tools.size()));
    }
}
