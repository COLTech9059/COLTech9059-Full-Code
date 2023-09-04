// Import necessary classes and packages
package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {
    // Create motor controller objects for left and right sides of the drive train
    private CANSparkMax LeftPrimary = new CANSparkMax(Constants.leftPPort, MotorType.kBrushed);
    private CANSparkMax RightPrimary = new CANSparkMax(Constants.rightPPort, MotorType.kBrushed);
    private CANSparkMax LeftFollower = new CANSparkMax(Constants.leftFPort, MotorType.kBrushed);
    private CANSparkMax RightFollower = new CANSparkMax(Constants.rightFPort, MotorType.kBrushed);
   
    // Create encoder objects for left and right sides of the drive train
    public Encoder Lencoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
    public Encoder Rencoder = new Encoder(2, 3, false, Encoder.EncodingType.k4X);

    // Create the differential drive object
    public final DifferentialDrive HamsterDrive = new DifferentialDrive(LeftPrimary, RightPrimary);

    public DriveTrain() {
        // Set deadband for the differential drive
        HamsterDrive.setDeadband(0.1);

        // Reset the factory defaults for the motor controllers
        LeftPrimary.restoreFactoryDefaults();
        RightPrimary.restoreFactoryDefaults();
        LeftFollower.restoreFactoryDefaults();
        RightFollower.restoreFactoryDefaults(); 
    
        // Set up the motor controller followers
        LeftFollower.follow(LeftPrimary);
        RightFollower.follow(RightPrimary);
    
        // Invert the right side motor controller
        RightPrimary.setInverted(true);
    }

    // Stop the drive train when activated
    public void stopDrive() {
        HamsterDrive.stopMotor();
    }

}