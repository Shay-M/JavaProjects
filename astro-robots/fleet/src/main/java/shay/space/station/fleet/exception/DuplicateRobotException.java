package shay.space.station.fleet.exception;

public class DuplicateRobotException extends RuntimeException {
    public DuplicateRobotException(final String message) {
        super(message);
    }
}
