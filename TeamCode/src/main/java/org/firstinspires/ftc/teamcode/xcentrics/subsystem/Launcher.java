package org.firstinspires.ftc.teamcode.xcentrics.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.xcentrics.command.halt;

import java.util.concurrent.TimeUnit;

@Configurable
public class Launcher extends SubsystemBase {
    public DcMotorEx fly;
    private CRServo feeder;
    public static PIDCoefficients pidCoefficients = new PIDCoefficients(0,0,0);
    halt halt = new halt();
    public static double targetSpeed = 100;

    public Launcher(HardwareMap hardwareMap, String name){
        fly = hardwareMap.get(DcMotorEx.class,"fly");
        feeder = hardwareMap.get(CRServo.class,"feeder");
        fly.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fly.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
    public void setSpeed(int speed1){
        targetSpeed = speed1;
    }
    public boolean canLaunch(){
         return (fly.getVelocity() >= 900 && fly.getVelocity() <= 1100);
    }
    public void launch(){
        feeder.setPower(1);
        halt.halt(0.5);
        feeder.setPower(0);
    }
    @Override
    public void periodic(){
        update_pid_coeffs();
        fly.setVelocity(targetSpeed);
        PanelsTelemetry.INSTANCE.getTelemetry().debug(fly.getVelocity());
    }
    

    public void update_pid_coeffs() {
        fly.setVelocityPIDFCoefficients(
            pidCoefficients.p,
                pidCoefficients.i,
                pidCoefficients.d,
                0 // no f
        );

    }
}
