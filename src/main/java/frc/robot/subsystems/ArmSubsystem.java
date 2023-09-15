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

// import the SubsystemBase superclass
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {

    // declare motor controller variable called armMotor as a CANSparkMax datatype
    private final CANSparkMax armMotor;

    // declare and initialize a sendablechooser variable called armScaleChooser
    SendableChooser<Double> armScaleChooser = new SendableChooser<Double>();

    // declare a double variable for the scale/speed/power of the arm
    public double CURRENT_ARM_SCALE;
    
    // create a constructor, a method that initializes and configures the objects in the ArmSubystem class
    public ArmSubsystem() {
        
        // instantiate the motor and set its direction
        armMotor = new CANSparkMax(Constants.ARM_MOTOR_CAN_ID, MotorType.kBrushless);
        armMotor.setInverted(Constants.ARM_MOTOR_INVERTED);

        // create label for SmartDashboard
        SmartDashboard.putString("Arm Speed ", "Select Scale");

        // list options for SmartDashboard application display
        armScaleChooser.addOption("100%", 1.0);
        armScaleChooser.setDefaultOption("75%", 0.75);
        armScaleChooser.addOption("50%", 0.5);
        armScaleChooser.addOption("25%", 0.25);

        // set the chosen option
        SmartDashboard.putData("Arm Speed", armScaleChooser);
    }
    
    // method that sets sets the power/speed to the motor when given a double argument
    public void extendArm(double power) {
        armMotor.set(power);
    }
    
    // method to kill power to the motor
    public void stopArm() {
        armMotor.set(0.0);
    }
    
    @Override
    public void periodic() {
        // continuously update the desired drive scale/speed
        this.CURRENT_ARM_SCALE = this.armScaleChooser.getSelected();
    }
}
