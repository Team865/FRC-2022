package ca.warp7.frc2022.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;
import ca.warp7.frc2022.lib.motor.MotorControlHelper;
import static ca.warp7.frc2022.Constants.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;

public class Elevator implements Subsystem {
    private static Elevator instance;

    public static Elevator getInstance() {
        if (instance == null) instance = new Elevator();
        return instance;
    }

    private static TalonFX elevatorMotor = MotorControlHelper
        .createMasterTalonFX(kElevatorID);


    private static DigitalInput beamBreakLow = new DigitalInput (kBeamBreakLowID);         
    private static DigitalInput beamBreakHigh = new DigitalInput (kBeamBreakHighID);       


    public static boolean getLowBeamBreak() {
        return !beamBreakLow.get();
    }

    public static boolean getHighBeamBreak() {
        return !beamBreakHigh.get();
    }

    public void setSpeed(double speed) {
        elevatorMotor.set(ControlMode.PercentOutput, speed);
    }

    @Override
    public void periodic() {
    }
}