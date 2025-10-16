package org.firstinspires.ftc.teamcode.xcentrics.paths;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;

public class redAutoPaths {
private final Pose startPose = new Pose(72, 120, Math.toRadians(90)); // Start Pose of our robot.
    private final Pose scorePose = new Pose(72, 20, Math.toRadians(115)); // Scoring Pose of our robot. It is facing the goal at a 115 degree angle.
    private final Pose PPGPose = new Pose(100, 83.5, Math.toRadians(0)); // Highest (First Set) of Artifacts from the Spike Mark.
    private final Pose PGPPose = new Pose(100, 59.5, Math.toRadians(0)); // Middle (Second Set) of Artifacts from the Spike Mark.
    private final Pose GPPPose = new Pose(100, 35.5, Math.toRadians(0)); // Lowest (Third Set) of Artifacts from the Spike Mark.

    private PathChain getPPG;
    private PathChain scorePPG;
    private PathChain getPGP;
    private Pathchain scoreGPG;
    private PathChain getGPP;
    private PathChain scoreGPP;
    private Follower follower;
    
    private int pathStatePPG; // Current state machine value
    private int pathStatePGP; // Current state machine value
    private int pathStateGPP; // Current state machine value
    private Camera camera;

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
        scorePPG = follower.pathBuilder()
                .addPath(new BezierLine(PPGPose, scorePose))
                .setLinearHeadingInterpolation(PPGPose.getHeading(), scorePose.getHeading())
                .build();
    }
    public void buildPathsPGP() {
        // basically just plotting the points for the lines that score the PGP pattern
        // Move to the first artifact pickup pose from the start pose
        grabPGP = follower.pathBuilder() // Changed from scorePGP to grabPGP
                .addPath(new BezierLine(startPose, PGPPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), PGPPose.getHeading())
                .build();
        // Move to the scoring pose from the first artifact pickup pose
        scorePGP = follower.pathBuilder()
                .addPath(new BezierLine(PGPPose, scorePose))
                .setLinearHeadingInterpolation(PGPPose.getHeading(), scorePose.getHeading())
                .build();
    }
    public void buildPathsGPP() {
        // basically just plotting the points for the lines that score the GPP pattern
        // Move to the first artifact pickup pose from the start pose
        grabGPP = follower.pathBuilder()
                .addPath(new BezierLine(startPose, GPPPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), GPPPose.getHeading())
                .build();
        // Move to the scoring pose from the first artifact pickup pose
        scoreGPP = follower.pathBuilder()
                .addPath(new BezierLine(GPPPose, scorePose))
                .setLinearHeadingInterpolation(GPPPose.getHeading(), scorePose.getHeading())
                .build();
    }
    //below is the state machine or each pattern
    public void updateStateMachinePPG() {
        switch (pathStatePPG) {
            case 0:
            camera.setCamera(false);
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
            camera.setCamera(false);
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
            camera.setCamera(false);
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