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

/**
 * Stores numerical or boolean constants that will be used by the robot code.
 * Nothing functional should be put in this class.
 *
 * @author Devilbotz
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Constants {
    // OI Devices
    // Joystick
    public static final int JOYSTICK = 0;
    public static final int JOYSTICK_TWO = 1;

    public static final int APPROACH_BUTTON = 5;
    public static final int CAMERA_BUTTON = 2;

    // Auto Constants
    public static final class AutoConstants {
        public static final double WHEEL_DIAMETER_INCHES = 6;
    }
}
