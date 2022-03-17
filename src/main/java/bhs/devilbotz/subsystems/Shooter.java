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
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

/**
 * Shooter subsystem
 *
 * @author Devilbotz
 * @version 1.0.0
 * @since 1.0.5
 */
public class Shooter extends PIDSubsystem {
    private final CANSparkMax shooterMotor;
    double setpoint;

    /**
     * Constructor for Shooter subsystem
     */
    public Shooter(double setpoint) {
        super(new PIDController(0.15187, 0, 0));
        shooterMotor = new CANSparkMax(8, CANSparkMax.MotorType.kBrushless);

        this.setpoint = setpoint;
        shooterMotor.setInverted(false);

    }

    /**
     * This method will be called once per scheduler run when
     *
     * @since 1.0.5
     */
    @Override
    public void periodic() {
        if (isEnabled()) {
            shooterMotor.set(m_controller.calculate(getMeasurement(), setpoint));
        }
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

    @Override
    protected void useOutput(double output, double setpoint) {
        System.out.println("output: " + output);

    }

    @Override
    protected double getMeasurement() {
        return shooterMotor.getEncoder().getVelocity();
    }

    public boolean atSetpoint() {
        return m_controller.atSetpoint();
    }

    public void stop() {
        shooterMotor.set(0);
        shooterMotor.stopMotor();
    }

    public void setSetpoints(double setpoint) {
        setSetpoint(setpoint);
    }

}