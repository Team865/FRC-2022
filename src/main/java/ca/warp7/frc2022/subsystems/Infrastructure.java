package ca.warp7.frc2022.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Infrastructure implements Subsystem {
    private static Infrastructure instance;

    public static Infrastructure getInstance() {
        if (instance == null) {
            instance = new Infrastructure();
        }
        return instance;
    }

//    private Compressor compressor = new Compressor(PneumaticsModuleType.CTREPCM);

    private Infrastructure() {
    }

    public void startCompressor() {
        //compressor.enableDigital();
    }

    public void stopCompressor() {
        //compressor.disable();
    }
}
