// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

//IMPORTANT NOTE TO PROGRAMMER: press Fn and F1, type 'deploy robot code' and press enter in order to deploy the code.
//Run the FRC Driver Station app in order to communicate with the RIO
//Cross your fingers and move the joystick!
//hope it works out, contact us if y'all need anything. Email me if you need at ansharyan03@gmail.com - Ansh, 4828 :D

public class Robot extends TimedRobot {
  // Left Front = 11, Left Rear = 12, Right Front = 13, Right Rear = 14
  private final CANSparkMax m_frontLeft = new CANSparkMax(11, MotorType.kBrushed);
  private final CANSparkMax m_rearLeft = new CANSparkMax(12, MotorType.kBrushed);
  private final CANSparkMax m_frontRight = new CANSparkMax(13, MotorType.kBrushed);
  private final CANSparkMax m_rearRight = new CANSparkMax(14, MotorType.kBrushed);

  private final MotorControllerGroup m_left = new MotorControllerGroup(m_frontLeft, m_rearLeft);
  private final MotorControllerGroup m_right = new MotorControllerGroup(m_frontRight, m_rearRight);

  private final DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);

  // Lower Shooter = 15, Upper Shooter = 16
  private final CANSparkMax m_Shooter_Lower = new CANSparkMax(15, MotorType.kBrushed);
  private final CANSparkMax m_Shooter_Upper = new CANSparkMax(16, MotorType.kBrushed);

  private final Joystick m_stick = new Joystick(0);

  double robo_speed = .5;
  double step = 0.1;
  double speed_intake = 0.25;
  double speed_launcher = -1;
  double motor_speed = .5;
  double timeElapsed = Timer.getFPGATimestamp();

  // Initializing the launch wheel
  public void setLaunchWheel (double motor_speed) {
    m_Shooter_Upper.set(motor_speed);
  }

  // Initializing the feed wheel
  public void setFeedWheel (double motor_speed) {
    m_Shooter_Lower.set(motor_speed);
  }

  // Initizaling the stop function
  public void stop () {
    m_Shooter_Lower.set(0);
    m_Shooter_Upper.set(0);
  }


  @Overide
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_left.setInverted(true);
    CameraServer.startAutomaticCapture();

    // Places a compass indicator for the gyro heading on the dashboard
    // Shuffleboard.getTab("gyro tab").add(gyro);
  }

  @Override
  public void teleopPeriodic() {
    // Drive with arcade drive, which makes the Y-axis drive forward and the X-axis
    // turn
    m_drive.arcadeDrive(-m_stick.getY() * robo_speed, -m_stick.getX() * robo_speed);

    // This method allows the driver to use the same joystick for high/low speed
    // control and gives more precise control
    // Reducing the speed with the A button but making sure it is still above 0
    if (m_stick.getRawButton(2) && (robo_speed - step > 0)) {
      robo_speed = robo_speed - step;
      Timer.delay(0.1);
    }
    // Increasing the speed with the B button but making sure it is below/equal to 1
    else if (m_stick.getRawButton(3) && (robo_speed + step <= 1)) {
      robo_speed = robo_speed + step;
      Timer.delay(0.1);
    }

    // Using the LB button to spin shooter motors in reverse to take in the note
    if (m_stick.getRawButton(5)) {
      m_Shooter_Upper.set(speed_intake);
      m_Shooter_Lower.set(speed_intake);
    }
    
    // Using the RB button, spin up the upper shooter for 1 second, then spin up the
    // lower shooter
    else if (m_stick.getRawButton(6)) {
      timeElapsed = Timer.getFPGATimestamp();
      m_Shooter_Upper.set(speed_launcher);
    }
    else if (Timer.getFPGATimestamp() > (timeElapsed + 1) && m_stick.getRawButton(6)) {
      m_Shooter_Lower.set(speed_launcher);
    }

    //else if (Timer.getFPGATimestamp() > (timeElapsed + 2)) {
     // m_Shooter_Upper.set(0);
     // m_Shooter_Lower.set(0);
    //}

    // Setting speed to 0 to stop motors once trigger is no longer pressed
    else {
      m_Shooter_Upper.set(0);
      m_Shooter_Lower.set(0);
    }
  }

  // public void autonomousInit() {
  // autoStart = Timer.getFPGATimestamp();
  // }

  /*
   * (non-Javadoc)
   * 
   * @see edu.wpi.first.wpilibj.IterativeRobotBase#autonomousPeriodic()
   */
  public void autonomousPeriodic() {
    double timeElapsed = Timer.getFPGATimestamp();
    // Drive backwards X seconds
    if (timeElapsed < 1) {
      m_drive.arcadeDrive(-0.5, 0);

      // intake controls
    } else if (timeElapsed < 5.1) {
      m_drive.arcadeDrive(0.55, 0);

      // intake controls
    } else if (timeElapsed < 15) {
      m_drive.arcadeDrive(.2, 0);

      // intake controls
    }
  }
}