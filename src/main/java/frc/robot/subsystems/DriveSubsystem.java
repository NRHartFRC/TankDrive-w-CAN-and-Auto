// Author: N. Rombach
// Last Updated : September 2023

// fetch the package from subsystems directory (package must be at top, imports are below)
package frc.robot.subsystems;

// import data from Constants.java file
import frc.robot.Constants;

// APIs fetched from the vendordeps directory (must download REVLib.json file from REV site to use these API classes)
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// import SmartDashboard-specific classes
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

// import SubsystemBase superclass
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// create a subclass that inherits classes/objects from SubsystemBase superclass
public class DriveSubsystem extends SubsystemBase {

    // declare member variables as CANSparkMax datatype in private scope (accessable locally, not program-wide)
    private CANSparkMax m_leftMotor;
    private CANSparkMax m_rightMotor;

    // declare and initialize a direction variable as an integer datatype in a private scope
    private int direction = 1;

    // declare a variable called driveScaleChooser and initilaize an instance for selecting desired drive speed/scale/power
    SendableChooser<Double> driveScaleChooser = new SendableChooser<Double>();

    // declare CURRENT_DRIVE_SCALE variable as a double datatype with a public modifier (program-wide scope)
    public double CURRENT_DRIVE_SCALE;
    
    public DriveSubsystem() {
        // instantiate the member variables, each with a unique deviceID and MotorType
        m_leftMotor = new CANSparkMax(Constants.LEFT_DRIVE_MOTOR_ID, MotorType.kBrushed);
        m_rightMotor = new CANSparkMax(Constants.RIGHT_DRIVE_MOTOR_ID, MotorType.kBrushed);

        // make objects that use inverting methods on the member variables as needed
        m_leftMotor.setInverted(Constants.DRIVE_INVERT_LEFT); // this is currently set to false
        m_rightMotor.setInverted(Constants.DRIVE_INVERT_RIGHT); // this is currently set to true

        // label for SmartDashboard
        SmartDashboard.putString("Drivetrain ", "Select Power");

        // list drive scale/power/speed options for SmartDashboard
        driveScaleChooser.addOption("100%", 1.0);
        driveScaleChooser.setDefaultOption("75%", 0.75);
        driveScaleChooser.addOption("50%", 0.5);
        driveScaleChooser.addOption("25%", 0.25);

        // set the selected driveScaleChooser value
        SmartDashboard.putData("Drivetrain Speed", driveScaleChooser);
    }
    
    // drive method that uses the set method to determine power to the drivetrain motors
    public void drive(double leftPercentPower, double rightPercentPower) {
        m_leftMotor.set(direction * leftPercentPower);
        m_rightMotor.set(direction * rightPercentPower);
    }
    
    // stop method incicates what the motors should do when they stop
    public void stop() {

        //kill power to motors using drive method and set values to zero
        drive(0.0, 0.0);
    }
    
    // method uses enum DIRECTION argument determine direction of drivetrain
    public void setDirection(DIRECTION direction) {
        this.direction = direction.direction;
    }
    
    // method flips the direction of the drivetrain when executed
    public void toggleDirection() {
        // the *= expression multiplies left and right sides and assigns product to left side
        this.direction *= -1;
    }
    
    @Override
    public void periodic() {
        //continuously update the selected drive scale/speed/power
        CURRENT_DRIVE_SCALE = driveScaleChooser.getSelected();
    }
    
    // define enum fields with two constants, INTAKE_FRONT, and EXTENDER_FRONT stored in DIRECTION
    enum DIRECTION {
        INTAKE_FRONT(1),
        EXTENDER_FRONT(-1);
    
        public int direction;
    
        DIRECTION(int direction) {
            this.direction = direction;
        }
    }
}
