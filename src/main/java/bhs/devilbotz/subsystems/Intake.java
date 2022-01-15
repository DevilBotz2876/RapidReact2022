package bhs.devilbotz.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    private WPI_TalonSRX intakeMotor;

    public Intake() {
        intakeMotor = new WPI_TalonSRX(6);
    }
}
