package shay.space.station.starter;


import shay.space.station.core.annotations._Multiton;
import shay.space.station.core.annotations.models._Hal9000;
import shay.space.station.core.annotations.models._Tachikomas;
import shay.space.station.tools.AbstractTool;
import shay.space.station.tools.Tool;

@_Multiton
@_Hal9000
@_Tachikomas
public class LaserCutter extends AbstractTool implements Tool {
    private static final double HEALING_SUCCESS_RATES = 0.9;
    private static final double USE_SUCCESS_RATES = 0.2;

    public LaserCutter() {
        super(HEALING_SUCCESS_RATES, USE_SUCCESS_RATES);
    }
}


//@Component
// public class FaultInjector {
//    @Value("50")
//    private int thresold;
//
//    private Random rng = new Random();
//
//    public boolean select() {
//        return rng.nextInt(100) > thresold;
//    }
//}
