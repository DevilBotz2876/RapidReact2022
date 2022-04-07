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

  /* function to get left master motor output voltage */
  @Log(name = "LMV" , columnIndex = 0, rowIndex = 0, height = 1, width = 1)
  public double getleftVoltage() {
    return driveTrain.getLeftMasterVoltage();
  }

  /* function to get right  master motor output voltage */
  @Log(name = "RMV" , tabName = "TalonSrxDiagnostic", columnIndex = 1, rowIndex = 0, height = 1, width = 1)
  public double getrightVoltage() { 
    return driveTrain.getRightMasterVoltage();
  }

  /* function to get left follower motor output voltage */
  @Log(name = "LFV" , tabName = "TalonSrxDiagnostic", columnIndex = 2, rowIndex = 0, height = 1, width = 1)
  public double getleftVoltage1() {
    return driveTrain.getLeftFollowerVoltage();
  }

  /* function to get right follower motor output voltage */
  @Log(name = "RFV" , tabName = "TalonSrxDiagnostic", columnIndex = 3, rowIndex = 0, height = 1, width = 1)
  public double getrightVoltage1() {
    return driveTrain.getRightFollowerVoltage();
  }

  /* function to get left master selected sensor velocity */
  @Log(name = "LMS" , tabName = "Drive", columnIndex = 1, rowIndex = 0, height = 1, width = 1)
  public double getLeftVelocity() {
    return driveTrain.getLeftMasterVelocity();
  }

  /* function to get right master selected sensor velocity */
  @Log(name = "RMS" , tabName = "Drive", columnIndex = 2, rowIndex = 0, height = 1, width = 1)
  public double getRightVelocity() {
    return driveTrain.getRightMasterVelocity();
  }

}
