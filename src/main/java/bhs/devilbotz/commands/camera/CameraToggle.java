package bhs.devilbotz.commands.camera;

import bhs.devilbotz.subsystems.CameraSystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class CameraToggle extends CommandBase {
    CameraSystem cameraSystem;

    public CameraToggle(CameraSystem cameraSystem) {
        this.cameraSystem = cameraSystem;
    }

    @Override
    public void initialize() {
        if (cameraSystem.getCameraIndex() == 1) {
            cameraSystem.setCameraOne();
        } else {
            cameraSystem.setCameraTwo();
        }
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

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}