package org.firstinspires.ftc.teamcode.xcentrics.paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.xcentrics.subsystem.Camera;

public class AutoPaths {
private final Pose startPose = new Pose(72, 120, Math.toRadians(90)); // Start Pose of our robot.
    private final Pose scorePoseBlue = new Pose(73, 83, Math.toRadians(45)); // Scoring Pose of our robot. It is facing the goal at a 115 degree angle.
    private final Pose scorePoseRed = new Pose(60,82,Math.toRadians(135));
    private final Pose PPGPose = new Pose(100, 83.5, Math.toRadians(0)); // Highest (First Set) of Artifacts from the Spike Mark.
    private final Pose PGPPose = new Pose(100, 59.5, Math.toRadians(0)); // Middle (Second Set) of Artifacts from the Spike Mark.
    private final Pose GPPPose = new Pose(100, 35.5, Math.toRadians(0)); // Lowest (Third Set) of Artifacts from the Spike Mark.

    private PathChain getPXX,getXPX,getXXG;
    private PathChain scorePXX,scoreXPX,scoreXXG;
    private PathChain getPGX,getXGX,getXXP;
    private PathChain scorePGX,scoreXGX,scoreXXP;
    private PathChain getGXX,getGPX,getGXP;
    private PathChain scoreGXX,scoreGPX,scoreGXP;
    private Follower follower;
    private int pathStatePPG; // Current state machine value
    private int pathStatePGP; // Current state machine value
    private int pathStateGPP; // Current state machine value
    private Camera camera;
    private boolean isRed = false; //What alliance are we on
    private final int PPGx = 83, PGPy = 58, GPPy = 33;

    public AutoPaths(Follower follower,Camera camera){
        this.follower = follower;
        this.camera = camera;
    }

