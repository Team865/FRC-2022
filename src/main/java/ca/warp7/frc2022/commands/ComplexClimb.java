package ca.warp7.frc2022.commands;

import ca.warp7.frc2022.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ComplexClimb{
    
    public static Command toggleBigPiston() {
        Climber climber = Climber.getInstance();
        return new InstantCommand(climber::toggleBigPiston);
    }

    public static Command toggleSmallPiston() {
        Climber climber = Climber.getInstance();
        return new InstantCommand(climber::toggleSmallPiston);
    }

    public static Command setBigPiston(boolean set) {
        Climber climber = Climber.getInstance();
        return new InstantCommand(() -> climber.setBigPiston(set));
    }

    public static Command setSmallPiston(boolean set) {
        Climber climber = Climber.getInstance();
        return new InstantCommand(() -> climber.setSmallPiston(set));
    }

    public static Command setClimbPosition(double set) {
        Climber climber = Climber.getInstance();
        return new InstantCommand(() -> climber.setClimberToPosition(set));
    }

    public static Command setClimbPositionRelativeOfMax(double set) {
        Climber climber = Climber.getInstance();
        double softMax = climber.getSoftMax();
        return new InstantCommand(()-> climber.setClimberToPosition(softMax + set));
    }

    public static Command disableLimitSwitch(boolean disable) {
        Climber climber = Climber.getInstance();
        return new InstantCommand(()-> climber.setDisableLimitSwitch(disable));
    }
}
