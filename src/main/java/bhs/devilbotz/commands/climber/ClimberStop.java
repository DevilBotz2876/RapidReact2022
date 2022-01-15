package bhs.devilbotz.commands.climber;

import bhs.devilbotz.subsystems.Climber;
import bhs.devilbotz.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimberStop extends CommandBase {
    private final Climber climber;

    public ClimberStop(Climber climber) {
        this.climber = climber;
        addRequirements(climber);
    }

    @Override
    public void execute() {

    }
}
