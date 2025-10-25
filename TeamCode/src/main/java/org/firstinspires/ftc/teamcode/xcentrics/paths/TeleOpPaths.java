package org.firstinspires.ftc.teamcode.xcentrics.paths;

public class TeleOpPaths {
    private static Pose currentPose;
    private static Pose scorePoseRed = new Pose(12,0,Math.toRadians(0));
    private static Pose scorePoseBlue = new Pose(-12,0,Math.toRadians(180));
    private static PathChain score;

    Follower follower;

    //constructor
    public TeleOpPaths(Follower follower){
        this.follower = follower;
    }

    //build paths
    public void buildPaths(boolean isRed){
        currentPose = follower.getPoseEstimate();
        if(isRed){
            score = follower.pathBuilder()
            .addPath(new BezierLine(currentPose, scorePose))
            .setLinearHeadingInterpolation(currentPose.getHeading(), scorePose.getHeading())
            .build();
        } else {
            score = follower.pathBuilder()
            .addPath(new BezierLine(currentPose, scorePoseBlue))
            .setLinearHeadingInterpolation(currentPose.getHeading(), scorePoseBlue.getHeading())
            .build();
        }
        
    }

}
