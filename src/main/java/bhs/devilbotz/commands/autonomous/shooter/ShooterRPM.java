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

package bhs.devilbotz.commands.autonomous.shooter;

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
public class ShooterRPM extends CommandBase {
    private final Shooter shooter;

    /**
     * IntakeStop constructor
     * @since 1.0.5
     */
    public ShooterRPM(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {

        System.out.println(shooter.getGoalWidget().getSelected().getName());
        if (shooter.getGoalWidget().getSelected().getName().equals("SetHighGoal")) {
            //loop 500 times
            for (int i = 0; i < 500; i++) {
                System.out.println("HIGH");
            }
            shooter.setHighGoal();
        } else {
            shooter.setLowGoal();
        }
    }

    /**
     * Executed when the command is initially scheduled
     * @since 1.0.5
     */
    @Override
    public void execute() {
        shooter.enable();
    }

    /**
     * Called once the command ends or is interrupted.
     * @param interrupted True if the command was interrupted, false otherwise.
     * @since 1.0.5
     */
    @Override
    public void end(boolean interrupted) {
    }

    /**
     * Returns true when the command should end.
     * @return True if the command should end, false otherwise.
     */
    @Override
    public boolean isFinished() {
        return shooter.atSetpoint();
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
