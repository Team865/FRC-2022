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
    public boolean isTargetReached(double epsilon) {
        return(false);
    }

    @Override
    public double getPercentError() {
        return(0.0);
    }

    @Override
    public double getError() {
        return (0.0);
    }

    @Override
    public void calcOutput() {
    }
}