package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Manipulator extends SubsystemBase{
    
    
    
    //pistons
    public static final DoubleSolenoid piston = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 1);
    public static final DoubleSolenoid piston2 = new DoubleSolenoid(PneumaticsModuleType.REVPH, 2, 3);
    public static final DoubleSolenoid piston3 = new DoubleSolenoid(PneumaticsModuleType.REVPH, 4, 5);

    public void toggleGrab() {
        piston3.toggle();
    }

    public void toggleHeight() {
        piston2.toggle();
    }

    public void toggleExtension() {
        piston.toggle();
    }

}
