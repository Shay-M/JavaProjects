package shay.space.station.tools;


import shay.space.station.tools.exception.ToolMalfunctionException;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface Tool {

    void setToolState(ToolState toolState);

    ToolState getToolState();

    CompletableFuture<Boolean> selfHealing(final Consumer<Throwable> completionCallback);

    CompletableFuture<Void> use(final Consumer<Boolean> completionCallback) throws ToolMalfunctionException;


}
