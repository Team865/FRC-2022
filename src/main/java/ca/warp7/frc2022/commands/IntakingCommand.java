package ca.warp7.frc2022.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.DoubleSupplier;

import ca.warp7.frc2022.subsystems.Intake;


public class IntakingCommand extends CommandBase{
    private DoubleSupplier intakingSupplier;
    private Intake intake = Intake.getInstance();
        
    public IntakingCommand(DoubleSupplier intakingSupplier) {
        this.intakingSupplier = intakingSupplier;
        addRequirements(intake);
    }
    public static IntakingCommand fullPower() {
        return new IntakingCommand(() -> 1.0);
    }

    public static IntakingCommand neutral() {
        return new IntakingCommand(() -> 0.0);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double intakeSpeed = intakingSupplier.getAsDouble();
        boolean intaking = intakeSpeed != 0.0;
        intake.setExtended(intaking);
        if (intaking) {
            intake.setSpeed(0.20 * intakeSpeed);
        } else
            intake.setSpeed(0.0);
    }
}