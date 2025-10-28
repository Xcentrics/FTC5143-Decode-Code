package org.firstinspires.ftc.teamcode.xcentrics.TeleOp;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
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
import org.firstinspires.ftc.teamcode.xcentrics.paths.TeleOpPaths;
import org.firstinspires.ftc.teamcode.xcentrics.subsystem.Intake;
import org.firstinspires.ftc.teamcode.xcentrics.subsystem.Launcher;

import java.util.function.Supplier;

@TeleOp(name = "TeleOpLive")
public class TeleOpLive extends OpMode 
{

    Intake intake;
    Launcher launcher;
    Follower follower;
    TelemetryManager telemetryManager;
    GamepadEx drive1,drive2;
    TeleOpPaths paths;
    private boolean autoDrive = false;
    private static boolean isRed = true;
    private Supplier<PathChain> pathChainSupplier;

    @Override
    public void init() 
    {

        intake = new Intake(hardwareMap,"intake");

        launcher = new Launcher(hardwareMap,"launcher");

        follower = Constants.createFollower(hardwareMap);

        telemetryManager = PanelsTelemetry.INSTANCE.getTelemetry();

        paths = new TeleOpPaths(follower);

        drive1 = new GamepadEx(gamepad1);
        drive2 = new GamepadEx(gamepad2);
    }

    @Override
    public void loop() 
    {
        //set launcher speed
        launcher.setSpeed(1000);
        //update follower
        follower.update();

        //update telemetry
        telemetryManager.update();

        //set drive controls
        follower.setTeleOpDrive(
                drive1.getLeftY(),
                drive1.getLeftX(),
                drive1.getRightX(),
                false
        );

        //intake controls
        if(drive2.getButton(GamepadKeys.Button.A))
        {
            intake.intake();
        }
        else if(drive2.getButton(GamepadKeys.Button.B))
        {
            intake.setPower(-1);
        }
        else
        {
            intake.stopIntake();
        }

        //launcher controls
        if(drive2.getButton(GamepadKeys.Button.A)){
            if(launcher.canLaunch()){
                launcher.launch();
            }
        }

        //set what alliance we are on
        if(drive1.getButton(GamepadKeys.Button.LEFT_BUMPER))
        {
            if(drive1.getButton(GamepadKeys.Button.RIGHT_BUMPER) && !isRed)
            {
                isRed = false;
            } else if(drive1.getButton(GamepadKeys.Button.RIGHT_BUMPER) && !isRed) {
                isRed = true;
            }
        }

        //auto drive
        if(drive1.getButton(GamepadKeys.Button.B)){
            autoDrive(isRed);
        }

        //stop auto drive
        if(drive1.getButton(GamepadKeys.Button.X)){
            autoDrive = false;
            follower.breakFollowing();
        }
    }

    private void autoDrive(boolean isRed)
    {
        if(!follower.isBusy())
        {
            follower.followPath(paths.score);
        }
    }
    
    //method for telemetry
    private void updateTelemetry()
    {
        telemetry.addData("Auto Drive: ", autoDrive);
        telemetry.addData("Is Red: ", isRed);
        telemetry.addData("Launcher Can Launch: ", launcher.canLaunch());
        telemetryManager.addData("Auto Drive: ", autoDrive);
        telemetryManager.addData("Is Red: ", isRed);
        telemetryManager.addData("Launcher Can Launch: ", launcher.canLaunch());
    }
}
