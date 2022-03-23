package ca.warp7.frc2022.commands;

import ca.warp7.frc2022.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class ClimbCommand extends CommandBase {
    private DoubleSupplier speedSupplier;
    private BooleanSupplier firstPiston;
    private BooleanSupplier secondPiston;
    private Climber climber = Climber.getInstance();

    public ClimbCommand(DoubleSupplier speedSupplier, BooleanSupplier firstPiston, BooleanSupplier secondPiston) {
        this.speedSupplier = speedSupplier;
        this.firstPiston = firstPiston;
        this.secondPiston = secondPiston;
        addRequirements(climber);
    }



    @Override
    public void execute() {
        climber.setSpeed(speedSupplier.getAsDouble());
        climber.setFirstPiston(firstPiston.getAsBoolean());
        climber.setSecondPiston(secondPiston.getAsBoolean());
    }
}

