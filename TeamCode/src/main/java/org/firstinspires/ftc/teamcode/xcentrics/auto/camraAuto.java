package org.firstinspires.ftc.teamcode.xcentrics.auto;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.xcentrics.paths.AutoPaths;
import org.firstinspires.ftc.teamcode.xcentrics.subsystem.Camera;
import org.firstinspires.ftc.teamcode.xcentrics.subsystem.Intake;
import org.firstinspires.ftc.teamcode.xcentrics.subsystem.Launcher;

@Autonomous(name = "auto")
public class camraAuto extends OpMode{
    private Camera camera;
    private Follower follower;
    private PanelsTelemetry panelsTelemetry;
    private Intake intake;
    private Launcher launcher;
    private AutoPaths paths;
    private int state = -1;

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        camera = new Camera(hardwareMap,"camera",follower,telemetry);
        intake = new Intake(hardwareMap,"intake");
        launcher = new Launcher(hardwareMap,"launcher");
        paths = new AutoPaths(follower);
        
    }

    @Override
    public void loop() {
        follower.update();

    }
    private void stateMachene(){
        switch(state){
            case 0:
            follower.turnToDegrees(90);
            camera.useCamera(true);
            if(camera.foundID() == 21){
                paths.buildPathsGPP();
                incrament();
                break;
            } else if(camera.foundID()==22){
                paths.buildPathsPGP();
                incrament();
                break;
            } else if(camera.foundID() == 23){
                paths.buildPathsPPG();
                incrament();
                break;
            }
            
            case 1:
            
            follower.followPath(paths.getBXX);
            intake.intake();
            incrament();
            break;
            
            case 2:
            if(!follower.isbusy()){
                follower.followPath(paths.scoreBXX);
                incrament();
                break;
            }
            
            case 3:
            if(!follower.isbusy()){
                if(launcher.canLaunch()){
                    intake.shoot();;
                }
            }
            incrament();
            break;
            
            case 4:
            follower.followPath(paths.getXBX);
            intake.intake();
            incrament();
            break;

            case 5:
            if(!follower.isbusy()){
                follower.followPath(paths.scoreXBX);
                incrament();
                break;
            }
            
        }
    }
    private void incrament(){
        state++;
    }
    private void setState(int i){
        state = i;
    }
}