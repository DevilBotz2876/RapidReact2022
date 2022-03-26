package bhs.devilbotz.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import io.github.oblarg.oblog.annotations.Log;

public class Transfer extends SubsystemBase {
    public enum BallColor {
        RED,
        BLUE,
        NONE
    }

    BallColor ballColor = BallColor.NONE;

    private final WPI_TalonSRX transferMotor;
    private final I2C.Port i2cPort = I2C.Port.kMXP;

    private final Encoder encoder = new Encoder(9, 8);

    private boolean intakeOut = false;

    // Shuffleboard
    ShuffleboardTab tab = Shuffleboard.getTab("LiveDebug");
    private final NetworkTableEntry transferSpeedWidget = tab.add("Set Transfer Speed", 1).withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 1).withPosition(0, 2).getEntry();

    // Color sensor setup
    private final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch colorMatcher = new ColorMatch();
    private final Color blueTarget = new Color(0.100, 0.300, 0.300);
    private final Color redTarget = new Color(0.520, 0.354, 0.124);

    public Transfer() {
        transferMotor = new WPI_TalonSRX(6);

        addChild("TransferMotor", transferMotor);

        setupColorSensor();

        encoder.setDistancePerPulse(0.1428571428571429);
    }

    private void setupColorSensor() {
        colorMatcher.addColorMatch(blueTarget);
        colorMatcher.addColorMatch(redTarget);
    }

    public BallColor getBallColor() {
        return this.ballColor;
    }

    public boolean ballPresent() {
        System.out.println(encoder.get());
        return colorSensor.getProximity() >= 175;
    }

    public void set(double speed) {
        transferMotor.set(speed);
        transferSpeedWidget.setDouble(speed);
    }

    public void setOut(double speed) {
        transferMotor.set(-speed);
        transferSpeedWidget.setDouble(-speed);
        intakeOut = true;
    }

    public void stop() {
        set(0);
        intakeOut = false;
        //transferMotor.set(0);
        //transferMotor.stopMotor();
    }

    /**
     * This method will be called once per scheduler run when
     *
     * @since 1.0.5
     */
    @Override
    public void periodic() {
        Color detectedColor = colorSensor.getColor();

        if (colorSensor.getProximity() > 175) {
            if (detectedColor.red > detectedColor.blue) {
                SmartDashboard.putString("BallColor", "RED");
                ballColor = BallColor.RED;
            } else if (detectedColor.blue > detectedColor.red && detectedColor.blue > .34) {
                SmartDashboard.putString("BallColor", "BLUE");
                ballColor = BallColor.BLUE;
            } else {
                SmartDashboard.putString("BallColor", "no color");
                ballColor = BallColor.NONE;
            }
        } else {
            SmartDashboard.putString("BallColor", "no ball");
            ballColor = BallColor.NONE;
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

    public boolean isIntakeOut() {
        return intakeOut;
    }

    public NetworkTableEntry getTransferSpeedWidget() {
        return transferSpeedWidget;
    }
}
