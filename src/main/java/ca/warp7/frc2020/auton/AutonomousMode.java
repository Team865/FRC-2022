package ca.warp7.frc2020.auton;

import ca.warp7.frc2020.auton.commands.*;
import ca.warp7.frc2020.commands.SingleFunctionCommand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

@SuppressWarnings("unused")
public class AutonomousMode {
    public static Command driveLowGearCharacterization() {
        return new SequentialCommandGroup(
                SingleFunctionCommand.getResetAutonomousDrive(),
                SingleFunctionCommand.getStopCompressor(),
                new DriveCharacterizationCommand()
        );
    }

    public static Command driveHighGearCharacterization() {
        return new SequentialCommandGroup(
                SingleFunctionCommand.getResetAutonomousDrive(),
                SingleFunctionCommand.getSetDriveHighGear(),
                SingleFunctionCommand.getStopCompressor(),
                new DriveCharacterizationCommand()
        );
    }

    public static Command simplePath() {
        return new SequentialCommandGroup(
                SingleFunctionCommand.getResetAutonomousDrive(),
                new RobotStateCommand(AutonomousPath.kOrigin),
                AutonomousPath.getOneMetreForward()
        );
    }

    public static Command squarePath(){
        return new SequentialCommandGroup(
                SingleFunctionCommand.getResetAutonomousDrive(),
                new RobotStateCommand(AutonomousPath.kOrigin),
                AutonomousPath.getMetreDistanceForward(1.0)
        );
    }
}
