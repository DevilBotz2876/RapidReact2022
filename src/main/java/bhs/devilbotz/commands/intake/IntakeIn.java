package bhs.devilbotz.commands.intake;

import bhs.devilbotz.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeIn extends CommandBase {
    private final Intake intake;

    public IntakeIn(Intake intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void execute() {

    }
}
