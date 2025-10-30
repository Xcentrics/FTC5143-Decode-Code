package org.firstinspires.ftc.teamcode.xcentrics.TeleOp.debug;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.xcentrics.subsystem.Launcher;

@Configurable
@TeleOp(name = "flyTuning", group = "Tuning")
public class flyTuning extends OpMode {
    Launcher launcher;

    @Override
    public void init() {
        launcher = new Launcher(hardwareMap, "launcher");
    }

    @Override
    public void loop() {
    launcher.periodic();
        PanelsTelemetry.INSTANCE.getTelemetry().debug(launcher.fly.getVelocity());
        telemetry.addData("fly", launcher.fly.getVelocity());
        telemetry.update();
        PanelsTelemetry.INSTANCE.getTelemetry().update();
    }
}
