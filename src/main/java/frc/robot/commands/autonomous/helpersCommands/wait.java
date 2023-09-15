// Author: N. Rombach
// Last Updated : September 2023

package frc.robot.commands.autonomous.helpersCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** Wait *******************************************************
 * Waits for a specified number of seconds. Useful for autonomous command groups!
 * */
public class wait extends CommandBase {
    double duration;
    Timer timer;
    
    public wait(final double time) {
        this.timer = new Timer();
        this.duration = time;
    }
    
    @Override
    public void initialize() {
        this.timer.reset();
        this.timer.start();
    }
    
    @Override
    public boolean isFinished() {
        return this.timer.get() >= this.duration;
    }
    
    @Override
    public void end(final boolean interrupted) {
        this.timer.reset();
    }
}
