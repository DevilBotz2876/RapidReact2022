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
import bhs.devilbotz.commands.shooter.ShooterForward;
import bhs.devilbotz.commands.shooter.ShooterInstantStop;
import bhs.devilbotz.commands.shooter.ShooterStop;
import bhs.devilbotz.commands.transfer.TransferInstantStop;
import bhs.devilbotz.commands.transfer.TransferStop;
import bhs.devilbotz.subsystems.DriveTrain;
import bhs.devilbotz.subsystems.Shooter;
import bhs.devilbotz.subsystems.Transfer;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

/**
 * AutoTest - Test autonomous routines
 *
 * @author Devilbotz
 * @version 1.0.5
 * @since 1.0.5
 */
public class ShootAndBackwardsAuto extends SequentialCommandGroup {
    /**
     * AutoTest constructor
     * @param drive {@link DriveTrain} subsystem
     * @since 1.0.0
     */
    public ShootAndBackwardsAuto(DriveTrain drive, Transfer transfer, Shooter shooter) {
        addCommands(
                new TransferInTimed(transfer, 1),
                new WaitCommand(0.25),
                new TransferOutTimed(transfer, 0.25),
                new WaitCommand(1),
                new ShooterRPM(shooter),
                new WaitCommand(0.5),
                new TransferInTimed(transfer, 2),
                new TransferInstantStop(transfer),
                new ShooterInstantStop(shooter),
                new WaitCommand(1.5),
                new DriveTimed(drive, 1.5, -0.75)
        );
    }
}
