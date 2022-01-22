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
import bhs.devilbotz.commands.autonomous.routines.AutoTest;
import bhs.devilbotz.commands.intake.IntakeIn;
import bhs.devilbotz.commands.intake.IntakeStop;
import bhs.devilbotz.commands.shooter.ShooterIn;
import bhs.devilbotz.commands.shooter.ShooterStop;
import bhs.devilbotz.subsystems.DriveTrain;
import bhs.devilbotz.subsystems.Intake;
import bhs.devilbotz.subsystems.Shooter;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

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

    // Joysticks
    private final Joystick joy = new Joystick(Constants.JOYSTICK);
    private final Joystick joy_two = new Joystick(Constants.JOYSTICK_TWO);

    // Driver station
    private final DriverStation.Alliance alliance = DriverStation.getAlliance();

    // Autonomous chooser
    private final SendableChooser<Command> autonomousChooser = new SendableChooser<>();

    // Subsytems
    // private final Intake intake = new Intake();
    private final Shooter shooter = new Shooter();


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
        printTeamData();
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
/*
        intake.setDefaultCommand(new IntakeStop(intake));

        new JoystickButton(joy, 1)
                .whileHeld(new IntakeIn(intake, this))
                .whenReleased(new IntakeStop(intake));
 */
        shooter.setDefaultCommand(new ShooterStop(shooter));

        new JoystickButton(joy_two, 1)
                .whileHeld(new ShooterIn(shooter, this))
                .whenReleased(new ShooterStop(shooter));
    }

    private void configureShuffleboard() {
        AutoTest autoTest = new AutoTest(driveTrain);
        autonomousChooser.addOption("Auto Test", autoTest);
        SmartDashboard.putData("Auto Chooser", autonomousChooser);
    }

    private void printTeamData() {
        System.out.println("Alliance: " + alliance);
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

    public Joystick getJoy_two() {
        return joy_two;
    }
}
