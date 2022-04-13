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
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXSimCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.hal.simulation.SimDeviceDataJNI;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * DriveTrain subsystem
 *
 * @author DevilBotz
 * @version 1.0.0
 * @since 1.0.0
 */
public class DriveTrain extends SubsystemBase {
    // TODO: Implement ProtonVision Camera for verifying robot position on field: More Info: https://docs.google.com/document/d/1HVLgF9lp8mT0i6iZaRI6uPFWSgSlUpih5I0hVsatgqA/edit?usp=sharing

    // Define talons
    private static final WPI_TalonSRX leftMaster = new WPI_TalonSRX(1);
    private static final WPI_TalonSRX rightMaster = new WPI_TalonSRX(3);
    private static final WPI_TalonSRX leftFollower = new WPI_TalonSRX(2);
    private static final WPI_TalonSRX rightFollower = new WPI_TalonSRX(4);

    // Define Gyroscope (NAVX)
    private static final AHRS navx = new AHRS(SPI.Port.kMXP);

    // Define differential drive
    private final DifferentialDrive differentialDrive = new DifferentialDrive(leftMaster, rightMaster);
    private final SlewRateLimiter leftSlew = new SlewRateLimiter(5);
    private final SlewRateLimiter rightSlew = new SlewRateLimiter(5);

    // Create the simulation model of our drivetrain.
    private final DifferentialDrivetrainSim driveSim = new DifferentialDrivetrainSim(
            // Create a linear system from our identification gains.
            LinearSystemId.identifyDrivetrainSystem(Constants.DriveConstants.SysID.KvLinear, Constants.DriveConstants.SysID.KaLinear, Constants.DriveConstants.SysID.KvAngular, Constants.DriveConstants.SysID.KaAngular),
            DCMotor.getCIM(Constants.DriveConstants.CIMCountPerSide),       // 2 CIM motors on each side of the drivetrain.
            Constants.DriveConstants.gearRatio,                    // 7.29:1 gearing reduction.
            Constants.DriveConstants.trackWidthMeters,                  // The track width is 0.7112 meters.
            Units.inchesToMeters(Constants.DriveConstants.wheelRadiusInches), // The robot uses 3" radius wheels.

            // The standard deviations for measurement noise:
            // x and y:          0.001 m
            // heading:          0.001 rad
            // l and r velocity: 0.1   m/s
            // l and r position: 0.005 m
            VecBuilder.fill(0.001, 0.001, 0.001, 0.1, 0.1, 0.005, 0.005));

    // Define objects for simulated inputs into Talon
    TalonSRXSimCollection leftMasterSim = leftMaster.getSimCollection();
    TalonSRXSimCollection rightMasterSim = rightMaster.getSimCollection();

    // Define field for displaying robot position
    Field2d field = new Field2d();

    // Define the odometry object using the navx
    DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(navx.getRotation2d());

    // NAVX Error correcting P value in order to keep the robot straight
    double kP = 0.35;

