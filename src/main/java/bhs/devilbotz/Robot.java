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

import bhs.devilbotz.commands.transfer.TransferIn;
import bhs.devilbotz.subsystems.DriveTrain;
import bhs.devilbotz.subsystems.Intake;
import bhs.devilbotz.subsystems.Shooter;
import com.pathplanner.lib.PathPlanner;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.WPILibVersion;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import io.github.oblarg.oblog.Logger;
import io.github.oblarg.oblog.annotations.Log;

/**
 * This is the main robot class.
 * It is automatically ran when the robot is started, and the correct methods are called.
 *
 * @author Devilbotz
 * @version 1.0.0
 * @since 1.0.0
 */
public class Robot extends TimedRobot {
    private Command autonomousCommand;

    private double time;

    private RobotContainer robotContainer;
    private Shooter shooter;
    private DriveTrain driveTrain;
    private final RamseteController ramseteController = new RamseteController();


    /**
     * This method is run when the robot is first started up and is used for initialization
     *
     * @since 1.0.0
     */
    @Override
    public void robotInit() {
        // Instantiate the RobotContainer.
        robotContainer = new RobotContainer();
        shooter = robotContainer.getShooter();
        driveTrain = robotContainer.getDriveTrain();
        Logger.configureLoggingAndConfig(this, false);
        
    }
    


    /**
     * This method is called every robot packet, no matter the mode.
     * This is used for diagnostic purposes.
     * <p>
     * This runs after the mode specific periodic methods, but before LiveWindow and
     * SmartDashboard integrated updating.
     *
     * @since 1.0.0
     */
    @Override
    public void robotPeriodic() {
        // Runs the Scheduler.
        CommandScheduler.getInstance().run();
        Logger.updateEntries();
    }

    /**
     * This method is called once when the robot is disabled.
     *
     * @since 1.0.0
     */
    @Override
    public void disabledInit() {
        shooter.setIsAuto(false);
    }

    /**
     * This method is called periodically when the robot is disabled.
     *
     * @since 1.0.0
     */
    @Override
    public void disabledPeriodic() {
    }


    /**
     * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
     */
    @Override
    public void autonomousInit() {
        autonomousCommand = robotContainer.getAutonomousCommand();

        /*
         * String autoSelected = SmartDashboard.getString("Auto Selector",
         * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
         * = new MyAutoCommand(); break; case "Default Auto": default:
         * autonomousCommand = new ExampleCommand(); break; }
         */

        // schedule the autonomous command (example)
        if (autonomousCommand != null) {
            autonomousCommand.schedule();
        }
    }


    /**
     * This method is called periodically during autonomous.
     *
     * @since 1.0.0
     */
    @Override
    public void autonomousPeriodic() {
    }
    /**
     * This method is called once when the robot is in teleoperated mode.
     *
     * @since 1.0.0
     */
    @Override
    public void teleopInit() {
        // Make sure that the autonomous stops running when teleop starts running.
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }


    /**
     * This method is called periodically while the robot is in teleoperated mode.
     *
     * @since 1.0.0
     */
    @Override
    public void teleopPeriodic() {
        if (!robotContainer.getShooter().isAuto() && !robotContainer.getTransfer().isIntakeOut()) {
            if (robotContainer.getTransfer().ballPresent()) {
                if (time <= 100) {
                    robotContainer.getTransfer().set(0.7);
                } else {
                    robotContainer.getTransfer().stop();
                }
                time += 1;
            } else {
                robotContainer.getTransfer().stop();
                time = 0;
            }
        }
    }


    /**
     * This robot is called once when the robot is in test mode.
     *
     * @since 1.0.0
     */
    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }


    /**
     * This method is called periodically during test mode.
     *
     * @since 1.0.0
     */
    @Override
    public void testPeriodic() {
    }
}
