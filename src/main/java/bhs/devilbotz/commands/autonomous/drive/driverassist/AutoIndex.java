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

package bhs.devilbotz.commands.autonomous.drive.driverassist;

import bhs.devilbotz.commands.intake.IntakeInToggle;
import bhs.devilbotz.commands.intake.IntakeStop;
import bhs.devilbotz.commands.transfer.TransferIn;
import bhs.devilbotz.commands.transfer.TransferStop;
import bhs.devilbotz.subsystems.Intake;
import bhs.devilbotz.subsystems.IntakeArm;
import bhs.devilbotz.subsystems.Shooter;
import bhs.devilbotz.subsystems.Transfer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * IntakeStop command
 * Stops the intake motor
 *
 * @author  Devilbotz
 * @version 1.0.0
 * @since 1.0.5
 */
public class AutoIndex extends CommandBase {
    private final Intake intake;
    private final Transfer transfer;
    private final IntakeArm intakeArm;

    /**
     * IntakeStop constructor
     * @since 1.0.5
     */
    public AutoIndex(Intake intake, Transfer transfer, IntakeArm intakeArm) {
        this.intake = intake;
        this.transfer = transfer;
        this.intakeArm = intakeArm;
    }

    @Override
    public void initialize() {
    }

    /**
     * Executed when the command is initially scheduled
     * @since 1.0.5
     */
    @Override
    public void execute() {
        new TransferIn(transfer);
        new IntakeInToggle(intake, intakeArm);
    }

    /**
     * Called once the command ends or is interrupted.
     * @param interrupted True if the command was interrupted, false otherwise.
     * @since 1.0.5
     */
    @Override
    public void end(boolean interrupted) {
        new TransferStop(transfer);
        new IntakeStop(intake, intakeArm);
    }

    /**
     * Returns true when the command should end.
     * @return True if the command should end, false otherwise.
     */
    @Override
    public boolean isFinished() {
        return false;
    }

    /**
     * If the command should run when the robot is disabled
     * @return True if the command should run when the robot is disabled, false otherwise.
     */
    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
