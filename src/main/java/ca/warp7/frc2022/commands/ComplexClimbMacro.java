package ca.warp7.frc2022.commands;

import ca.warp7.frc2022.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ComplexClimbMacro extends SequentialCommandGroup{


    public ComplexClimbMacro() {
        addCommands(
            ComplexClimb.disableLimitSwitch(true),
            ComplexClimb.setSmallPiston(true),
            new WaitCommand(0.5),
            ComplexClimb.setBigPiston(false),
            new WaitCommand(0.4),
            ComplexClimb.setClimbPositionRelativeOfMax(-20),
            new WaitCommand(0.2),
            ComplexClimb.setBigPiston(true),
            new WaitCommand(1),
            ComplexClimb.disableLimitSwitch(false)
        );
    }
}
