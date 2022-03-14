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
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

/**
 * Shooter subsystem
 *
 * @author Devilbotz
 * @version 1.0.0
 * @since 1.0.5
 */
public class Shooter extends SubsystemBase implements Loggable {
    private final CANSparkMax shooterMotor;
    ShuffleboardTab tab = Shuffleboard.getTab("LiveDebug");
    private final NetworkTableEntry shooterSpeedWidget = tab.add("Set Shooter Speed", 0.75).withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 1).withPosition(0, 1).getEntry();
    private final SimpleMotorFeedforward shooterFeedForward = new SimpleMotorFeedforward(0.05, 12.0 / 5000);
    PIDController pidController = new PIDController(1, 0, 0);


    private final double setpoint = 3000;
    /**
     * Constructor for Shooter subsystem
     */
    public Shooter() {
        shooterMotor = new CANSparkMax(8, CANSparkMax.MotorType.kBrushless);
        shooterMotor.setInverted(true);

        pidController.setTolerance(50);
        pidController.reset();
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
        System.out.println((pidController.calculate(shooterMotor.getEncoder().getVelocity(), setpoint) + shooterFeedForward.calculate(setpoint)));
        shooterMotor.setVoltage((pidController.calculate(shooterMotor.getEncoder().getVelocity(), setpoint)) + shooterFeedForward.calculate(setpoint));
    }

    public void stop() {
        shooterMotor.set(0);
        shooterMotor.stopMotor();
    }

    public NetworkTableEntry getShooterSpeedWidget() {
        return shooterSpeedWidget;
    }

    @Log(name = "Shooter Speed", tabName = "LiveDebug", columnIndex = 2, rowIndex = 1, height = 1, width = 1)
    double speed() {
        return shooterMotor.getEncoder().getVelocity();
    }

    @Log(name = "Shooter Temp", tabName = "LiveDebug", columnIndex = 3, rowIndex = 1, height = 1, width = 1)
    double temp() {
        return shooterMotor.getMotorTemperature();
    }
}