package bhs.devilbotz.commands.climber;

import bhs.devilbotz.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimberDown extends CommandBase {
    private final Climber climber;

    public ClimberDown(Climber climber) {
        this.climber = climber;
        addRequirements(climber);
    }

    @Override
    public void execute() {

    }
}
