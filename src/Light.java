// BUTTONS USED FOR THE GAME

import javax.swing.JButton;
import java.awt.Color;

public class Light extends JButton{
	boolean isOn;
	int xCoordinate;
	int yCoordinate;

	public Light () {

	}

	// will toggle status of light and UI
	public void toggleLight () {
		if (this.isOn == true) {
			this.setBackground(Color.DARK_GRAY);
			this.isOn = false;
		} else {
			this.setBackground(Color.WHITE);
			this.isOn = true;
		}
	}
}
