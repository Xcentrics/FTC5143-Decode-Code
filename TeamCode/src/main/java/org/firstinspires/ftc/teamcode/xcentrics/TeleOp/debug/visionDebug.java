package org.firstinspires.ftc.teamcode.xcentrics.TeleOp.debug;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.xcentrics.subsystem.Camera;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@TeleOp(name = "VisionDebug", group = "debug")
public class visionDebug extends OpMode {

    Camera camera;
    Follower follower;

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        camera = new Camera(hardwareMap, "cameraDebug",follower,telemetry);

    }

    @Override
    public void loop() {
        camera.periodic();
        if (gamepad1.dpad_down) {
            camera.useCamera(false);
        } else if (gamepad1.dpad_up) {
            camera.useCamera(true);
        }
    }

  // end method telemetryAprilTag()
}
