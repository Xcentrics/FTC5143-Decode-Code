package org.firstinspires.ftc.teamcode.xcentrics.TeleOp.debug;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.xcentrics.subsystem.Camera;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@TeleOp(name = "VisionDebug", group = "debug")
public class visionDebug extends OpMode {

    Camera camera;

    @Override
    public void init() {
        camera = new Camera(hardwareMap, "cameraDebug");

    }

    @Override
    public void loop() {
        camera.periodic();
        telemetryAprilTag();
        if (gamepad1.dpad_down) {
            camera.vision(false);
        } else if (gamepad1.dpad_up) {
            camera.vision(true);
        }
    }

    private void telemetryAprilTag() {

        List<AprilTagDetection> currentDetections = camera.tagDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }   // end for() loop

        // Add "key" information to telemetry
        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        telemetry.addLine("RBE = Range, Bearing & Elevation");

    }   // end method telemetryAprilTag()
}
