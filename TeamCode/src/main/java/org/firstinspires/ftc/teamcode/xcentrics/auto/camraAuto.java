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
        commandScheduler.getInstance().run();
        stateMachene();

    }
    private void stateMachene(){

        switch(state){
          case -1:
            //read the april tag to get the pattren
            camera.useCamera(true);
            //run the command scheduler
            commandScheduler.getInstance().run();
            //build the paths based on the pattren detected
            if(camera.foundID() == 21){
                paths.buildPathsPPG();
                incrament();
                break;
            } else if(camera.foundID() == 22){
                paths.buildPathsPGP();
                incrament();
                break;
            } else if(camera.foundID() == 23){
                paths.buildPathsGPP();
                incrament();
                break;
            }
        case 2:
            //follow the paths based on the pattren detected
            follower.followPath(paths.getBXX);
            intake.intake();
            incrament();
            break;
        case 3:
            //go to score pose
            if(!follower.isBusy()){
                follower.followPath(paths.scoreBXX);
                launcher.setSpeed(1000);
                incrament();
                break;
            }
        case 4:
        //launch ball
            if((!follower.isBusy()&&launcher.canLaunch())){
                launcher.launch();
                intake.stopIntake();
                incrament();
                break;
            }
        case 5:
        //get the second ball
         follower.followPath(paths.getXBX);
         intake.intake();
         incrament();
         break;
        case 6:
        //go to score pose
        if(!follower.isBusy()){
            follower.followPath(paths.scoreXBX);
            launcher.setSpeed(1000);
            incrament();
            break;
        }
        case 7:
        //score second ball
        if((!follower.isBusy()&&launcher.canLaunch())){
            launcher.launch();
            intake.stopIntake();
            incrament();
            break;
        }
        case 8:
        //get the third ball
        follower.followPath(paths.getXXB);
        intake.intake();
        incrament();
        break;
        case 9:
        //go to score pose
        if(!follower.isBusy()){
            follower.followPath(paths.scoreXXB);
            launcher.setSpeed(1000);
            incrament();;
            break;
        }
        case 10:
        //score the third ball 
        if((!follower.isBusy()&&launcher.canLaunch())){
            launcher.launch();
            intake.stopIntake();
            incrament();
            break;
        }
        case 11:
        //move out of start zone
        follower.followPath(paths.getXBX);
        intake.stopIntake();
        launcher.setSpeed(0);)
        break;
    }
    }
    private void incrament(){
        state++;
    }
    private void setState(int i){
        state = i;
    }
}