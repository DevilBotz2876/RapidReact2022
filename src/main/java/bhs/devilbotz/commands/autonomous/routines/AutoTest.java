package bhs.devilbotz.commands.autonomous.routines;

import bhs.devilbotz.commands.autonomous.drive.DriveRotate;
import bhs.devilbotz.commands.autonomous.drive.DriveTimed;
import bhs.devilbotz.subsystems.DriveTrain;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoTest extends SequentialCommandGroup {
    public AutoTest(DriveTrain drive) {
        addCommands(
                new DriveRotate(drive, -90, 0.5),
                new DriveTimed(drive, 1, 0.7));
    }
}
