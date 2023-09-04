package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
//import frc.robot.Robot;

public class Manipulator extends SubsystemBase{
    
    
    
    //Subsystem elements

    public static final CANSparkMax manipulatorMotor = new CANSparkMax(Constants.manipulatorMotorPort, MotorType.kBrushless);
    public final DigitalInput limitSwitch = new DigitalInput(4);
    public final DigitalInput rearLimitSwitch = new DigitalInput(5);

    public void extendLadder() {
        manipulatorMotor.set(0.40);
    }
    public void retractLadder() {
        manipulatorMotor.set(-0.40);
    } 
    public void stopLadder() {
        manipulatorMotor.set(0.0);
    } 
    /*
    public void toggleManipulatorHeight() {
        Robot.raisePistons.toggle();
    }
    public void toggleGrabber() {
        Robot.grabPiston.toggle();
    }
    */
}


