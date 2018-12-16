package frc.robot;
public class robotconfig {

public static final int drivetrain_solenoid_low_gear_channel = 1;
public static final int drivetrain_solenoid_high_gear_channel = 2;



public static final int m_left_talon_port = 1;
public static final int s_left_talon_port = 2;
public static final boolean m_left_inverted = false;

public static final double m_left_velocity_kp_low = 1;
public static final double m_left_velocity_ki_low = 0;
public static final double m_left_velocity_kd_low = 0;
public static final double m_left_velocity_kf_low = 0;

public static final double m_left_position_kp_low = 1;
public static final double m_left_position_ki_low = 1;
public static final double m_left_position_kd_low = 1;
public static final double m_left_position_kf_low = 1;

public static final double m_left_velocity_kp_high = 1;
public static final double m_left_velocity_ki_high = 0;
public static final double m_left_velocity_kd_high = 0;
public static final double m_left_velocity_kf_high = 0;

public static final double m_left_position_kp_high = 1;
public static final double m_left_position_ki_high = 1;
public static final double m_left_position_kd_high = 1;
public static final double m_left_position_kf_high = 1;

public static final int m_right_talon_port = 3;
public static final int s_right_talon_port = 4;
public static final boolean m_right_inverted = false;

public static final double m_right_velocity_kp_low = m_left_velocity_kp_low;
public static final double m_right_velocity_ki_low = m_left_velocity_ki_low;
public static final double m_right_velocity_kd_low = m_left_velocity_kd_low;
public static final double m_right_velocity_kf_low = m_left_velocity_kf_low;

public static final double m_right_position_kp_low = m_left_position_kp_low;
public static final double m_right_position_ki_low = m_left_position_ki_low;
public static final double m_right_position_kd_low = m_left_position_kd_low;
public static final double m_right_position_kf_low = m_left_position_kf_low;

public static final double m_right_velocity_kp_high = m_left_velocity_kp_high;
public static final double m_right_velocity_ki_high = m_left_velocity_ki_high;
public static final double m_right_velocity_kd_high = m_left_velocity_kd_high;
public static final double m_right_velocity_kf_high = m_left_velocity_kf_high;

public static final double m_right_position_kp_high = m_left_position_kp_high;
public static final double m_right_position_ki_high = m_left_position_ki_high;
public static final double m_right_position_kd_high = m_left_position_kd_high;
public static final double m_right_position_kf_high = m_left_position_kf_high;

}