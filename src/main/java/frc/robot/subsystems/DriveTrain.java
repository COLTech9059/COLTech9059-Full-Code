// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {
    //creates the motor controllers for HamsterDrive


    private CANSparkMax LeftPrimary = new CANSparkMax(Constants.leftPPort, MotorType.kBrushed);
    private CANSparkMax RightPrimary = new CANSparkMax(Constants.rightPPort, MotorType.kBrushed);
    private CANSparkMax LeftFollower = new CANSparkMax(Constants.leftFPort, MotorType.kBrushed);
    private CANSparkMax RightFollower = new CANSparkMax(Constants.rightFPort, MotorType.kBrushed);
   
   

    public final DifferentialDrive HamsterDrive = new DifferentialDrive(LeftPrimary, RightPrimary);

    public DriveTrain() {
        HamsterDrive.setDeadband(0.1);

        LeftPrimary.restoreFactoryDefaults();
        RightPrimary.restoreFactoryDefaults();
        LeftFollower.restoreFactoryDefaults();
        RightFollower.restoreFactoryDefaults(); 
    
        LeftFollower.follow(LeftPrimary);
        RightFollower.follow(RightPrimary);
    
        
        RightPrimary.setInverted(true);
    }

    public void arcadeDrive(XboxController dController) {
        double forwardPower = dController.getLeftY();
        double turn = dController.getRightX();
        double turnPower = turn *= 0.5;

        if (dController.getLeftBumper()) turnPower *= 2;

        HamsterDrive.arcadeDrive(forwardPower, turnPower);
    }

    public void stopDrive() {
        HamsterDrive.stopMotor();
    }

}
