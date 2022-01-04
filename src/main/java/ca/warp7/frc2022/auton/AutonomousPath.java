package ca.warp7.frc2022.auton;

import ca.warp7.frc2022.Constants;
import ca.warp7.frc2022.auton.commands.DriveTrajectoryCommand;
import ca.warp7.frc2022.auton.pathfollower.RamseteFollower;
import ca.warp7.frc2022.lib.trajectory.TimedPath2d;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveKinematicsConstraint;
import edu.wpi.first.wpilibj.trajectory.constraint.TrajectoryConstraint;
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
    
    public static final Pose2d kOrigin =
            new Pose2d(0.0, 0.0, new Rotation2d());

    public static Command getCircle() {
        return new TimedPath2d("Circle movement.", new Pose2d())
                .addPoint(new Pose2d(1.25, -0.75, Rotation2d.fromDegrees(0.0)))
                .addPoint(new Pose2d(2.5, 0, Rotation2d.fromDegrees(90.0)))
                .addPoint(new Pose2d(1.25, 0.75, Rotation2d.fromDegrees(180.0)))
                .addPoint(new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(270)))
                .addPoint(new Pose2d(1.25, -0.75, Rotation2d.fromDegrees(0.0)))
                .setConfig(createTrajectoryConfig())
                .setFollower(new RamseteFollower())
                .convertTo(DriveTrajectoryCommand::new);
    }
}

