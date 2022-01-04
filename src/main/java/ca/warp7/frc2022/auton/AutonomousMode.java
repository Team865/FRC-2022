package ca.warp7.frc2022.auton;

import ca.warp7.frc2022.auton.commands.*;
import ca.warp7.frc2022.commands.SingleFunctionCommand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

@SuppressWarnings("unused")
public class AutonomousMode {
    public static Command circlePath(){
        return new SequentialCommandGroup(
                SingleFunctionCommand.getResetAutonomousDrive(),
                new RobotStateCommand(AutonomousPath.kOrigin),
                AutonomousPath.getCircle()
        );
    }
}