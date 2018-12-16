package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.sun.java.swing.plaf.windows.TMSchema.Control;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.robotconfig;

/**
 * Drivetrain subsystem. Initilizes the 4 drivetrain talons based on robotmap settings
 */
public class drivetrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

    TalonSRX m_left_talon = new TalonSRX(robotconfig.m_left_talon_port);
    TalonSRX s_left_talon = new TalonSRX(robotconfig.s_left_talon_port);
    TalonSRX m_right_talon = new TalonSRX(robotconfig.m_right_talon_port);
    TalonSRX s_right_talon = new TalonSRX(robotconfig.s_right_talon_port);
    DoubleSolenoid shifter_solenoid = new DoubleSolenoid(robotconfig.drivetrain_solenoid_low_gear_channel, robotconfig.drivetrain_solenoid_high_gear_channel);
    
    public void init() {
      s_left_talon.set(ControlMode.Follower, m_left_talon.getDeviceID());
      s_right_talon.set(ControlMode.Follower, m_right_talon.getDeviceID());
      if ( robotconfig.drivetrain_starting_gear == "low" ) {
        setLowGear();
      }
      else if ( robotconfig.drivetrain_starting_gear == "high" ) {
        setHighGear();
      }
    }


  public void setLowGear() {
    m_left_talon.config_kP(0, robotconfig.m_left_velocity_kp_low, 0);
    m_left_talon.config_kI(0, robotconfig.m_left_velocity_ki_low, 0);
    m_left_talon.config_kD(0, robotconfig.m_left_velocity_kd_low, 0);
    m_left_talon.config_kF(0, robotconfig.m_left_velocity_kf_low, 0);
    
    m_right_talon.config_kP(0, robotconfig.m_right_velocity_kp_low, 0);
    m_right_talon.config_kI(0, robotconfig.m_right_velocity_ki_low, 0);
    m_right_talon.config_kD(0, robotconfig.m_right_velocity_kd_low, 0);
    m_right_talon.config_kF(0, robotconfig.m_right_velocity_kf_low, 0);
    shifter_solenoid.set(DoubleSolenoid.Value.kForward);
    // TODO verify that kForward is low gear

  }
  public void setHighGear() {
    m_left_talon.config_kP(0, robotconfig.m_left_velocity_kp_high, 0);
    m_left_talon.config_kI(0, robotconfig.m_left_velocity_ki_high, 0);
    m_left_talon.config_kD(0, robotconfig.m_left_velocity_kd_high, 0);
    m_left_talon.config_kF(0, robotconfig.m_left_velocity_kf_high, 0);
    
    m_right_talon.config_kP(0, robotconfig.m_right_velocity_kp_high, 0);
    m_right_talon.config_kI(0, robotconfig.m_right_velocity_ki_high, 0);
    m_right_talon.config_kD(0, robotconfig.m_right_velocity_kd_high, 0);
    m_right_talon.config_kF(0, robotconfig.m_right_velocity_kf_high, 0);
    shifter_solenoid.set(DoubleSolenoid.Value.kReverse);
    // TODO verify that kForward is high gear

  }

  public void arcade(double forwardspeed, double turnspeed) {
    double leftspeed = forwardspeed + turnspeed;
    double rightspeed = forwardspeed - turnspeed;

    m_left_talon.set(ControlMode.Velocity, leftspeed);
    m_right_talon.set(ControlMode.Velocity, rightspeed);
    
  }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
