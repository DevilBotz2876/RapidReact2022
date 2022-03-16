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

import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Shooter subsystem
 *
 * @author Devilbotz
 * @version 1.0.0
 * @since 1.0.5
 */
public class Shooter extends SubsystemBase {//implements Loggable {
    private final CANSparkMax shooterMotor;
    //ShuffleboardTab tab = Shuffleboard.getTab("LiveDebug");
    //private final NetworkTableEntry shooterSpeedWidget = tab.add("Set Shooter Speed", 0.75).withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 1).withPosition(0, 1).getEntry();
    // private final SimpleMotorFeedforward shooterFeedForward = new SimpleMotorFeedforward(0.05, 12.0 / 5000);

    // TODO: need to tune P. Maybe run sysid? 
    // Can also just tune by trial/error using smartdash outputs to see if shooter reaches setpoint
    private PIDController shooterPID = new PIDController(1, 0, 0);

    /**
     * Constructor for Shooter subsystem
     */
    public Shooter() {
        shooterMotor = new CANSparkMax(8, CANSparkMax.MotorType.kBrushless);
        
        // TODO: Why are we setting inverted here?
        shooterMotor.setInverted(true);

        addChild("shootershooterPID", shooterPID);
    }

    /**
     * This method will be called once per scheduler run when
     *
     * @since 1.0.5
     */
    @Override
    public void periodic() {
        SmartDashboard.putNumber("Sh_Get", shooterMotor.get());
        SmartDashboard.putNumber("Sh_AppOut", shooterMotor.getAppliedOutput());
        SmartDashboard.putNumber("Sh_BusVolt", shooterMotor.getBusVoltage());
        SmartDashboard.putNumber("Sh_Enc_V", shooterMotor.getEncoder().getVelocity());
        SmartDashboard.putNumber("Sh_Enc_V_CF", shooterMotor.getEncoder().getVelocityConversionFactor());

        SmartDashboard.putData("Sh_PID", shooterPID);
        SmartDashboard.putNumber("Sh_PID_V_Err", shooterPID.getVelocityError());
        SmartDashboard.putNumber("Sh_PID_Setpoint", shooterPID.getSetpoint());
        SmartDashboard.putNumber("Sh_PID_Calc", shooterPID.calculate(shooterMotor.getEncoder().getVelocity()));
        SmartDashboard.putBoolean("Sh_PID_At", shooterPID.atSetpoint());
    }

    /**
     * This method will be called once per scheduler run when in simulation
     *
     * @since 1.0.5
     */
    @Override
    public void simulationPeriodic() {

    }

    // -12 to 12 volts
    public void setVoltage(double volts) {
        shooterMotor.setVoltage(volts);
    }

    // -1 to 1 speed
    public void setSpeed(double speed) {
        shooterMotor.set(speed);
    }

    // only tested rpm in one direction at max speed/voltage, got about 5200 measured.  Should test in other direction too.
    public void setSetpoint(double rpm) {
        // TODO:
        // - check rpm input is not out of range.
        // - check sign of rpm, don't want to spin shooter in wrong direction
        
        shooterPID.reset();
        shooterPID.setSetpoint(rpm);        
    }

    public void updatePIDOutput() {
        
        // Something not quite right still.. without negating output shooter oscillates.  Adding negative made it stop 
        // oscillating.  But shooter did not reach setpoint. Not sure if this will actually spin shooter in correct direction.
        // Related to figure out why setInverted is called in constructor. 
        // Was just trying to get PID to reach setpoint, ignore direction.
        // Ran out of time before could get this fully working.

        double output = MathUtil.clamp(shooterPID.calculate(shooterMotor.getEncoder().getVelocity()), -0.5, 0.5);
        SmartDashboard.putNumber("Sh_output", -output);
        shooterMotor.set(-output);
    }

    public boolean readyToShoot() {
        return shooterPID.atSetpoint();
    }

    public void set(double speed) {
        //System.out.println((shooterPID.calculate(shooterMotor.getEncoder().getVelocity(), setpoint) + shooterFeedForward.calculate(setpoint)));
        //shooterMotor.setVoltage((shooterPID.calculate(shooterMotor.getEncoder().getVelocity(), setpoint)) + shooterFeedForward.calculate(setpoint));
    }

    public void stop() {
        shooterMotor.set(0);
        shooterMotor.stopMotor();
        shooterPID.setSetpoint(0);
    }

    // public NetworkTableEntry getShooterSpeedWidget() {
    //     return shooterSpeedWidget;
    // }

    // @Log(name = "Shooter Speed", tabName = "LiveDebug", columnIndex = 2, rowIndex = 1, height = 1, width = 1)
    // double speed() {
    //     return shooterMotor.getEncoder().getVelocity();
    // }

    // @Log(name = "Shooter Temp", tabName = "LiveDebug", columnIndex = 3, rowIndex = 1, height = 1, width = 1)
    // double temp() {
    //     return shooterMotor.getMotorTemperature();
    // }
}