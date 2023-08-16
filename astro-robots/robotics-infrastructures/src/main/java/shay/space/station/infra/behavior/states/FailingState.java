package shay.space.station.infra.behavior.states;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shay.space.station.core.annotations.models._FailingState;
import shay.space.station.infra.base.IRobotActions;
import shay.space.station.infra.behavior.IRobotState;
import shay.space.station.ui.infra.Output;


@Component
@_FailingState
public class FailingState implements IRobotState {
    @Autowired
    Output m_output;

    @Override
    public void dispatchState(final IRobotActions actions) {
        actions.notActive();
    }

    @Override
    public void rebootState(final IRobotActions actions) {
        actions.startReboot();
    }

    @Override
    public void selfDiagnosisState(final IRobotActions actions) {
        actions.startFixingTools();

    }

    @Override
    public String toString() {
        return "Failing";
    }
}
