package shay.space.station.infra.base;

import shay.space.station.infra.behavior.IRobotState;

public interface IRobotActions {

    void setStates(IRobotState states);

    void startSelfDiagnosis();

    void startFixingTools();

    void startWork();

    void startReboot();

    void notActive();
}
