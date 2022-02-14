// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package bhs.devilbotz.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;
import bhs.devilbotz.Constants;

import java.time.temporal.TemporalUnit;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class TalonSrxDiagnostic extends SubsystemBase implements Loggable {
  /** Creates a new TalonSrxDiagnostic. */
  private static final WPI_TalonSRX leftMaster = new WPI_TalonSRX(1);
  private static final WPI_TalonSRX rightMaster = new WPI_TalonSRX(4);
  private static final WPI_TalonSRX leftFollower = new WPI_TalonSRX(2);
  private static final WPI_TalonSRX rightFollower = new WPI_TalonSRX(3);
  public TalonSrxDiagnostic() {
  
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

    @Log(name = "LeftMaster Voltage" , columnIndex = 0, rowIndex = 0, height = 1, width = 1)
    public double getleftVoltage(){ 
        return leftMaster.getMotorOutputVoltage();
    }
    @Log(name = "RightMaster Voltage" , tabName = "TalonSrxDiagnostic", columnIndex = 1, rowIndex = 0, height = 1, width = 1)
    public double getrightVoltage(){ 
        return rightMaster.getMotorOutputVoltage();
    }
    @Log(name = "LeftFollower Voltage" , tabName = "TalonSrxDiagnostic", columnIndex = 2, rowIndex = 0, height = 1, width = 1)
    public double getleftVoltage1(){ 
        return leftFollower.getMotorOutputVoltage();
    }
    @Log(name = "RightFollower Voltage" , tabName = "TalonSrxDiagnostic", columnIndex = 3, rowIndex = 0, height = 1, width = 1)
    public double getrightVoltage1(){ 
        return rightFollower.getMotorOutputVoltage();
    }
}
