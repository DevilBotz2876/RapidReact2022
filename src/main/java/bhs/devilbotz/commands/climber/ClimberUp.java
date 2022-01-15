package bhs.devilbotz.commands.climber;

import bhs.devilbotz.subsystems.Climber;
import bhs.devilbotz.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimberUp extends CommandBase {
    private final Climber climber;

    public ClimberUp(Climber climber) {
        this.climber = climber;
        addRequirements(climber);
    }

    @Override
    public void execute() {

    }
}
