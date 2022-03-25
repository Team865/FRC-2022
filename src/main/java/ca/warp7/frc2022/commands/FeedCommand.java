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
    private double pTime = -1.0;
    double speedWithoutIntake;
    double manualSpeedWithIntake;
    double autoSpeedWithIntake;
    double finalSpeedWithIntake;

    private DoubleSupplier speedSupplier;
    private DoubleSupplier speedWithIntakeSupplier;

    public FeedCommand(DoubleSupplier speedSupplier, DoubleSupplier speedWithIntakeSupplier) {
        this.speedSupplier = speedSupplier;
        this.speedWithIntakeSupplier = speedWithIntakeSupplier;
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
        double feedSpeedWithIntake = speedWithIntakeSupplier.getAsDouble();
        double time = Timer.getFPGATimestamp();

        speedWithoutIntake = 0.3 * feedSpeed;
        manualSpeedWithIntake = 0.3 * feedSpeedWithIntake;

        if (lowBeamBreak && !highBeamBreak) {
            autoSpeedWithIntake = 0.3;
            pTime = time;
        }

        if (highBeamBreak) {
            if (time - pTime < 0.3 && pTime >= 0) {
                autoSpeedWithIntake = 0.3;
            } else {
                autoSpeedWithIntake = 0.0;
            }
        }

        if (autoSpeedWithIntake > Math.abs(manualSpeedWithIntake)) {
            finalSpeedWithIntake = autoSpeedWithIntake;
        } else if (autoSpeedWithIntake < Math.abs(manualSpeedWithIntake)) {
            finalSpeedWithIntake = manualSpeedWithIntake;
        } else {
            finalSpeedWithIntake = 0;
        }

        if (Math.abs(finalSpeedWithIntake) >= Math.abs(speedWithoutIntake)) {
            elevator.setSpeed(finalSpeedWithIntake);
            intake.setSpeedFromElevator(finalSpeedWithIntake);
        } else if (Math.abs(finalSpeedWithIntake) < Math.abs(speedWithoutIntake)) {
            elevator.setSpeed(speedWithoutIntake);
        } else {
            elevator.setSpeed(0);
            intake.setSpeedFromElevator(0);
        }            
    }

    @Override
    public void end(boolean interrupted) {
        elevator.setSpeed(0.0);
    }
}