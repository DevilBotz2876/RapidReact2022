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

import java.sql.Driver;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Intake subsystem
 *
 * @author Devilbotz
 * @version 1.0.0
 * @since 1.0.5
 */
public class Intake extends SubsystemBase {

    enum BallColor {
        RED,
        BLUE,
        NONE
    }

    BallColor ballColor = BallColor.NONE;


    private final WPI_TalonSRX intakeMotor;

    // The REV color sensor code is based on example from REV docs.
    // https://www.revrobotics.com/rev-31-1557/
    // https://github.com/REVrobotics/Color-Sensor-v3-Examples/blob/master/Java/Color%20Match/src/main/java/frc/robot/Robot.java
    private final I2C.Port i2cPort = I2C.Port.kMXP;
    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch m_colorMatcher = new ColorMatch();
    private final Color kBlueTarget = new Color(0.100, 0.300, 0.300);
    private final Color kRedTarget = new Color(0.520, 0.354, 0.124);

    /**
     * Constructor for Intake subsystem
     */
    public Intake() {
        intakeMotor = new WPI_TalonSRX(6);

        addChild("IntakeMotor", intakeMotor);

        intakeMotor.setInverted(false);

        setupColorSensor();
        
    }

    /**
     * This method will be called once per scheduler run when
     *
     * @since 1.0.5
     */
    @Override
    public void periodic() {
        detectBallColor();
        
        // Comment/Uncomment this so you can see raw RGB/proxmity values on shuffleboard/smartdashboard.
        detectColorExample();
    }

    /**
     * This method will be called once per scheduler run when in simulation
     *
     * @since 1.0.5
     */
    @Override
    public void simulationPeriodic() {

    }

    public void setupColorSensor() {
        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
    }

    public BallColor getBallColor() {
        return this.ballColor;
    }

    public void detectBallColor() {
        Color detectedColor = m_colorSensor.getColor();
        // sensor proximity returns value 0-2047. Testing showed that value never really
        // went below 150ish. If no object is within 2-3inches the proximity just hovers
        // between 100-200ish. The color values are not accurate until proximity reading
        // > 200ish.
        //
        // Instead of trying to detect an actual color using absolute values(or ranges)
        // we compare the RGB values between themselves to determine if we see a blue or
        // red ball, or something else. This will work because there are only 2 objects
        // we need to distinguish between. When detecting blue ball color, testing
        // showed that you could get false positive in front of black/dark target.
        // Checking for a min value for blue helped filter out those false positives.
        if (m_colorSensor.getProximity() > 200) {
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

    public void detectColorExample() {
        // The example code from REV uses ColorMatcher to determine if sensor sees a
        // specific color. Tried to adjust values for k constants based on output from
        // Smartdashboard while testing. Could not find a combo of values that worked to
        // match colors using ColorMatch class. Realized next day that maybe this didn't
        // work b/c did not call setupColorSensor() in constructor.. whooops... That's ok
        // tho because we don't need to identify specific color of balls. See
        // detectBallColor() method.
        //
        // Leaving this here for demo/example purpose.
        Color detectedColor = m_colorSensor.getColor();
        String colorString;
        ColorMatchResult match = m_colorMatcher.matchColor(detectedColor);

        if (match == null) {
            colorString = "null";
        } else if (match.color == kBlueTarget) {
            colorString = "Blue";
        } else if (match.color == kRedTarget) {
            colorString = "Red";
        } else {
            colorString = "Unknown";
        }

        int proximity = m_colorSensor.getProximity();

        /**
         * Open Smart Dashboard or Shuffleboard to see the color detected by the
         * sensor.
         */
        SmartDashboard.putNumber("Red", detectedColor.red);
        SmartDashboard.putNumber("Green", detectedColor.green);
        SmartDashboard.putNumber("Blue", detectedColor.blue);
        SmartDashboard.putString("Detected Color", colorString);
        SmartDashboard.putNumber("Proximity", proximity);
    }

}
