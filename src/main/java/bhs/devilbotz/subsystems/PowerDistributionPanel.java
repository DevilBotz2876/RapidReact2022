// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package bhs.devilbotz.subsystems;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

public class PowerDistributionPanel extends SubsystemBase implements Loggable{
  PowerDistribution pdp = new PowerDistribution(0, ModuleType.kCTRE);
  /** Creates a new PowerDistributionPanel. */
  public PowerDistributionPanel() {


  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  // displays this to first column and row (0,0)
  @Log(name = "PDP Temp", tabName = "PowerDistributionPanel",   columnIndex = 0, rowIndex = 0, height = 1, width = 1)
	public double getTemperature() {
		// return pdp.getTemperature();
        return 10.0;
	}
}