    /**
     * The constructor for the DriveTrain subsystem
     *
     * @since 1.0.0
     */
    public DriveTrain() {
        setupTalons();
        resetNavx();

        // Add the field to the shuffleboard drivers tab
        Shuffleboard.getTab("Drive").add("Field", field).withPosition(6, 1).withSize(5, 3).withWidget(BuiltInWidgets.kField);
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
        rightMaster.setInverted(RobotBase.isSimulation());

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
     * @since 1.0.0
     */
    public double getAverageEncoderDistance() {
        double leftDistance = leftMaster.getSelectedSensorPosition()
                * (Constants.AutoConstants.WHEEL_DIAMETER_INCHES * Math.PI / 4096);
        double rightDistance = rightMaster.getSelectedSensorPosition()
                * (Constants.AutoConstants.WHEEL_DIAMETER_INCHES * Math.PI / 4096);
        return ((Math.abs(leftDistance) + Math.abs(rightDistance)) / 2);
    }

    /**
     * Calculates the distance traveled by the robot by reading encoder values for the left encoder
     * @return the linear distance traveled by the left side of the robot in inches
     *
     * @since 3/25/2022
     */
    public double getLeftEncoderDistance() {
        return leftMaster.getSelectedSensorPosition()
                * (Constants.AutoConstants.WHEEL_DIAMETER_INCHES * Math.PI / 4096);
    }

    /**
     * Calculates the distance traveled by the robot by reading encoder values for the right encoder
     * @return the linear distance traveled by the right side of the robot in inches
     *
     * @since 4/13/2022
     */
    public double getRightEncoderDistance() {
        return leftMaster.getSelectedSensorPosition()
                * (Constants.AutoConstants.WHEEL_DIAMETER_INCHES * Math.PI / 4096);
    }

    /**
     * Tank drive method
     *
     * @param leftSpeed The speed of the left side of the robot
     * @param rightSpeed The speed of the right side of the robot
     */
    public void tankDrive(double leftSpeed, double rightSpeed) {
        double error = -navx.getRate();

        // Set the speed of the left and right sides of the robot while correcting for the gyro error
        // Implementation of a simple, pure code, P loop (PID)
        differentialDrive.tankDrive(leftSlew.calculate(leftSpeed) + kP * error, rightSlew.calculate(rightSpeed) + kP * error);
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
     * This method will be called once per scheduler run
     *
     * @since 1.0.5
     */
    @Override
    public void periodic() {
        odometry.update(navx.getRotation2d(),
                nativeUnitsToDistanceMeters(leftMaster.getSelectedSensorPosition()),
                nativeUnitsToDistanceMeters(rightMaster.getSelectedSensorPosition()));
        field.setRobotPose(odometry.getPoseMeters());
    }

    /**
     * This method will be called once per scheduler run when in simulation
     *
     * @since 1.0.5
     */
    @Override
    public void simulationPeriodic() {
        leftMasterSim.setBusVoltage(RobotController.getBatteryVoltage());
        rightMasterSim.setBusVoltage(RobotController.getBatteryVoltage());

        /*
         * CTRE simulation is low-level, so SimCollection inputs
         * and outputs are not affected by SetInverted(). Only
         * the regular user-level API calls are affected.
         *
         * WPILib expects +V to be forward.
         * Positive motor output lead voltage is ccw. We observe
         * on our physical robot that this is reverse for the
         * right motor, so negate it.
         */
        driveSim.setInputs(leftMasterSim.getMotorOutputLeadVoltage(),
                -rightMasterSim.getMotorOutputLeadVoltage());

        /*
         * Advance the model by 20 ms. Note that if you are running this
         * subsystem in a separate thread or have changed the nominal
         * timestep of TimedRobot, this value needs to match it.
         */
        driveSim.update(0.02);

        /*
         * Update all of our sensors.
         *
         * Since WPILib's simulation class is assuming +V is forward,
         * but -V is forward for the right motor, we need to negate the
         * position reported by the simulation class. Basically, we
         * negated the input, so we need to negate the output.
         *
         * We also observe on our physical robot that a positive voltage
         * across the output leads results in a positive sensor velocity
         * for both the left and right motors, so we do not need to negate
         * the output any further.
         * If we had observed that a positive voltage results in a negative
         * sensor velocity, we would need to negate the output once more.
         */
        leftMasterSim.setQuadratureRawPosition(
                distanceToNativeUnits(
                        driveSim.getLeftPositionMeters()
                ));
        leftMasterSim.setQuadratureVelocity(
                velocityToNativeUnits(
                        driveSim.getLeftVelocityMetersPerSecond()
                ));
        rightMasterSim.setQuadratureRawPosition(
                distanceToNativeUnits(
                        -driveSim.getRightPositionMeters()
                ));
        rightMasterSim.setQuadratureVelocity(
                velocityToNativeUnits(
                        -driveSim.getRightVelocityMetersPerSecond()
                ));

        int dev = SimDeviceDataJNI.getSimDeviceHandle("navX-Sensor[0]");
        SimDouble angle = new SimDouble(SimDeviceDataJNI.getSimValueHandle(dev, "Yaw"));
        // NavX expects clockwise positive, but sim outputs clockwise negative
        angle.set(Math.IEEEremainder(-driveSim.getHeading().getDegrees(), 360));
    }

    // Helper methods to convert between different units of measurement to native units

    /**
     * Converts distance in meters to native units.
     * @param positionMeters distance in meters
     * @return distance in native units
     *
     * @since 4/13/2022
     */
    private int distanceToNativeUnits(double positionMeters) {
        double wheelRotations = positionMeters / (2 * Math.PI * Units.inchesToMeters(Constants.DriveConstants.wheelRadiusInches));
        double motorRotations = wheelRotations * Constants.DriveConstants.sensorGearRatio;
        return (int) (motorRotations * Constants.DriveConstants.countsPerRev);
    }

    /**
     * Converts velocity in meters per second to native units.
     * @param velocityMetersPerSecond velocity in meters per second
     * @return velocity in native units
     *
     * @since 4/13/2022
     */
    private int velocityToNativeUnits(double velocityMetersPerSecond) {
        double wheelRotationsPerSecond = velocityMetersPerSecond / (2 * Math.PI * Units.inchesToMeters(Constants.DriveConstants.wheelRadiusInches));
        double motorRotationsPerSecond = wheelRotationsPerSecond * Constants.DriveConstants.sensorGearRatio;
        double motorRotationsPer100ms = motorRotationsPerSecond / Constants.DriveConstants.k100msPerSecond;
        return (int) (motorRotationsPer100ms * Constants.DriveConstants.countsPerRev);
    }

    /**
     * Converts native units to distance in meters.
     * @param sensorCounts native units
     * @return distance in meters
     */
    private double nativeUnitsToDistanceMeters(double sensorCounts) {
        double motorRotations = sensorCounts / Constants.DriveConstants.countsPerRev;
        double wheelRotations = motorRotations / Constants.DriveConstants.sensorGearRatio;
        return wheelRotations * (2 * Math.PI * Units.inchesToMeters(Constants.DriveConstants.wheelRadiusInches));
    }

    /**
     * Getter for the left follower Talon.
     * @return left follower Talon
     *
     * @since 3/26/2022
     */
    public WPI_TalonSRX getLeftFollower() {
        return leftFollower;
    }

    /**
     * Getter for the right follower Talon.
     * @return right follower Talon
     *
     * @since 3/26/2022
     */
    public WPI_TalonSRX getRightFollower() {
        return rightFollower;
    }

}
