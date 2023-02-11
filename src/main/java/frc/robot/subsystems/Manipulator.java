package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.Constants;

public class Manipulator {
    public DoubleSolenoid piston = Constants.piston;
    public Compressor compressor = new Compressor(1, PneumaticsModuleType.REVPH);
    public DoubleSolenoid piston2 = Constants.piston2;
        //test
    public void toggleHeight() {
        piston2.toggle();
    }

    public void toggleExtension() {
        piston.toggle();
    }
}
