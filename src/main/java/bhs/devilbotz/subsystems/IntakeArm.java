package bhs.devilbotz.subsystems;

import bhs.devilbotz.commands.intakeArm.IntakeArmUp;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeArm extends SubsystemBase {
    private final WPI_TalonSRX intakeArmMotor;
    ShuffleboardTab tab = Shuffleboard.getTab("LiveDebug");
    private final NetworkTableEntry intakeArmSpeedWidget = tab.addPersistent("Set Intake Arm Speed", 0.45).withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 1).withPosition(0, 3).getEntry();
    private boolean intakeArmUp = true;
    private boolean defenseMode = false;


    public IntakeArm() {
        intakeArmMotor = new WPI_TalonSRX(5);

        addChild("IntakeArmMotor", intakeArmMotor);

        intakeArmMotor.setInverted(true);
    }

    public boolean isIntakeArmUp() {
        return intakeArmUp;
    }

    public void setIntakeArmUp(double speed) {
        intakeArmMotor.set(speed);
        intakeArmSpeedWidget.setDouble(speed);
        intakeArmUp = true;
    }

    public void setIntakeArmDown(double speed) {
        intakeArmMotor.set(-speed);
        intakeArmSpeedWidget.setDouble(speed);
        intakeArmUp = false;
    }

    public void set(double speed) {
        intakeArmMotor.set(speed);
    }

    public void stop() {
        intakeArmMotor.set(0);
    }

    /**
     * This method will be called once per scheduler run when
     *
     * @since 1.0.5
     */
    @Override
    public void periodic() {
        if (defenseMode) {
            // TODO: FIX
            new IntakeArmUp(this);
        }
    }

    public void setDefenseMode(boolean enabled) {
        this.defenseMode = enabled;
    }

    /**
     * This method will be called once per scheduler run when in simulation
     *
     * @since 1.0.5
     */
    @Override
    public void simulationPeriodic() {

    }

    public NetworkTableEntry getIntakeArmSpeedWidget() {
        return intakeArmSpeedWidget;
    }
}
