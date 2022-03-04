package ca.warp7.frc2022.subsystems;

import ca.warp7.frc2022.lib.LazySolenoid;
import ca.warp7.frc2022.lib.motor.MotorControlHelper;
import edu.wpi.first.wpilibj2.command.Subsystem;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import static ca.warp7.frc2022.Constants.*;

public class Intake implements Subsystem {
    private static Intake instance;

    private double speedFromIntake;
    private double speedFromElevator;

    public static Intake getInstance() {
        if (instance == null) instance = new Intake();
        return instance;
    }

    private TalonFX intakeMotor = MotorControlHelper
        .createMasterTalonFX(kIntakeID);

    private LazySolenoid intakePiston =
        new LazySolenoid(kIntakePistonID, kEnableSolenoids);

    private Intake(){
    }

    public void setSpeedFromIntake(double speed) {
        speedFromIntake = speed;
    }

    public void setSpeedFromElevator(double speed) {
        speedFromElevator = speed;
    }

    public void setExtended(boolean extended) {
        intakePiston.set(extended);
    }

    public boolean isExtended() {
        return intakePiston.get();
    }

    public void toggle() {
        setExtended(!intakePiston.get());
    }

    @Override
    public void periodic() {
        
        if (Math.abs(speedFromIntake) >= Math.abs(speedFromElevator)) {
            intakeMotor.set(TalonFXControlMode.PercentOutput, speedFromIntake);
        } else if (Math.abs(speedFromElevator) > Math.abs(speedFromIntake)) {
            intakeMotor.set(TalonFXControlMode.PercentOutput, speedFromElevator);
        } else {
            intakeMotor.set(TalonFXControlMode.PercentOutput, 0.0);
        }

    }
}