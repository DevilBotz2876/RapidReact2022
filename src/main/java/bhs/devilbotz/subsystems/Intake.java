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

import bhs.devilbotz.RobotContainer;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Intake subsystem
 *
 * @author Devilbotz
 * @version 1.0.0
 * @since 1.0.5
 */
public class Intake extends SubsystemBase {
    private final CANSparkMax intakeMotor;
    private boolean isOn = false;

    /**
     * Constructor for Intake subsystem
     */
    public Intake() {
        intakeMotor = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);
        intakeMotor.setInverted(false);
    }

    /**
     * This method will be called once per scheduler run when
     *
     * @since 1.0.5
     */
    @Override
    public void periodic() {

        System.out.println("Intake: temp: " + intakeMotor.getMotorTemperature() + " Speed: " + intakeMotor.getEncoder().getVelocity());

    }

    /**
     * This method will be called once per scheduler run when in simulation
     *
     * @since 1.0.5
     */
    @Override
    public void simulationPeriodic() {

    }

    public void intakeIn(double speed) {
        intakeMotor.set(speed);
        isOn = true;
    }

    public void intakeOut() {
        intakeMotor.set(-0.5);
        isOn = true;
    }

    public void stop() {
        intakeMotor.set(0);
        isOn = false;
    }

    public boolean isOn() {
        return isOn;
    }

}
