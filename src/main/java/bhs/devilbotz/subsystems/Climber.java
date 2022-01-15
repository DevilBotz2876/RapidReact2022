package bhs.devilbotz.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
    private WPI_TalonSRX climberMotor;

    public Climber() {
        climberMotor = new WPI_TalonSRX(5);
    }
}
