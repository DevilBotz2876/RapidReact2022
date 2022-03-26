package bhs.devilbotz.commands.autonomous.drive.driverassist.helper;

import bhs.devilbotz.commands.autonomous.transfer.TransferInTimed;
import bhs.devilbotz.subsystems.DriveTrain;
import bhs.devilbotz.subsystems.Shooter;
import bhs.devilbotz.subsystems.Transfer;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class PrepareForShoot extends ParallelCommandGroup {
    public PrepareForShoot(DriveTrain drive, Transfer transfer, Shooter shooter) {
        addCommands(
                new TransferInTimed(transfer, 0.9)
        );
    }

}
