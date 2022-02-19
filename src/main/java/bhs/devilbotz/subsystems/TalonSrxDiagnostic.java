// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package bhs.devilbotz.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

public class TalonSrxDiagnostic extends SubsystemBase implements Loggable {
  /** Creates a new TalonSrxDiagnostic. */  
  private DriveTrain driveTrain;
  
  public TalonSrxDiagnostic(DriveTrain driveTrainObj) {
      driveTrain =  driveTrainObj;    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }

    @Log(name = "LMV" , columnIndex = 0, rowIndex = 0, height = 1, width = 1)
    public double getleftVoltage(){ 
        return driveTrain.getLeftMaster().getMotorOutputVoltage();
    }
    @Log(name = "RMV" , tabName = "TalonSrxDiagnostic", columnIndex = 1, rowIndex = 0, height = 1, width = 1)
    public double getrightVoltage(){ 
        return driveTrain.getRightMaster().getMotorOutputVoltage();        
    }
    @Log(name = "LFV" , tabName = "TalonSrxDiagnostic", columnIndex = 2, rowIndex = 0, height = 1, width = 1)
    public double getleftVoltage1(){ 
        return driveTrain.getLeftFollower().getMotorOutputVoltage();
    }
    @Log(name = "RFV" , tabName = "TalonSrxDiagnostic", columnIndex = 3, rowIndex = 0, height = 1, width = 1)
    public double getrightVoltage1(){ 
        return driveTrain.getRightFollower().getMotorOutputVoltage();
    }
    @Log(name = "LMS" , tabName = "Drive", columnIndex = 1, rowIndex = 0, height = 1, width = 1)
    public double getLeftVelocity(){
        return driveTrain.getLeftMaster().getSelectedSensorVelocity();
    }
    @Log(name = "RMS" , tabName = "Drive", columnIndex = 2, rowIndex = 0, height = 1, width = 1)
    public double getRightVelocity(){
        return driveTrain.getRightMaster().getSelectedSensorVelocity();
    }
    
}
