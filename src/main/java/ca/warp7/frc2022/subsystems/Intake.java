package ca.warp7.frc2022.subsystems;

import ca.warp7.frc2022.lib.LazySolenoid;
import ca.warp7.frc2022.lib.motor.MotorControlHelper;
import edu.wpi.first.wpilibj2.command.Subsystem;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import static ca.warp7.frc2022.Constants.*;

public class Intake implements Subsystem {
    private static Intake instance;

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

    public void setSpeed(double speed) {
        intakeMotor.set(TalonFXControlMode.PercentOutput, speed);
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
}