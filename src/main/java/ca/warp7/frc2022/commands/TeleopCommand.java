/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.warp7.frc2022.commands;

import ca.warp7.frc2022.Constants;
import ca.warp7.frc2022.auton.commands.RobotStateCommand;
import ca.warp7.frc2022.auton.commands.VisionAlignCommand;
import ca.warp7.frc2022.lib.Util;
import ca.warp7.frc2022.lib.XboxController;
import ca.warp7.frc2022.subsystems.Launcher;
import ca.warp7.frc2022.subsystems.LauncherInterface;
import ca.warp7.frc2022.subsystems.Limelight;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * This class is responsible for scheduling the proper commands while operator
 * controlled
 */
public class TeleopCommand extends CommandBase {
    private Command curvatureDriveCommand = Constants.kUseKinematicsDrive ?
            new KinematicsDriveCommand(this::getXSpeed, this::getZRotation, this::isQuickTurn) :
            new PercentDriveCommand(this::getXSpeed, this::getZRotation, this::isQuickTurn);

    private Command intakeCommand = new IntakingCommand(this::getIntakeSpeed);
    private Command feedCommand = new FeedCommand(this::getFeedSpeed, this::getFeedSpeedWithIntake);
    private Command climbCommand = new ClimbCommand(this::getClimbSpeed, this::getFirstPiston, this::getSecondPiston);

    //    private Command controlPanelDisplay = new ControlPanelCommand(this::getControlPanelSpinnerSpeed);

    private Command resetRobotStateCommand = new RobotStateCommand();

    private Command setLowGearDriveCommand = SingleFunctionCommand.getSetDriveLowGear();
    private Command setHighGearDriveCommand = SingleFunctionCommand.getSetDriveHighGear();
    private Command zeroYawCommand = SingleFunctionCommand.getZeroYaw();
    private Command brakeCommand = SingleFunctionCommand.getSetDriveBrakeMode();

    private LauncherInterface launcher = Launcher.getInstance();

    private XboxController driver = new XboxController(0);
    private XboxController operator = new XboxController(1);

    private boolean isIntaking = false;
    private boolean isReversed = false;

    private boolean isClose = false;
    private boolean isPriming = false;
    private boolean isFeeding = false;
    private boolean isFeedingWithIntake = false;
    private boolean isHighGoal = false;
    private boolean isLaunching = false;
    private boolean isClimbing = false;
    private boolean firstSolenoidOpen = false;
    private boolean secondSolenoidOpen = false;

//    public double getControlPanelSpinnerSpeed() {
//        return operator.rightX;
//    }

    public double getIntakeSpeed() {
        SmartDashboard.putBoolean("Intake isReversed", isReversed);
        if (isIntaking)
            return Util.applyDeadband(driver.rightTrigger, 0.2) * (isReversed ? -1 : 1);
        return 0.0;
    }

    private double getXSpeed() {
        return Util.applyDeadband(driver.leftY / -1, 0.2);
    }

    private double getZRotation() {
        double zRotation = Util.applyDeadband(driver.rightX, 0.15);
        if (driver.backButton.isDown()) zRotation *= 0.5;
        if (isQuickTurn() || driver.leftY < 0) {
            return zRotation;
        } else {
            return -1 * zRotation;
        }
    }

    private boolean isQuickTurn() {
        return driver.leftBumper.isHeldDown();
    }

    private double getVisionAlignSpeed() {
        return getXSpeed() / 2.0;
    }

    private double getFeedSpeed() {
        if (isFeeding)
            return 1;
        return 0.0;
    }


    public double getFeedSpeedWithIntake() {
        if (isFeedingWithIntake)
            return 1;
        return 0.0;
    }


    private double getClimbSpeed() {
        SmartDashboard.putBoolean("Match climb configuration", !isClimbing);
        double climbSpeed = Util.applyDeadband(operator.leftY, 0.4);
        
        if (isClimbing) {
            return climbSpeed;
        } else if (!isClimbing) {
            return climbSpeed * -1;
        } else {
            return 0;
        }
    }

    private boolean getFirstPiston() {
        return firstSolenoidOpen;
    }

    private boolean getSecondPiston() {
        return secondSolenoidOpen;
    }

    private boolean isLaunching() {
        return isLaunching;
    }

    private boolean isHighGoal() {
        return isHighGoal;
    }

    @Override
    public void initialize() {
        launcher.reset();
        Limelight.getInstance().setPipeline(0);

        zeroYawCommand.schedule();
        resetRobotStateCommand.schedule();

        setLowGearDriveCommand.schedule();
        curvatureDriveCommand.schedule();
        brakeCommand.schedule();

        feedCommand.schedule();
        climbCommand.schedule();
        intakeCommand.schedule();
    }

    @Override
    public void execute() {
        driver.collectControllerData();
        operator.collectControllerData();
        launcher.setRunLauncher(this.isLaunching());
        launcher.isHighGoal(this.isHighGoal());
        // Driver

        if (!isIntaking) {
            isIntaking = driver.rightTrigger > 0.22;
        } else {
            isIntaking = driver.rightTrigger > 0.2;
        }

        if (driver.yButton.isPressed()) {
            isReversed = !isReversed;
        }

        // Operator

        if (operator.yButton.isPressed()) {
            isHighGoal = !isHighGoal;
        }

        if (operator.rightBumper.isPressed()) {
            launcher.reset();
            isLaunching = !isLaunching;
        }

        if (operator.bButton.isPressed()) {
            firstSolenoidOpen = !firstSolenoidOpen;
        }

        if (operator.aButton.isPressed()) {
            secondSolenoidOpen = !secondSolenoidOpen;
        }

        if (operator.rightTrigger > 0.22) {
            isFeeding = true;
        } else {
            isFeeding = false;
        }

        if (operator.leftTrigger > 0.22) {
            isFeedingWithIntake = true;
        } else {
            isFeedingWithIntake = false;
        }

        if (operator.startButton.isPressed()) {
            isClimbing = !isClimbing;
        }

        // if (operator.xButton.isPressed()) {
        //     SingleFunctionCommand.complexClimb();
        // }
    }
}
