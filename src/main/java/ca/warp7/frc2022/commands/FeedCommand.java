package ca.warp7.frc2022.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static ca.warp7.frc2022.Constants.*;

import java.util.function.DoubleSupplier;
import ca.warp7.frc2022.subsystems.Elevator;
import ca.warp7.frc2022.subsystems.Intake;
import ca.warp7.frc2022.subsystems.Launcher;
import ca.warp7.frc2022.subsystems.LauncherInterface;

public class FeedCommand  extends CommandBase{
    private Intake intake = Intake.getInstance();
    private Elevator elevator = Elevator.getInstance();
    private LauncherInterface launcher = Launcher.getInstance();

    private DoubleSupplier speedSupplier;

    public FeedCommand(DoubleSupplier speedSupplier) {
        this.speedSupplier = speedSupplier;
        addRequirements(elevator);
    }

    @Override
    public void execute() {
        boolean lowBeamBreak = Elevator.getLowBeamBreak();
        boolean highBeamBreak = Elevator.getHighBeamBreak();
        double feedSpeed = speedSupplier.getAsDouble();

        //if (feedSpeed != 0) {
            elevator.setSpeed(feedSpeed);
        //}
        
    }

    @Override
    public void end(boolean interrupted) {
        elevator.setSpeed(0.0);
    }
}