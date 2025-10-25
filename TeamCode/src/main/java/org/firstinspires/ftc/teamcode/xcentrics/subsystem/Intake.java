package org.firstinspires.ftc.teamcode.xcentrics.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake extends SubsystemBase {
    private DcMotor intake;
    public static double speed = 0;

    public Intake(HardwareMap hardwareMap,String name){
        intake = hardwareMap.get(DcMotor.class,"intake");
    }

    public void setPower(double speed){
        this.speed = speed;
    }
    public void intake(){
        speed = 1.0;
    }

    @Override
    public void periodic() {
        intake.setPower(speed);
    }
}
