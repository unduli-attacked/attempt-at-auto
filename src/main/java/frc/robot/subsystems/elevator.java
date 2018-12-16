/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.robotconfig;

import edu.wpi.first.wpilibj.DoubleSolenoid;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class elevator extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public TalonSRX elevator_talon = new TalonSRX(robotconfig.elevator_talon_port);
  public TalonSRX intake_talon = new TalonSRX(robotconfig.intake_talon_port);
  public DoubleSolenoid clamp_solenoid = new DoubleSolenoid(robotconfig.elevator_solenoid_clamp_channel, robotconfig.elevator_solenoid_open_channel);


  public void setElevatorSetpoint(int setpoint){
    this.position_setpoint = setpoint;
  }
  

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
