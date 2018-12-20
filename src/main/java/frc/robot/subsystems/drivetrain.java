package frc.robot.subsystems;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import frc.robot.Robot;
import frc.robot.lib.encoderlib;
import frc.robot.robotconfig;

import frc.robot.commands.arcade_drive;

// import frc.robot.commands.drivetrain_shift_high;
// import frc.robot.commands.drivetrain_shift_low;


/**
 * Drivetrain subsystem. Initilizes the 4 drivetrain talons based on robotmap
 * settings
 */
public class drivetrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

    

    public TalonSRX m_left_talon = new TalonSRX(robotconfig.m_left_talon_port);
    public TalonSRX s_left_talon = new TalonSRX(robotconfig.s_left_talon_port);
    public TalonSRX m_right_talon = new TalonSRX(robotconfig.m_right_talon_port);
    public TalonSRX s_right_talon = new TalonSRX(robotconfig.s_right_talon_port);
    public String current_gear;

    // PIDController left_position_pid_controller_HIGH_GEAR = new PIDController(robotconfig.m_left_position_kp_high, robotconfig.m_left_position_ki_high, robotconfig.m_left_position_kd_high, robotconfig.m_left_position_kf_high, source, output) // TODO Implament position PID controllers for left and right drivetrain sides

    public void init() {
      m_left_talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,30); // TODO put encoder stats on smartdashboard
      m_right_talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,30); // TODO put encoder stats on smartdashboard
      s_left_talon.set(ControlMode.Follower, m_left_talon.getDeviceID());
      s_right_talon.set(ControlMode.Follower, m_right_talon.getDeviceID());
      m_right_talon.configSensorTerm(SensorTerm.Diff0, FeedbackDevice.QuadEncoder, 30); // Quadrature Encoder of current Talon
      m_left_talon.configSensorTerm(SensorTerm.Diff0, FeedbackDevice.QuadEncoder, 30); // Quadrature Encoder of current Talon

      m_left_talon.configPeakOutputForward(+1.0, 30);
      m_left_talon.configPeakOutputReverse(-1.0, 30);
      m_right_talon.configPeakOutputForward(+1.0, 30);
      m_right_talon.configPeakOutputReverse(-1.0, 30);


    	/* 1ms per loop.  PID loop can be slowed down if need be.
      * For example,
      * - if sensor updates are too slow
      * - sensor deltas are very small per update, so derivative error never gets large enough to be useful.
      * - sensor movement is very slow causing the derivative error to be near zero.
      */
      int closedLoopTimeMs = 1;
      // TODO can I get rid of this code, or is it disabling something important?
      m_right_talon.configSetParameter(ParamEnum.eSampleVelocityPeriod, closedLoopTimeMs, 0x00, 0,30);
      m_right_talon.configSetParameter(ParamEnum.eSampleVelocityPeriod, closedLoopTimeMs, 0x00, 1, 30);
      
      // public String gear_state;

      // Robot.shifter.set(DoubleSolenoid.Value.kForward);
      // Robot.shifter.set(DoubleSolenoid.Value.kReverse);

      setHighGear();

      // if ( robotconfig.drivetrain_starting_gear == "low" ) {
      //   new drivetrain_shift_low();
      // }
      // if ( robotconfig.drivetrain_starting_gear == "high" ) {
      //   new drivetrain_shift_high();
      // }

    }
    


    public void setHighGear() {
      this.m_left_talon.config_kP(0, robotconfig.m_left_velocity_kp_high, 30);
      this.m_left_talon.config_kI(0, robotconfig.m_left_velocity_ki_high, 30);
      this.m_left_talon.config_kD(0, robotconfig.m_left_velocity_kd_high, 30);
      this.m_left_talon.config_kF(0, robotconfig.m_left_velocity_kf_high, 30);
      this.m_left_talon.config_IntegralZone(0, robotconfig.m_left_velocity_izone_high, 30);
      // this.m_left_talon.configMaxIntegralAccumulator(0, robotconfig.m_left_velocity_max_integral_high, 0);
  
      this.m_right_talon.config_kP(0, robotconfig.m_right_velocity_kp_high, 30);
      this.m_right_talon.config_kI(0, robotconfig.m_right_velocity_ki_high, 30);
      this.m_right_talon.config_kD(0, robotconfig.m_right_velocity_kd_high, 30);
      this.m_right_talon.config_kF(0, robotconfig.m_right_velocity_kf_high, 30);
      this.m_right_talon.config_IntegralZone(0, robotconfig.m_right_velocity_izone_high, 30);
      // this.m_right_talon.configMaxIntegralAccumulator(0, robotconfig.m_right_velocity_max_integral_high, 0);
      
      m_left_talon.setSelectedSensorPosition(0, 0, 10);
      m_right_talon.setSelectedSensorPosition(0, 0, 10);
      m_left_talon.setInverted(true);
      s_left_talon.setInverted(true);

      Robot.drivetrain_shift_high();

      current_gear = "high";
    }

    public void setLowGear() {
      this.m_left_talon.config_kP(0, robotconfig.m_left_velocity_kp_low, 0);
      this.m_left_talon.config_kI(0, robotconfig.m_left_velocity_ki_low, 0);
      this.m_left_talon.config_kD(0, robotconfig.m_left_velocity_kd_low, 0);
      this.m_left_talon.config_kF(0, robotconfig.m_left_velocity_kf_low, 0);
      this.m_left_talon.config_IntegralZone(0, robotconfig.m_left_velocity_izone_low, 0);
      // this.m_left_talon.configMaxIntegralAccumulator(0, robotconfig.m_left_velocity_max_integral_low, 0);
      
      this.m_right_talon.config_kP(0, robotconfig.m_right_velocity_kp_low, 0);
      this.m_right_talon.config_kI(0, robotconfig.m_right_velocity_ki_low, 0);
      this.m_right_talon.config_kD(0, robotconfig.m_right_velocity_kd_low, 0);
      this.m_right_talon.config_kF(0, robotconfig.m_right_velocity_kf_low, 0);
      this.m_right_talon.config_IntegralZone(0, robotconfig.m_right_velocity_izone_low, 0);
      // this.m_right_talon.configMaxIntegralAccumulator(0, robotconfig.m_right_velocity_max_integral_low, 0);
  
      Robot.drivetrain_shift_low();

      current_gear = "low";
    }

    public double getLeftDistance() {return encoderlib.rawToDistance(this.m_left_talon.getSelectedSensorPosition(0), 
      robotconfig.POSITION_PULSES_PER_ROTATION, robotconfig.left_wheel_effective_diameter); }
    public double getRightDistance() {return encoderlib.rawToDistance(this.m_right_talon.getSelectedSensorPosition(0), 
      robotconfig.POSITION_PULSES_PER_ROTATION, robotconfig.right_wheel_effective_diameter);}
    public double getLeftVelocity() {return encoderlib.rawToDistance(this.m_left_talon.getSelectedSensorVelocity(0) * 10, //Mulitply by 10 because units are per 100ms 
      robotconfig.POSITION_PULSES_PER_ROTATION, robotconfig.left_wheel_effective_diameter);}
    public double getRightVelocity() {return encoderlib.rawToDistance(this.m_right_talon.getSelectedSensorVelocity(0) * 10, 
      robotconfig.POSITION_PULSES_PER_ROTATION, robotconfig.right_wheel_effective_diameter);}

    /**
     * Set a velocity setpoint for the left drivetrain talons
     * @param speed in feet per second
     * @return doesn't return anything, but sets the left talon speed to the raw conversion of speed.
     */
    public void setVelocityLeft(double speed) {
      double rawSpeedLeft = encoderlib.distanceToRaw(speed, robotconfig.POSITION_PULSES_PER_ROTATION, robotconfig.left_wheel_effective_diameter) / 10; 
      m_left_talon.set(ControlMode.Velocity, rawSpeedLeft);// Divide by 10, because the Talon expects native units per 100ms, not native units per second
    }

    /**
     * Set a velocity setpoint for the right drivetrain talons
     * @param speed in feet per second
     * @return doesn't return anything, but sets the right talon speed to the raw conversion of speed.
     */
    public void setVelocityRight(double speed) {
      double rawSpeedRight = encoderlib.distanceToRaw(speed, robotconfig.POSITION_PULSES_PER_ROTATION, robotconfig.right_wheel_effective_diameter) / 10; 
      m_right_talon.set(ControlMode.Velocity, rawSpeedRight);// Divide by 10, because the Talon expects native units per 100ms, not native units per second
    }


  public void arcadeDriveMethod(double forwardspeed, double turnspeed){

    if ((forwardspeed < 0.02) && (forwardspeed > -0.02)) { forwardspeed = 0; }
    if ((turnspeed < 0.01) && (turnspeed > -0.01)) { turnspeed = 0; }
    
    if (robotconfig.driving_squared) {
    forwardspeed = forwardspeed * Math.abs(forwardspeed);
    turnspeed = turnspeed * Math.abs(turnspeed);
    }
    if (Robot.drivetrain.current_gear == "high"){
        forwardspeed = forwardspeed * robotconfig.max_forward_speed_high;
        turnspeed = turnspeed * robotconfig.max_turn_speed_high;}
    if (Robot.drivetrain.current_gear == "low"){
        forwardspeed = forwardspeed * robotconfig.max_forward_speed_low;
        turnspeed = turnspeed * robotconfig.max_turn_speed_low;}

    double leftspeed = -forwardspeed + turnspeed;
    double rightspeed = -forwardspeed - turnspeed;

    SmartDashboard.putNumber("Left wheels feet/sec setpoint: ", leftspeed);
    SmartDashboard.putNumber("Right wheels feet/sec setpoint: ", rightspeed);

    double leftspeedraw = encoderlib.distanceToRaw(leftspeed, robotconfig.POSITION_PULSES_PER_ROTATION, robotconfig.left_wheel_effective_diameter);
    double rightspeedraw = encoderlib.distanceToRaw(rightspeed, robotconfig.POSITION_PULSES_PER_ROTATION, robotconfig.right_wheel_effective_diameter);

    SmartDashboard.putNumber("Left wheels raw setpoint: ", leftspeedraw);
    SmartDashboard.putNumber("Right wheels raw setpoint: ", rightspeedraw);

    // setVelocityLeft(leftspeed*100);
    // setVelocityRight(rightspeed*100);

    m_left_talon.set(ControlMode.Velocity, leftspeedraw);
    m_right_talon.set(ControlMode.Velocity, rightspeedraw);

    // m_left_talon.set(ControlMode.PercentOutput, 0.3);
    // m_right_talon.set(ControlMode.PercentOutput, 0.3);
    // Robot.arcade_running = false;
  }

    
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new arcade_drive());
  }
}
