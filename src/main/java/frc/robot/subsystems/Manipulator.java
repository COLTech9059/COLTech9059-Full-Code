package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

public class Manipulator extends SubsystemBase{
    
    
    
    //Subsystem elements

    public static final CANSparkMax manipulatorMotor = new CANSparkMax(7, MotorType.kBrushless);

    public void extendLadder() {
        manipulatorMotor.set(0.5);
    }
    public void retractLadder() {
        manipulatorMotor.set(-0.5);
    } 
    public void stopLadder() {
        manipulatorMotor.set(0);
    } 
    public void toggleManipulatorHeight() {
        Robot.raisePiston.toggle();
        Robot.raisePiston2.toggle();
    }
    public void toggleGrabber() {
        Robot.piston3.toggle();
    }
}


