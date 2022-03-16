// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package bhs.devilbotz.commands;

import bhs.devilbotz.commands.shooter.ShooterRPM;
import bhs.devilbotz.commands.shooter.ShooterStop;
import bhs.devilbotz.commands.transfer.TransferIn;
import bhs.devilbotz.subsystems.Shooter;
import bhs.devilbotz.subsystems.Transfer;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ShootOneCargo extends SequentialCommandGroup {

  public ShootOneCargo(Shooter shooter, Transfer transfer, double rpm) {
    
    addCommands(
      new ShooterRPM(shooter, rpm), // wait for shooter to reach set rpm
      new TransferIn(transfer).withTimeout(2), // move cargo to shooter, may need to adjust time
      new WaitCommand(3), // wait for shooter to actually shoot, may need to adjust time
      new ShooterStop(shooter) // stop shooter
    );
  }
}
