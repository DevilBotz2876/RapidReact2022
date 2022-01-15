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

import edu.wpi.first.wpilibj2.command.CommandBase;
import bhs.devilbotz.subsystems.DriveTrain;

public class DriveRotate extends CommandBase {
    private final DriveTrain m_drive;
    private final double degrees, rotationSpeed;
    private double initialRotation;

    public DriveRotate(DriveTrain drive, double degrees, double rotationSpeed) {
        this.degrees = degrees;
        this.rotationSpeed = rotationSpeed;
        m_drive = drive;
        addRequirements(drive);
    }

    /**
     * Called when the command is initially scheduled.
     */
    @Override
    public void initialize() {
        m_drive.resetNavx();
        initialRotation = m_drive.getAngle().getDegrees();
    }

    /**
     * Called every time the scheduler runs while the command is scheduled.
     */
    @Override
    public void execute() {
        m_drive.tankDrive(rotationSpeed, -rotationSpeed);
    }

    /**
     * Called once the command ends or is interrupted.
     *
     * @param interrupted whether the command was interrupted by another one
     */
    @Override
    public void end(boolean interrupted) {
        m_drive.tankDrive(0, 0);
    }

    /**
     * Returns true when the command should end.
     *
     * @return whether the command should end
     */
    @Override
    public boolean isFinished() {
        if (degrees < 0) {
            return m_drive.getAngle().getDegrees() <= initialRotation + degrees;
        } else {
            return m_drive.getAngle().getDegrees() >= initialRotation + degrees;
        }
    }
}