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


    private static Command getShootCellsCommand(int numBalls) {
        return new SequentialCommandGroup(
            new AutoFeedCommand(() -> true).withTimeout(numBalls)
        );
    }

    public static Command shootBall() {
        return new AutoLauncherCommand(true).withTimeout(2.5).deadlineWith(
            new AutoFeedCommand(() -> true)
        );
    }

    public static Command shoot_taxi() {
        return new SequentialCommandGroup(
            SingleFunctionCommand.getResetAutonomousDrive(),
            new RobotStateCommand(new Pose2d()),
            getShootCellsCommand(1),
            AutonomousPath.OneMeterForward()
        );
    }

    public static Command closestToHanger(){
        return new SequentialCommandGroup(
            SingleFunctionCommand.getResetAutonomousDrive(),
            SingleFunctionCommand.getZeroYaw(),
            new RobotStateCommand(AutonomousPath.kStarting),
            SingleFunctionCommand.runIntake(),
            AutonomousPath.getSingleBall(),
            SingleFunctionCommand.stopIntake(),
            AutonomousPath.returnFromSingleBall(),
            new VisionAlignCommand(() -> 0).withTimeout(0.75),
            SingleFunctionCommand.startLauncher(),
            new WaitCommand(0.5),
            getShootCellsCommand(1).withTimeout(0.75),
            new WaitCommand(0.5),
            getShootCellsCommand(1).withTimeout(1.25),
            SingleFunctionCommand.stopLauncher()
            // AutonomousPath.getOpponentTrechTwoBallsToShoot()
            // SingleFunctionCommand.startLauncher()
            // new WaitCommand(0.5),
            // getShootCellsCommand(1),
            // SingleFunctionCommand.stopLauncher(),
            // AutonomousPath.FirstBallLocation()

            // SingleFunctionCommand.getResetAutonomousDrive(),
            // new RobotStateCommand(new Pose2d(5, 0 , new Rotation2d())),
            // SingleFunctionCommand.setLauncherHigh(),
            // SingleFunctionCommand.startLauncher(),
            // new WaitCommand(1.5),
            // getShootCellsCommand(1),
            // SingleFunctionCommand.stopLauncher(),
            // AutonomousPath.moveBack(),
            // QuickTurnCommand.ofFieldOrientedAngle(Rotation2d.fromDegrees(90)).withTimeout(2)

            // SingleFunctionCommand.getResetAutonomousDrive(),
            // new RobotStateCommand(new Pose2d(5, 0 , new Rotation2d())),
            // SingleFunctionCommand.setLauncherHigh(),
            // SingleFunctionCommand.startLauncher(),
            // new WaitCommand(1.5),
            // getShootCellsCommand(1),
            // SingleFunctionCommand.stopLauncher(),
            // AutonomousPath.moveToCalculatedPoint(),
            // SingleFunctionCommand.runIntake(),
            // QuickTurnCommand.ofFieldOrientedAngle(Rotation2d.fromDegrees(90)).withTimeout(2),
            // new RobotStateCommand(new Pose2d(2.984, 0 , new Rotation2d(90))),
            // AutonomousPath.moveToBall(),
            // new WaitCommand(1),
            // SingleFunctionCommand.stopIntake(),
            // AutonomousPath.moveBackToCalculatedPoint(),
            // QuickTurnCommand.ofFieldOrientedAngle(Rotation2d.fromDegrees(0)).withTimeout(2),
            // new RobotStateCommand(new Pose2d(2.984, 0 , new Rotation2d(0))),
            // AutonomousPath.moveBackToStart(),
            // SingleFunctionCommand.startLauncher(),
            // new WaitCommand(1.5),
            // getShootCellsCommand(1)
        );
    }

    public static Command shootMoveBack(){
        return new SequentialCommandGroup(
            SingleFunctionCommand.getResetAutonomousDrive(),
            new RobotStateCommand(new Pose2d(3, 0 , new Rotation2d())),
            new SequentialCommandGroup(
                SingleFunctionCommand.setUseAutoSpeed(true),
                SingleFunctionCommand.startLauncher(),
                new WaitCommand(1),
                getShootCellsCommand(1).withTimeout(1.25),
                SingleFunctionCommand.stopLauncher(),
                new WaitCommand(6),
                AutonomousPath.moveBack(),
                SingleFunctionCommand.setUseAutoSpeed(false)

            )
        );
    }

    
}