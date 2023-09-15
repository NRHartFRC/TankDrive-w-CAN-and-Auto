// Author: N. Rombach
// Last Updated : September 2023

// import the autonomous directory and its contents
package frc.robot.commands.autonomous;

// import the primary Robot class
import frc.robot.Robot;

// import individual commands
import frc.robot.commands.TimedRotateCCWCommand;
import frc.robot.commands.TimedDriveBackwardCommand;
import frc.robot.commands.TimedExtendArmCommand;
import frc.robot.commands.TimedDriveStraightCommand;

//import the SmartDashboard
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//import the autonomous chain-effect class called SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// create a class called Auton_Default that is a subclass of the CommandBase superclass
public class Auton_Default extends SequentialCommandGroup {

    // create a constructor, a method that initializes, and the objects in the Auton_Default class
    public Auton_Default() {
        
        // create label for the SmartDashboard
        SmartDashboard.putString("Auton Mode ", "Default");
        
        // create options for Autonomous routine. Try making the parameters / arguments initialized variables instead of numbers
        this.addCommands(new TimedDriveStraightCommand(1.0, 0.1));
        this.addCommands(new TimedExtendArmCommand(Robot.m_armSubsystem, 1.0, 0.1));
        this.addCommands(new TimedDriveBackwardCommand(1.0, 0.1));
        this.addCommands(new TimedRotateCCWCommand(1.0, -0.1));

        // addCommands(new TimedArmDownCommand(2, 0.3)); // does not exist
        // addCommands(new ToggleArmCommand(true)); // does not exist
    }
}

/*Need to create a new command?
For the new command (.java file) you need to create, follow the method structure: 
public class myNewCommand extends 
    - public void initialize() {} 
    - public void execute() {} 
    - public void isFinished() {} 
    - public void end() {}
Still have questions? Go to README.md file and find commands.html link under Commands.
*/
