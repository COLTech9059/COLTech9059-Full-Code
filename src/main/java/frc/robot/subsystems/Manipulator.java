package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class Manipulator extends SubsystemBase{
    
    // Subsystem elements

    // Creates a motor controller object for the manipulator motor
    public static final CANSparkMax manipulatorMotor = new CANSparkMax(Constants.manipulatorMotorPort, MotorType.kBrushless);
    // Creates a digital input object for the limit switch
    public final DigitalInput limitSwitch = new DigitalInput(4);
    // Creates a digital input object for the rear limit switch
    public final DigitalInput rearLimitSwitch = new DigitalInput(5);

    // Methods to control the manipulator motor
    public void extendLadder() {
        manipulatorMotor.set(0.40);
    }
    // Create the function to retract the ladder
    public void retractLadder() {
        manipulatorMotor.set(-0.40);
    } 
    // Create the function to stop the ladder
    public void stopLadder() {
        manipulatorMotor.set(0.0);
    } 

    // Methods to toggle the grabber and piston height using a command-based approach
    public void toggleManipulatorHeight() {
        Robot.raisePistons.toggle();
    }
    // Create the function to toggle the grabber
    public void toggleGrabber() {
        Robot.grabPiston.toggle();
    }
}
