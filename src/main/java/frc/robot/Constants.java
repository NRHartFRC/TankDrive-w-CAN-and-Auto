// Author: N. Rombach
// Last Updated : September 2023

// import the robot package objects
package frc.robot;

// create a new class called constants to assign variables that do not change
public class Constants {

    // What does INITIALIZE mean? Answer: public static final int CONTROLLER_USB_PORT_ID
    // What does DECLARE mean? Answer:  = 0

    // declare and initialize the controller USB port number from FRC Driver Station
    public static final int CONTROLLER_USB_PORT_ID = 0;

    // controller input axes
    public static final int LEFT_VERTICAL_JOYSTICK_AXIS = 1;
    public static final int RIGHT_VERTICAL_JOYSTICK_AXIS = 5;
    public static final int RIGHT_HORIZONTAL_JOYSTICK_AXIS = 2;
    public static final int Y_BUTTON = 4;

    // SparkMax CAN IDs
    public static final int LEFT_DRIVE_MOTOR_ID = 1;
    public static final int RIGHT_DRIVE_MOTOR_ID = 2;
    public static final int ARM_MOTOR_CAN_ID = 3;

   // arm options
    public static final boolean ARM_MOTOR_INVERTED = false;

    // drivetrain constants    
    public static final boolean DRIVE_INVERT_LEFT = false;
    public static final boolean DRIVE_INVERT_RIGHT = true;

    // declare and initialize the LED PWM Port(s)
    public static final int LED_PWM_ID = 4;
}
