package org.firstinspires.ftc.teamcode.xcentrics.paths;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;

public class redAutoPaths {
    private final Pose startPose = new Pose(72,120,Math.toRadians(90));
    private final Pose scorePose = new Pose();
    private final Pose PPGPose = new Pose();
    private final Pose PGPPose = new Pose();
    private final Pose GPPPose = new Pose();

    private PathChain getPPG;
    private PathChain scorePPG;
    private PathChain getPGP;
    private Pathchain scoreGPG;
    private PathChain getGPP;
    private PathChain scoreGPP;
}
