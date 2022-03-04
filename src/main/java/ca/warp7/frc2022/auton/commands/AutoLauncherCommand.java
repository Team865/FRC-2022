package ca.warp7.frc2022.auton.commands;

import ca.warp7.frc2022.subsystems.Launcher;
import ca.warp7.frc2022.subsystems.LauncherInterface;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoLauncherCommand extends CommandBase{
    private LauncherInterface launcher = Launcher.getInstance();
    private boolean isEnabled;

    public AutoLauncherCommand(boolean enabled) {
        this.isEnabled = enabled;
        addRequirements(launcher);
    }

    @Override
    public void execute() {
        boolean enabled = isEnabled;
        launcher.setRunLauncher(enabled);        
    }
}
