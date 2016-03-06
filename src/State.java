// STATE OF LIGHTS

import java.util.LinkedList;
import java.awt.Point;

public class State {
	private int[][] config;
	private LinkedList <Point> toggledLights;

	/*CONSTRUCTORS FOR STATE*/
	public State () {
		config = new int[5][5];
		toggledLights = new LinkedList <Point> ();
		
		turnOnAllLights();
		clearToggledLights();
	}

	public State (int[][] litUp) {
		config = new int[5][5];
		toggledLights = new LinkedList <Point> ();
		
		for (int x = 0; x < 5; x += 1) {
			for (int y = 0; y < 5; y += 1) {
				config[x][y] = litUp[x][y];
			}
		}
		
		clearToggledLights();
	}
	
	public State (int[][] litUp, LinkedList <Point> tmpToggled) {
		config = new int[5][5];
		toggledLights = new LinkedList <Point> ();
		
		for (int x = 0; x < 5; x += 1) {
			for (int y = 0; y < 5; y += 1) {
				config[x][y] = litUp[x][y];
				}
		}
		
		toggledLights = (LinkedList <Point>) tmpToggled.clone();
	}
	/*END OF CONSTRUCTORS*/

	// will add new point to toggledLights
	public void addToggled (int x, int y) {
		toggledLights.add(new Point(x,y));
	}

	// prints the current config of the state
	public void printConfig () {
		for (int x = 0; x < 5; x += 1) {
			for (int y = 0; y < 5; y += 1) {
				System.out.print(config[x][y]);
				System.out.print(" ");
			}
			System.out.print("\n");
		}

		System.out.print("\n");
	}
    
	// toggles light; on to off and vice versa
    public void toggleConfigLight (int x, int y) {
        if (config[x][y] == 1) {
            config[x][y] = 0;
        } else {
            config[x][y] = 1;
        }
    }
    
	// clears all toggled lights
	public void clearToggledLights () {
        toggledLights.clear();
    }
    
	// prints a point
    public void printPoint (Point point) {
        System.out.println(point.x + " and " + point.y);
        System.out.println("");            
    }
    
	// set all lights to 0
    public void turnOffAllLights () {
        for (int x = 0; x < 5; x += 1) {
            for (int y = 0; y < 5; y += 1) {
                config[x][y] = 0;
            }
        }
        clearToggledLights();
    }
    
	// set all lights to 1
    public void turnOnAllLights () {
        for (int x = 0; x < 5; x += 1) {
            for (int y = 0; y < 5; y += 1) {
                config[x][y] = 1;
            }
        }
        clearToggledLights();        
    }
	
	// get configuration of the state
	public int[][] getConfig () {
		return config;
	}
	
	// get value of config per lights
	public int getConfigValue (int x, int y) {
		return config[x][y];
	}
	
	// get toggled lights
	public LinkedList <Point> getToggled () {
		return toggledLights;
	}
}
