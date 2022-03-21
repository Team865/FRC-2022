package ca.warp7.frc2022.auton;

import ca.warp7.frc2022.auton.commands.*;
import ca.warp7.frc2022.commands.IntakingCommand;
import ca.warp7.frc2022.commands.SingleFunctionCommand;
import ca.warp7.frc2022.subsystems.Launcher;
import ca.warp7.frc2022.subsystems.LauncherInterface;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

@SuppressWarnings("unused")
public class AutonomousMode {

    private static LauncherInterface launcher = Launcher.getInstance();

    // public static Command shootBall() {
    //     return SingleFunctionCommand.startLauncher().withTimeout(2.5).deadlineWith(
    //         new AutoFeedCommand(() -> true)
    //     );
    // }

    public static Command shootBall() {
        return new AutoLauncherCommand(true).withTimeout(2.5).deadlineWith(
            new AutoFeedCommand(() -> true)
        );
    }

    public static Command shootMoveBack(){
        return new SequentialCommandGroup(
            SingleFunctionCommand.getResetAutonomousDrive(),
            new RobotStateCommand(new Pose2d(5, 0 , new Rotation2d())),
            new SequentialCommandGroup(
                shootBall(),
                AutonomousPath.moveBack(),
                new IntakingCommand(() -> 0.3).withTimeout(0.25),
                QuickTurnCommand.ofFieldOrientedAngle(Rotation2d.fromDegrees(90)).withTimeout(2),
                //AutonomousPath.grabBall(),
                AutonomousPath.moveBackfromback(),
                QuickTurnCommand.ofFieldOrientedAngle(Rotation2d.fromDegrees(0)),
                new IntakingCommand(() -> 0.0).withTimeout(0.25),
                AutonomousPath.returnFromBall()

            )
        );
    }
}