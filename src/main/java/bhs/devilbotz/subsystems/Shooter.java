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
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
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
    ShuffleboardTab tab = Shuffleboard.getTab("LiveDebug");
    private final NetworkTableEntry shooterSpeedWidget = tab.add("Shooter Speed", 1).withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 1).withPosition(0, 1).getEntry();
    private boolean shooterRunning = false;

    /**
     * Constructor for Shooter subsystem
     */
    public Shooter() {
        shooterMotor = new CANSparkMax(8, CANSparkMax.MotorType.kBrushless);
        shooterMotor.setInverted(true);
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

    public void set(double speed) {
        shooterSpeedWidget.setDouble(speed);
        shooterMotor.set(speed);
        shooterRunning = true;
    }

    public void stop() {
        shooterMotor.set(0);
        shooterMotor.stopMotor();
        shooterRunning = false;
    }

    public boolean isRunning() {
        return shooterRunning;
    }

    public NetworkTableEntry getShooterSpeedWidget() {
        return shooterSpeedWidget;
    }

}