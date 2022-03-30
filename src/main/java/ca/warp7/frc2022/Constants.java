/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.warp7.frc2022;

import ca.warp7.frc2022.lib.NetworkUtil;
import ca.warp7.frc2022.lib.control.PID;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    // Configuration

    public static final boolean kEnableSolenoids = true;
    public static final boolean kEnableDriveTrain = true;
    public static final boolean kEnableLauncher = true;

    public static final boolean kDebugCommandScheduler = false;
    public static final boolean kUseKinematicsDrive = false;
    public static final boolean kUseNotifierForMainLoop = false;

    // CAN IDs

    public static final int kDriveLeftMasterID = 31;
    public static final int kDriveLeftFollowerID = 32;
    public static final int kDriveLeftSecondFollowerID = 33;

    public static final int kDriveRightMasterID = 21;
    public static final int kDriveRightFollowerID = 22;
    public static final int kDriveRightSecondFollowerID = 23;


    public static final int kClimbMasterID = 41;
    public static final int kClimbFollowerID = 42;

    public static final int kElevatorID = 51;

    public static final int kIntakeID = 61;

    //Swapped these so they go in the right dirrection compared to on the phenox tuner
    public static final int kLauncherFollowerID = 55;
    public static final int kLauncherMasterID = 56;

    //public static final int kControlPanelManipulatorID = -1;

    // PH ID
    public static final int kIntakePistonID = 1;
    public static final int kFirstPistonsID = 0;
    public static final int kSecondPistonsID = 2;

    // DIO IDs

    public static final int kBeamBreakLowID = 1;
    public static final int kBeamBreakHighID = 2;

    // Elevator Tuning
    public static final double kElevatorSpeed = 0.20;

    // Intake Tuning
    public static final double kIntakeSpeed = 0.05;

    // Launcher Tuning
    public static final boolean kIsLauncherLobber = true;

    // kLobber controls shooter speed
    public static final double kLobberRPS = 25.5;
    public static final double kShooterRPS = 65.0; //60 is good

    public static final double kLauncherTicksPerRotation = 2048.0;
    public static final double kLauncherVelocityFrequency = 0.1;
    public static final double kLauncherGearRatio = 1;

    // Tuning for the launcher's PID
    public static final double kLauncherP = 0.001;
    public static final double kLauncherI = 0.0;
    public static final double kLauncherD = 0.0;
    // This one kinda works like a RPS to voltage ratio.
    public static final double kLauncherF = 0.0095;

    // Drive Train Tuning

    public static final double kLowGearRampRate = 0.15;
    public static final double kHighGearRampRate = 0.3;

    public static final PID kAutonLowGearVelocityPID =
        new PID(0.2, 0.0, 0.0, 0.0);
    public static final PID kTeleopLowGearVelocityPID =
        new PID(0.0, 0.0, 0.0, 0.0);
    public static final PID kTeleopHighGearVelocityPID =
        new PID(0.0, 0.0, 0.0, 0.0);
    public static final PID kVisionAlignmentYawPID =
        new PID(0.025, 0.0, 0.002, 0.0);
    // units: degrees => percent
    public static final PID kQuickTurnPID =
        new PID(0.04, 0.0, 0.0, 0.0);


    // Drive Train Constants

    public static final double kWheelBaseRadius = 0.31; // metres
    public static final double kWheelRadius = 0.0760858711932102; // metres
    public static final double kMaxVoltage = 12.0; // volts


    public static final double kGearRatio = 9.47;
    public static final double kMetresPerRotation =
        (2 * Math.PI * kWheelRadius) / kGearRatio; // ticks/m
    public static final SimpleMotorFeedforward kTransmission =
        new SimpleMotorFeedforward(0.0353, 4.140, 0.401);

    
    @SuppressWarnings("unused")
    private static class PracticeRobotDetector {
        private static final String kPracticeRobotAddress = "00-80-2F-27-06-8E";
        private static final boolean kIsPracticeRobot =
        NetworkUtil.getMACAddress().equals(kPracticeRobotAddress);
    }

    public static boolean isPracticeRobot() {
        return true;
    }
}