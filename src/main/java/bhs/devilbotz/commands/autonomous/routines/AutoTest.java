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

import bhs.devilbotz.Robot;
import bhs.devilbotz.commands.autonomous.drive.DriveRotate;
import bhs.devilbotz.commands.autonomous.drive.DriveTimed;
import bhs.devilbotz.subsystems.DriveTrain;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * AutoTest - Test autonomous routines
 *
 * @author Devilbotz
 * @version 1.0.5
 * @since 1.0.5
 */
public class AutoTest extends SequentialCommandGroup {
    /**
     * AutoTest constructor
     * @param drive {@link DriveTrain} subsystem
     * @since 1.0.0
     */
    public AutoTest(DriveTrain drive) {
        addCommands(
                new DriveRotate(drive, -90, 0.5),
                new DriveTimed(drive, 1, 0.7));
    }
}
