// Import necessary classes and packages
package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.IO;

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

    // Drive the robot using arcade drive with an Xbox controller
    public void arcadeDrive(XboxController dController) {
        double forward = IO.dController.getLeftY();
        double change = 0;
        double forwardPower = forward + change;
    
        // Apply a change to the forward power if moving in reverse
        if (forward < 0) change = 0.2;
        if (forward > 0) change = -0.2;

        double turn = IO.dController.getRightX();
        double turnPower = turn *= 0.5;

        // Drive the robot using arcade drive
        HamsterDrive.arcadeDrive(forwardPower, turnPower);
    }

    // Stop the drive train
    public void stopDrive() {
        HamsterDrive.stopMotor();
    }

}