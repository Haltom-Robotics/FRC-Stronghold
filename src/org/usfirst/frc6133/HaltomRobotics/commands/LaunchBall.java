// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc6133.HaltomRobotics.commands;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc6133.HaltomRobotics.Robot;
import org.usfirst.frc6133.HaltomRobotics.RobotMap;

/**
 *
 */
public class LaunchBall extends Command {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    
    int loops = 0;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public LaunchBall() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.launcher);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	/*RobotMap.launcherTalonLauncherR.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	RobotMap.launcherTalonLauncherR.reverseSensor(true);
    	RobotMap.launcherTalonLauncherR.configEncoderCodesPerRev(20);
    	RobotMap.launcherTalonLauncherR.configNominalOutputVoltage(+0.0f, -0.0f);
    	RobotMap.launcherTalonLauncherR.configPeakOutputVoltage(+12.0f, -12.0f);
    	RobotMap.launcherTalonLauncherR.setProfile(0);
    	RobotMap.launcherTalonLauncherR.setF(1.4476);
    	RobotMap.launcherTalonLauncherR.setP(2.5575);
    	RobotMap.launcherTalonLauncherR.setI(0);
    	RobotMap.launcherTalonLauncherR.setD(0);
    	RobotMap.launcherTalonLauncherR.setEncPosition(0);*/
    	//Robot.launcher.setPIDLauncher(m_targetSpeed);
    	try {
    		Robot.vision.getTableValues();
    	} catch (java.lang.NullPointerException e) {
    		
    	}
        
        
    	
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	try {
    		Robot.vision.getTableValues();
    	} catch (java.lang.NullPointerException e) {
    		
    	}
    	Robot.isAiming = true;
    	if (Robot.vision.goalsFound > 0)
    	{
    		Robot.pitch.adjustPitchPID(Robot.vision.calcAngle());
    		System.out.println("CalcAngle: " + Robot.vision.calcAngle());
    	}
    	Robot.launcher.setPIDLauncher(RobotMap.SPEED);

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (Value.kForward.equals(RobotMap.launcherPush.get()))
        	{
        		Timer.delay(.1);
        		return true;
        	}
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.launcher.setLauncher(0);
    	Robot.pitch.stop();
    	Robot.drive.stop();
    	Robot.isAiming = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
