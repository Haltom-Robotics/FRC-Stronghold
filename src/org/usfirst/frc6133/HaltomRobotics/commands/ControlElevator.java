
package org.usfirst.frc6133.HaltomRobotics.commands;

import org.usfirst.frc6133.HaltomRobotics.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ControlElevator extends Command{

	public ControlElevator() {
		// TODO Auto-generated constructor stub
		requires(Robot.elevator);
	}
	protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.elevator.adjustElevator(Robot.oi.getJoystick());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevator.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

}