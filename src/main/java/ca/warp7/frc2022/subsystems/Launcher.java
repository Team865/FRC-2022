package ca.warp7.frc2022.subsystems;

import static ca.warp7.frc2022.Constants.*;
import ca.warp7.frc2022.lib.Util;

public class Launcher  implements LauncherInterface{
    private static LauncherInterface instance;
    private final double targetRPS;
    private double currentRPS;

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
        // currentRPS = flywheelMasterNeo.getEncoder().getVelocity() / kFlywheelGearRatio / 60;
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
        if (targetRPS == 0.0)
            this.setVoltage(0.0);
        else
            this.setVoltage((targetRPS + getError() * kLauncherKp) * kLauncherKv +
                    kLauncherKs * Math.signum(targetRPS));
    }

    private void setVoltage(double voltage) {
        //flywheelMasterNeo.set(voltage / 12);
    }
}