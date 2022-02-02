package ca.warp7.frc2022.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;

public class Intake implements Subsystem {
    private static Intake instance;
    private boolean shouldPistonsBeOut;
    private double rollerSpeed;

    public static Intake getInstance() {
        if (instance == null) instance = new Intake();
        return instance;
    }

    private Intake(){
        shouldPistonsBeOut = false;
        rollerSpeed = 0.0;
    }

    @Override
    public void periodic() {
    }

    public void setRollerSpeed(double newRollerSpeed){
        rollerSpeed = newRollerSpeed;
    }

    public void setShouldPistonsBeOut(boolean newPistonState){
        shouldPistonsBeOut = newPistonState;
    }
}
