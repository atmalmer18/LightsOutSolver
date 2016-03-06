// CONTAINS MAIN FUNCTION

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class MendozaEx2 {
  public static void main (String[] args) {

	// configure frame and panels
	JFrame frame = new JFrame();
	JPanel headerPanel = new JPanel();
	JPanel lightsPanel = new JPanel();
	JPanel menuPanel = new JPanel();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	LightsOut lightsOut = new LightsOut(headerPanel, lightsPanel, menuPanel);

	// positioning elements
	frame.getContentPane().add(headerPanel, BorderLayout.NORTH);
	frame.getContentPane().add(lightsPanel, BorderLayout.CENTER);
	frame.getContentPane().add(menuPanel, BorderLayout.SOUTH);

	// show game
	frame.pack();
	frame.setVisible(true);
	
	System.out.println("\nGame generated");
  }
}
