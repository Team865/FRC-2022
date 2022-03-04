package ca.warp7.frc2022.auton;

import ca.warp7.frc2022.auton.commands.*;
import ca.warp7.frc2022.commands.SingleFunctionCommand;
import ca.warp7.frc2022.subsystems.Launcher;
import ca.warp7.frc2022.subsystems.LauncherInterface;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

@SuppressWarnings("unused")
public class AutonomousMode {

    private static LauncherInterface launcher = Launcher.getInstance();

    public static Command shootBall() {
        return new AutoLauncherCommand(true).withTimeout(2.5).deadlineWith(
            new AutoFeedCommand(() -> true)
        );
    }

    public static Command shootMoveBack(){
        return new SequentialCommandGroup(
            SingleFunctionCommand.getResetAutonomousDrive(),
            new RobotStateCommand(new Pose2d(2, 0 , new Rotation2d())),
            new SequentialCommandGroup(
                shootBall(),
                AutonomousPath.moveBack()
            )
        );
    }
}