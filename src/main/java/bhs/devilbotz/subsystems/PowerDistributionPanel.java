// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package bhs.devilbotz.subsystems;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

public class PowerDistributionPanel extends SubsystemBase {// implements Loggable{
  PowerDistribution pdp = new PowerDistribution(0, ModuleType.kCTRE);

  /** Creates a new PowerDistributionPanel. */
  public PowerDistributionPanel() {
    pdp.clearStickyFaults();

  }
 @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public PowerDistribution getPDP() {
    return pdp;
  }

  /*
  // displays this to first column and row (0,0)
  @Log(name = "PDP Temp", tabName = "PowerDistributionPanel",   columnIndex = 0, rowIndex = 0, height = 1, width = 1)
	public double getTemperature() {
		return pdp.getTemperature();
        // return 1.0;
        // line above for test on shuffleboard
        
	}
  @Log(name = "PDP Voltage" , tabName = "PowerDistributionPanel", columnIndex = 1, rowIndex = 0, height = 1, width = 1)
  public double getVoltage(){
    return pdp.getVoltage();
    // return 1.0;
    // line above for test on shuffleboard
  }
  @Log(name= "PDP Current 0" , tabName = "PowerDistributionPanel", columnIndex = 2, rowIndex = 0, height = 1, width = 1)
  public double getCurrentZero(){
    return pdp.getCurrent(0);
  }
  @Log(name= "PDP Current 1" , tabName = "PowerDistributionPanel", columnIndex = 3, rowIndex = 0, height = 1, width = 1)
  public double getCurrentOne(){
    return pdp.getCurrent(1);
  }
  @Log(name= "PDP Current 6" , tabName = "PowerDistributionPanel", columnIndex = 4, rowIndex = 0, height = 1, width = 1)
  public double getCurrentSix(){
    return pdp.getCurrent(6);
  }
  @Log(name= "PDP Current 7" , tabName = "PowerDistributionPanel", columnIndex = 0, rowIndex = 1, height = 1, width = 1)
  public double getCurrentSeven(){
    return pdp.getCurrent(7);
  }
 @Log(name= "PDP Current 12" , tabName = "PowerDistributionPanel", columnIndex = 1, rowIndex = 1, height = 1, width = 1)
  public double getCurrentTwelve(){
    return pdp.getCurrent(12);
  }
  @Log(name= "PDP Current 13" , tabName = "PowerDistributionPanel", columnIndex = 2, rowIndex = 1, height = 1, width = 1)
  public double getCurrentThirteen(){
    return pdp.getCurrent(13);
  }
  @Log(name= "PDP Current 14" , tabName = "PowerDistributionPanel", columnIndex = 3, rowIndex = 1, height = 1, width = 1)
  public double getCurrentFourteen(){
    return pdp.getCurrent(14);
  }
  @Log(name= "PDP Current 15" , tabName = "PowerDistributionPanel", columnIndex = 4, rowIndex = 1, height = 1, width = 1)
  public double getCurrentFifteen(){
    return pdp.getCurrent(15);
  }

   */
}
