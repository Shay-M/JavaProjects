package shay.space.station.starter;


import org.springframework.beans.factory.annotation.Qualifier;
import shay.space.station.core.annotations._Multiton;
import shay.space.station.core.annotations.models._ActiveState;
import shay.space.station.core.annotations.models._FailingState;
import shay.space.station.core.annotations.models._Hal9000;
import shay.space.station.core.annotations.models._Tachikomas;
import shay.space.station.core.random.ProbabilityOfSuccess;
import shay.space.station.tools.AbstractTool;
import shay.space.station.tools.Tool;

import java.util.List;

@_Multiton
@_Hal9000
@_Tachikomas
public class Disruptor extends AbstractTool {
    private static final double HEALING_SUCCESS_RATES = 0.9;
    private static final double USE_SUCCESS_RATES = 0.2;

    public Disruptor() {
        super(HEALING_SUCCESS_RATES, USE_SUCCESS_RATES);
    }


}
