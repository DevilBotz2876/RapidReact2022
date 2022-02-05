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
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Shooter subsystem
 *
 * @author Devilbotz
 * @version 1.0.0
 * @since 1.0.5
 */
public class Shooter extends SubsystemBase {
    private final CANSparkMax shooterMotor;

    /**
     * Constructor for Shooter subsystem
     */
    public Shooter() {
        shooterMotor = new CANSparkMax(5, CANSparkMax.MotorType.kBrushless);
        shooterMotor.setInverted(false);
        shooterMotor.setClosedLoopRampRate(0);
        shooterMotor.setOpenLoopRampRate(0);
    }

    /**
     * This method will be called once per scheduler run when
     *
     * @since 1.0.5
     */
    @Override
    public void periodic() {
        System.out.println("Shooter: temp: " + shooterMotor.getMotorTemperature() + " Speed: " + shooterMotor.getEncoder().getVelocity());

    }

    /**
     * This method will be called once per scheduler run when in simulation
     *
     * @since 1.0.5
     */
    @Override
    public void simulationPeriodic() {

    }

    public void shooterOn(double speed) {
        shooterMotor.set(speed);
    }

    public void stop() {
        shooterMotor.set(0);
    }

    public boolean shootReady() {
        return shooterMotor.getEncoder().getVelocity() >= 2800;
    }

}