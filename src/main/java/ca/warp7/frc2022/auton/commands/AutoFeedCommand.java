package ca.warp7.frc2022.auton.commands;

import static ca.warp7.frc2022.Constants.*;
import ca.warp7.frc2022.subsystems.Elevator;
import ca.warp7.frc2022.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.BooleanSupplier;

public class AutoFeedCommand extends CommandBase {
    private Elevator elevator = Elevator.getInstance();
    private Intake intake = Intake.getInstance();

    private BooleanSupplier shootingSupplier;

    public AutoFeedCommand(BooleanSupplier shootingSupplier) {
        this.shootingSupplier = shootingSupplier;
        addRequirements(elevator);
    }

    @Override
    public void execute() {
        boolean isShooting = shootingSupplier.getAsBoolean();

        if (isShooting) {
            elevator.setSpeed(kAutoFeedSpeed);
            intake.setSpeedFromElevator(kAutoFeedSpeed);
        } else {
            elevator.setSpeed(0.0);
            intake.setSpeedFromElevator(0.0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        elevator.setSpeed(0.0);
        intake.setSpeedFromElevator(0.0);
    }
}