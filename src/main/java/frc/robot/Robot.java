// Author: N. Rombach
// Last Updated : September 2023

// THIS IS A COMMENT. Statements after these // or between /*this*/ are comments.

// import the entire frc.robot package (must be at top, because this the top-level directory)
// this encapulates everything below, below will not work without this.
package frc.robot;

// import Trigger and InstantCommand classes, Trigger is used to map controller buttons to events
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.InstantCommand;

// import standard CommandScheduler class
import edu.wpi.first.wpilibj2.command.CommandScheduler;

// import SmartDashboard class (used for graphical interaction, it is a separate appliction)
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

// import custom commands (these exist as real files to the left <---)
import frc.robot.commands.autonomous.Auton_Default;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.commands.POVMotorCommand;

// import Human-Interaction-Devices class that connects to FRC Driverstation
import edu.wpi.first.wpilibj.GenericHID;

// import CommandBase class (requires WPILibNewCommands.json dependency in vendordeps!)
import edu.wpi.first.wpilibj2.command.CommandBase;

// import the TimedRobot superclass
import edu.wpi.first.wpilibj.TimedRobot;

// this Robot subclass inherits classes from TimedRobot superclass
public class Robot extends TimedRobot {

    // declare a member variable called m_autonomousCommand as a CommandBase datatype
    CommandBase m_autonomousCommand;

    // declare and initialize the autonChooser variable
    SendableChooser<CommandBase> autonChooser = new SendableChooser<CommandBase>();

    // declare a controller variable of GenericHID datatype and initialize it to a USB port from FRC Driver Station
    public static final GenericHID controller = new GenericHID(Constants.CONTROLLER_USB_PORT_ID);

    // declare a private (local-scope) variable named povTrigger that using the Trigger class (datatype)
    private Trigger povTrigger;

    // declare a private (local-scope) variable named povMotorCommand that uses the POVMotorCommand class (datatype)
    private POVMotorCommand povMotorCommand;

    // create an instance of the DriveSubsystem class and assign it to the member variable m_driveSubsystem
    public static final DriveSubsystem m_driveSubsystem = new DriveSubsystem();

    // create an instance of the ArmSubsystem class and assign it to the member variable m_armSubsystem
    public static final ArmSubsystem m_armSubsystem = new ArmSubsystem();
    
    // TimedRobot has its own robotInit class. We are overriding the superclass with out own custom robotInit method.
    @Override
    public void robotInit() {

        // method that binds commands to physical buttons on a controller (method defined at bottom)
        configureButtonBindings();

        // set the DriveCommand (actuall file) to execute by default 
        m_driveSubsystem.setDefaultCommand(new DriveCommand());
        
        // setup POV (D-Pad) for ancillary motor using POVMotorCommand that takes three objects/arguments
        povMotorCommand = new POVMotorCommand(m_armSubsystem, controller, povTrigger);

        // add autonomous routines to the chooser (appears on SmartDashboard application)
        autonChooser.setDefaultOption("Default Auto", new Auton_Default());
            // autonChooser.addOption("Booger", new Auton_Custom1(m_armSubsystem));
            // autonChooser.addOption("Flicker", new Auton_Custom2(m_armSubsystem));
        
        // // specify which auto mode (data, autonChooser) to use for SmartDashboard
        // SmartDashboard.putData("Auto Mode", autonChooser);

        // Create instances of your autonomous commands
        Auton_Default defaultAuton = new Auton_Default(); // exists in commands>autonomous>Auton_Default.java
            // Auton_Custom1 customAuton1 = new Auton_Custom1(m_armSubsystem); // does not exist yet
            // Auton_Custom2 customAuton2 = new Auton_Custom2(m_armSubsystem); // does not exist yet

        // configure sendable chooser to select defaultAuton variable by default
        autonChooser.setDefaultOption("Default Auto", defaultAuton);
            // autonChooser.addOption("Custom Auto 1", customAuton1);
            // autonChooser.addOption("Custom Auto 2", customAuton2);

        // specify which auto mode (data, autonChooser) to use for SmartDashboard
        SmartDashboard.putData("Auto Mode", autonChooser);
    }
    
    @Override
    public void robotPeriodic() {
        // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
        // commands, running already-scheduled commands, removing finished or interrupted commands,
        // and running subsystem periodic() methods. This must be called from the robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run();
    }
    
    @Override
    public void autonomousInit() {

        // print message to console
        System.out.println("AUTONOMOUS MODE STARTED");

        // initialize/assign the m_autonomousCommand variable to whichever autonomous mode that was selected from SmartDashboard
        m_autonomousCommand = autonChooser.getSelected();
        
        // != means not-equal-to
        // apply selected autonChooser mode, then schedule it to execute!
        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }
    }
    
    @Override
    public void autonomousPeriodic() {
        // run a single iteration of the selected autonomous instance scheduled earlier
        CommandScheduler.getInstance().run();
    }
    
    @Override
    public void teleopInit() {
        // add one-time teleop setup code here
    }
    
    @Override
    public void teleopPeriodic() {
        // continuously execute if/when the POV (D-pad)
        povMotorCommand.execute();
    }
    
    @Override
    public void testInit() {
        // cancel all running commands at the start of test mode
        CommandScheduler.getInstance().cancelAll();
    }
    
    @Override
    public void testPeriodic() {
    }
    
    /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or onse of its subclasses ({@link edu.wpi.first.wpilibj.Joystick} 
   * or {@link XboxController}), and then passing it to a {@link edu.wpi.first.wpilibj2.command.button.Trigger}.
   */
    private void configureButtonBindings() {
        
        // create a Trigger for the Y Button to instantly toggle the direction of the drivetrain
        new Trigger(() -> controller.getRawButton(Constants.Y_BUTTON)).onTrue(new InstantCommand(() -> m_driveSubsystem.toggleDirection()));

        // create instance of the povTrigger that will react when POV/D-pad is pressed
        povTrigger = new Trigger(() -> controller.getPOV() != -1);

        // create new instance of the POVMotor that takes three objects/arguments to work
        new POVMotorCommand(m_armSubsystem, controller, povTrigger);
    }
}
