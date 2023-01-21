// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.*;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Timer;



//IMPORTANT NOTE TO PROGRAMMER: press Fn and F1, type 'deploy robot code' and press enter in order to deploy the code.
// Run the FRC Driver Station app in order to communicate with the RIO
// Cross your fingers and move the joystick!
// hope it works out, contact us if y'all need anything. Email me if you need at ansharyan03@gmail.com - Ansh, 4828 :D


/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with
 * arcade steering.
 */
public class Robot extends TimedRobot {
  //left front = 11, left rear = 12, right front = 13, right rear = 14
  private final CANSparkMax m_frontLeft = new CANSparkMax(11, MotorType.kBrushed);
  private final CANSparkMax m_rearLeft = new CANSparkMax(12, MotorType.kBrushed);
  private final MotorControllerGroup m_left = new MotorControllerGroup(m_frontLeft, m_rearLeft);

  private final CANSparkMax m_frontRight = new CANSparkMax(13, MotorType.kBrushed);
  private final CANSparkMax m_rearRight = new CANSparkMax(14, MotorType.kBrushed);
  private final MotorControllerGroup m_right = new MotorControllerGroup(m_frontRight, m_rearRight);

  private final DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);

  private final CANSparkMax m_shooter = new CANSparkMax(15, MotorType.kBrushed);

  private final VictorSPX intake = new VictorSPX(5);
  private final Joystick m_stick = new Joystick(0);

  double autoStart = 0.0;
  
  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_left.setInverted(true);
    CameraServer.startAutomaticCapture();
  }

  @Override
  public void teleopPeriodic() {
    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.
    m_drive.arcadeDrive(-m_stick.getY(), m_stick.getX());

    if(m_stick.getRawButton(1)){
      m_drive.arcadeDrive(0.3, 0);
    }

    if(m_stick.getRawButton(3)){
      m_drive.arcadeDrive(0.3, 0);
    }

    if(m_stick.getRawButton(4)){
      intake.set(VictorSPXControlMode.PercentOutput, 0.3);
    }
    else {
      intake.set(VictorSPXControlMode.PercentOutput, 0);
    }

    if (m_stick.getRawButton(5)) {
      m_shooter.set(0.3);
    }
    else {
      m_shooter.set(0);
    }

    if (m_stick.getRawButton(6)) {
      m_shooter.set(-
      
      
      
      
      
      
      
      
      
      
      
    .3);
    }
    else {
      m_shooter.set(0);
    }
  }
  public void autonomousInit() {
    autoStart = Timer.getFPGATimestamp();
  }


  public void autonomousPeriodic() {
    double timeElapsed = Timer.getFPGATimestamp() - autoStart;
    if (timeElapsed < 5) {
      m_drive.arcadeDrive(0.5, 0);

      //intake controls 
      

     
      


    }
  
  }
}
