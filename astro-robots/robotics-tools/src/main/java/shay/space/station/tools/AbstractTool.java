package shay.space.station.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import shay.space.station.core.random.ProbabilityOfSuccess;
import shay.space.station.tools.exception.ToolMalfunctionException;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractTool implements Tool {
    protected static final long SEC = 1000L;
    protected static final int MIN_TIME = 3;
    protected static final int MAX_TIME = 180;

    protected final String m_toolName;
    private final double m_healingSuccessRates;
    private final double m_useSuccessRates;
    protected ToolState m_toolState;

    @Autowired
    ProbabilityOfSuccess probabilityOfSuccess;

    public AbstractTool(final double healingSuccessRates, final double useSuccessRates) {
        m_healingSuccessRates = healingSuccessRates;
        m_useSuccessRates = useSuccessRates;
        m_toolName = this.getClass().getSimpleName();
        m_toolState = ToolState.READY;
    }


    @Override
    public void setToolState(ToolState toolState) {
        m_toolState = toolState;
    }

    @Override
    public ToolState getToolState() {
        return m_toolState;
    }

    // @Override
    // public boolean selfHealing() {
    //     return false;
    // }

    @Async
    @Override
    public void use() throws ToolMalfunctionException {
        // if (!probabilityOfSuccess.isSuccess(m_useSuccessRates)) {
        //     m_toolState = ToolState.MALFUNCTION;
        //     throw new ToolMalfunctionException();
        // }

        try {
            System.out.println("start long work");
            System.out.println(Thread.currentThread().threadId());
            final int useTime = ThreadLocalRandom.current().nextInt(MIN_TIME, MAX_TIME);
            System.out.println("Duty shift cycle will take a random time between 30-180 seconds.");

            Thread.sleep(SEC * useTime);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("end long work");
        // return CompletableFuture.completedFuture("diamonds");

    }

    @Override
    public boolean selfHealing() {
        assert (m_toolState == ToolState.MALFUNCTION);
        final int repairTime = ThreadLocalRandom.current().nextInt(10, 20);
        System.out.println("Starting self-healing on tool " + m_toolName + ". Expected repair time: " + repairTime + " seconds.");
        try {
            Thread.sleep(repairTime * SEC);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean repairSucceeded = probabilityOfSuccess.isSuccess(m_healingSuccessRates);
        System.out.println("Self-healing " + (repairSucceeded ? "successful" : "failed") + " on tool " + m_toolName);
        return repairSucceeded;

    }

    @Override
    public String toString() {
        return "Tool name: " + m_toolName + " | State: " + m_toolState.toString().toLowerCase();
    }
}
