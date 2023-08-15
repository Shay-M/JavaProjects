package shay.space.station.infra.behavior.states;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shay.space.station.core.annotations.models._ActiveState;
import shay.space.station.infra.base.IRobotActions;
import shay.space.station.infra.base.IRobotDate;
import shay.space.station.infra.behavior.IRobotState;
import shay.space.station.ui.console.Output;


@Component
@_ActiveState
public class ActiveState implements IRobotState {
    @Autowired
    Output m_output;

    @Override
    public void dispatchState(final IRobotActions actions) {
        actions.startWork();

    }

    @Override
    public void rebootState(final IRobotActions actions) {
        actions.startReboot();

    }

    @Override
    public void selfDiagnosisState(final IRobotActions actions) {
        m_output.print("This operation is valid only if the robot is in a FAILING state.");

    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
