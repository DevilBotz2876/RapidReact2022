package bhs.devilbotz.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Indexing extends SubsystemBase {
    // private final WPI_TalonSRX indexingMotor;

    public Indexing() {
        // TODO: Connect Indexing Motor to TalonSRX
        // indexingMotor = new WPI_TalonSRX(7);
    }

    public void indexIn(double speed) {
        // indexingMotor.set(speed);
    }

    public void indexOut(double speed) {
        // indexingMotor.set(-speed);
    }

    public void indexIdle() {
        // indexingMotor.set(0);
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
