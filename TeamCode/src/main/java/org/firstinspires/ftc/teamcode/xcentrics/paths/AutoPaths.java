package org.firstinspires.ftc.teamcode.xcentrics.paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.xcentrics.subsystem.Camera;

public class AutoPaths {
private final Pose startPose = new Pose(72, 120, Math.toRadians(90)); // Start Pose of our robot.
    private final Pose scorePoseBlue = new Pose(84, 84, Math.toRadians(50)); // Scoring Pose of our robot. It is facing the goal at a 115 degree angle.
    private final Pose scorePoseRed = new Pose(60,82,Math.toRadians(130));
    private final Pose PPGPose = new Pose(100, 83.5, Math.toRadians(0)); // Highest (First Set) of Artifacts from the Spike Mark.
    private final Pose PGPPose = new Pose(100, 59.5, Math.toRadians(0)); // Middle (Second Set) of Artifacts from the Spike Mark.
    private final Pose GPPPose = new Pose(100, 35.5, Math.toRadians(0)); // Lowest (Third Set) of Artifacts from the Spike Mark.

    private PathChain grabPPG;
    private PathChain scorePPG;
    private PathChain grabPGP;
    private PathChain scorePGP;
    private PathChain grabGPP;
    private PathChain scoreGPP;
    private Follower follower;
    
    private int pathStatePPG; // Current state machine value
    private int pathStatePGP; // Current state machine value
    private int pathStateGPP; // Current state machine value
    private Camera camera;
    private boolean isRed = false; //What aliance are we on

    public AutoPaths(Follower follower,Camera camera){
        this.follower = follower;
        this.camera = camera;
    }


    public void buildPathsPPG() {
        // basically just plotting the points for the lines that score the PPG pattern
        grabPPG = follower.pathBuilder() //
                .addPath(new BezierLine(startPose, PPGPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), PPGPose.getHeading())
                .build();
        // Move to the scoring pose from the first artifact pickup pose
        if (isRed) {
            scorePPG = follower.pathBuilder()
                    .addPath(new BezierLine(PPGPose, scorePoseRed ))
                    .setLinearHeadingInterpolation(PPGPose.getHeading(), scorePoseRed.getHeading())
                    .build();
        } else {
            scorePPG = follower.pathBuilder()
                    .addPath(new BezierLine(PPGPose, scorePoseBlue))
                    .setLinearHeadingInterpolation(PPGPose.getHeading(), scorePoseBlue.getHeading())
                    .build();
        }

    }
    public void buildPathsPGP() {
        // basically just plotting the points for the lines that score the PGP pattern
        // Move to the first artifact pickup pose from the start pose
        grabPGP = follower.pathBuilder() // Changed from scorePGP to grabPGP
                .addPath(new BezierLine(startPose, PGPPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), PGPPose.getHeading())
                .build();
        // Move to the scoring pose from the first artifact pickup pose
        if (isRed) {
            scorePGP = follower.pathBuilder()
                    .addPath(new BezierLine(PGPPose, scorePoseRed))
                    .setLinearHeadingInterpolation(PGPPose.getHeading(), scorePoseRed.getHeading())
                    .build();
        } else {
            scorePGP = follower.pathBuilder()
                    .addPath(new BezierLine(PGPPose, scorePoseBlue))
                    .setLinearHeadingInterpolation(PGPPose.getHeading(), scorePoseBlue.getHeading())
                    .build();
        }
    }
    public void buildPathsGPP() {
        // basically just plotting the points for the lines that score the GPP pattern
        // Move to the first artifact pickup pose from the start pose
        grabGPP = follower.pathBuilder()
                .addPath(new BezierLine(startPose, GPPPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), GPPPose.getHeading())
                .build();
        // Move to the scoring pose from the first artifact pickup pose
        if(isRed){
            scoreGPP = follower.pathBuilder()
                    .addPath(new BezierLine(GPPPose, scorePoseRed))
                    .setLinearHeadingInterpolation(GPPPose.getHeading(), scorePoseRed.getHeading())
                    .build();
        } else {
            scoreGPP = follower.pathBuilder()
                    .addPath(new BezierLine(GPPPose, scorePoseBlue))
                    .setLinearHeadingInterpolation(GPPPose.getHeading(), scorePoseBlue.getHeading())
                    .build();
        }
    }
    //below is the state machine or each pattern
    public void updateStateMachinePPG() {
        switch (pathStatePPG) {
            case 0:
                // Move to the scoring position from the start position
                follower.followPath(grabPPG);
                setpathStatePPG(1); // Call the setter method
                break;
            case 1:
                // Wait until we have passed all path constraints
                if (!follower.isBusy()) {
                    // Move to the first artifact pickup location from the scoring position
                    follower.followPath(scorePPG);
                    setpathStatePPG(-1); //set it to -1 so it stops the state machine execution
                }
                break;
        }
    }
    public void updateStateMachinePGP() {
        switch (pathStatePGP) {
            case 0:
                // Move to the scoring position from the start position
                follower.followPath(grabPGP);
                setpathStatePGP(1); // Call the setter method
                break;
            case 1:
                // Wait until we have passed all path constraints
                if (!follower.isBusy()) {
                    // Move to the first artifact pickup location from the scoring position
                    follower.followPath(scorePGP);
                    setpathStatePGP(-1); // Call the setter for PGP
                }
                break;
        }
    }
    public void updateStateMachineGPP() {
        switch (pathStateGPP) {
            case 0:
                // Move to the scoring position from the start position
                follower.followPath(grabGPP);
                setpathStateGPP(1); // Call the setter method
                break;
            case 1:
                // Wait until we have passed all path constraints
                if (!follower.isBusy()) {
                    // Move to the first artifact pickup location from the scoring position
                    follower.followPath(scoreGPP);
                    setpathStateGPP(-1); //set it to -1 so it stops the state machine execution
                }
                break;
        }
    }
    // Setter methods for pathState variables placed at the class level
    void setpathStatePPG(int newPathState) {
        this.pathStatePPG = newPathState;
    }
    void setpathStatePGP(int newPathState) {
        this.pathStatePGP = newPathState;
    }
    void setpathStateGPP(int newPathState) {
        this.pathStateGPP = newPathState;
    }

}