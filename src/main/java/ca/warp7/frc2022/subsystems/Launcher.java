package ca.warp7.frc2022.subsystems;

import static ca.warp7.frc2022.Constants.*;
import ca.warp7.frc2022.lib.Util;
import ca.warp7.frc2022.lib.control.PIDController;
import ca.warp7.frc2022.lib.control.PID;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.*;
import ca.warp7.frc2022.lib.motor.MotorControlHelper;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Launcher  implements LauncherInterface{
    private static LauncherInterface instance;
    private final double fullSpeedRPS;
    private double targetRPS;
    private double currentRPS;
    private PIDController voltageCalculator;
    private boolean runLauncher;
    private double outputVoltPercent;
    private static TalonFX launcherMotorMaster;
    private static TalonFX launcherMotorFollower;


    public static LauncherInterface getInstance() {
        if(kEnableLauncher){
            if (instance == null) instance = new Launcher();
        }
        else{
            instance = LazyLauncher.getInstance();
        } 
        return instance;
    }

    private Launcher(){
        voltageCalculator = new PIDController(new PID(kLauncherP, kLauncherI, kLauncherD, kLauncherF));

        runLauncher = true;

        launcherMotorMaster = MotorControlHelper.createMasterTalonFX(kLauncherMasterID);
        launcherMotorFollower = MotorControlHelper.assignFollowerTalonFX(launcherMotorMaster, kLauncherFollowerID, InvertType.OpposeMaster);

        launcherMotorMaster.setNeutralMode(NeutralMode.Coast);
        launcherMotorFollower.setNeutralMode(NeutralMode.Coast);

        if(kIsLauncherLobber){
            fullSpeedRPS = kLobberRPS;
        }
        else{
            fullSpeedRPS = kShooterRPS;
        }
        targetRPS = 0.0;
        currentRPS = 0.0;
    }

    @Override
    public void periodic() {
        //Note: .getSelectedSensorVelocity returns in ticks per miliseconds.
        currentRPS = launcherMotorMaster.getSelectedSensorVelocity() 
            / kLauncherTicksPerRotation / kLauncherVelocityFrequency * kLauncherGearRatio;
        this.updateTargetRPS();
        this.updateVoltage();
        
        SmartDashboard.putNumber("Current voltage percent", outputVoltPercent * 100);
        SmartDashboard.putNumber("Current RPS dif", this.getError());
        SmartDashboard.putNumber("Target RPS", targetRPS);
        SmartDashboard.putNumber("Current RPS", currentRPS);
        SmartDashboard.putBoolean("Is launcher running", runLauncher);
        SmartDashboard.putBoolean("Is RPS target reached", this.isTargetReached(0.1));
    }

    //Bad temp documentation note: Epsilon is the allowed decemal error since doubles and floats subtract weird.
    @Override
    public boolean isTargetReached(double epsilon) {
        return Util.epsilonEquals(getPercentError(), 0.0, epsilon);
    }

    @Override
    public void setRunLauncher(boolean newRunLauncher) {
        runLauncher = newRunLauncher;
    }

    @Override
    public double getPercentPower(){
        return (outputVoltPercent);
    }

    private void updateTargetRPS(){
        targetRPS = runLauncher ? fullSpeedRPS : 0.0;
    }

    private double getPercentError() {
        if (targetRPS != 0.0)
            return getError() / targetRPS;
        else
            return 0.0;
    }

    private double getError() {
        return targetRPS - currentRPS;
    }

    private void updateVoltage() {
        outputVoltPercent = 0.0;
        if (targetRPS != 0.0){
            outputVoltPercent = voltageCalculator.calculate(targetRPS, currentRPS);
        }
        SmartDashboard.putNumber("Target RPS", targetRPS);
        SmartDashboard.putNumber("Current RPT", currentRPS);
        SmartDashboard.putNumber("Raw PID Value", outputVoltPercent);
        outputVoltPercent = MathUtil.clamp(outputVoltPercent, 0.0, 1.0);
        this.setVoltage(outputVoltPercent);
    }

    private void setVoltage(double voltage) {
        launcherMotorMaster.set(ControlMode.PercentOutput, voltage);
    }

    public void reset() {
        voltageCalculator = new PIDController(new PID(kLauncherP, kLauncherI, kLauncherD, kLauncherF));
    }
}