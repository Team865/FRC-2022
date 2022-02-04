package ca.warp7.frc2022.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;

public class Climber implements Subsystem {
    private static Climber instance;
    private double climbingSpeed;

    public static Climber getInstance() {
        if (instance == null) instance = new Climber();
        return instance;
    }

    private Climber(){
        climbingSpeed = 0.0;
    }

    @Override
    public void periodic() {
    }

    public void setClimberSpeed(double newClimbingSpeed){
        climbingSpeed = newClimbingSpeed;
    }
}
