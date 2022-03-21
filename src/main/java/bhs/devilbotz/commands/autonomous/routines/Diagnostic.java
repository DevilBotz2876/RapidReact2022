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

package bhs.devilbotz.commands.autonomous.routines;

import bhs.devilbotz.commands.autonomous.drive.DriveTimed;
import bhs.devilbotz.commands.autonomous.shooter.ShooterRPM;
import bhs.devilbotz.commands.autonomous.transfer.TransferInTimed;
import bhs.devilbotz.commands.autonomous.transfer.TransferOutTimed;
import bhs.devilbotz.commands.intake.IntakeIn;
import bhs.devilbotz.commands.intake.IntakeInToggle;
import bhs.devilbotz.commands.intake.IntakeStop;
import bhs.devilbotz.commands.intakeArm.IntakeArmDown;
import bhs.devilbotz.commands.intakeArm.IntakeArmStop;
import bhs.devilbotz.commands.intakeArm.IntakeArmUp;
import bhs.devilbotz.commands.shooter.ShooterForward;
import bhs.devilbotz.commands.shooter.ShooterReverse;
import bhs.devilbotz.commands.shooter.ShooterStop;
import bhs.devilbotz.subsystems.*;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/**
 * AutoTest - Test autonomous routines
 *
 * @author Devilbotz
 * @version 1.0.5
 * @since 1.0.5
 */
public class Diagnostic extends SequentialCommandGroup {
    /**
     * AutoTest constructor
     * @param drive {@link DriveTrain} subsystem
     * @since 1.0.0
     */
    public Diagnostic(DriveTrain drive, Intake intake, Shooter shooter, Transfer transfer, IntakeArm intakeArm) {
        addCommands(
                new DriveTimed(drive, 1, -0.7),
                new WaitCommand(0.5),
                new DriveTimed(drive, 1, 0.7),
                new WaitCommand(0.5),
                new IntakeIn(intake, transfer),
                new WaitCommand(0.5),
                new IntakeStop(intake),
                new WaitCommand(0.5),
                new ShooterRPM(shooter),
                new WaitCommand(0.5),
                new ShooterStop(shooter, transfer),
                new ShooterForward(shooter,  0.5),
                new WaitCommand(0.5),
                new ShooterReverse(shooter, 0.5),
                new WaitCommand(0.5),
                new ShooterStop(shooter, transfer),
                new WaitCommand(0.5),
                new TransferInTimed(transfer, 1),
                new TransferOutTimed(transfer, 1),
                new IntakeArmUp(intakeArm),
                new WaitCommand(1),
                new IntakeArmDown(intakeArm),
                new WaitCommand(1),
                new IntakeArmStop(intakeArm)
        );
    }
}
