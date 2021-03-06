package ca.warp7.frc2022.auton;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

import java.util.function.Supplier;

public class AutonomousSelector {
    private static AutonomousSelector instance;

    public static AutonomousSelector getInstance() {
        if (instance == null) instance = new AutonomousSelector();
        return instance;
    }

    private AnalogInput a1 = new AnalogInput(0);
    private AnalogInput a2 = new AnalogInput(1);
    private AnalogInput a3 = new AnalogInput(2);
    private AnalogInput a4 = new AnalogInput(3);

    public SelectedMode getSelectedMode() {
        return SelectedMode.NothingMode;
    }

    public enum SelectedMode {
        NothingMode(InstantCommand::new);

        private Supplier<Command> mode;

        SelectedMode(Supplier<Command> mode) {
            this.mode = mode;
        }

        public Command create() {
            return mode.get();
        }
    }
}
