// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

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
    
    // Creates the objects for HamsterDrive
    private CANSparkMax LeftPrimary = new CANSparkMax(Constants.leftPPort, MotorType.kBrushed);
    private CANSparkMax RightPrimary = new CANSparkMax(Constants.rightPPort, MotorType.kBrushed);
    private CANSparkMax LeftFollower = new CANSparkMax(Constants.leftFPort, MotorType.kBrushed);
    private CANSparkMax RightFollower = new CANSparkMax(Constants.rightFPort, MotorType.kBrushed);
   
    public Encoder Lencoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
    public Encoder Rencoder = new Encoder(2, 3, false, Encoder.EncodingType.k4X);

    public final DifferentialDrive HamsterDrive = new DifferentialDrive(LeftPrimary, RightPrimary);

    public DriveTrain() {
        HamsterDrive.setDeadband(0.1);
        // Reset motor controller settings to default
        LeftPrimary.restoreFactoryDefaults();
        RightPrimary.restoreFactoryDefaults();
        LeftFollower.restoreFactoryDefaults();
        RightFollower.restoreFactoryDefaults(); 
    
        // Set followers to follow their primaries
        LeftFollower.follow(LeftPrimary);
        RightFollower.follow(RightPrimary);
    
        // Set the right side as inverted
        RightPrimary.setInverted(true);
    }
    // Creates the arcade drive function that we use
    public void arcadeDrive(XboxController dController) {
        double forward = IO.dController.getLeftY();
        double change = 0;
        double forwardPower = forward + change;
    
        if (forward < 0) change = 0.2;
        if (forward > 0) change = -0.2;

        double turn = IO.dController.getRightX();
        double turnPower = turn *= 0.5;

        HamsterDrive.arcadeDrive(forwardPower, turnPower);
    }

    public void stopDrive() {
        HamsterDrive.stopMotor();
    }

}




