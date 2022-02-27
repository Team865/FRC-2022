package ca.warp7.frc2022.subsystems;

import static ca.warp7.frc2022.Constants.*;
import ca.warp7.frc2022.lib.Util;
import ca.warp7.frc2022.lib.control.PIDController;
import ca.warp7.frc2022.lib.control.PID;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.*;
import ca.warp7.frc2022.lib.motor.MotorControlHelper;
import edu.wpi.first.math.MathUtil;

public class Launcher  implements LauncherInterface{
    private static LauncherInterface instance;
    private final double targetRPS;
    private double currentRPS;
    private PIDController voltageCalculator;
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

        launcherMotorMaster = MotorControlHelper.createMasterTalonFX(kLauncherMasterID);
        launcherMotorFollower = MotorControlHelper.assignFollowerTalonFX(launcherMotorMaster, kLauncherFollowerID, InvertType.OpposeMaster);

        if(kIsLauncherLobber){
            targetRPS = kLobberRPS;
        }
        else{
            targetRPS = kShooterRPS;
        }
        currentRPS = 0.0;
    }

    @Override
    public void periodic() {
        //Returns in ms
        currentRPS = launcherMotorMaster.getSelectedSensorVelocity() / 60;
    }

    //Bad temp documentation note: Epsilon is the allowed decemal error since doubles and floats subtract weird.
    @Override
    public boolean isTargetReached(double epsilon) {
        return Util.epsilonEquals(getPercentError(), 0.0, epsilon);
    }

    @Override
    public double getPercentError() {
        if (targetRPS != 0.0)
            return getError() / targetRPS;
        else
            return 0.0;
    }

    @Override
    public double getError() {
        return targetRPS - currentRPS;
    }

    @Override
    public void calcOutput() {
        double outputVolts = 0.0;
        if (targetRPS != 0.0){
            voltageCalculator.calculate(targetRPS, currentRPS);
        }
        outputVolts = MathUtil.clamp(outputVolts, 0.0, 1.0);
        this.setVoltage(outputVolts);
    }

    private void setVoltage(double voltage) {
        launcherMotorMaster.set(ControlMode.PercentOutput, voltage);
    }
}