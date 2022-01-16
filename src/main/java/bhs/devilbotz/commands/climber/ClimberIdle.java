package bhs.devilbotz.commands.climber;

import bhs.devilbotz.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimberIdle extends CommandBase {
    private final Climber climber;

    public ClimberIdle(Climber climber) {
        this.climber = climber;
        addRequirements(climber);
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
