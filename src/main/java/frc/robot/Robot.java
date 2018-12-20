/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.auto.auto_action_DRIVE;
import frc.robot.commands.arcade_drive;
// import frc.robot.commands.drivetrain_shift_high;
// import frc.robot.commands.drivetrain_shift_low;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
// import frc.robot.commands.arcade_drive;
import frc.robot.subsystems.*;
// import frc.robot.commands.*;


/**
 * 5940 Offseason Command codebase. Currently very much a WIP, but funcitonal. Teleop feature parity with 2018-codebase
 * @author Matthew Morley
 */
public class Robot extends TimedRobot {
  public static boolean arcade_running = false;
  public static drivetrain drivetrain  = new drivetrain();
  public static intake intake = new intake();
  public static elevator elevator = new elevator();
  public static wrist wrist = new wrist();
  public static robotconfig robotconfig = new robotconfig();
  public static OI m_oi;
  public static ADXRS450_Gyro gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);

  //Compressor stuff
  Compressor compressor = new Compressor(9);
  private static DoubleSolenoid shifterDoubleSolenoid = new DoubleSolenoid(9, 7, 3);
  private static DoubleSolenoid intakeDoubleSolenoid = new DoubleSolenoid(9, 0, 6);
  
  // Pneumatic wrappers, this is so jank
  public static void drivetrain_shift_high(){ shifterDoubleSolenoid.set(DoubleSolenoid.Value.kForward); }
  public static void drivetrain_shift_low(){ shifterDoubleSolenoid.set(DoubleSolenoid.Value.kReverse); }
  public static void intake_close(){ intakeDoubleSolenoid.set(DoubleSolenoid.Value.kForward); }
  public static void intake_open(){ intakeDoubleSolenoid.set(DoubleSolenoid.Value.kReverse); }

  // Set setpoints to 0 at the top
  // TODO move this to their respective subsystems
  public static double elevator_setpoint = 0;
  public static double wrist_setpoint = 0;
  
  // Auto stuff
  // Apparently this has to be defined up here
  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    // setup Operator Input (OI)
    m_oi = new OI();

    // Init subsystems
    // TODO will init be run by default, or do I have to call it?
    drivetrain.init();
    elevator.init();
    // wrist.init(); // Wrist disabled for now lol

    // Set compressor mode
    compressor.setClosedLoopControl(true);

    //Setup gyro
		gyro.reset();

    // Auto setup - TODO setup more auto stuff in it's own file
    m_chooser.addDefault("Default Auto", new auto_action_DRIVE(5, "high", 2, 15));
    // m_chooser.addObject("My Auto", new MyAutoCommand());

  }

  /**
   * This function is called every robot packet, no matter the mode. Use
  //  * this for items like diagnostics that you want ran during disabled,
  //  * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("Fore speed axis value", m_oi.getForwardAxis());
    SmartDashboard.putNumber("Turn speed axis value", m_oi.getTurnAxis());
    SmartDashboard.putString("Drivetrain gear", drivetrain.current_gear);

    SmartDashboard.putNumber("Left talon speed", drivetrain.m_left_talon.getSelectedSensorVelocity(0));
    SmartDashboard.putNumber("Left talon error", drivetrain.m_left_talon.getClosedLoopError(0));
    SmartDashboard.putNumber("Right talon speed", drivetrain.m_right_talon.getSelectedSensorVelocity(0));
    SmartDashboard.putNumber("Right talon error", drivetrain.m_right_talon.getClosedLoopError(0));

    SmartDashboard.putNumber("Intake target speed per OI:", m_oi.getIntakeSpeed());

    // SmartDashboard.putNumber("Throttle output", throttle.getRawAxis(1));
    SmartDashboard.putNumber("Elevator axis value", m_oi.getThrottleAxis());
    SmartDashboard.putNumber("Elevator inches height", elevator.getElevatorAxisInches());
    SmartDashboard.putNumber("Elevator height", elevator.getHeight());
    SmartDashboard.putNumber("Elevator error", 4096-elevator.getHeight());

    SmartDashboard.putBoolean("Arcade command running", arcade_running);

    // SmartDashboard.putNumber("Wrist angle setpoint", wrist_setpoint); 
    // SmartDashboard.putNumber("Wrist talon pos", elevator.elevator_talon.getSelectedSensorPosition(0));
    // SmartDashboard.putNumber("Wrist error", elevator.elevator_talon.getClosedLoopError(0));
    // SmartDashboard.putNumber("Wrist angle (deg)", wrist.getAngle());
    // SmartDashboard.putNumber("Wrist angular velocity (deg/s)", wrist.getAngularVelocity());
    
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // drivetrain.init();
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    

    // TODO replace this with a default command
    double target_intake_speed = m_oi.getIntakeSpeed() / 1;
    // intake.setSpeed(target_intake_speed);
    intake.talon_left.set(ControlMode.PercentOutput, target_intake_speed);
    intake.talon_right.set(ControlMode.PercentOutput, target_intake_speed);

    // elevator.setPercent(target_intake_speed);

    /**
     * Update the elevator PID method
     */
    // elevator_setpoint = elevator_setpoint+throttle.getRawAxis(1)*10;
    // elevator.setHeight(elevator_setpoint);

    // drivetrain.arcadeDriveMethod(m_oi.getForwardAxis(), m_oi.getTurnAxis());

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
