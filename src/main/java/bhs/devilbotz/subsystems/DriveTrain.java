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

package bhs.devilbotz.subsystems;

import bhs.devilbotz.Constants;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * DriveTrain subsystem
 *
 * @author DevilBotz
 * @version 1.0.0
 * @since 1.0.0
 */
public class DriveTrain extends SubsystemBase {
    // Define talons
    private static final WPI_TalonSRX leftMaster = new WPI_TalonSRX(1);
    private static final WPI_TalonSRX rightMaster = new WPI_TalonSRX(3);
    private static final WPI_TalonSRX leftFollower = new WPI_TalonSRX(2);
    private static final WPI_TalonSRX rightFollower = new WPI_TalonSRX(4);

    // Define NAVX
    private static final AHRS navx = new AHRS(SPI.Port.kMXP);

    // Define differential drive
    private final DifferentialDrive differentialDrive = new DifferentialDrive(leftMaster, rightMaster);

    private final SlewRateLimiter leftSlew = new SlewRateLimiter(2);

    private final SlewRateLimiter rightSlew = new SlewRateLimiter(2);

    /**
     * The constructor for the DriveTrain subsystem
     *
     * @since 1.0.0
     */
    public DriveTrain() {
        setupTalons();
        resetNavx();
    }

  
    /**
     * Gets the NAVX
     *
     * @return The NAVX
     */
    public static AHRS getNavx() {
        return navx;
    }

    /**
     * Sets up the talons
     *
     * @since 1.0.0
     */
    private void setupTalons() {
        rightMaster.setInverted(true);
        leftMaster.setInverted(false);
        // Set the talons to follow each other
        rightFollower.follow(rightMaster);
        leftFollower.follow(leftMaster);

        // Set the follower talons to invert to match the master talons
        rightFollower.setInverted(InvertType.FollowMaster);
        leftFollower.setInverted(InvertType.FollowMaster);

        // Set the sensor phase of the master talons
        rightMaster.setSensorPhase(true);
        leftMaster.setSensorPhase(true);
    }

    /**
     * Resets/sets up the navx
     *
     * @since 1.0.0
     */
    public void resetCalNavx() {
        // Reset the gyro
        navx.reset();

        // Calibrate the gyro
        navx.calibrate();

    }

    /**
     * Reset the NAVX
     *
     * @since 1.0.0
     */
    public void resetNavx() {
        navx.reset();
    }

    /**
     * Resets the encoders
     *
     * @since 1.0.0
     */
    public void resetEncoders() {
        leftMaster.setSelectedSensorPosition(0, 0, 0);
        rightMaster.setSelectedSensorPosition(0, 0, 0);
    }

    /**
     * Gets the left master talon
     *
     * @return The left master talon
     *
     * @since 1.0.0
     */
    public WPI_TalonSRX getLeftMaster() {
        return leftMaster;
    }

    /**
     * Gets the right master talon
     *
     * @return The right master talon
     *
     * @since 1.0.0
     */
    public WPI_TalonSRX getRightMaster() {
        return rightMaster;
    }

    /**
     * Calculates the distance traveled by the robot by reading encoder values
     *
     * @return the linear distance traveled by the robot in inches
     */
    public double getAverageEncoderDistance() {
        double leftDistance = leftMaster.getSelectedSensorPosition()
                * (Constants.AutoConstants.WHEEL_DIAMETER_INCHES * Math.PI / 4096);
        double rightDistance = rightMaster.getSelectedSensorPosition()
                * (Constants.AutoConstants.WHEEL_DIAMETER_INCHES * Math.PI / 4096);
        return ((Math.abs(leftDistance) + Math.abs(rightDistance)) / 2);
    }


    /**
     * Tank drive method
     *
     * @param leftSpeed The speed of the left side of the robot
     * @param rightSpeed The speed of the right side of the robot
     */
    public void tankDrive(double leftSpeed, double rightSpeed) {
        differentialDrive.tankDrive(leftSlew.calculate(leftSpeed), rightSlew.calculate(rightSpeed));
    }

    /**
     * Arcade drive method
     *
     * @param speed The speed of the robot
     * @param rotation The rotation of the robot
     */
    public void arcadeDrive(double speed, double rotation) {
        differentialDrive.arcadeDrive(speed, rotation);
    }

    /**
     * Set the talons modes
     *
     * @param mode The mode to set the talons to
     */
    public void setTalonMode(NeutralMode mode) {
        leftMaster.setNeutralMode(mode);
        rightMaster.setNeutralMode(mode);
        leftFollower.setNeutralMode(mode);
        rightFollower.setNeutralMode(mode);
    }

    /**
     * Gets the gyro angles as a Rotational2d
     *
     * @return the gyro angles as a Rotational2d
     */
    public Rotation2d getAngle() {
        // Negative because WPILib Gyro is CW positive
        return Rotation2d.fromDegrees(-navx.getAngle());
    }

        /**
     * This method will be called once per scheduler run when
     *
     * @since 1.0.5
     */
    @Override
    public void periodic() {

    }

    /**
     * This method will be called once per scheduler run when in simulation
     *
     * @since 1.0.5
     */
    @Override
    public void simulationPeriodic() {
        

    }

}
