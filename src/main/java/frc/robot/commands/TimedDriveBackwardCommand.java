// Author: N. Rombach
// Last Updated : September 2023

// import the files/classes/objects/instances from the commands folder
package frc.robot.commands;

// import Robot class
import frc.robot.Robot;

// use software timer feature
import edu.wpi.first.wpilibj.Timer;

// import custom DriveSubsystem class
import frc.robot.subsystems.DriveSubsystem;

// import CommandBase superclass
import edu.wpi.first.wpilibj2.command.CommandBase;

// create a class called TimedDriveBackwardCommand that is a subclass of the CommandBase superclass
public class TimedDriveBackwardCommand extends CommandBase {

    // declare variables in a local scope for this class only
    private final DriveSubsystem m_drivetrainSubsystem;
    private final Timer timer;
    private final double duration;
    private final double driveRate;
    
    // create a constructor, a method that initializes and configures the objects in the TimedDriveBackwardCommand class
    public TimedDriveBackwardCommand(final double time, final double driveRate) {
        this.timer = new Timer();
        this.duration = time;
        this.driveRate = driveRate;
        this.m_drivetrainSubsystem = Robot.m_driveSubsystem;
        this.addRequirements(this.m_drivetrainSubsystem);
    }
    
    // clear and start the timer at start of schedule for this class
    @Override
    public void initialize() {
        this.timer.reset();
        this.timer.start();
    }
    
    @Override
    public void execute() {
        // capture currentTime
        final double currentTime = this.timer.get();

        // if current time is less than the specified duration, invoke drive method
        if (currentTime < this.duration) {
            this.m_drivetrainSubsystem.drive(-this.driveRate, -this.driveRate);
        }
        else {
            this.m_drivetrainSubsystem.stop();
        }
    }
    
    @Override
    public boolean isFinished() {
        // when left side is greater-than-or-equal to right side, this command is finished
        return this.timer.get() >= this.duration;
    }
    
    @Override
    public void end(final boolean interrupted) {
        // stop commands from continuing their executions
        this.m_drivetrainSubsystem.stop();
    }
}
