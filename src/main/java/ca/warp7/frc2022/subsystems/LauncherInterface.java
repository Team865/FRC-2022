package ca.warp7.frc2022.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;

public interface LauncherInterface extends Subsystem {
    //Bad temp documentation note: Epsilon is the allowed decemal error since doubles and floats subtract weird.
    public boolean isTargetReached();

    public void setRunLauncher(boolean newRunLauncher);

    public void isHighGoal(boolean isHighGoal);

    public double getPercentPower();

    public void reset();

    public void toggleUseLimelightSpeed();

    public void setUseAutoSpeed(boolean use);
}