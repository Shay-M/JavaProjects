package shay.space.station.infra.base;


import shay.space.station.infra.behavior.IRobotState;
import shay.space.station.tools.Tool;

import java.util.List;

public interface IRobotDate {

    IRobotState getRobotStates();

    RobotData getData();

    List<Tool> getTools();


}
