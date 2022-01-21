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

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Climber subsystem
 *
 * @author Devilbotz
 * @version 1.0.0
 * @since 1.0.5
 */

public class Climber extends SubsystemBase {
    private final WPI_TalonSRX climberMotor;

    /**
     * Constructor for Climber subsystem
     *
     * @since 1.0.5
     */
    public Climber() {
        climberMotor = new WPI_TalonSRX(5);
        addChild("ClimberMotor", climberMotor);
        climberMotor.setInverted(false);
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
