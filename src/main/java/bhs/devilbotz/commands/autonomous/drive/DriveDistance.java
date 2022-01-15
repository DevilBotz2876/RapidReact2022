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
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveDistance extends CommandBase {
    private final DriveTrain m_drive;
    private final double m_distance;
    private final double m_speed;
    private final SlewRateLimiter filter;

    /**
     * Creates a new DriveDistance.
     *
     * @param inches The number of inches the robot will drive
     * @param speed The speed at which the robot will drive
     * @param drive The drive subsystem on which this command will run
     */
    public DriveDistance(DriveTrain drive, double inches, double speed) {
        m_distance = inches;
        m_speed = speed;
        m_drive = drive;
        addRequirements(drive);

        filter = new SlewRateLimiter(4);
    }

    /**
     * Runs when the command is first scheduled.
     */
    @Override
    public void initialize() {
        m_drive.resetEncoders();
    }

    /**
     * Called every time the scheduler runs while the command is scheduled.
     */
    @Override
    public void execute() {
        m_drive.arcadeDrive(filter.calculate(m_speed), 0);
    }

    /**
     * Called once the command ends or is interrupted.
     *
     * @param interrupted Whether the command was interrupted.
     */
    @Override
    public void end(boolean interrupted) {
        m_drive.arcadeDrive(0, 0);
    }

    /**
     * Returns true when the command should end.
     *
     * @return Whether the command should end.
     */
    @Override
    public boolean isFinished() {
        return m_drive.getAverageEncoderDistance() >= m_distance;
    }
}