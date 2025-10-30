package org.firstinspires.ftc.teamcode.xcentrics.auto;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.xcentrics.command.halt;
@Autonomous(name = "basic auto")

public class basicAuto extends OpMode {
    DcMotor FL,BL,BR,FR;
    TelemetryManager telemetryManager = PanelsTelemetry.INSTANCE.getTelemetry();
    halt halt = new halt();

    @Override
    public void init() {
    FL = hardwareMap.get(DcMotor.class,"FL");
    FL.setDirection(DcMotorSimple.Direction.REVERSE);
    FR = hardwareMap.get(DcMotor.class,"FR");
    BL = hardwareMap.get(DcMotor.class,"BL");
    BL.setDirection(DcMotorSimple.Direction.REVERSE);
    BR = hardwareMap.get(DcMotor.class,"BR");
    FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }


int i = 0;
    @Override
    public void loop() {
    if(i ==0){
        setPower(1);
        halt.halt(1);
        setPower(0);
        i++;
    }
    }
    private void setPower(double power){
        FL.setPower(power);
        FR.setPower(power);
        BL.setPower(power);
        BR.setPower(power);
    }
}
