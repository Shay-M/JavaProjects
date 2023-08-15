package shay.space.station.starter;


import shay.space.station.core.annotations._Multiton;
import shay.space.station.tools.AbstractTool;

@_Multiton
public class StaticBrush extends AbstractTool {
    private static final double HEALING_SUCCESS_RATES = 0.9;
    private static final double USE_SUCCESS_RATES = 0.2;

    public StaticBrush() {
        super(HEALING_SUCCESS_RATES, USE_SUCCESS_RATES);
    }

}
