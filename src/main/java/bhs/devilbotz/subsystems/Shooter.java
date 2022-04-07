/*-------------------------------------------------------------------------------*/
/* Copyright (c) 2021-2022 BHS Devilbotz. All Rights Reserved.                   */
/* Open Source Software - may be modified, commercialized, distributed,          */
/* sub-licensed and used for private use under the terms of the License.md       */
/* file in the root of the source code tree.                                     */
/*                                                                               */
/* You MUST include the original copyright and license files in any and all      */
/* revised/modified code. You may NOT remove this header under any circumstance  */
/* unless explicitly noted                                                       */
/*-------------------------------------------------------------------------------*/

package bhs.devilbotz.subsystems;

import bhs.devilbotz.commands.autonomous.shooter.SetHighGoal;
import bhs.devilbotz.commands.autonomous.shooter.SetLowGoal;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Shooter subsystem
 *
 * @author Devilbotz
 * @version 1.0.0
 * @since 1.0.5
 */
public class Shooter extends SubsystemBase {
    ShuffleboardTab tab = Shuffleboard.getTab("LiveDebug");
    ShuffleboardTab driveTab = Shuffleboard.getTab("Drive");
    private final NetworkTableEntry shooterSpeedWidget = tab.addPersistent("Set Shooter Speed", -3000).withSize(2, 1).withPosition(0, 1).getEntry();

    private final SendableChooser<Command> goalChooser = new SendableChooser<>();
    private final NetworkTableEntry atSetpointWidget = driveTab.add("At Setpoint", false).withSize(1, 1).withPosition(1, 2).getEntry();

    boolean isAuto = false;

    private final CANSparkMax shooterMotor;

    private final SparkMaxPIDController pidController;
    private final RelativeEncoder encoder;

    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;

    public boolean PIDEnabled = false;
    double setPoint;

    /**
     * Constructor for Shooter subsystem
     */
    public Shooter() {
        shooterMotor = new CANSparkMax(8, CANSparkMax.MotorType.kBrushless);
        shooterMotor.setInverted(true);

        pidController = shooterMotor.getPIDController();

        encoder = shooterMotor.getEncoder();

        setHighGoal();

        goalChooser.setDefaultOption("High Goal", new SetHighGoal(this, true));
        goalChooser.addOption("Low Goal", new SetLowGoal(this, false));

        Shuffleboard.getTab("Drive").add("Goal Chooser", goalChooser).withSize(2, 1).withPosition(0, 1);


        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
        SmartDashboard.putNumber("I Zone", kIz);
        SmartDashboard.putNumber("Feed Forward", kFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);
    }

    public void setHighGoal() {
        kP = 0.00015;
        kI = 0.00000002;
        kD = 0;
        kIz = 0;
        kFF = 0.000172;
        kMaxOutput = 1;
        kMinOutput = -1;
        maxRPM = 5200;

        pidController.setP(kP);
        pidController.setI(kI);
        pidController.setD(kD);
        pidController.setIZone(kIz);
        pidController.setFF(kFF);
        pidController.setOutputRange(kMinOutput, kMaxOutput);
        setSetPoint(-3050);
    }

    public void setLowGoal() {
        kP = 0.00015;
        kI = 0.00000002;
        kD = 0;
        kIz = 0;
        kFF = 0.000174;
        kMaxOutput = 1;
        kMinOutput = -1;
        maxRPM = 5200;

        pidController.setP(kP);
        pidController.setI(kI);
        pidController.setD(kD);
        pidController.setIZone(kIz);
        pidController.setFF(kFF);
        pidController.setOutputRange(kMinOutput, kMaxOutput);
        setSetPoint(-2100);
    }

    public void setSetPoint(double speed) {
        shooterSpeedWidget.setDouble(speed);
    }

    /**
     * This method will be called once per scheduler run when
     *
     * @since 1.0.5
     */
    @Override
    public void periodic() {
        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);
        double iz = SmartDashboard.getNumber("I Zone", 0);
        double ff = SmartDashboard.getNumber("Feed Forward", 0);
        double max = SmartDashboard.getNumber("Max Output", 0);
        double min = SmartDashboard.getNumber("Min Output", 0);

        if((p != kP)) { pidController.setP(p); kP = p; }
        if((i != kI)) { pidController.setI(i); kI = i; }
        if((d != kD)) { pidController.setD(d); kD = d; }
        if((iz != kIz)) { pidController.setIZone(iz); kIz = iz; }
        if((ff != kFF)) { pidController.setFF(ff); kFF = ff; }
        if((max != kMaxOutput) || (min != kMinOutput)) {
            pidController.setOutputRange(min, max);
            kMinOutput = min; kMaxOutput = max;
        }

        if (PIDEnabled) {
            setPoint = -shooterSpeedWidget.getDouble(3000);
            pidController.setReference(setPoint, CANSparkMax.ControlType.kVelocity);

            SmartDashboard.putNumber("SetPoint", setPoint);
            SmartDashboard.putNumber("ProcessVariable", encoder.getVelocity());

            // Shuffleboard widget
            SmartDashboard.putBoolean("AtSetpoint", atSetpoint());
        }
    }

    /**
     * This method will be called once per scheduler run when in simulation
     *
     * @since 1.0.5
     */
    @Override
    public void simulationPeriodic() {

    }

    public boolean atSetpoint() {

        double velocity = encoder.getVelocity();
        double tolerance = 35;
        double error = velocity - setPoint;
        atSetpointWidget.setBoolean(Math.abs(error) <= tolerance);

        return Math.abs(error) <= tolerance;
    }

    public void setIsAuto(boolean isAuto) {
        this.isAuto = isAuto;
    }

    public void set(double speed) {
        System.out.println(shooterMotor.getEncoder().getVelocity());
        shooterMotor.set(speed);
        isAuto = false;
    }

    public boolean isAuto() {
        return isAuto;
    }

    public void enable() {
        PIDEnabled = true;
    }

    public void disable() {
        PIDEnabled = false;
    }

    public void stop() {
        shooterMotor.set(0);
        shooterMotor.stopMotor();
    }

    public NetworkTableEntry getShooterSpeedWidget() {
        return shooterSpeedWidget;
    }

    public NetworkTableEntry getAtSetpointWidget() {
        return atSetpointWidget;
    }

    public SendableChooser<Command> getGoalWidget() {
        return goalChooser;
    }

}