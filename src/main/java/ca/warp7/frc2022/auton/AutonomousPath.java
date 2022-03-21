package ca.warp7.frc2022.auton;

import ca.warp7.frc2022.Constants;
import ca.warp7.frc2022.auton.commands.DriveTrajectoryCommand;
import ca.warp7.frc2022.auton.commands.RobotStateCommand;
import ca.warp7.frc2022.auton.pathfollower.RamseteFollower;
import ca.warp7.frc2022.lib.trajectory.TimedPath2d;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveKinematicsConstraint;
import edu.wpi.first.math.trajectory.constraint.TrajectoryConstraint;
import edu.wpi.first.wpilibj2.command.Command;

@SuppressWarnings({"unused", "Unused"})
public class AutonomousPath {

    public static final DifferentialDriveKinematics kKinematics =
            new DifferentialDriveKinematics(Constants.kWheelBaseRadius * 2);

    public static final TrajectoryConstraint kKinematicsConstraint =
            new DifferentialDriveKinematicsConstraint(kKinematics, 10.0);

    public static TrajectoryConfig createTrajectoryConfig() {
        return new TrajectoryConfig(2.2, 2.2)
                .addConstraint(kKinematicsConstraint);
    }
    

    public static final Pose2d kStarting =
        new Pose2d(0.0, 0.0, new Rotation2d());

    public static final Pose2d kTeleopStart =
        new Pose2d(0.0, 0.0, new Rotation2d());

    public static Command moveBack() {
        return new TimedPath2d("Shoot then move back.", new Pose2d(5, 0, new Rotation2d()))
            .addPoint(2.05, 0, 0)
            .setConfig(createTrajectoryConfig())
            .setReversed(true)
            .setFollower(new RamseteFollower())
            .convertTo(DriveTrajectoryCommand::new);
    }
    public static Command moveBackfromback() {
        return new TimedPath2d("move back.", new Pose2d(2.05, 0, new Rotation2d()))
            .addPoint(2.65, 0, 0)
            .setConfig(createTrajectoryConfig())
            .setReversed(false)
            .setFollower(new RamseteFollower())
            .convertTo(DriveTrajectoryCommand::new);
    }
    public static Command grabBall() {
        return new TimedPath2d("move back.", new Pose2d(0.32, 0, new Rotation2d()))
            .addPoint(0.4, 0, 0)
            .setConfig(createTrajectoryConfig())
            .setReversed(false)
            .setFollower(new RamseteFollower())
            .convertTo(DriveTrajectoryCommand::new);
    }
    public static Command returnFromBall() {
        return new TimedPath2d("move back.", new Pose2d(2.65, 0, new Rotation2d()))
            .addPoint(4.5, 0, 0)
            .setConfig(createTrajectoryConfig())
            .setReversed(false)
            .setFollower(new RamseteFollower())
            .convertTo(DriveTrajectoryCommand::new);
    }

}