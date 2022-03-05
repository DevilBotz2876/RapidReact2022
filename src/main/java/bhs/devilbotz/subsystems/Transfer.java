package bhs.devilbotz.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Transfer extends SubsystemBase {
    private final WPI_TalonSRX transferMotor;

    public Transfer() {
        transferMotor = new WPI_TalonSRX(6);

        addChild("TransferMotor", transferMotor);
    }

    public void setTransfer(double speed) {
        transferMotor.set(speed);
    }

    public void transferIdle() {
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
}
