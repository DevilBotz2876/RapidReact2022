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
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.WPILibVersion;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
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

    private final RamseteController ramseteController = new RamseteController();

    // Trajectory following
    Trajectory testPath = PathPlanner.loadPath("Test Path", 3, 1);

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
        robotContainer.getDriveTrain().getField().getObject("traj").setTrajectory(testPath);
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

    Timer timer;
    @Override
    public void autonomousInit() {
        timer = new Timer();
        timer.start();
        /*
        autonomousCommand = robotContainer.getAutonomousCommand();

        if (autonomousCommand != null) {
            autonomousCommand.schedule();
        }

         */

        robotContainer.getDriveTrain().resetOdometry(testPath.getInitialPose());
    }


    /**
     * This method is called periodically during autonomous.
     *
     * @since 1.0.0
     */
    @Override
    public void autonomousPeriodic() {
        if (timer.get() < testPath.getTotalTimeSeconds()) {
            // Get the desired pose from the trajectory.
            var desiredPose = testPath.sample(timer.get());

            // Get the reference chassis speeds from the Ramsete controller.
            var refChassisSpeeds = ramseteController.calculate(robotContainer.getDriveTrain().getPose(), desiredPose);

            // Set the linear and angular speeds.
            robotContainer.getDriveTrain().arcadeDrive(refChassisSpeeds.vxMetersPerSecond, refChassisSpeeds.omegaRadiansPerSecond);
        } else {
            robotContainer.getDriveTrain().arcadeDrive(0,0 );
        }
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
