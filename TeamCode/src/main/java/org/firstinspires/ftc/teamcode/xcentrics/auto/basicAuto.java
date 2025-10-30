package org.firstinspires.ftc.teamcode.xcentrics.auto;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.xcentrics.command.halt;
@Autonomous(name = "basic auto")

public class basicAuto extends OpMode {
    Follower follower;
    TelemetryManager telemetryManager = PanelsTelemetry.INSTANCE.getTelemetry();
    halt halt = new halt();

    @Override
    public void init() {
    follower = Constants.createFollower(hardwareMap);
    }

    @Override
    public void start(){
        follower.startTeleOpDrive();
    }

    @Override
    public void loop() {
        follower.update();
        telemetryManager.update();
        follower.setTeleOpDrive(1,0,0);
        follower.update();
        halt.halt(0.5);
        follower.setTeleOpDrive(0,0,0);
        follower.update();
    }
}
