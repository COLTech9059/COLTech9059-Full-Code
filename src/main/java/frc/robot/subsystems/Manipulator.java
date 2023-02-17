package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

public class Manipulator extends SubsystemBase{
    
    
    
    //Subsystem elements

    public static final CANSparkMax manipulatorMotor = new CANSparkMax(7, MotorType.kBrushless);

    public void extendManipulator() {
        manipulatorMotor.set(.5);
    }
    public void stopExtension() {
        manipulatorMotor.set(0);
    }
    public void toggleManipulatorHeight() {
        Robot.piston.toggle();
    }
    public void toggleGrab() {
        Robot.piston2.toggle();
    }
}


