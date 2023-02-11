package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.Constants;

public class Manipulator {
    public Compressor compressor = new Compressor(1, PneumaticsModuleType.REVPH);
    public DoubleSolenoid piston = Constants.piston;
    public DoubleSolenoid piston2 = Constants.piston2;
    public DoubleSolenoid piston3 = Constants.piston3;

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
