package org.firstinspires.ftc.teamcode.xcentrics.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.xcentrics.paths.AutoPathsRed;
import org.firstinspires.ftc.teamcode.xcentrics.pipelines.ball;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.opencv.core.Rect;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.List;
@Configurable
public class Camera extends SubsystemBase {
    
    private static final int PPG_TAG_ID = 23;
    private static final int PGP_TAG_ID = 22;
    private static final int GPP_TAG_ID = 21;
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;
    private AprilTagDetection desiredTag = null;
    private TelemetryManager panlesTelemetry;
    private static int foundID = -1;
    private static boolean cameraOn = true,findAprilTag = true;
    private AutoPathsRed auto;
    private Follower follower;
    private ball ball = new ball();
    Telemetry telemetry;
    List<AprilTagDetection> currentDetections;
    public Camera(HardwareMap hwMap, String name, Follower follower, Telemetry telemetry){
        auto = new AutoPathsRed(follower);
        panlesTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();
        this.telemetry = telemetry;
        aprilTag = new AprilTagProcessor.Builder().build();
        
        //Adjust image decimation to trade-off detection-range for decetion rate
        aprilTag.setDecimation(2);

        //build vison portal
        visionPortal = new VisionPortal.Builder()
        .setCamera(hwMap.get(WebcamName.class,"Webcam 1"))
        .addProcessor(aprilTag)
        .build();


    }
    public int foundID(){
        if(foundID != -1){
            return foundID;
        } else {
            return -1;
        }
    }
    public void useCamera(boolean bo){
        cameraOn = bo;
    }
    public void perodic(){

        if(cameraOn) {
            if(findAprilTag){
                // Step through the list of detected tags and look for a matching tag
                currentDetections = aprilTag.getDetections();
                for (AprilTagDetection detection : currentDetections) {
                    // Look to see if we have size info on this tag.
                    if (detection.metadata != null) {
                        //  Check to see if we want to track towards this tag.
                        if (detection.id == PPG_TAG_ID) {
                            desiredTag = detection;
                            foundID = 21; // This should likely be PPG_TAG_ID or the corresponding state machine ID
                            break;  // don't look any further.
                        } else if (detection.id == PGP_TAG_ID) {
                            desiredTag = detection;
                            foundID = 22; // This should likely be PGP_TAG_ID or the corresponding state machine ID
                            break;  // don't look any further.
                        } else if (detection.id == GPP_TAG_ID) {
                            desiredTag = detection;
                            foundID = 23; // This should likely be GPP_TAG_ID or the corresponding state machine ID
                            break;  // don't look any further.
                        }
                    } else {
                        // This tag is NOT in the library, so we don't have enough information to track to it.
                        panlesTelemetry.addData("Tag ID %d is not in TagLibrary", detection.id);
                    }
                }


   // end for() loop

                // Add "key" information to telemetry;
            }
        } else {

            telemetry.update();
        }

    }
    private void addTelemetry(String caption, Object value)  {
    telemetry.addData(caption, value);
    panlesTelemetry.debug(caption, value);
    }
    private void addLine(String line) {
        telemetry.addLine(line);
        panlesTelemetry.debug(line);
    }
    public void telemetry(){
        List<org.opencv.core.Rect> detectedPurpleBalls = ball.getPurpleBallRects();
        List<org.opencv.core.Rect> detectedGreenBalls = ball.getGreenBallRects();

        addTelemetry("Purple Balls Detected", detectedPurpleBalls.size());
        addTelemetry("Green Balls Detected", detectedGreenBalls.size());

        // You can then loop through the lists to get info on each ball
        for (Rect purpleRect : detectedPurpleBalls) {
            addTelemetry("Purple Ball X", purpleRect.x);
            addTelemetry("Purple Ball Y", purpleRect.y);
        }
        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            } else {
                addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }
        addTelemetry("# AprilTags Detected", currentDetections.size());
    }


}
