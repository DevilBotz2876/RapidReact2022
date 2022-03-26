package bhs.devilbotz.commands.autonomous.drive.driverassist;

import bhs.devilbotz.RobotContainer;
import bhs.devilbotz.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Approach extends CommandBase {
    private final DriveTrain drive;

    private final Ultrasonic ultrasonic;
    private final Ultrasonic ultrasonicTwo;
    private final AnalogPotentiometer ultrasonicThree;

    private final RobotContainer robotContainer;

    public Approach(DriveTrain drive, RobotContainer robotContainer) {
        this.drive = drive;
        this.robotContainer = robotContainer;


        ultrasonic = new Ultrasonic(9, 10);
        ultrasonicTwo = new Ultrasonic(7, 6);

        ultrasonicThree = new AnalogPotentiometer(0);

        Ultrasonic.setAutomaticMode(true);
        ultrasonic.setEnabled(true);
        ultrasonicTwo.setEnabled(true);
    }


    /**
     * Runs when the command is first scheduled.
     */
    @Override
    public void initialize() {
    }

    /**
     * Called every time the scheduler runs while the command is scheduled.
     */
    @Override
    public void execute() {
        System.out.println("Ultrasonic: " + ultrasonicThree.get());
        if (Math.abs((ultrasonicTwo.getRangeMM() - ultrasonic.getRangeMM()) / ultrasonic.getRangeMM()) < 0.25) {
            drive.tankDrive(0.5, 0.5);
        } else {
            if (ultrasonic.getRangeMM() >= ultrasonicTwo.getRangeMM()) {
                drive.tankDrive(-0.5, 0.5);
            } else {
                drive.tankDrive(0.5, -0.5);
            }
        }
    }

    /**
     * Called once the command ends or is interrupted.
     *
     * @param interrupted Whether the command was interrupted.
     */
    @Override
    public void end(boolean interrupted) {
        drive.tankDrive(0, 0);
    }

    /**
     * Returns true when the command should end.
     *
     * @return Whether the command should end.
     */
    @Override
    public boolean isFinished() {
        return (((Math.abs((ultrasonicTwo.getRangeMM() - ultrasonic.getRangeMM()) / ultrasonic.getRangeMM()) < 0.25) && (ultrasonic.getRangeMM() <= 300.0 && ultrasonicTwo.getRangeMM() <= 300.0) || (ultrasonic.getRangeMM() >= 4500.0 && ultrasonicTwo.getRangeMM() >= 4500.0)) || ((robotContainer.getJoy().getY() >= 0.15 || robotContainer.getJoyTwo().getY() >= 0.15) || (robotContainer.getJoy().getY() <= -0.15 || robotContainer.getJoyTwo().getY() <= -0.15)));
    }

    public Ultrasonic getUltrasonic() {
        return ultrasonic;
    }

    public Ultrasonic getUltrasonicTwo() {
        return ultrasonicTwo;
    }
}
