package shay.space.station.robots.models;

import org.springframework.beans.factory.annotation.Qualifier;
import shay.space.station.core.annotations._Multiton;
import shay.space.station.core.annotations._Robot;
import shay.space.station.core.annotations.models._ActiveState;
import shay.space.station.core.annotations.models._FailingState;
import shay.space.station.core.annotations.models._Hal9000;
import shay.space.station.core.annotations.models._Maschinenmensch;
import shay.space.station.core.random.ProbabilityOfSuccess;
import shay.space.station.infra.base.Robot;
import shay.space.station.infra.behavior.IRobotState;
import shay.space.station.tools.Tool;

import java.util.List;

@_Robot
@_Multiton
public final class Maschinenmensch extends Robot {
    private static final double SUCCESS_PRES = 0.9;


    private Maschinenmensch(@_Maschinenmensch final List<Tool> tools,
                            final @Qualifier("probabilityOfSuccessSpaceRobot") ProbabilityOfSuccess ps,
                            final @_ActiveState IRobotState activeState,
                            final @_FailingState IRobotState failingState) {


        super(tools, ps.isSuccess(SUCCESS_PRES) ? activeState : failingState);
    }

}

