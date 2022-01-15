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
import bhs.devilbotz.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The declaration class for the robot
 *
 * The structure of the robot (including subsystems, commands, and button mappings) should be declared here.
 *
 * @author Devilbotz
 * @version 1.0.0
 * @since 1.0.0
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined
    private final DriveTrain driveTrain = new DriveTrain();

    // Joysticks
    private final Joystick joy = new Joystick(Constants.JOYSTICK);
    private final Joystick joy_two = new Joystick(Constants.JOYSTICK_TWO);



    /**
     * The container for the robot
     *
     * Contains subsystems, OI devices, and commands
     *
     * @since 1.0.0
     */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
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
    }
    
    
    /**
     * Passes the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     * @since 1.0.0
     */
    /*
    public Command getAutonomousCommand()
    {
        // An ExampleCommand will run in autonomous
        return autoCommand;
    }
     */
}
