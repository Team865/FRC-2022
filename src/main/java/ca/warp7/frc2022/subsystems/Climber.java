package ca.warp7.frc2022.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import ca.warp7.frc2022.lib.LazySolenoid;
import ca.warp7.frc2022.lib.control.LatchedBoolean;
import ca.warp7.frc2022.lib.motor.MotorControlHelper;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import static ca.warp7.frc2022.Constants.*;


public class Climber implements Subsystem {
    private static Climber instance;
    private double climberPosition = 0;
    private boolean bigPistonStatus;
    private boolean smallPistonStatus;
    private boolean limitOveride = false;
    private double targetPosition;
    private double positionalSpeed;
    private double targetPositionDelta;
    private boolean targetHasChanged;
    private double oldTarget = 0;
    private double softClimbMin;
    private double softClimbMax;

    public static Climber getInstance() {
        if (instance == null) instance = new Climber();
        return instance;
    }
    
    private TalonFX climberMaster = MotorControlHelper
        .createMasterTalonFX(kClimbID);

    private LazySolenoid firstTraversalPiston = new LazySolenoid (kFirstPistonsID, kEnableSolenoids);
    private LazySolenoid secondTraversalPiston = new LazySolenoid (kSecondPistonsID, kEnableSolenoids);

    private DigitalInput limitSwitch = new DigitalInput(kLimitSwitchID);


    public Climber() {
        resetClimberPosition();
        softClimbMin = kClimberMin;
        softClimbMax = kClimberMax;
        climberMaster.setNeutralMode(NeutralMode.Brake);
    }

    public void toggleLimitOveride() {
        limitOveride = !limitOveride;
    }

    public void resetClimberPosition(){
        climberMaster.setSelectedSensorPosition(0);
    }

    
    public void setSpeed(double opSpeed){
        double finalSpeed;

        if (Math.abs(positionalSpeed) >= Math.abs(opSpeed)) {
            finalSpeed = positionalSpeed;
        } else if (Math.abs(positionalSpeed) < Math.abs(opSpeed)) {
            finalSpeed = opSpeed;
        } else {
            finalSpeed = 0;
        }
        
        if(!limitOveride) {
            if (finalSpeed < 0 && climberPosition >= softClimbMin) {
                climberMaster.set(TalonFXControlMode.PercentOutput, finalSpeed);
            } else if (finalSpeed > 0 && climberPosition <= softClimbMax) {
                climberMaster.set(TalonFXControlMode.PercentOutput, finalSpeed);
            } else {
                climberMaster.set(TalonFXControlMode.PercentOutput, 0);
            }
        } else {
            climberMaster.set(TalonFXControlMode.PercentOutput, finalSpeed);
        }
    }

    public boolean getBigPistonStatus() {
        return bigPistonStatus;
    }

    public boolean getSmallPistonStatus() {
        return smallPistonStatus;
    }

    public void toggleBigPiston() {

        bigPistonStatus = !bigPistonStatus;
        firstTraversalPiston.toggle();
    }
    public void toggleSmallPiston() {
        smallPistonStatus = !smallPistonStatus;

        secondTraversalPiston.toggle();
    }

    public void setBigPiston(boolean firstPiston) {
        bigPistonStatus = firstPiston;
        firstTraversalPiston.set(firstPiston);
    }
    public void setSmallPiston(boolean secondPiston) {
        smallPistonStatus = secondPiston;


        secondTraversalPiston.set(secondPiston);
    }

    public void setClimberToPosition(double targetPosition) {
        this.targetPosition = targetPosition;
    }

    public double getSoftMax() {
        return softClimbMax;
    }

    public void periodic() {
        targetPositionDelta = targetPosition - climberPosition;
        boolean movingUp = false;
        boolean movingDown = false;

        if(!targetHasChanged) {
            if ((targetPosition != oldTarget) && targetPosition != 0) {
                targetHasChanged = true;
                oldTarget = targetPosition;
            }
        }

        if(targetHasChanged && targetPositionDelta > 0 || movingUp) {
            movingUp = true;
            positionalSpeed = 0.6;

            if(targetPositionDelta < climberPosition) {
                movingUp = false;
                targetHasChanged = false;
                positionalSpeed  = 0;
                oldTarget = 0;
                targetPosition = 0;
            }
        }

        if(targetHasChanged && targetPositionDelta < 0 || movingDown) {
            movingDown = true;
            positionalSpeed = -0.60;

            if(targetPositionDelta > climberPosition) {
                movingDown = false;
                targetHasChanged = false;
                positionalSpeed  = 0;
                oldTarget = 0;
                targetPosition = 0;
            }
        }

        if(limitSwitch.get()) {
            setSmallPiston(false);
            //softClimbMin = climberPosition - 0.5;
            //softClimbMax = softClimbMin + kTravelFromRungToMax + 0.5;
        }

        SmartDashboard.putNumber("SoftClimbMax", softClimbMax);    
        SmartDashboard.putNumber("SoftClimbMin", softClimbMin);    
        SmartDashboard.putNumber("Climber POS", climberPosition);    


        climberPosition = Math.floor((climberMaster.getSelectedSensorPosition() 
            / kClimberTicksPerRotation  / kLauncherGearRatio) * 100) / 100;
    }
}