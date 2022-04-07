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
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import io.github.oblarg.oblog.annotations.Config;

/**
 * Intake subsystem
 *
 * @author Devilbotz
 * @version 1.0.0
 * @since 1.0.5
 */
public class Intake extends SubsystemBase {
    ShuffleboardTab tab = Shuffleboard.getTab("LiveDebug");
    private final NetworkTableEntry intakeSpeedWidget = tab.addPersistent("Set Intake Speed", 0.65).withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 1).withPosition(0, 0).getEntry();

    ShuffleboardTab driveTab = Shuffleboard.getTab("Drive");
    private final NetworkTableEntry toggleWidget = driveTab.add("Intake On?", false).withSize(1, 1).withPosition(6, 0).getEntry();


    private final WPI_TalonSRX intakeMotor;
    double numSCurrentOver = 0;


    /**
     * Constructor for Intake subsystem
     */
    public Intake() {
        intakeMotor = new WPI_TalonSRX(7);

        addChild("IntakeMotor", intakeMotor);
    }

    /**
     * This method will be called once per scheduler run when
     *
     * @since 1.0.5
     */
    @Override
    public void periodic() {
        double s_current = intakeMotor.getStatorCurrent();
        SmartDashboard.putNumber("Intake S_Current", s_current);
        if (s_current > 20.0) {
            SmartDashboard.putBoolean("Intake S_OVER", true);
            numSCurrentOver++;
        } else {
            SmartDashboard.putBoolean("Intake S_OVER", false);
            numSCurrentOver = 0;
        }
        if (numSCurrentOver > 20) {
            SmartDashboard.putBoolean("Intake S_OFF", true);
            intakeMotor.set(0);
        } else {
            SmartDashboard.putBoolean("Intake S_OFF", false);
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

    public void set(double speed) {
        intakeSpeedWidget.setDouble(speed);
        intakeMotor.set(speed);
        toggleWidget.setBoolean(true);

    }

    public void stop() {
        intakeMotor.set(0);
        intakeMotor.stopMotor();
        toggleWidget.setBoolean(false);
    }

    public NetworkTableEntry getIntakeSpeedWidget() {
        return intakeSpeedWidget;
    }

}
