package ca.warp7.frc2022.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;
import ca.warp7.frc2022.lib.LazySolenoid;
import ca.warp7.frc2022.lib.motor.MotorControlHelper;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import static ca.warp7.frc2022.Constants.*;

public class Climber implements Subsystem {
    private static Climber instance;

    public static Climber getInstance() {
        if (instance == null) instance = new Climber();
        return instance;
    }
    
    private TalonSRX climberMaster = MotorControlHelper
        .createMasterTalonSRX(kClimbMasterID);

    private LazySolenoid firstTraversalPiston = new LazySolenoid (kFirstPistonsID, kEnableSolenoids);
    private LazySolenoid secondTraversalPiston = new LazySolenoid (kSecondPistonsID, kEnableSolenoids);


    public Climber() {
        MotorControlHelper
            .assignFollowerTalonSRX(climberMaster, kClimbFollowerID, false);
    }
  
    public void setSpeed(double speed){
        climberMaster.set(TalonSRXControlMode.PercentOutput, speed);
    }

    public void setFirstPiston(boolean firstPiston) {
        firstTraversalPiston.set(firstPiston);
    }
    public void setSecondPiston(boolean secondPiston) {
        secondTraversalPiston.set(secondPiston);
    }

}
