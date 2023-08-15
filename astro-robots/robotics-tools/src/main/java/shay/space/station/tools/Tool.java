package shay.space.station.tools;


import shay.space.station.tools.exception.ToolMalfunctionException;

public interface Tool {

    void setToolState(ToolState toolState);

    ToolState getToolState();

    boolean selfHealing();

    void use() throws ToolMalfunctionException;

}
