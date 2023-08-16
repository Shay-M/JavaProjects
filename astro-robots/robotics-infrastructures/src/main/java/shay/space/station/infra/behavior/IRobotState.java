package shay.space.station.infra.behavior;

import shay.space.station.infra.base.IRobotActions;
import shay.space.station.infra.base.IRobotDate;

public interface IRobotState {

    void dispatchState(final IRobotActions actions);

    void rebootState(final IRobotActions actions);

    void selfDiagnosisState(final IRobotActions actions);

    String toString();

    default String getStateName() {
        return this.getClass().getSimpleName();
    }
}
