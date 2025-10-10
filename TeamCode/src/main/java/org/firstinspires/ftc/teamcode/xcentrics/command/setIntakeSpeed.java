package org.firstinspires.ftc.teamcode.xcentrics.command;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.xcentrics.subsystem.Intake;

public class setIntakeSpeed extends CommandBase {
    private final Intake intake;
    double speed;
    public setIntakeSpeed(Intake intake, double speed){
        this.intake = intake;
        this.speed = speed;
        addRequirements(intake);
    }
    @Override
    public void initialize(){
        intake.setPower(speed);
    }
    @Override
    public boolean isFinished(){
        return true;
    }


}
