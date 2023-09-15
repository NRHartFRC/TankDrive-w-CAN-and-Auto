// Author: N. Rombach
// Last Updated : September 2023

// import the files/classes/objects/instances from the commands folder
package frc.robot.commands;

// import Trigger and InstantCommand classes, Trigger is used to map controller buttons to events
import edu.wpi.first.wpilibj2.command.button.Trigger;

// import Human-Interaction-Devices class that connects to FRC Driverstation
import edu.wpi.first.wpilibj.GenericHID;

// import custom ArmSubsystem class
import frc.robot.subsystems.ArmSubsystem;

// import CommandBase superclass
import edu.wpi.first.wpilibj2.command.CommandBase;

// create a class called POVMotorCommand that is a subclass of the CommandBase superclass
public class POVMotorCommand extends CommandBase {

    // declare variables in a local scope for this class only using private modifier
    private final ArmSubsystem armSubsystem;
    private final GenericHID controller;
    
    // create a constructor, a method that initializes and configures the objects in the POVMotorCommand class
    public POVMotorCommand(final ArmSubsystem armSubsystem, final GenericHID controller, final Trigger povTrigger) {
        this.armSubsystem = armSubsystem;
        this.controller = controller;
        this.addRequirements(armSubsystem);
    }
    
    @Override
    public void initialize() {
    }
    
    @Override
    public void execute() {
        // capture the button that is pressed
        final int povAngle = this.controller.getPOV();

        if (povAngle == 0) {
            // power variable is the speed that is applied for this button, change if needed
            final double power = 0.5 * this.armSubsystem.CURRENT_ARM_SCALE;
            this.armSubsystem.extendArm(power);
        }
        else if (povAngle == 180) {
            // power variable is the speed that is applied for this button, change if needed
            final double power = -0.5 * this.armSubsystem.CURRENT_ARM_SCALE;
            this.armSubsystem.extendArm(power);
        }
        else {
            this.armSubsystem.stopArm();
        }
    }
    
    @Override
    public boolean isFinished() {
        return false; // run continuously
    }
    
    @Override
    public void end(final boolean interrupted) {
        this.armSubsystem.stopArm();
    }
}
