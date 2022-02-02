package ca.warp7.frc2022.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;

//TODO: make a variable in Constants.java to store if the launcher is a lobber or a shooter.

public class Launcher implements Subsystem {
    private static Launcher instance;
    private double lancherSpeed;

    public static Launcher getInstance() {
        if (instance == null) instance = new Launcher();
        return instance;
    }

    private Launcher(){
        lancherSpeed = 0.0;
    }

    @Override
    public void periodic() {
    }

    public void launcherSpeed(double newSpeed){
        lancherSpeed = newSpeed;
    }
}
