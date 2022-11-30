package ca.warp7.frc2022.subsystems;

public class LazyLauncher implements LauncherInterface {
    private static LauncherInterface instance;

    public static LauncherInterface getInstance() {
        if (instance == null) instance = new LazyLauncher();
        return instance;
    }

    private LazyLauncher(){
    }

    @Override
    public void periodic() {
    }

    
    @Override
    public boolean isTargetReached() {
        return(true);
    }

    @Override
    public void setRunLauncher(boolean newRunLauncher) {
    }

    @Override
    public double getPercentPower(){
        return (0.0);
    }

    @Override
    public void reset() {}

    @Override
    public void isHighGoal(boolean isHighGoal) {
    }

    public void toggleUseLimelightSpeed(){
        
    }

    public void setUseAutoSpeed(boolean use){
    }
}