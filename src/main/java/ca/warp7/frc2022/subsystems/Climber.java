package ca.warp7.frc2022.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;
import ca.warp7.frc2022.lib.LazySolenoid;
import ca.warp7.frc2022.lib.motor.MotorControlHelper;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import static ca.warp7.frc2022.Constants.*;

public class Climber implements Subsystem {
    private static Climber instance;

    public static Climber getInstance() {
        if (instance == null) instance = new Climber();
        return instance;
    }
    
    private TalonFX climberMaster = MotorControlHelper
        .createMasterTalonFX(kClimbID);

    private LazySolenoid firstTraversalPiston = new LazySolenoid (kFirstPistonsID, kEnableSolenoids);
    private LazySolenoid secondTraversalPiston = new LazySolenoid (kSecondPistonsID, kEnableSolenoids);


    public Climber() {
    }
  
    public void setSpeed(double speed){
        climberMaster.set(TalonFXControlMode.PercentOutput, speed);
    }

    public void setBigPiston(boolean firstPiston) {
        firstTraversalPiston.set(firstPiston);
    }
    public void setSmallPiston(boolean secondPiston) {
        secondTraversalPiston.set(secondPiston);
    }
}
