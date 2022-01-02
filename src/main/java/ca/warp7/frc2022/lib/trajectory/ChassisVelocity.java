package ca.warp7.frc2022.lib.trajectory;

/**
 * this is simpler than WPILib's ChassisSpeeds
 */
public class ChassisVelocity {
    private double linear;
    private double angular;

    public ChassisVelocity(double linear, double angular) {
        this.linear = linear;
        this.angular = angular;
    }

    public double getAngular() {
        return angular;
    }

    public double getLinear() {
        return linear;
    }
}
