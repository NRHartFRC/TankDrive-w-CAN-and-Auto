// Author: N. Rombach
// Last Updated : September 2023

// import the files/classes/objects/instances from the commands folder
package frc.robot.commands;

// use software timer feature
import edu.wpi.first.wpilibj.Timer;

// import custom DriveSubsystem class
import frc.robot.subsystems.ArmSubsystem;

// import CommandBase superclass
import edu.wpi.first.wpilibj2.command.CommandBase;

// create a class called TimedExtendArmCommand that is a subclass of the CommandBase superclass
public class TimedExtendArmCommand extends CommandBase {

    // declare variables in a local scope for this class only
    private final ArmSubsystem m_armSubsystem;
    private final Timer timer;
    private final double duration;
    private final double power;
    
    // create a constructor, a method that initializes and configures the objects in the TimedExtendArmCommand class
    public TimedExtendArmCommand(final ArmSubsystem armSubsystem, final double duration, final double power) {
        this.timer = new Timer();
        this.m_armSubsystem = armSubsystem;
        this.duration = duration;
        this.power = power;
        this.addRequirements(this.m_armSubsystem);
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
        double currentTime = this.timer.get();

        // if current time is less than the specified duration
        if (currentTime < this.duration) {
            this.m_armSubsystem.extendArm(this.power);
        }
        else {
            this.m_armSubsystem.stopArm();
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
        this.m_armSubsystem.stopArm();
    }
}
