// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.DriveTrain;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  public static Manipulator manipulator = new Manipulator();
  public static DriveTrain drivetrain = new DriveTrain();
  public static IO io = new IO();

  public final Compressor compressor = new Compressor(01, PneumaticsModuleType.REVPH);

  public static final DoubleSolenoid raisePistons = new DoubleSolenoid(01,PneumaticsModuleType.REVPH, 0, 1);
  public static final DoubleSolenoid grabPiston = new DoubleSolenoid(01, PneumaticsModuleType.REVPH, 2, 3);

  private final Timer time = new Timer();
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    drivetrain.Lencoder.reset();
    drivetrain.Rencoder.reset();

    //Configures the encoder to return a distance of 1.36 inches for every 1 pulse(full rotations of encoder/motor)
    drivetrain.Lencoder.setDistancePerPulse(1.36/1);
    drivetrain.Rencoder.setDistancePerPulse(1.36/1);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

    if (compressor.getPressure() >= 115) compressor.disable();
    if (compressor.getPressure() <= 90) compressor.enableDigital();

    SmartDashboard.putNumber("Left Encoder RPM", drivetrain.Lencoder.getRate());
    SmartDashboard.putNumber("Right Encoder RPM", drivetrain.Rencoder.getRate());

    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();


  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {

     

    time.reset();
    time.start();
        // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    
      //for auto, use the distance in feet multiplied by 12(to get inches)
    if (drivetrain.Lencoder.getDistance() < 1 * 12) {
      //negative speeds must be used to go forward in auto because reasons
      drivetrain.HamsterDrive.arcadeDrive(-0.8, 0);
    }
    if (drivetrain.Lencoder.getDistance() > 1 * 12 && drivetrain.Lencoder.getDistance() < 12.5 * 12) {
      drivetrain.HamsterDrive.arcadeDrive(0.8, 0);
    } 
  
  } 

  @Override
  public void teleopInit() {
    
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double forward = IO.dController.getLeftY();
    double change = 0;
    double forwardPower = forward + change;

    if (forward < 0) change = 0.2;
    if (forward > 0) change = -0.2;

    double turn = IO.dController.getRightX();
    double turnPower = turn *= 0.5;

    drivetrain.HamsterDrive.arcadeDrive(forwardPower, turnPower);

  //Pneumatics/Manipulator controls
  // && manipulator.limitSwitch.get() == false
  // && manipulator.rearLimitSwitch.get() == false
    if (IO.dController.getRightTriggerAxis() > 0.2) manipulator.extendLadder(); 
    if (IO.dController.getLeftTriggerAxis() > 0.2) manipulator.retractLadder(); 
    if (IO.dController.getLeftTriggerAxis() < 0.2 && IO.dController.getRightTriggerAxis() < 0.2) manipulator.stopLadder();
    if (IO.dController.getRightBumper()) manipulator.toggleManipulatorHeight();
    if (IO.dController.getLeftBumper()) manipulator.toggleGrabber();
    
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
