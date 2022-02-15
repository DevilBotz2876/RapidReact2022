/*-------------------------------------------------------------------------------*/
/* Copyright (c) 2021-2022 BHS Devilbotz. All Rights Reserved.                   */
/* Open Source Software - may be modified, commercialized, distributed,          */
/* sub-licensed and used for private use under the terms of the License.md       */
/* file in the root of the source code tree.                                     */
/*                                                                               */
/* When doing any of the above, you MUST include the original                    */
/* copyright and license files in any and all revised/modified code.             */
/* You may NOT remove this header under any circumstance unless explicitly noted */
/*-------------------------------------------------------------------------------*/

package bhs.devilbotz.commands.autonomous.drive;

import bhs.devilbotz.subsystems.DriveTrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
/**
 * DriveTimed - Drives the robot for a certain amount of time
 *
 * @author Devilbotz
 * @version 1.0.5
 * @since 1.0.0
 */
public class DriveTimed extends CommandBase {
    private final DriveTrain drive;
    private final double time, speed;
    private long startTime;

    /**
     * DriveTimed constructor
     * @param drive {@link DriveTrain} subsystem
     * @param time time to drive
     * @param speed speed to drive at
     * @since 1.0.0
     */
    public DriveTimed(DriveTrain drive, double time, double speed) {
        this.drive = drive;
        this.time = time;
        this.speed = speed;
        addRequirements(drive);
    }

    /**
     * Called when the command is initially scheduled.
     */
    @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Called every time the scheduler runs while the command is scheduled.
     */
    @Override
    public void execute() {
        drive.tankDrive(speed, speed);
    }

    /**
     * Called once the command ends or is interrupted.
     *
     * @param interrupted whether the command was interrupted by another one
     */
    @Override
    public void end(boolean interrupted) {
    }

    /**
     * Returns true when the command should end.
     *
     * @return whether the command should end
     */
    @Override
    public boolean isFinished() {
        return (System.currentTimeMillis() - startTime) / 1000.0 >= time;
    }
}