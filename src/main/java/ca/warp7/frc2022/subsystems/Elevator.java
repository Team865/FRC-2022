package ca.warp7.frc2022.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;

public class Elevator implements Subsystem {
    private static Elevator instance;
    private double elevatorSpeed;

    public static Elevator getInstance() {
        if (instance == null) instance = new Elevator();
        return instance;
    }

    private Elevator(){
        elevatorSpeed = 0.0;
    }

    @Override
    public void periodic() {
    }

    public void setElevatorSpeed(double newElevatorSpeed){
        elevatorSpeed = newElevatorSpeed;
    }
}