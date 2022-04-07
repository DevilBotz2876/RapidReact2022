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
 * DriveDistance - Drives the robot for a certain distance
 *
 * @author Devilbotz
 * @version 1.0.5
 * @since 1.0.0
 */
public class DriveTurnLeft extends CommandBase {
    private final DriveTrain drive;
    private final double leftInches, rightInches, leftSpeed, rightSpeed;

    /**
     * DriveDistance constructor
     * @param drive {@link DriveTrain} subsystem
     * @since 1.0.0
     */
    public DriveTurnLeft(DriveTrain drive, double leftInches, double rightInches, double leftSpeed, double rightSpeed) {
        this.leftInches = leftInches;
        this.rightInches = rightInches;
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
        this.drive = drive;
        addRequirements(drive);
    }

    /**
     * Runs when the command is first scheduled.
     */
    @Override
    public void initialize() {
        drive.resetEncoders();
    }

    /**
     * Called every time the scheduler runs while the command is scheduled.
     */
    @Override
    public void execute() {
        drive.tankDrive(leftSpeed, rightSpeed);
    }

    /**
     * Called once the command ends or is interrupted.
     *
     * @param interrupted Whether the command was interrupted.
     */
    @Override
    public void end(boolean interrupted) {
        drive.tankDrive(0, 0);
    }

    /**
     * Returns true when the command should end.
     *
     * @return Whether the command should end.
     */
    @Override
    public boolean isFinished() {
        return drive.getLeftEncoderDistance() <= leftInches;
    }
}