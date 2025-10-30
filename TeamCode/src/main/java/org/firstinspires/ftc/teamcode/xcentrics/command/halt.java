package org.firstinspires.ftc.teamcode.xcentrics.command;

import java.util.concurrent.TimeUnit;

public class halt {
    public halt(){

    }
    public volatile double time = 0.0;

    // internal time tracking
    private volatile long startTime = 0; // in nanoseconds
    public void halt(double seconds) {
        resetRuntime();
        while (getRuntime() < seconds) {}
    }
    public double getRuntime() {
        final double NANOSECONDS_PER_SECOND = TimeUnit.SECONDS.toNanos(1);
        return (System.nanoTime() - startTime) / NANOSECONDS_PER_SECOND;
    }
    public void resetRuntime() {
        startTime = System.nanoTime();
    }
}
