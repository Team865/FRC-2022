package ca.warp7.frc2022.subsystems.drivetrain;

import ca.warp7.frc2022.lib.control.PID;


/*
    This class is suppose to be a substitute for an instance of DriveTrainVariant. To the rest of the program,
    its a valid DriveTrainVariant, however it does not actually do anything.

    THIS CLASS IS NOT USED WHEN THE DRIVE TRAIN IS ENABLED, NONE OF THE METHODS DO ANYTHING OF USE.
*/
public final class LazyDriveTrainVariant implements DriveTrainVariant {
    public LazyDriveTrainVariant() {
    }

    @Override
    public void setVelocityPID(
            double leftVelocityRotationsPerSecond,
            double rightVelocityRotationsPerSecond,
            double leftVoltage,
            double rightVoltage) {
    }

    @Override
    public void setPositionPID(double leftDistanceRotations, double rightDistanceRotations) {
    }

    @Override
    public void configurePID(PID pid) {
    }

    @Override
    public void configureRampRate(double secondsFromNeutralToFull) {
    }

    @Override
    public void setEncoderPosition(double leftRotations, double rightRotations) {
    }

    @Override
    public void setBrake() {
    }

    @Override
    public void setCoast() {
    }

    @Override
    public double getLeftPositionRotations() {
        return (0.0);
    }

    @Override
    public double getRightPositionRotations() {
        return (0.0);
    }

    @Override
    public double getLeftVelocityRPS() {
        return (0.0);
    }

    @Override
    public double getRightVelocityRPS() {
        return (0.0);
    }

    @Override
    public double getLeftVoltage() {
        return (0.0);
    }

    @Override
    public double getRightVoltage() {
        return (0.0);
    }

    @Override
    public void neutralOutput() {
    }

    @Override
    public void setPercentOutput(double leftPercent, double rightPercent) {
    }

    @Override
    public double getLeftPIDErrorRotations() {
        return(0.0);
        
    }

    @Override
    public double getRightPIDErrorRotations() {
        return(0.0);
        
    }
}