package bhs.devilbotz.commands.intake;

import bhs.devilbotz.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeIn extends CommandBase {
    private final Intake intake;

    public IntakeIn(Intake intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
