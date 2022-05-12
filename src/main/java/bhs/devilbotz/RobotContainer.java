/*-------------------------------------------------------------------------------*/
/* Copyright (c) 2021-2022 BHS Devilbotz. All Rights Reserved.                   */
/* Open Source Software - may be modified, commercialized, distributed,          */
/* sub-licensed and used for private use under the terms of the License.md       */
/* file in the root of the source code tree.                                     */
/*                                                                               */
/* You MUST include the original copyright and license files in any and all      */
/* revised/modified code. You may NOT remove this header under any circumstance  */
/* unless explicitly noted                                                       */
/*-------------------------------------------------------------------------------*/

package bhs.devilbotz;

import bhs.devilbotz.commands.DriveCommand;
import bhs.devilbotz.commands.autonomous.drive.driverassist.DefenseModeDisable;
import bhs.devilbotz.commands.autonomous.drive.driverassist.DefenseModeEnable;
import bhs.devilbotz.commands.autonomous.drive.driverassist.ShootTwoBalls;
import bhs.devilbotz.commands.autonomous.routines.Backwards;
import bhs.devilbotz.commands.autonomous.routines.Diagnostic;
import bhs.devilbotz.commands.autonomous.routines.ShootAndBackwardsAuto;
import bhs.devilbotz.commands.camera.CameraToggle;
import bhs.devilbotz.commands.intake.IntakeInToggle;
import bhs.devilbotz.commands.intake.IntakeStop;
import bhs.devilbotz.commands.intakeArm.IntakeArmDown;
import bhs.devilbotz.commands.intakeArm.IntakeArmStop;
import bhs.devilbotz.commands.intakeArm.IntakeArmUp;
import bhs.devilbotz.commands.shooter.ShooterForward;
import bhs.devilbotz.commands.shooter.ShooterForwardPID;
import bhs.devilbotz.commands.shooter.ShooterReverse;
import bhs.devilbotz.commands.shooter.ShooterStop;
import bhs.devilbotz.commands.transfer.TransferIn;
import bhs.devilbotz.commands.transfer.TransferOut;
import bhs.devilbotz.commands.transfer.TransferStop;
import bhs.devilbotz.subsystems.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import io.github.oblarg.oblog.Logger;


/**
 * The declaration class for the robot.
 * The structure of the robot (including subsystems, commands, and button mappings) should be declared here.
 *
 * @author Devilbotz
 * @version 1.0.0
 * @since 1.0.0
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined
    private final DriveTrain driveTrain = new DriveTrain();
    private final Intake intake = new Intake();
    private final Transfer transfer = new Transfer();
    private final Shooter shooter = new Shooter();
    private final IntakeArm intakeArm = new IntakeArm();

    // Joysticks
    private final Joystick joy = new Joystick(Constants.JOYSTICK);
    private final Joystick joy_two = new Joystick(Constants.JOYSTICK_TWO);

    // Camera system
    private final CameraSystem cameraSystem = new CameraSystem();
    private final PowerDistributionPanel powerDistributionPanel = new PowerDistributionPanel();

    // Autonomous chooser
    private final SendableChooser<Command> autonomousChooser = new SendableChooser<>();


    /**
     * The container for the robot
     * Contains subsystems, OI devices, and commands
     *
     * @since 1.0.0
     */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
        configureShuffleboard();

        intake.setDefaultCommand(new IntakeStop(intake, intakeArm));
        shooter.setDefaultCommand(new ShooterStop(shooter, transfer));
        intakeArm.setDefaultCommand(new IntakeArmStop(intakeArm));

        SmartDashboard.putData("Shooter", shooter);
        SmartDashboard.putData("Intake", intake);
        SmartDashboard.putData("Transfer", transfer);
        SmartDashboard.putData("IntakeArm", intakeArm);
        SmartDashboard.putData("DriveTrain", driveTrain);


        // The first argument is the root container
        // The second argument is whether logging and config should be given separate tabs
        Logger.configureLoggingAndConfig(this, false);
    }

    /**
     * Used to define button -> command mappings
     *
     * @since 1.0.0
     */
    private void configureButtonBindings() {
        driveTrain.setDefaultCommand(new DriveCommand(driveTrain,
                () -> -joy.getY(),
                () -> -joy_two.getY()
        ));

        new JoystickButton(joy_two, Constants.CAMERA_BUTTON)
                .whenPressed(new CameraToggle(cameraSystem));

        new JoystickButton(joy_two, Constants.INTAKE_BUTTON)
                .toggleWhenPressed(new IntakeInToggle(intake, intakeArm));

        new JoystickButton(joy_two, 3)
                .whileHeld(new TransferIn(transfer))
                .whenReleased(new TransferStop(transfer));

        new JoystickButton(joy, 3)
                .whileHeld(new TransferOut(transfer))
                .whenReleased(new TransferStop(transfer));

        new JoystickButton(joy, 8)
                .whileHeld(new ShooterForward(shooter, 1))
                .whenReleased(new ShooterStop(shooter, transfer));

        new JoystickButton(joy_two, 5)
                .toggleWhenPressed(new ShooterForwardPID(shooter, 4000));

        new JoystickButton(joy, 5)
                .whileHeld(new ShooterReverse(shooter, 4000));

        new JoystickButton(joy_two, 6)
                .whileHeld(new IntakeArmUp(intakeArm));

        new JoystickButton(joy_two, 7)
                .whileHeld(new IntakeArmDown(intakeArm));

        new JoystickButton(joy, 4)
                .whileHeld(new IntakeArmUp(intakeArm));

        new JoystickButton(joy, 2)
                .whileHeld(new IntakeArmDown(intakeArm));

        new JoystickButton(joy_two, 1)
                .whenReleased(new ShootTwoBalls(driveTrain, transfer, shooter));

        new JoystickButton(joy, 11)
                .whenPressed(new DefenseModeEnable(driveTrain, intakeArm));

        new JoystickButton(joy, 10)
                .whenPressed(new DefenseModeDisable(driveTrain, intakeArm));

    }

    private void configureShuffleboard() {
        ShootAndBackwardsAuto shootAndBackwardsAuto = new ShootAndBackwardsAuto(driveTrain, transfer, shooter);
        autonomousChooser.setDefaultOption("Shoot & Backwards Auto", shootAndBackwardsAuto);

        Backwards backwards = new Backwards(driveTrain);
        autonomousChooser.addOption("Backwards ONLY", backwards);

        Diagnostic diagnostic = new Diagnostic(driveTrain, intake, shooter, transfer, intakeArm);
        autonomousChooser.addOption("Diagnostic", diagnostic);

        Shuffleboard.getTab("Drive").add("Auto Chooser", autonomousChooser).withSize(2, 1).withPosition(0, 0);
    }


    /**
     * Passes the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     * @since 1.0.0
     */
    public Command getAutonomousCommand() {
        return autonomousChooser.getSelected();
    }
  
    public Joystick getJoy() {
        return joy;
    }

    public Joystick getJoyTwo() {
        return joy_two;
    }

    // public DriveTrain getDriveTrain() {
    //     return driveTrain;
    // }

    // public Intake getIntake() {
    //     return intake;
    // }

    public Transfer getTransfer() {
        return transfer;
    }

    public Shooter getShooter() {
        return shooter;
    }

    public DriveTrain getDriveTrain() {
        return driveTrain;
    }


}
