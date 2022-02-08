package bhs.devilbotz.subsystems;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Indexing extends SubsystemBase {
    private final Ultrasonic ultrasonic;
    private final Ultrasonic ultrasonicTwo;

    // private final WPI_TalonSRX indexingMotor;

    public Indexing() {
        // TODO: Connect Indexing Motor to TalonSRX
        // indexingMotor = new WPI_TalonSRX(7);

        ultrasonic = new Ultrasonic(9, 8);
        ultrasonicTwo = new Ultrasonic(7, 6);

        Ultrasonic.setAutomaticMode(true);
        ultrasonic.setEnabled(true);
        ultrasonicTwo.setEnabled(true);
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

    public Ultrasonic getUltrasonic() {
        return ultrasonic;
    }

    public Ultrasonic getUltrasonicTwo() {
        return ultrasonicTwo;
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
