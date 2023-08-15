package shay.space.station.core.random;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
@Component("probabilityOfSuccessSpaceRobot")
public class ProbabilityOfSuccess {
    public boolean isSuccess(final double p) {
        final SecureRandom secureRandom = new SecureRandom();
        return secureRandom.nextDouble() <= p;
    }
}