    //paths for PPG pattern
    public void buildPathsPPG(){
        //grab the first purple
       getPXX = follower.pathBuilder()
               .addPath(new BezierLine(new Pose(73,83),new Pose(42,83)))
               .setLinearHeadingInterpolation(Math.toRadians(135),Math.toRadians(180))
               .addPath(new BezierLine(new Pose(42,83),new Pose(35,83)))
               .setConstantHeadingInterpolation(Math.toRadians(180))
               .build();
       //score the first purple
        if(isRed) {
            scorePXX = follower.pathBuilder()
                    .addPath(new BezierLine(new Pose(35, 83), new Pose(73, 83)))
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                    .build();
        } else {
            scorePXX = follower.pathBuilder()
                    .addPath(new BezierLine(new Pose(35, 83),scorePoseBlue))
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(45))
                    .build();
        }
       //grab the second purple
        getXPX = follower.pathBuilder()
                .addPath(new BezierLine(new Pose(73,83),new Pose(42,83)))
                .setLinearHeadingInterpolation(Math.toRadians(135),Math.toRadians(180))
                .addPath(new BezierLine(new Pose(42,38),new Pose(30,83)))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
        //score the second purple
        if(isRed) {
            scoreXPX = follower.pathBuilder()
                    .addPath(new BezierLine(new Pose(30, 83), new Pose(73, 83)))
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                    .build();
        } else {
            scoreXPX = follower.pathBuilder()
                    .addPath(new BezierLine(new Pose(30,83),scorePoseBlue))
                    .setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(45))
                    .build();
        }
        //grab the last green
        getXXG = follower.pathBuilder()
                .addPath(new BezierLine(new Pose(73,83),new Pose(42,83)))
                .setLinearHeadingInterpolation(Math.toRadians(135),Math.toRadians(180))
                .addPath(new BezierLine(new Pose(42,83),new Pose(25,83)))
                .build();

        if(isRed){
            scoreXXG = follower.pathBuilder()
                    .addPath(new BezierLine(new Pose(25,83),new Pose(73,83)))
                    .setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(135))
                    .build();
        } else {
            scoreXXG = follower.pathBuilder()
                    .addPath(new BezierLine(new Pose(25,83),scorePoseBlue))
                    .setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(45))
                    .build();
        }
    }
    public void buildPathsPGP(){
        //grab the first purple
        getPGX = follower.pathBuilder()
                .addPath(new BezierLine(new Pose(73, PGPy),new Pose(42, PGPy)))
                .setLinearHeadingInterpolation(Math.toRadians(135),Math.toRadians(180))
                .addPath(new BezierLine(new Pose(42, PGPy),new Pose(35, PGPy)))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
        //score the first purple
        if(isRed){
            scorePGX = follower.pathBuilder()
                    .addPath(new BezierLine(new Pose(35, PGPy),new Pose(73,83)))
                    .setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(135))
                    .build();
        } else {
            scorePGX = follower.pathBuilder()
                    .addPath(new BezierLine(new Pose(35, PGPy),scorePoseBlue))
                    .setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(45))
                    .build();
        }
        //grab the second purple
        getXGX = follower.pathBuilder()
                .addPath(new BezierLine(new Pose(73, PGPy),new Pose(42, PGPy)))
                .setLinearHeadingInterpolation(Math.toRadians(135),Math.toRadians(180))
                .addPath(new BezierLine(new Pose(42, PGPy),new Pose(30, PGPy)))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
        //score the second purple
        if(isRed){
            scoreXGX = follower.pathBuilder()
                    .addPath(new BezierLine(new Pose(30, PGPy),new Pose(73,83)))
                    .setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(135))
                    .build();
        } else {
            scoreXGX = follower.pathBuilder()
                    .addPath(new BezierLine(new Pose(30, PGPy),scorePoseBlue))
                    .setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(45))
                    .build();
        }
        //grab the last green
        getXXP = follower.pathBuilder()
                .addPath(new BezierLine(new Pose(73, PGPy),new Pose(42,83)))
                .setLinearHeadingInterpolation(Math.toRadians(135),Math.toRadians(180))
                .addPath(new BezierLine(new Pose(42,83),new Pose(25,83)))
                .build();
       if(isRed){
           scoreXXP = follower.pathBuilder()
                   .addPath(new BezierLine(new Pose(25,83),scorePoseRed))
                   .setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(135))
                   .build();
       } else {
           scoreXXP = follower.pathBuilder()
                   .addPath(new BezierLine(new Pose(25,83),scorePoseBlue))
                   .setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(45))
                   .build();
       }
    }
    public void buildPathsGPP(){
        //grab the first purple
        getGXP = follower.pathBuilder()
                .addPath(new BezierLine(new Pose(73, GPPy),new Pose(42, GPPy)))
                .setLinearHeadingInterpolation(Math.toRadians(135),Math.toRadians(180))
                .addPath(new BezierLine(new Pose(42, GPPy),new Pose(35, GPPy)))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
        //score the first purple
        if(isRed){
            scoreGXX = follower.pathBuilder()
                    .addPath(new BezierLine(new Pose(35, GPPy),new Pose(73,83)))
                    .setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(135))
                    .build();
        } else {
            scoreGXX = follower.pathBuilder()
                    .addPath(new BezierLine(new Pose(35, GPPy),scorePoseBlue))
                    .setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(45))
                    .build();
        }
        //grab the second purple
        getGPX = follower.pathBuilder()
                .addPath(new BezierLine(new Pose(73, GPPy),new Pose(42, GPPy)))
                .setLinearHeadingInterpolation(Math.toRadians(135),Math.toRadians(180))
                .addPath(new BezierLine(new Pose(42, GPPy),new Pose(30, GPPy)))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
        //score the second purple
        if(isRed){
            scoreGPX = follower.pathBuilder()
                    .addPath(new BezierLine(new Pose(30, GPPy),new Pose(73,83)))
                    .setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(135))
                    .build();
        } else {
            scoreGPX = follower.pathBuilder()
                    .addPath(new BezierLine(new Pose(30, GPPy),scorePoseBlue))
                    .setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(45))
                    .build();
        }
        //grab the last green
        getGXP = follower.pathBuilder()
                .addPath(new BezierLine(new Pose(73, GPPy),new Pose(42, GPPy)))
                .setLinearHeadingInterpolation(Math.toRadians(135),Math.toRadians(180))
                .addPath(new BezierLine(new Pose(42, GPPy),new Pose(25, GPPy)))
                .build();
        if(isRed){
            scoreGXP = follower.pathBuilder()
                    .addPath(new BezierLine(new Pose(25, GPPy),new Pose(73,83)))
                    .setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(135))
                    .build();
        } else {
            scoreGXP = follower.pathBuilder()
                    .addPath(new BezierLine(new Pose(25, GPPy),scorePoseBlue))
                    .setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(45))
                    .build();
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