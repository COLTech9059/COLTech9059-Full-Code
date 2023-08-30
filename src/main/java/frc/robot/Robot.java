// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.DriveTrain;



/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  // Declare all the objects for hardware components
  private Command m_autonomousCommand;

  // Create the objects for the robot.java file (they don't really work or belong anywhere else)
  public static DriveTrain drivetrain = new DriveTrain();
  public static IO io = new IO();

  public final Compressor compressor = new Compressor(01, PneumaticsModuleType.REVPH);

  // Create the cube piston object on PH hub ports 4 and 5
  private static DoubleSolenoid cubePiston = new DoubleSolenoid(1, PneumaticsModuleType.REVPH, 4, 5);

  private final Timer timer = new Timer();
  private final Timer timer2 = new Timer();
  private final Timer cubeTimer = new Timer();
  // Create the Autonomous control variable
  private int autoMode = 0;
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
  /** Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    * autonomous chooser on the dashboard. 
    */
    drivetrain.Lencoder.reset();
    drivetrain.Rencoder.reset();

    //Configures the encoders to return a distance of 0.68 inches per half pulse 
    //(or 1.36 inches per full pulse) where each pulse is a full rotation of the encoder/motor)
    drivetrain.Lencoder.setDistancePerPulse(0.68/0.5);
    drivetrain.Rencoder.setDistancePerPulse(0.68/0.5);

    // Set the state of the solenoids so they can be toggled later
    //Manipulator.raisePistons.set(Value.kForward);
    //Manipulator.grabPiston.set(Value.kForward);

    timer.reset();
    cubeTimer.reset();
    cubeTimer.start();

    cubePiston.set(Value.kReverse);
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
    // Create an if loop to make sure the compressor never goes over 115 PSI and not below 90PSI once it is pressurized
    if (compressor.getPressure() >= 115) compressor.disable();
    if (compressor.getPressure() <= 90) compressor.enableDigital();

    // Displays the Left and Right encoder rates on the dashboard with the specified names
    SmartDashboard.putNumber("Left Encoder Rate", drivetrain.Lencoder.getRate());
    SmartDashboard.putNumber("Right Encoder Rate", drivetrain.Rencoder.getRate());
  /** Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    * commands, running already-scheduled commands, removing finished or interrupted commands,
    * and running subsystem periodic() methods.  This must be called from the robot's periodic
    * block in order for anything in the Command-based framework to work.
    */
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

    timer.reset();
    timer.start();

    ////////////////////////////////////////////////////////////////////////////////
    /**  0 = No driving in autonomous                           ////////////////////
     * 1 = Autonomous for left or right position                ////////////////////
     * 2 = Autonomous for middle position (Charge Station)      ////////////////////
     *//////////////////////////////////////////////////////////////////////////////
    autoMode = 1;                                               ////////////////////
    ////////////////////////////////////////////////////////////////////////////////
  }

  /** This function is called periodically during autonomous. */
   @Override
   public void autonomousPeriodic() {
    /**  This variable will control which autonomous will run   
    * 
    */

    // These if statements control all of our autonomous program, and we use timers to control them

    // Auto for Left and Right sides of the Charge Station
    if (timer.get() < 6.2 && autoMode == 1) {
      timer2.reset();
      cubePiston.set(Value.kForward);
      timer2.start();

      if (timer2.get() > 1.0) cubePiston.set(Value.kReverse);     

      drivetrain.HamsterDrive.arcadeDrive(-0.3, 0.0, false);
    } 

    // Auto for engaging to the Charge Station 
    // 3.2 seconds should get to the middle, so overshoot by 1.3
    if (timer.get() < 2.3 && autoMode == 2) {
      cubePiston.set(Value.kForward);
      
      if (timer.get() <= 1.33 + 0.75 && timer.get() > 0.75) drivetrain.HamsterDrive.arcadeDrive(-0.7, 0.0, false);
      //if (timer.get() > 4.25) drivetrain.HamsterDrive.arcadeDrive(0.7, 0.0, false);
    }

    // No driving in auto if autoMode is equal to 0
    if (autoMode == 0) {
      drivetrain.HamsterDrive.arcadeDrive(0, 0, false);
      timer2.reset();
      cubePiston.set(Value.kForward);

      timer2.start();

      if (timer2.get() > 1.0) cubePiston.set(Value.kReverse);
    }
  } 

  @Override
  public void teleopInit() {
    
  /** This makes sure that the autonomous stops running when
    * teleop starts running. If you want the autonomous to
    * continue until interrupted by another command, remove
    * this line or comment it out.
    */
    cubePiston.set(Value.kReverse);

    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    // Get the value of the Y-Axis on the joystick
    double forward = IO.dController.getLeftY();

    // Adjust Speed/Power so that it will always be at a max of 80%
    double change = 0;
    double forwardPower = forward + change;

    if (forward < 0) change = 0.2;
    if (forward > 0) change = -0.2;

    // Set turn to the value of the X-Axis on the joystick
    double turn = IO.dController.getRightX();

    // Reduce Turn Power
    double turnPower = turn *= 0.73;

    // Drive the Robot with <forwardPower> and <turnPower>
    drivetrain.HamsterDrive.arcadeDrive(forwardPower, turnPower);

    // Pneumatics/Manipulator Controls
    // if (IO.dController.getRightTriggerAxis() > 0.2 && !manipulator.limitSwitch.get()) manipulator.extendLadder(); else manipulator.stopLadder(); 
    // if (IO.dController.getLeftTriggerAxis() > 0.2 && manipulator.rearLimitSwitch.get() == false ) manipulator.retractLadder(); 
    // if (IO.dController.getLeftTriggerAxis() < 0.2 && IO.dController.getRightTriggerAxis() < 0.2) manipulator.stopLadder();
 
    // if (IO.dController.getRightBumper() && manipulator.raiseTime.get() > 1) manipulator.toggleManipulatorHeight();
    // if (IO.dController.getLeftBumper() && manipulator.grabTime.get() > 1) manipulator.toggleGrabber();
    
    if (IO.dController.getLeftBumper()) cubePiston.set(Value.kReverse);
    // if (IO.dController.getRightBumper()) cubePiston.set(Value.kForward);
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
