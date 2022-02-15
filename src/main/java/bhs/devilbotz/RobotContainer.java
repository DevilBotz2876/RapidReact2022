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
import bhs.devilbotz.commands.autonomous.drive.driverassist.Approach;
import bhs.devilbotz.commands.autonomous.routines.AutoTest;
import bhs.devilbotz.commands.camera.CameraToggle;
import bhs.devilbotz.subsystems.CameraSystem;
import bhs.devilbotz.subsystems.DriveTrain;
import bhs.devilbotz.subsystems.Indexing;
import bhs.devilbotz.subsystems.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import io.github.oblarg.oblog.Logger;


/**
 * The declaration class for the robot
 * <p>
 * The structure of the robot (including subsystems, commands, and button mappings) should be declared here.
 *
 * @author Devilbotz
 * @version 1.0.0
 * @since 1.0.0
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined
    private final DriveTrain driveTrain = new DriveTrain();
    private final Indexing indexing = new Indexing();

    // private final PowerDistributionPanel pdp = new PowerDistributionPanel();
    // TODO: Add more subsystems once they are physically attached to robot.  
    // Careful not to add them here before they are ready else robot code may not run.

    // Joysticks
    private final Joystick joy = new Joystick(Constants.JOYSTICK);
    private final Joystick joy_two = new Joystick(Constants.JOYSTICK_TWO);

    // Camera system
    private final CameraSystem cameraSystem = new CameraSystem();

    // Autonomous chooser
    private final SendableChooser<Command> autonomousChooser = new SendableChooser<>();


    /**
     * The container for the robot
     * <p>
     * Contains subsystems, OI devices, and commands
     *
     * @since 1.0.0
     */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
        configureShuffleboard();

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
        ));;

        new JoystickButton(joy_two, Constants.APPROACH_BUTTON)
                .whenPressed(new Approach(driveTrain, this));

        new JoystickButton(joy_two, Constants.CAMERA_BUTTON)
                .whenPressed(new CameraToggle(cameraSystem));
    }

    private void configureShuffleboard() {
        AutoTest autoTest = new AutoTest(driveTrain);
        autonomousChooser.addOption("Auto Test", autoTest);

        Shuffleboard.getTab("Drive").add("Auto Chooser", autonomousChooser).withSize(2, 1).withPosition(0, 0);
    }


    /**
     * Passes the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     *
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

    public DriveTrain getDriveTrain() {
        return driveTrain;
    }

    public Indexing getIndexing() {
        return indexing;
    }
}
