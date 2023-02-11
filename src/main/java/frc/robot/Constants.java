// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    //HamsterDrive motor controller ports
    public static final int leftPPort = 1;
    public static final int rightPPort = 2;
    public static final int leftFPort = 3;
    public static final int rightFPort = 4;

    //piston
    public static final DoubleSolenoid piston = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 1);
    public static final DoubleSolenoid piston2 = new DoubleSolenoid(PneumaticsModuleType.REVPH, 2, 3);
    public static final DoubleSolenoid piston3 = new DoubleSolenoid(PneumaticsModuleType.REVPH, 4, 5);


}
