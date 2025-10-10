package org.firstinspires.ftc.teamcode.xcentrics.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;


import org.firstinspires.ftc.robotcore.external.android.util.Size;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class Camera extends SubsystemBase {

    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;
    public List<AprilTagDetection> curretnDetections = aprilTag.getDetections();
    boolean vision = true,flag = false;

    public void vision(boolean b){
        vision = b;
    }
    public Camera(HardwareMap hardwareMap, String name){
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();
        visionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class,"Webcam 1"), aprilTag);
    }

    public List<AprilTagDetection> tagDetections(){
        return curretnDetections;
    }

    @Override
    public void periodic(){
        if(vision && flag){
            visionPortal.resumeStreaming();
        } else if(!vision && !flag) {
            flag = true;
            visionPortal.stopStreaming();
        }
    }
}
