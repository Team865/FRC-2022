package ca.warp7.frc2022.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Subsystem;

@SuppressWarnings("unused")
public class Limelight implements Subsystem {

    private static Limelight instance;

    private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
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

    @Override
    public void periodic() {
    }
}
