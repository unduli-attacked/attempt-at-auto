package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotConfig;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.lib.EncoderLib;;


/**
 * A wrist subsystem with Talon hardware PID
 * Was never tested, the 775s burned out
 * 
 * @author Matthew Morley
 */
public class Wrist extends Subsystem {

  public TalonSRX m_wrist_talon = new TalonSRX(RobotConfig.wrist.m_wrist_talon_port);
  private TalonSRX s_wrist_talon = new TalonSRX(RobotConfig.wrist.s_wrist_talon_port);

  public void init() {

    // setup the mag encoder
    this.m_wrist_talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,30); // TODO put encoder stats on smartdashboard
    this.s_wrist_talon.set(ControlMode.Follower, s_wrist_talon.getDeviceID());
    this.m_wrist_talon.configSensorTerm(SensorTerm.Diff0, FeedbackDevice.QuadEncoder, 30); // Quadrature Encoder of current Talon
    this.m_wrist_talon.configPeakOutputForward(+1.0, 30);
    this.m_wrist_talon.configPeakOutputReverse(-1.0, 30);
    this.m_wrist_talon.setSelectedSensorPosition(0, 0, 10); // WARNING TODO so if the robot calls init(), the wrist will zero itself. Is this the behavior we want? Limit switches? 

    // configure PID
    this.m_wrist_talon.config_kP(0, RobotConfig.wrist.talon_config.position_kp, 0);
    this.m_wrist_talon.config_kI(0, RobotConfig.wrist.talon_config.position_ki, 0);
    this.m_wrist_talon.config_kD(0, RobotConfig.wrist.talon_config.position_kd, 0);
    this.m_wrist_talon.config_kF(0, RobotConfig.wrist.talon_config.position_kf, 0);
    this.m_wrist_talon.config_IntegralZone(0, RobotConfig.wrist.talon_config.position_izone, 0);
    // this.m_wrist_talon.configMaxIntegralAccumulator(0, RobotConfig.m_left_velocity_max_integral_low, 0);
  }
  
  public double getAngle(){
    return EncoderLib.rawToDegrees(
      this.m_wrist_talon.getSelectedSensorPosition(0), 
      RobotConfig.driveTrain.POSITION_PULSES_PER_ROTATION);
  }
  public double getAngularVelocity(){
    return EncoderLib.rawToDegrees(
      this.m_wrist_talon.getSelectedSensorVelocity(0), 
      RobotConfig.driveTrain.POSITION_PULSES_PER_ROTATION) * 10; // Angular velocity. Natively is raw per 100ms, so times by 10 to get degrees per second
  }

  public void setAngle(double target_angle){ // TODO verify math
    double targetRaw = EncoderLib.degreesToRaw(
      target_angle,
      RobotConfig.driveTrain.POSITION_PULSES_PER_ROTATION);
    m_wrist_talon.set(ControlMode.Position, targetRaw);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
