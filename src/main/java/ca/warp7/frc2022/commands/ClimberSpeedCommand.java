package ca.warp7.frc2022.commands;

import ca.warp7.frc2022.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.DoubleSupplier;

public class ClimberSpeedCommand extends CommandBase {
    private DoubleSupplier speedSupplier;
    private Climber climber = Climber.getInstance();

    public ClimberSpeedCommand(DoubleSupplier speedSupplier) {
        this.speedSupplier = speedSupplier;
        addRequirements(climber);
    }

    @Override
    public void execute() {
            climber.setSpeed(speedSupplier.getAsDouble());
    }
}

