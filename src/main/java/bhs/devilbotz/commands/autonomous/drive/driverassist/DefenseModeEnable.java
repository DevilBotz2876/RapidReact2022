package bhs.devilbotz.commands.autonomous.drive.driverassist;

import bhs.devilbotz.subsystems.DriveTrain;
import bhs.devilbotz.subsystems.IntakeArm;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DefenseModeEnable extends CommandBase {
    private final DriveTrain driveTrain;
    private final IntakeArm intakeArm;


    public DefenseModeEnable(DriveTrain driveTrain, IntakeArm intakeArm) {
        this.intakeArm = intakeArm;
        this.driveTrain = driveTrain;
    }


    /**
     * Runs when the command is first scheduled.
     */
    @Override
    public void initialize() {
        intakeArm.setDefenseMode(true);
    }

    /**
     * Called every time the scheduler runs while the command is scheduled.
     */
    @Override
    public void execute() {

    }

    /**
     * Called once the command ends or is interrupted.
     *
     * @param interrupted Whether the command was interrupted.
     */
    @Override
    public void end(boolean interrupted) {
    }

    /**
     * Returns true when the command should end.
     *
     * @return Whether the command should end.
     */
    @Override
    public boolean isFinished() {
        return true;
    }
}
