package bhs.devilbotz.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    private WPI_TalonSRX shooterMotor;

    public Shooter() {
        shooterMotor = new WPI_TalonSRX(7);
    }
}