// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package bhs.devilbotz.subsystems;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

public class PowerDistributionPanel extends SubsystemBase implements Loggable {
    PowerDistribution pdp = new PowerDistribution(0, ModuleType.kCTRE);

    /**
     * Creates a new PowerDistributionPanel.
     */
    public PowerDistributionPanel() {


    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    // displays this to first column and row (0,0)
    @Log(name = "PDP Temp", tabName = "PowerDistributionPanel", columnIndex = 0, rowIndex = 0, height = 1, width = 1)
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
  @Log(name= "PDP Current 1" , tabName = "PowerDistributionPanel", columnIndex = 2, rowIndex = 0, height = 1, width = 1)
  public double getCurrentOne(){
    return pdp.getCurrent(1);
  }
  @Log(name= "PDP Current 2" , tabName = "PowerDistributionPanel", columnIndex = 3, rowIndex = 0, height = 1, width = 1)
  public double getCurrentTwo(){
    return pdp.getCurrent(2);
  }
  @Log(name= "PDP Current 3" , tabName = "PowerDistributionPanel", columnIndex = 4, rowIndex = 0, height = 1, width = 1)
  public double getCurrentThree(){
    return pdp.getCurrent(3);
  }
  @Log(name= "PDP Current 4" , tabName = "PowerDistributionPanel", columnIndex = 0, rowIndex = 1, height = 1, width = 1)
  public double getCurrentFour(){
    return pdp.getCurrent(4);
  }
 @Log(name= "PDP Current 5" , tabName = "PowerDistributionPanel", columnIndex = 1, rowIndex = 1, height = 1, width = 1)
  public double getCurrentFive(){
    return pdp.getCurrent(5);
  }
  @Log(name= "PDP Current 6" , tabName = "PowerDistributionPanel", columnIndex = 2, rowIndex = 1, height = 1, width = 1)
  public double getCurrentsix(){
    return pdp.getCurrent(6);
  }
  @Log(name= "PDP Current 7" , tabName = "PowerDistributionPanel", columnIndex = 3, rowIndex = 1, height = 1, width = 1)
  public double getCurrentSeven(){
    return pdp.getCurrent(7);
  }
  @Log(name= "PDP Current 8" , tabName = "PowerDistributionPanel", columnIndex = 4, rowIndex = 1, height = 1, width = 1)
  public double getCurrenteight(){
    return pdp.getCurrent(8);
  }
}
