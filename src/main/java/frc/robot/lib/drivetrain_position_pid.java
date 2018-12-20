// import frc.robot.robotconfig;

// edu.wpi.first.wpilibj.command.PIDSubsystem;


// public class drivetrain_position_pid extends PIDSubsystem {
//     // final Talon talon = new Talon(RobotConfig.talon);

//     // final Encoder encoder = new Encoder(RobotConfig.encoderA,
//                                         // RobotConfig.encoderB);

//     double kp, ki, kd;
//     double setAbsoluteTolerance;

//     public drivetrain_position_pid() {
//         // Put your PID constants here
//         super("drivetrain_position_pid", 0.5, 0.5, 0.1);

        
//         this.encoder.setDistancePerPulse(robotconfig.POSITION_PULSES_PER_ROTATION);
//         this.setAbsoluteTolerance(0.05);
//         this.getPIDController().setContinuous(false);
//     }

//     @Override
//     public void initDefaultCommand() {
//     }

//     /**
//      * @return The current position of the encoder
//      */
//     @Override
//     protected double returnPIDInput() {
//         return this.encoder.getDistance();
//     }

//     /**
//      * Set the motor speed based on the output of the PID algorithm
//      */
//     @Override
//     protected void usePIDOutput(double output) {
//         this.talon.set(output);
//     }
// }