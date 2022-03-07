package bhs.devilbotz.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Transfer extends SubsystemBase {
    private final WPI_TalonSRX transferMotor;
    ShuffleboardTab tab = Shuffleboard.getTab("LiveDebug");
    private final NetworkTableEntry transferSpeedWidget = tab.add("Transfer Speed", 1).withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 1).withPosition(0, 2).getEntry();
    private boolean transferRunning = false;

    public Transfer() {
        transferMotor = new WPI_TalonSRX(6);

        addChild("TransferMotor", transferMotor);
    }

    public void set(double speed) {
        transferMotor.set(speed);
        transferSpeedWidget.setDouble(speed);
        transferRunning = true;
    }

    public void stop() {
        transferMotor.set(0);
        transferMotor.stopMotor();
        transferRunning = false;
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

    public boolean isRunning() {
        return transferRunning;
    }

    public NetworkTableEntry getTransferSpeedWidget() {
        return transferSpeedWidget;
    }
}
