package bhs.devilbotz.commands.shooter;

import bhs.devilbotz.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShooterStop extends CommandBase {
    private final Shooter shooter;

    public ShooterStop(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void execute() {

    }
}
