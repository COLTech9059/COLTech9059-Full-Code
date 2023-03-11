package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;

public class Commands {
    // Drive Train commands ( we don't use these)
    public static final Command EmergencyStop = new InstantCommand(() -> Robot.drivetrain.stopDrive(), Robot.drivetrain);

    public static final Command HamsterCommand = new RunCommand(() -> Robot.drivetrain.arcadeDrive(Robot.io.dController), Robot.drivetrain);

}
