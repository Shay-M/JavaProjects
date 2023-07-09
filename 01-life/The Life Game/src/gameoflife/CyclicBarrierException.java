package gameoflife;

public class CyclicBarrierException extends RuntimeException {
    public CyclicBarrierException(final Exception ex) {
        super(ex);
    }
}
