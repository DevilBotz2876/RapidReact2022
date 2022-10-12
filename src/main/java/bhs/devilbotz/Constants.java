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

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

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

    public static final int INTAKE_BUTTON = 4;

    // Auto Constants
    public static final class AutoConstants {
        public static final double WHEEL_DIAMETER_INCHES = 6;

        // Reasonable baseline values for a RAMSETE follower in units of meters and seconds
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;

        public static final double kMaxSpeedMetersPerSecond = 3;
        public static final double kMaxAccelerationMetersPerSecondSquared = 1;
    }

    // TODO: Get real values at meeting - 4/13/2022
    public static final class DriveConstants {

        public static final int countsPerRev = 4096;  //Encoder counts per revolution of the motor shaft.
        public static final double sensorGearRatio = 1; //Gear ratio is the ratio between the *encoder* and the wheels.  On the AndyMark drivetrain, encoders mount 1:1 with the gearbox shaft.
        public static final double gearRatio = 10.71;
        public static final double wheelRadiusInches = 3;
        public static final double trackWidthMeters = 0.5588;
        public static final int k100msPerSecond = 10;
        public static final int CIMCountPerSide = 2;
        public static final double kPDriveVel = 8.5;

        public static final DifferentialDriveKinematics driveKinematics =
                new DifferentialDriveKinematics(trackWidthMeters);

        // Variables taken from the SysID Tool
        public static final class SysID {
            public static final double KvLinear = 1.98;
            public static final double KaLinear = 0.2;
            public static final double KvAngular = 1.5;
            public static final double KaAngular = 0.3;

            public static final double ksVolts = 0.22;
            public static final double kvVoltSecondsPerMeter = 1.98;
            public static final double kaVoltSecondsSquaredPerMeter = 0.2;
        }
    }
}
