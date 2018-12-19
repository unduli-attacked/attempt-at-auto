package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class arcade_drive extends Command {
    double forwardspeed;
    double turnspeed;
    public arcade_drive(){
        requires(Robot.drivetrain);
    }

    // public void arcade(double forwardspeed, double turnspeed, Boolean isSquared) {





  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double forwardspeed = Robot.m_oi.getForwardAxis();
    double turnspeed = Robot.m_oi.getTurnAxis();

    if ((forwardspeed < 0.02) && (forwardspeed > -0.02)) { forwardspeed = 0; }
    if ((turnspeed < 0.01) && (turnspeed > -0.01)) { turnspeed = 0; }

    // m_left_talon.set(ControlMode.PercentOutput, 0.25);
    // m_right_talon.set(ControlMode.PercentOutput, 0.25);
    
    if (Robot.robotconfig.driving_squared) {
    forwardspeed = forwardspeed * Math.abs(forwardspeed);
    turnspeed = turnspeed * Math.abs(turnspeed);
    }
    if (Robot.drivetrain.current_gear == "high"){
        forwardspeed = forwardspeed * Robot.robotconfig.max_forward_speed_high;
        turnspeed = turnspeed * Robot.robotconfig.max_turn_speed_high;}
    if (Robot.drivetrain.current_gear == "low"){
        forwardspeed = forwardspeed * Robot.robotconfig.max_forward_speed_low;
        turnspeed = turnspeed * Robot.robotconfig.max_turn_speed_low;}

    double leftspeed = -forwardspeed + turnspeed;
    double rightspeed = -forwardspeed - turnspeed;

    Robot.drivetrain.setVelocityLeft(leftspeed);
    Robot.drivetrain.setVelocityRight(rightspeed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}