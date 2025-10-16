package org.firstinspires.ftc.teamcode.xcentrics.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;


import org.firstinspires.ftc.robotcore.external.android.util.Size;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.xcentrics.paths.AutoPaths;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class Camera extends SubsystemBase {
    
    private static final int PPG_TAG_ID = 23;
    private static final int PGP_TAG_ID = 22;
    private static final int GPP_TAG_ID = 21;
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;
    private AprilTagDetection desiredTag = null;
    private TelemetryManager panlesTelemetry;
    private static int foundID = -1;
    private static boolean cameraOn = true;
    private AutoPaths auto;
    private Follower follower;
    
    public Camera(HardwareMap hwMap, String name,Follower follower){
        auto = new AutoPaths(follower,this);
        panlesTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();

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
        if(cameraOn){
            // Step through the list of detected tags and look for a matching tag
            List<AprilTagDetection> currentDetections = aprilTag.getDetections();
            for (AprilTagDetection detection : currentDetections) {
                // Look to see if we have size info on this tag.
                if (detection.metadata != null) {
                    //  Check to see if we want to track towards this tag.
                    if (detection.id == PPG_TAG_ID) {
                        // call lines for the PGP pattern
                        auto.buildPathsPPG();

                        desiredTag = detection;
                        foundID = 21; // This should likely be PPG_TAG_ID or the corresponding state machine ID
                        break;  // don't look any further.
                    } else if (detection.id == PGP_TAG_ID) {
                        // call lines for the PGP pattern
                        auto.buildPathsPGP();
                        desiredTag = detection;
                        foundID = 22; // This should likely be PGP_TAG_ID or the corresponding state machine ID
                        break;  // don't look any further.
                    } else if (detection.id == GPP_TAG_ID) {
                        // call lines for the GPP pattern
                        auto.buildPathsGPP();

                        desiredTag = detection;
                        foundID = 23; // This should likely be GPP_TAG_ID or the corresponding state machine ID
                        break;  // don't look any further.
                    }
                } else {
                    // This tag is NOT in the library, so we don't have enough information to track to it.
                    panlesTelemetry.addData("Tag ID %d is not in TagLibrary", detection.id);
                }
            }
            }
    }


}
