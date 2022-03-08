package bhs.devilbotz.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import io.github.oblarg.oblog.annotations.Log;

public class Transfer extends SubsystemBase {
    private final WPI_TalonSRX transferMotor;
    ShuffleboardTab tab = Shuffleboard.getTab("LiveDebug");
    private final NetworkTableEntry transferSpeedWidget = tab.add("Set Transfer Speed", 1).withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 1).withPosition(0, 2).getEntry();

    public Transfer() {
        transferMotor = new WPI_TalonSRX(6);

        addChild("TransferMotor", transferMotor);
    }

    public void set(double speed) {
        transferMotor.set(speed);
        transferSpeedWidget.setDouble(speed);
    }

    public void stop() {
        transferMotor.set(0);
        transferMotor.stopMotor();
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

    public NetworkTableEntry getTransferSpeedWidget() {
        return transferSpeedWidget;
    }
}
