package ca.warp7.frc2022.auton;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

import java.util.function.Supplier;

public class AutonomousSelector {
    private static AutonomousSelector instance;
    private String[] autos = {"Taxi", "2 Ball", "3 Ball", "4 Ball", "Reject Ball"};
    private String autoName;

    public static AutonomousSelector getInstance() {
        if (instance == null) instance = new AutonomousSelector();
        return instance;
    }

    public void initialize() {
        SmartDashboard.putStringArray("Auto List", autos);
        autoName = SmartDashboard.getString("Auto Selector", "Taxi");
    }

    public SelectedMode getSelectedMode() {
        switch(autoName) {
            case "Taxi":
                System.out.println("Running Taxi Auto");
                return SelectedMode.TAXI;
            case "2 Ball":
                System.out.println("Running 2 Ball Auto");
                return SelectedMode.TWOBALL;
            case "3 Ball":
                System.out.println("Running 3 Ball Auto");
                return SelectedMode.THREEBALL;
            case "4 Ball":
                System.out.println("Running 4 Ball Auto");
                return SelectedMode.FOURBALL;
            case "Reject Ball":
                System.out.println("Running Reject Ball Auto");
                return SelectedMode.REJECTBALL;
            default:
                return SelectedMode.NoAuto;

        }
    }

    public enum SelectedMode {
        NoAuto(InstantCommand::new),
        TAXI(InstantCommand::new),
        TWOBALL(InstantCommand::new),
        THREEBALL(InstantCommand::new),
        FOURBALL(InstantCommand::new),
        REJECTBALL(InstantCommand::new);

        private Supplier<Command> mode;

        SelectedMode(Supplier<Command> mode) {
            this.mode = mode;
        }

        public Command create() {
            return mode.get();
        }
    }
}
