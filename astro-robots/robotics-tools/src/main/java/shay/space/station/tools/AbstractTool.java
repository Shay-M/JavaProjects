package shay.space.station.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import shay.space.station.core.random.ProbabilityOfSuccess;
import shay.space.station.tools.exception.ToolMalfunctionException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

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


    @Async
    @Override
    public CompletableFuture<Void> use(final Consumer<Boolean> completionCallback) throws ToolMalfunctionException {
        if (probabilityOfSuccess.isSuccess(m_useSuccessRates)) {
            m_toolState = ToolState.MALFUNCTION;
            throw new ToolMalfunctionException();
        }

        return CompletableFuture.runAsync(() -> {
            try {
                // Simulate tool usage
                int useTime = ThreadLocalRandom.current().nextInt(MIN_TIME, MAX_TIME);
                Thread.sleep(SEC * useTime);
                boolean success = true;
                completionCallback.accept(success);
            }
            catch (InterruptedException e) {
                completionCallback.accept(false);
            }
        });
    }

    @Async
    @Override
    public CompletableFuture<Boolean> selfHealing(Consumer<Throwable> completionCallback) {
        assert (m_toolState == ToolState.MALFUNCTION);
        final int repairTime = ThreadLocalRandom.current().nextInt(10, 20);
        // System.out.println("Starting self-healing on tool " + m_toolName + ". Expected repair time: " + repairTime + " seconds.");

        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(repairTime * SEC);
                final boolean repairSucceeded = probabilityOfSuccess.isSuccess(m_healingSuccessRates);
                return repairSucceeded;
            }
            catch (InterruptedException e) {
                completionCallback.accept(e); // Notify the completion callback about the exception
                return false; // Indicate that self-healing failed due to interruption
            }
            catch (Exception e) {
                completionCallback.accept(e);
                return false;
            }
        });
    }


    @Override
    public String toString() {
        return "Name: " + m_toolName + ", On State: " + m_toolState.toString().toLowerCase();
    }
}
