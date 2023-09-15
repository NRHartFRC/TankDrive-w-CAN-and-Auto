// Author: N. Rombach
// Last Updated : September 2023

// fetch the package from subsystems directory (package must be at top, imports are below)
package frc.robot.commands;

// impor the primary Robot class
import frc.robot.Robot;

// import constants for creating instances
import frc.robot.Constants;

// import SmartDashboard-specific classes
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

// import DriveSubsystem classes
import frc.robot.subsystems.DriveSubsystem;

// import the CommandBase class
import edu.wpi.first.wpilibj2.command.CommandBase;

// create DriveCommand subclass that inherits CommandBase classes as a superclass
public class DriveCommand extends CommandBase {

    // declare m_subsystem as a member variable of the DriveSubsystem class
    private DriveSubsystem m_subsystem;

    // declare a driveChooser variable and instantiate by assigning it to a binary SendableChooser
    SendableChooser<Boolean> driveChooser = new SendableChooser<Boolean>();
    
    // create a constructor, a method that initializes and configures the objects in the DriveCommand class
    public DriveCommand() {
        // assing member subsystem variable to the member driveSubsystem variable in the Robot class
        m_subsystem = Robot.m_driveSubsystem;

        // ensure the m_subsystem instance is used instead of its relatives
        addRequirements(m_subsystem);

        // label for SmartDashboard
        SmartDashboard.putString("Drive Type ", "Select");

        // objects that set the options for drive move
        driveChooser.setDefaultOption("Tank Drive", true);
        driveChooser.addOption("Arcade Drive", false);

        // maps the Drive Mode key to the sendable DriveChooser variable
        SmartDashboard.putData("Drive Mode", driveChooser);
    }
    
    @Override
    public void initialize() {
        // one-time initialization code for DriveCommand here
    }
    
    @Override
    public void execute() {
        if (driveChooser.getSelected()) { // Tank drive
            Double left_power = -1.0 * Robot.controller.getRawAxis(Constants.LEFT_VERTICAL_JOYSTICK_AXIS) * m_subsystem.CURRENT_DRIVE_SCALE;
            Double right_power = -1.0 * Robot.controller.getRawAxis(Constants.RIGHT_VERTICAL_JOYSTICK_AXIS) * m_subsystem.CURRENT_DRIVE_SCALE;
            m_subsystem.drive(left_power, right_power);
        }
        else { // Arcade Drive
            Double turning_power = -1.0 * Robot.controller.getRawAxis(Constants.RIGHT_HORIZONTAL_JOYSTICK_AXIS) * m_subsystem.CURRENT_DRIVE_SCALE;
            Double drive_power = -1.0 * Robot.controller.getRawAxis(Constants.LEFT_VERTICAL_JOYSTICK_AXIS) * m_subsystem.CURRENT_DRIVE_SCALE;
            m_subsystem.drive(drive_power - turning_power, drive_power + turning_power);
        }
    }
    
    // called once the command ends or is interrupted
    @Override
    public void end(final boolean interrupted) {
        m_subsystem.stop();
    }
    
    // returns true or false when the command should end
    @Override
    public boolean isFinished() {
        return false; // false = command will never finish (we don't want it to)
    }
}
