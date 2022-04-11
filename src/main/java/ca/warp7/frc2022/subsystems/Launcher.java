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
    private double fullSpeedRPS;
    private double targetRPS;
    private double currentRPS;
    private PIDController voltageCalculator;
    private boolean runLauncher;
    private boolean highGoal;
    private double outputVoltPercent;
    private static TalonFX launcherMotorMaster;
    private static TalonFX launcherMotorFollower;
    private double limelightShooterRPS;

    private Limelight limelight = Limelight.getInstance();


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

        launcherMotorMaster = MotorControlHelper.createMasterTalonFX(kLauncherMasterID);
        launcherMotorMaster.setInverted(true);
        launcherMotorFollower = MotorControlHelper.assignFollowerTalonFX(launcherMotorMaster, kLauncherFollowerID, InvertType.OpposeMaster);

        launcherMotorMaster.setNeutralMode(NeutralMode.Coast);
        launcherMotorFollower.setNeutralMode(NeutralMode.Coast);

        targetRPS = 0.0;
        currentRPS = 0.0;
    }

    @Override
    public void periodic() {
        //Note: .getSelectedSensorVelocity returns in ticks per miliseconds.
        currentRPS = launcherMotorMaster.getSelectedSensorVelocity() 
            / kLauncherTicksPerRotation / kLauncherVelocityFrequency * kLauncherGearRatio;
        this.updateShooterSpeedFromLimelight();
        this.updateTargetRPS();
        this.updateVoltage();
        this.updateHighGoal();
        
        SmartDashboard.putNumber("Current voltage percent", outputVoltPercent * 100);
        SmartDashboard.putNumber("Current RPS dif", this.getError());
        SmartDashboard.putNumber("Target RPS", targetRPS);
        SmartDashboard.putNumber("Current RPS", currentRPS);
        SmartDashboard.putBoolean("Is launcher running", runLauncher);
        SmartDashboard.putBoolean("Is RPS target reached", this.isTargetReached());
        SmartDashboard.putBoolean("Is shooting high goal", highGoal);
    }

    //Bad temp documentation note: Epsilon is the allowed decemal error since doubles and floats subtract weird.
    @Override
    public boolean isTargetReached() {
        return Util.epsilonEquals(getPercentError(), 0.0, 0.1);
    }

    @Override
    public void setRunLauncher(boolean newRunLauncher) {
        runLauncher = newRunLauncher;
    }

    @Override
    public void isHighGoal(boolean isHighGoal) {
        highGoal = isHighGoal;
    }

    @Override
    public double getPercentPower(){
        return (outputVoltPercent);
    }

    private void updateHighGoal() {
        fullSpeedRPS = limelightShooterRPS;
    }

    private void updateTargetRPS(){
        targetRPS = runLauncher ? fullSpeedRPS : 0.0;
    }

    /**
     *  Updates shooter speed from limelight
     */
    private void updateShooterSpeedFromLimelight() {
        limelightShooterRPS = limelight.calculateBestRPS();
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