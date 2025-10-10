package org.firstinspires.ftc.teamcode.xcentrics.TeleOp;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.localizers.ThreeWheelLocalizer;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.xcentrics.subsystem.Intake;
import org.firstinspires.ftc.teamcode.xcentrics.subsystem.Launcher;

import java.util.function.Supplier;

@TeleOp(name = "TeleOpLive")
public class TeleOpLive extends OpMode {

    Intake intake;
    Launcher launcher;
    Follower follower;
    TelemetryManager telemetryManager;
    GamepadEx drive1,drive2;
    private boolean autoDrive = false;
    private Supplier<PathChain> pathChainSupplier;

    @Override
    public void init() {

    intake = new Intake(hardwareMap,"intake");

    launcher = new Launcher(hardwareMap,"launcher");

    follower = Constants.createFollower(hardwareMap);

    telemetryManager = PanelsTelemetry.INSTANCE.getTelemetry();

    drive1 = new GamepadEx(gamepad1);
    drive2 = new GamepadEx(gamepad2);
    }

    @Override
    public void loop() {
        follower.update();
        telemetryManager.update();

        follower.setTeleOpDrive(
                drive1.getLeftY(),
                drive1.getLeftX(),
                drive1.getRightX(),
                true
        );


    }
}
