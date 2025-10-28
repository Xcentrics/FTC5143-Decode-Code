package org.firstinspires.ftc.teamcode.xcentrics.auto;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.xcentrics.paths.AutoPathsRed;
import org.firstinspires.ftc.teamcode.xcentrics.subsystem.Camera;
import org.firstinspires.ftc.teamcode.xcentrics.subsystem.Intake;
import org.firstinspires.ftc.teamcode.xcentrics.subsystem.Launcher;

@Autonomous(name = "auto")
@Configurable
public class cameraAuto extends OpMode{
    private Camera camera;
    private Follower follower;
    private PanelsTelemetry panelsTelemetry;
    private Intake intake;
    private Launcher launcher;
    private AutoPathsRed paths;
    private int state = -1;
    private boolean isRed = false;

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        camera = new Camera(hardwareMap,"camera",follower,telemetry);
        intake = new Intake(hardwareMap,"intake");
        launcher = new Launcher(hardwareMap,"launcher");
        paths = new AutoPathsRed(follower);
        
    }

    @Override
    public void loop() {
        follower.update();
        CommandScheduler.getInstance().run();
        stateMachene();

    }
    private void stateMachene(){

        switch(state){
          case -1:
            //read the april tag to get the pattren
            camera.useCamera(true);
            //run the command scheduler
            CommandScheduler.getInstance().run();
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
            CommandScheduler.getInstance().run();
            incrament();
            break;
        case 3:
            //go to score pose
            if(!follower.isBusy()){
                follower.followPath(paths.scoreBXX);
                CommandScheduler.getInstance().run();
                launcher.setSpeed(1000);
                incrament();
                break;
            }
        case 4:
        //launch ball
            if((!follower.isBusy()&&launcher.canLaunch())){
                CommandScheduler.getInstance().run();
                launcher.launch();
                CommandScheduler.getInstance().run();
                intake.stopIntake();
                CommandScheduler.getInstance().run();
                incrament();
                break;
            }
        case 5:
        //get the second ball
         follower.followPath(paths.getXBX);
         CommandScheduler.getInstance().run();
         intake.intake();
         incrament();
         break;
        case 6:
        //go to score pose
        if(!follower.isBusy()){
            follower.followPath(paths.scoreXBX);
            CommandScheduler.getInstance().run();
            launcher.setSpeed(1000);
            CommandScheduler.getInstance().run();
            incrament();
            break;
        }
        case 7:
        //score second ball
        if((!follower.isBusy()&&launcher.canLaunch())){
            CommandScheduler.getInstance().run();
            launcher.launch();
            CommandScheduler.getInstance().run();
            intake.stopIntake();
            CommandScheduler.getInstance().run();
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
            CommandScheduler.getInstance().run();
            launcher.setSpeed(1000);
            CommandScheduler.getInstance().run();
            incrament();;
            break;
        }
        case 10:
        //score the third ball 
        if((!follower.isBusy()&&launcher.canLaunch())){
            CommandScheduler.getInstance().run();
            launcher.launch();
            CommandScheduler.getInstance().run();
            intake.stopIntake();
            CommandScheduler.getInstance().run();
            incrament();
            break;
        }
        case 11:
        //move out of start zone
        follower.followPath(paths.getXBX);
            CommandScheduler.getInstance().run();
        intake.stopIntake();
            CommandScheduler.getInstance().run();
        launcher.setSpeed(0);
            CommandScheduler.getInstance().run();
        break;
    }
    }
    private void incrament(){
        CommandScheduler.getInstance().run();
        state++;
    }
    private void setState(int i){
        state = i;
    }
}