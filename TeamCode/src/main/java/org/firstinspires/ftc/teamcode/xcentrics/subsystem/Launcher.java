package org.firstinspires.ftc.teamcode.xcentrics.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
@Configurable
public class Launcher extends SubsystemBase {
    private DcMotorEx fly;
    private CSServo feeder;
    public static int p = 0,i = 0,d = 0;
    public static int speed = 0;

    public Launcher(HardwareMap hardwareMap, String name){
        fly = hardwareMap.get(DcMotorEx.class,"fly");
        feeder = hardwareMap.get(CSServo.class,"feeder");
        fly.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void setSpeed(double speed1){
        speed = speed1;
    }
    public boolean canLaunch(){
         return (fly.getVelocity() >= 900 && fly.getVelocity() <= 1100);
    }
    public void launch(){
        feeder.setPower(1);
        halt(0.5);
        feeder.setPower(0);
    }
    @Override
    public void periodic(){
        fly.setVelocityPIDFCoefficients(p,i,d,0);
        fly.setVelocity(speed, AngleUnit.DEGREES);
    }
    
    public volatile double time = 0.0;

    // internal time tracking
    private volatile long startTime = 0; // in nanoseconds
    protected void halt(double seconds) {
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
