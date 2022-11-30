package ca.warp7.frc2022.commands;

import ca.warp7.frc2022.subsystems.*;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import static ca.warp7.frc2022.Constants.*;


@SuppressWarnings("unused")
public class SingleFunctionCommand {
    public static Command setLauncherHigh() {
        LauncherInterface launcher = Launcher.getInstance();

        return new InstantCommand(() -> launcher.isHighGoal(true));
    }

    public static Command setLauncherLow() {
        LauncherInterface launcher = Launcher.getInstance();

        return new InstantCommand(() -> launcher.isHighGoal(false));
    }

    public static Command runIntake() {
        Intake intake = Intake.getInstance();

        return new InstantCommand(() -> {
            intake.setExtended(true);
            intake.setSpeedFromIntake(0.6);
        });
    }

    public static Command stopIntake() {
        Intake intake = Intake.getInstance();

        return new InstantCommand(() -> {
            intake.setExtended(false);
            intake.setSpeedFromIntake(0.0);
        });
    }

    public static Command startLauncher() {
        LauncherInterface launcher = Launcher.getInstance();
        return new InstantCommand(() -> {
            launcher.setRunLauncher(true);
        });
    }

    public static Command stopLauncher() {
        LauncherInterface launcher = Launcher.getInstance();
        return new InstantCommand(() -> {
            launcher.setRunLauncher(false);
        });

    }

    /**
     * Resets the drive train for auto driving. Make sure to call
     * at the start of all routines!
     */
    public static Command getResetAutonomousDrive() {
        DriveTrain driveTrain = DriveTrain.getInstance();
        return new InstantCommand(() -> {
            driveTrain.neutralOutput();
            driveTrain.configureRampRate(kLowGearRampRate);
            driveTrain.configurePID(kAutonLowGearVelocityPID);
            driveTrain.setBrake();
            driveTrain.setEncoderPosition(0, 0);
            driveTrain.setRobotState(new Pose2d());
            driveTrain.zeroYaw();
        });
    }

    public static Command getSetDriveBrakeMode() {
        DriveTrain driveTrain = DriveTrain.getInstance();
        return new InstantCommand(driveTrain::setBrake);
    }

    public static Command getDriveServo() {
        DriveTrain driveTrain = DriveTrain.getInstance();
        return new FunctionalCommand(
                () -> driveTrain.setEncoderPosition(0, 0),
                () -> driveTrain.setWheelPositionPID(0, 0),
                (interrupted) -> driveTrain.neutralOutput(),
                () -> false,
                driveTrain
        );
    }

    public static Command getZeroYaw() {
        DriveTrain driveTrain = DriveTrain.getInstance();
        return new InstantCommand(driveTrain::zeroYaw);
    }

    public static Command getStartCompressor() {
        Infrastructure infrastructure = Infrastructure.getInstance();
        return new InstantCommand(infrastructure::startCompressor);
    }

    public static Command getStopCompressor() {
        Infrastructure infrastructure = Infrastructure.getInstance();
        return new InstantCommand(infrastructure::stopCompressor);
    }

    public static Command toggleBigPiston() {
        Climber climber = Climber.getInstance();

        return new InstantCommand(() -> climber.toggleBigPiston());
    }

    public static Command toggleSmallPiston() {
        Climber climber = Climber.getInstance();

        return new InstantCommand(() -> climber.toggleSmallPiston());
    }

    public static Command toggleClimberOveride() {
        Climber climber = Climber.getInstance();
        return new InstantCommand(() -> climber.toggleLimitOveride());
    }

    public static Command setClimberToBar() {
        Climber climber = Climber.getInstance();

        return new InstantCommand(() -> climber.setClimberToPosition(kClimberRung));
    }

    public static Command limitSwitchTimeout(double delay) {
        Climber climber = Climber.getInstance();

        return new SequentialCommandGroup(
            new InstantCommand(() -> climber.setDisableLimitSwitch(true)),
            new WaitCommand(delay),
            new InstantCommand(() -> climber.setDisableLimitSwitch(false))
        );
    }


    public static Command toggleUseLimelightSpeed() {
        LauncherInterface launcher = Launcher.getInstance();
        
        return new InstantCommand(launcher::toggleUseLimelightSpeed);
    }

    public static Command setUseAutoSpeed(boolean use) {
        LauncherInterface launcher = Launcher.getInstance();
        
        return new InstantCommand(() -> launcher.setUseAutoSpeed(use));
    }

}
// Fingers locked on bar -> Actuate Big -> Extend hooks -> Actuate Big in ~3in -> Pull down on hooks until they engage -> Actuate Big out -> Release fingers -> Pull up with hooks