package ca.warp7.frc2022.subsystems.drivetrain;

import ca.warp7.frc2022.lib.control.PID;

/**
 * makes it easy to swap drive train motors (or simulate them)
 */
public interface DriveTrainVariant {
    /**
     * Neutral the output of the left and right motors.
     */
    void neutralOutput();

    /**
     * Set the percent output, between -1.0 and 1.0, of the left and right motors.
     * @param leftPercent Value for left motor
     * @param rightPercent Value for right motor
     */
    void setPercentOutput(double leftPercent, double rightPercent);

    /**
     * Set the desired velocity setpoint and feed forward of the left and right motors
     * @param leftVelocityRotationsPerSecond Velocity of left motor in rotations per second
     * @param rightVelocityRotationsPerSecond Velocity of right motor in rotations per second
     * @param leftVoltage Left motor voltage feed forward in volts
     * @param rightVoltage Right motor voltage feed forward in volts
     */
    void setVelocityPID(
            double leftVelocityRotationsPerSecond, double rightVelocityRotationsPerSecond,
            double leftVoltage, double rightVoltage
    );

    /**
     * Set the position setpoint for the left and right sides of the drivetrain
     * @param leftDistanceRotations Left position target in rotations
     * @param rightDistanceRotations Right position target in rotations
     */
    void setPositionPID(double leftDistanceRotations, double rightDistanceRotations);

    /**
     * Configure the PID gains of both the left and right motors.
     * @param pid PID to configure
     */
    void configurePID(PID pid);

    /**
     * Configures the open-loop ramp rate of throttle output of both the left and right motors.
     * @param secondsFromNeutralToFull The minimum amount of seconds in which the motors should go from neutral to full throttle.
     */
    void configureRampRate(double secondsFromNeutralToFull);

    /**
     * Set the encoder position
     * @param leftRotations Rotations made by the left motor
     * @param rightRotations Rotations made by the right motor
     */
    void setEncoderPosition(double leftRotations, double rightRotations);

    /**
     * Set the neutral mode to Brake.
     */
    void setBrake();

    /**
     * Set the neutral mode to Coast.
     */
    void setCoast();

    /**
     * @return The encoder position of the left motor in rotations made
     */
    double getLeftPositionRotations();

    /**
     * @return The encoder position of the left motor in rotations made
     */
    double getRightPositionRotations();

    /**
     * @return The current velocity of the left motor in rotations per second
     */
    double getLeftVelocityRPS();

    /**
     * @return The current velocity of the right motor in rotations per second
     */
    double getRightVelocityRPS();

    /**
     * @return The voltage applied to the left motor in volts
     */
    double getLeftVoltage();

    /**
     * @return The voltage applied to the right motor in volts
     */
    double getRightVoltage();

    /**
     * @return The difference, in rotations, between the target position PID and the current sensor value, for the left motor.
     */
    double getLeftPIDErrorRotations();

    /**
     * @return The difference, in rotations, between the target position PID and the current sensor value, for the right motor.
     */
    double getRightPIDErrorRotations();
}
