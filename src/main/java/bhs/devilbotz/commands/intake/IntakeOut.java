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

package bhs.devilbotz.commands.intake;

import bhs.devilbotz.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * IntakeOut command
 * Runs the intake motor
 *
 * @author Devilbotz
 * @version 1.0.0
 * @since 1.0.5
 */
public class IntakeOut extends CommandBase {
    private final Intake intake;

    /**
     * IntakeOut constructor
     *
     * @param intake {@link Intake} subsystem
     *
     * @since 1.0.5
     */
    public IntakeOut(Intake intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    /**
     * Executed when the command is initially scheduled
     *
     * @since 1.0.5
     */
    @Override
    public void execute() {
        intake.set(-intake.getIntakeSpeedWidget().getDouble(0.65));

    }

    /**
     * Called once the command ends or is interrupted.
     *
     * @param interrupted True if the command was interrupted, false otherwise.
     *
     * @since 1.0.5
     */
    @Override
    public void end(boolean interrupted) {
    }

    /**
     * Returns true when the command should end.
     *
     * @return True if the command should end, false otherwise.
     */
    @Override
    public boolean isFinished() {
        return false;
    }

    /**
     * If the command should run when the robot is disabled
     *
     * @return True if the command should run when the robot is disabled, false otherwise.
     */
    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
