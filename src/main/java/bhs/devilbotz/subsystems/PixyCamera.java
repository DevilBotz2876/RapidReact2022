// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package bhs.devilbotz.subsystems;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.Pixy2CCC;
import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;
import io.github.pseudoresonance.pixy2api.links.SPILink;

public class PixyCamera extends SubsystemBase {

  private Pixy2 pixy;

  /** Creates a new PixyCamera. */
  public PixyCamera() {
    initialize();
    SmartDashboard.putString("pixy subsystem here?", "yes");
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    Block b = getBiggestBlock();
    String msg = "null";
    if (b != null) {
      msg = b.toString();
    }
    SmartDashboard.putString("pixy block", msg);
    //SmartDashboard.putString("pixy block", "test fake");
  }
  
  public void initialize() {
		pixy = Pixy2.createInstance(new SPILink()); // Creates a new Pixy2 camera using SPILink
		int rc = pixy.init(); // Initializes the camera and prepares to send/receive data
    SmartDashboard.putString("pixy init", String.valueOf(rc));
		pixy.setLamp((byte) 1, (byte) 1); // Turns the LEDs on
		pixy.setLED(255, 255, 255); // Sets the RGB LED to full white
	}

	public Block getBiggestBlock() {
		// Gets the number of "blocks", identified targets, that match signature 1 on the Pixy2,
		// does not wait for new data if none is available,
		// and limits the number of returned blocks to 25, for a slight increase in efficiency
		//int blockCount = pixy.getCCC().getBlocks(false, Pixy2CCC.CCC_SIG1, 25);
    int blockCount = pixy.getCCC().getBlocks(false, Pixy2CCC.CCC_SIG1|Pixy2CCC.CCC_SIG2, 25);
		//System.out.println("Found " + blockCount + " blocks!"); // Reports number of blocks found
    SmartDashboard.putString("pixy blocks", String.valueOf(blockCount));
		if (blockCount <= 0) {
			return null; // If blocks were not found, stop processing
		}
		ArrayList<Block> blocks = pixy.getCCC().getBlockCache(); // Gets a list of all blocks found by the Pixy2
		Block largestBlock = null;
		for (Block block : blocks) { // Loops through all blocks and finds the widest one
			if (largestBlock == null) {
				largestBlock = block;
			} else if (block.getWidth() > largestBlock.getWidth()) {
				largestBlock = block;
			}
		}
		return largestBlock;
	}
}
