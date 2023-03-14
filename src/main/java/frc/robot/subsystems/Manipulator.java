package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants; 

public class Manipulator extends SubsystemBase{
    
    // Subsystem elements

    // Creates a motor controller object for the manipulator motor
    public static final CANSparkMax manipulatorMotor = new CANSparkMax(Constants.manipulatorMotorPort, MotorType.kBrushless);
    // Creates a digital input object for the front limit switch
    public final DigitalInput limitSwitch = new DigitalInput(4);
    // Creates a digital input object for the rear limit switch
    public final DigitalInput rearLimitSwitch = new DigitalInput(5);

    // Create the double solenoid objects
    public static final DoubleSolenoid raisePistons = new DoubleSolenoid(01,PneumaticsModuleType.REVPH, 0, 1);
    public static final DoubleSolenoid grabPiston = new DoubleSolenoid(01, PneumaticsModuleType.REVPH, 2, 3);

    // Create the timers for solenoids
    public Timer raiseTime = new Timer();
    public Timer grabTime = new Timer();
    
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
        raisePistons.toggle();
        raiseTime.reset();
        raiseTime.start();
    }
    // Create the function to toggle the grabber
    public void toggleGrabber() {
        grabPiston.toggle();
        grabTime.reset();
        grabTime.start();
    }
}
