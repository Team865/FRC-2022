package ca.warp7.frc2022.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import ca.warp7.frc2022.Constants;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Subsystem;

@SuppressWarnings("unused")
public class Limelight implements Subsystem {

    private static Limelight instance;

    private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private NetworkTableEntry tv = table.getEntry("tv");
    private NetworkTableEntry tx = table.getEntry("tx");
    private NetworkTableEntry ty = table.getEntry("ty");
    private NetworkTableEntry tl = table.getEntry("tl");
    private NetworkTableEntry pipeline = table.getEntry("pipeline");

    private double smoothHorizontalAngle = 0.0;
    private boolean smoothAngleExists = false;

    public static Limelight getInstance() {
        if (instance == null) instance = new Limelight();
        return instance;
    }

    public void setPipeline(int pipe) {
        pipeline.setNumber(pipe);
    }

    private boolean prevEnabled = false;
    private double prevT;
    private double prevAngle;
    private DriveTrain driveTrain = DriveTrain.getInstance();

    @Override
    public void periodic() {
        boolean enabled = RobotState.isEnabled();
        if (enabled) {
            double t = Timer.getFPGATimestamp();
            double angle = driveTrain.getContinousAngleRadians() * 180 / Math.PI;
            if (prevEnabled) {
                if (this.hasValidTarget()) {
                    double angleChange = -1 * (prevAngle - angle);
                    double angularVelocity = angleChange / (prevT - t); // TODO div/0?
                    double latency = this.getLatencySeconds();

                    double targetAngle = this.getHorizontalAngle() + angularVelocity * latency;
                    System.out.println(angularVelocity);
                    if (smoothAngleExists) {
                        smoothHorizontalAngle += angleChange;
                        double smoothing = 0.9;
                        smoothHorizontalAngle = smoothHorizontalAngle * smoothing + targetAngle * (1 - smoothing);
                        smoothAngleExists = true;
                    } else
                        smoothHorizontalAngle = targetAngle;
                } else
                    smoothAngleExists = false;
            }

            prevT = t;
            prevAngle = angle;
        }
        prevEnabled = enabled;

    }


    /**
     * @return best RPS for shooter depending on distance.
     */
    public Double calculateBestRPS() {
        double distance = this.getCameraToTarget();


        // 0.0687 + 53.5x + -8.04x^2
        return  MathUtil.clamp(72.7 + (-17.9 * distance) + (14.3 * Math.pow(distance, 2)) + (-2.19 * Math.pow(distance, 3)), 0, 95);
    }

    public void setSmoothHorizontalAngle(Double smoothHorizontalAngle) {
        this.smoothHorizontalAngle = smoothHorizontalAngle;
    }

    public Double getSmoothHorizontalAngle() {
        return this.smoothHorizontalAngle;
    }

    public boolean hasValidTarget() {
        return tv.getDouble(0.0) != 0;
    }

    public double getHorizontalAngle() {
        return tx.getDouble(Double.NaN);
    }

    public double getVerticalAngle() {
        return ty.getDouble(Double.NaN);
    }

    public double getLatencySeconds() {
        return tl.getDouble(0.0) / 1000;
    }

    // the center location of the cargo hub
    private static final Pose2d kTargetToField = new Pose2d(8.3, 4, Rotation2d.fromDegrees(0));

    // the centre height of the target
    // high goal 8'8" = 2.6416m
    private static final double kTargetCentreHeight = 2.6416;

    // the height of the camera (lense and in meters) relattive to the floor
    private static final double kCameraHeight = 1;

    // the angle that the camera is mounted relative to the horizontal in degrees.
    private static final double kCameraMountingAngle = 40;

    // the relative height between the camera and the target
    private static final double kCameraToTargetHeight = kTargetCentreHeight - kCameraHeight;

    public double getCameraToTarget() {
        return kCameraToTargetHeight / Math.tan(Math.toRadians(getVerticalAngle() + kCameraMountingAngle));
    }
}
