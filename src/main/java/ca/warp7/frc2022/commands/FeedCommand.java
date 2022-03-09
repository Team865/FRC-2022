package ca.warp7.frc2022.commands;

import edu.wpi.first.wpilibj.Timer;
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
    private double pTime = -1.0;
    double speedWithoutIntake;
    double speedWithIntake;

    private DoubleSupplier speedSupplier;

    public FeedCommand(DoubleSupplier speedSupplier) {
        this.speedSupplier = speedSupplier;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        pTime = -1.0;
    }

    @Override
    public void execute() {
        boolean lowBeamBreak = Elevator.getLowBeamBreak();
        boolean highBeamBreak = Elevator.getHighBeamBreak();
        double feedSpeed = speedSupplier.getAsDouble();
        double time = Timer.getFPGATimestamp();

        speedWithoutIntake = 0.6 * feedSpeed;

        if (lowBeamBreak && !highBeamBreak) {
            speedWithIntake = 0.3;
            pTime = time;
        }

        if (highBeamBreak) {
            if (time - pTime < 0.40 && pTime >= 0) {
                speedWithIntake = 0.2;
            } else {
                speedWithIntake = 0.0;
            }
        }

        if (Math.abs(speedWithoutIntake) > speedWithIntake) {
            elevator.setSpeed(speedWithoutIntake);
        } else if (speedWithIntake > Math.abs(speedWithoutIntake)) {
            elevator.setSpeed(speedWithIntake);
            intake.setSpeedFromElevator(speedWithIntake);      
        } else {
            elevator.setSpeed(0.0);
            intake.setSpeedFromElevator(0.0);
        }
            
    }

    @Override
    public void end(boolean interrupted) {
        elevator.setSpeed(0.0);
    }
}