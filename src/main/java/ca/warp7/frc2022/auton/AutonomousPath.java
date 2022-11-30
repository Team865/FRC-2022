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
            new DifferentialDriveKinematicsConstraint(kKinematics, 2);

    public static TrajectoryConfig createTrajectoryConfig() {
        return new TrajectoryConfig(2, 1)
                .addConstraint(kKinematicsConstraint);
    }
    

    public static final Pose2d kStarting =
        new Pose2d(6.687, 1.571, Rotation2d.fromDegrees(135));

    public static final Pose2d kFirstBallMidPoint =
        new Pose2d(5.946, 2.079, Rotation2d.fromDegrees(180));

    public static final Pose2d kFirstBall =
        new Pose2d(4.5, 2.079, Rotation2d.fromDegrees(180));

    public static final Pose2d kHangerBallShoot =
        new Pose2d(6.514, 0.808, Rotation2d.fromDegrees(155));

    public static final Pose2d kOpponentTrench2 =
        new Pose2d(6.781, 2.666, new Rotation2d());

    public static final Pose2d kLeftInitLine =
        new Pose2d(6.622,  2.553, new Rotation2d());

    public static final Pose2d kCentreFieldshoot =
        new Pose2d(3.1, 0.0, Rotation2d.fromDegrees(-28));


    public static Command moveToCalculatedPoint() {
        return new TimedPath2d("Move to calculated mid point for ball", new Pose2d(5,0,new Rotation2d()))
            .addPoint(2.984, 0, 0)
            .setConfig(createTrajectoryConfig())
            .setReversed(true)
            .setFollower(new RamseteFollower())
            .convertTo(DriveTrajectoryCommand::new);
    }

    public static Command moveToBall(){
        return new TimedPath2d("Move to calculated mid point for ball", new Pose2d(2.984,0,new Rotation2d(90)))
        .addPoint(3.75, 0, 0)
        .setConfig(createTrajectoryConfig())
        .setFollower(new RamseteFollower())
        .convertTo(DriveTrajectoryCommand::new);
    }

    public static Command moveBackToCalculatedPoint(){
        return new TimedPath2d("Move to calculated mid point for ball", new Pose2d(3.75,0,new Rotation2d(90)))
        .addPoint(2.984, 0, 0)
        .setConfig(createTrajectoryConfig())
        .setReversed(true)
        .setFollower(new RamseteFollower())
        .convertTo(DriveTrajectoryCommand::new);
    }

    public static Command moveBackToStart(){
        return new TimedPath2d("Move to calculated mid point for ball", new Pose2d(2.984,0,new Rotation2d()))
        .addPoint(5.0, 0, 0)
        .setConfig(createTrajectoryConfig())
        .setFollower(new RamseteFollower())
        .convertTo(DriveTrajectoryCommand::new);
    }

    public static Command moveBack() {
        return new TimedPath2d("Shoot \then move back.", new Pose2d(3, 0, new Rotation2d()))
            .addPoint(5, 0, 0)
            .setConfig(createTrajectoryConfig())
            .setReversed(false)
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


    public static Command OneMeterForward() {
        return new TimedPath2d("Move One Meter Forward", new Pose2d())
            .addPoint(1, 0 ,0)
            .setConfig(createTrajectoryConfig())
            .setReversed(false)
            .setFollower(new RamseteFollower())
            .convertTo(DriveTrajectoryCommand::new);
    }

    public static Command FirstBallLocation() {
        return new TimedPath2d("Move One Meter Forward", new Pose2d(0  , 0, new Rotation2d(34)))
            .addPoint(1, 0, 0)
            .setConfig(createTrajectoryConfig())
            .setReversed(false)
            .setFollower(new RamseteFollower())
            .convertTo(DriveTrajectoryCommand::new);
    }

    public static Command getOpponentTrenchTwoBalls() {
        return new TimedPath2d("opponent trench two balls", kLeftInitLine)
                .addPoint(kOpponentTrench2)
                .setConfig(createTrajectoryConfig())
                .setFollower(new RamseteFollower())
                .convertTo(DriveTrajectoryCommand::new);
    }

    public static Command getOpponentTrechTwoBallsToShoot() {
        return new TimedPath2d("OpponentTrechTwoBallsToShoot", kOpponentTrench2)
                .addPoint(kCentreFieldshoot)
                .setConfig(createTrajectoryConfig())
                .setReversed(true)
                .setFollower(new RamseteFollower())
                .convertTo(DriveTrajectoryCommand::new);
    }

    public static Command getSingleBall() {
        return new TimedPath2d("Get Single Ball", kStarting)
            .addPoint(kFirstBall)
            .setConfig(createTrajectoryConfig())
            .setFollower(new RamseteFollower())
            .convertTo(DriveTrajectoryCommand::new);
    }

    public static Command returnFromSingleBall() {
        return new TimedPath2d("return from single ball", kFirstBall)
            .addPoint(kHangerBallShoot)
            .setConfig(createTrajectoryConfig())
            .setReversed(true)
            .setFollower(new RamseteFollower())
            .convertTo(DriveTrajectoryCommand::new);
    }
}