package org.firstinspires.ftc.teamcode.xcentrics.auto;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.xcentrics.subsystem.Camera;
import org.firstinspires.ftc.teamcode.xcentrics.subsystem.Intake;
import org.firstinspires.ftc.teamcode.xcentrics.subsystem.Launcher;

@Autonomous(name = "auto")
public class camraAuto extends OpMode{
    private Camera camera;
    private Follower follower;
    private PanelsTelemetry panelsTelemetry;
    private Intake intake;
    private Launcher launcher;

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        camera = new Camera(hardwareMap,"camera",follower,telemetry);
        intake = new Intake(hardwareMap,"intake");
        launcher = new Launcher(hardwareMap,"launcher");
    }

    @Override
    public void loop() {

    }
}