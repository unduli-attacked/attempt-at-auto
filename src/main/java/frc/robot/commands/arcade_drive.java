package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Default drivetrain command. This *should* be called as the default drivetrain command and be overridden in autononmous (provided auto requires drivetrain???)
 * This command uses the Robot.m_oi to set the speed based on xbox controller inputs, arcade style
 * @author Matthew Morley
 */
public class arcade_drive extends Command {

  /** 
   * This command runs arcade drive as the default command for the drivetrain.
   * This command will reserve the drivetrain.
   */
  public arcade_drive(){
    requires(Robot.drivetrain);
  }

  @Override
  protected void initialize() {
    // TODO add logging
  }

  @Override
  protected void execute() {
    Robot.drivetrain.arcadeDriveMethod(Robot.m_oi.getForwardAxis(), Robot.m_oi.getTurnAxis());
  }

  @Override
  protected boolean isFinished() {
    return false; // This command never cancels itself
  }

  // Make sure that arcade exits cleanly
  @Override
  protected void end() {
    Robot.drivetrain.arcadeDriveMethod(0,0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.drivetrain.arcadeDriveMethod(0, 0);
  }
}
