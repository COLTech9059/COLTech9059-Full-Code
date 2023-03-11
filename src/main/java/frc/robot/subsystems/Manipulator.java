package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class Manipulator extends SubsystemBase{
    
    
    
    // Create the objects for the manipulator
    public static final CANSparkMax manipulatorMotor = new CANSparkMax(Constants.manipulatorMotorPort, MotorType.kBrushless);
    public final DigitalInput limitSwitch = new DigitalInput(4);
    public final DigitalInput rearLimitSwitch = new DigitalInput(5);

    // Create the function to extend the ladder
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
    // Create the function to toggle the manipulator height
    public void toggleManipulatorHeight() {
        Robot.raisePistons.toggle();
    }
    // Create the function to toggle the grabber
    public void toggleGrabber() {
        Robot.grabPiston.toggle();
    }
}


