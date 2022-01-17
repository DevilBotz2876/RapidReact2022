package bhs.devilbotz.commands.intake;

import bhs.devilbotz.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeStop extends CommandBase {
    private final Intake intake;

    public IntakeStop(Intake intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void execute() {

    }
}
