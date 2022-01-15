/*-------------------------------------------------------------------------------*/
/* Copyright (c) 2021-2022 BHS Devilbotz. All Rights Reserved.                   */
/* Open Source Software - may be modified, commercialized, distributed,          */
/* sub-licensed and used for private use under the terms of the License.md       */
/* file in the root of the source code tree.                                     */
/*                                                                               */
/* You MUST include the original copyright and license files in any and all      */
/* revised/modified code. You may NOT remove this header under any circumstance  */
/* unless explicitly noted                                                       */
/*-------------------------------------------------------------------------------*/

package bhs.devilbotz;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * The runner class for the robot.
 *
 * Do NOT Modify this file
 *
 * @author Devilbotz
 * @version 1.0.0
 * @since 1.0.0
 */

public final class Main {
    private Main() {}

   /**
    * The main method for the robot.
    * This method will be called when the robot runs.
    *
    * @param args The command line arguments.
    * @since 1.0.0
    * */
    public static void main(String... args)
    {
        RobotBase.startRobot(Robot::new);
    }
}
