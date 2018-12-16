package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.robotconfig;

/**
 * Drivetrain subsystem. Initilizes the 4 drivetrain talons based on robotmap settings
 */
public class drivetrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  void init() {
    TalonSRX m_left = new TalonSRX(robotconfig.m_left_talon_port);


    
    TalonSRX m_right = new TalonSRX(robotconfig.m_right_talon_port);
  
  
  
  
  }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
