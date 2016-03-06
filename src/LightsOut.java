// GAME FILE

import java.lang.Integer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Point;

import java.util.Random;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.io.*;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class LightsOut {
	LightsOut game;
	GameStatus statusButton;
	State state;

	Light[][] lights;
	Light[][] output;

	public LightsOut (JPanel headerPanel, JPanel lightsPanel, JPanel menuPanel) {
		JLabel headerLabel = new JLabel("LIGHTS OUT", SwingConstants.CENTER);

		statusButton = new GameStatus();
		lights = new Light[5][5];

		// configure headers and labels
		headerLabel.setForeground(Color.GREEN);
		headerPanel.setBackground(Color.DARK_GRAY);
		headerPanel.add(headerLabel);
		lightsPanel.setLayout(new GridLayout(5, 5));

		for (int i = 0; i < 5; i += 1) {
            for (int j = 0; j < 5; j += 1) {
                lights[i][j] = new Light();

                // configure buttons
                lights[i][j].setPreferredSize(new Dimension(100, 100));

				// will generate a random board
                // if (Math.random() < 0.5) {
                    lights[i][j].setBackground(Color.DARK_GRAY);
                    lights[i][j].isOn = false;
                // } else {
                    // lights[i][j].setBackground(Color.WHITE);
                    // lights[i][j].isOn = true;
                // }

                // add click listener for light button
                lights[i][j].addActionListener(new ActionListener () {
					public void actionPerformed (ActionEvent e) {
						Object source = e.getSource();
						boolean isWin = true;
						if (source instanceof Light) {
                            Light btn = (Light)source;
                            for (int x = 0; x < 5; x += 1) {
                                for (int y = 0; y < 5; y += 1) {
									// if button selected, then toggle lights closest to it
									if (lights[x][y] == btn) {
										lights[x][y].toggleLight();
										if (x != 4) lights[x+1][y].toggleLight();
										if (x != 0) lights[x-1][y].toggleLight();
										if (y != 4) lights[x][y+1].toggleLight();
										if (y != 0) lights[x][y-1].toggleLight();
										System.out.println("[" + x + "," + y + "] clicked!");
										break;
                                    }
                                }
                            }

							for (int a = 0; a < 5; a += 1) {
								for (int b = 0; b < 5; b += 1) {
									// if there exist even one on light, set game to not win
									if (lights[a][b].isOn == true) {
										isWin = false;
										break;
									}
								}
							}

							// if win then close game then prompt
							if (isWin == true) {
								JOptionPane.showMessageDialog(new JFrame(), "You win!");
								System.exit(0);
							} else {
								isWin = true;
							}
                        }
                    }
				});

				// add button to game
				lights[i][j].xCoordinate = i;
				lights[i][j].yCoordinate = j;
				lightsPanel.add(lights[i][j]);
			}
		}

		// menu panel
		JButton generateButton = new JButton("Generate");

		// will randomize the board on click
		generateButton.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				System.out.println("Lights out panels randomized");
				for (int x = 0; x < 5; x += 1) {
					for (int y = 0; y < 5; y += 1) {
						if (Math.random() < 0.5) {
							lights[x][y].setBackground(Color.DARK_GRAY);
							lights[x][y].isOn = false;
						} else {
							lights[x][y].setBackground(Color.WHITE);
							lights[x][y].isOn = true;
						}
					}
				}

				if(statusButton.isPaused == true) statusButton.toggleStatus();
			}
		});

		// will show solution to the puzzle
		statusButton.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				Object source = e.getSource();
				GameStatus btn = (GameStatus)source;
				int[][] config = new int[5][5];

				System.out.println("\nWill present solution to the puzzle \n");

				if (statusButton.isPaused == true) {
					for (int x = 0; x < 5; x += 1) {
						for (int y = 0; y < 5; y += 1) {
							lights[x][y].setEnabled(true);
						}
					}
				} else {
					for (int x = 0; x < 5; x += 1) {
						for (int y = 0; y < 5; y += 1) {
							lights[x][y].setEnabled(false);
                            if (lights[x][y].isOn == true) {
								config[x][y] = 1;
							} else {
								config[x][y] = 0;
							}
						}
					}
					state = toToggle(BFS(new State(config)));
				
					// create frame for answer
					JFrame outputFrame = new JFrame();
					JPanel outputHeaderPanel = new JPanel();
					JPanel outputLightsPanel = new JPanel();
					JPanel outputMenuPanel = new JPanel();
					JLabel headerLabel = new JLabel("SOLUTION", SwingConstants.CENTER);

					output = new Light[5][5];

					// configure headers and labels
					headerLabel.setForeground(Color.GREEN);
					outputHeaderPanel.setBackground(Color.DARK_GRAY);
					outputHeaderPanel.add(headerLabel);
					outputLightsPanel.setLayout(new GridLayout(5, 5));

					for (int i = 0; i < 5; i += 1) {
						for (int j = 0; j < 5; j += 1) {
							output[i][j] = new Light();

							// configure buttons
							output[i][j].setPreferredSize(new Dimension(100, 100));

							if (state.getConfigValue(i, j) == 0) {
								output[i][j].setBackground(Color.DARK_GRAY);
								output[i][j].isOn = false;
							} else {
								output[i][j].setBackground(Color.WHITE);
								output[i][j].isOn = true;
							}

							// add button to game
							output[i][j].xCoordinate = i;
							output[i][j].yCoordinate = j;
							outputLightsPanel.add(output[i][j]);
						}
					}
					
					JButton outputSaveButton = new JButton("Save Solution");
					
					outputSaveButton.setBackground(Color.BLACK);
					outputSaveButton.setForeground(Color.GREEN);
					
					outputMenuPanel.add(outputSaveButton);
					
					// will enable the solution board be saved to a file
					outputSaveButton.addActionListener(new ActionListener () {
						public void actionPerformed (ActionEvent e) {
							System.out.println("Will get board to file\n");
							
							try {
								PrintWriter writer = new PrintWriter(new FileOutputStream("lightsout.out"));
								for (int x = 0; x < 5; x += 1) {
									for (int y = 0; y < 5; y += 1) {
										writer.print(state.getConfigValue(x, y) + " ");
									}
									writer.println("");
								}

								writer.close();
							} catch(Exception err){ }
						}
					});
					
					outputMenuPanel.setBackground(Color.DARK_GRAY);

					// positioning elements
					outputFrame.getContentPane().add(outputHeaderPanel, BorderLayout.NORTH);
					outputFrame.getContentPane().add(outputLightsPanel, BorderLayout.CENTER);
					outputFrame.getContentPane().add(outputMenuPanel, BorderLayout.SOUTH);
					
					// show game
					outputFrame.pack();
					outputFrame.setVisible(true);
				}

				btn.toggleStatus();
            }
		});
		
		JButton fileButton = new JButton("FILE");
		
		fileButton.setBackground(Color.BLACK);
		fileButton.setForeground(Color.GREEN);
		
		headerPanel.add(fileButton);
		
		// allows load of board from a file
		fileButton.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				System.out.println("Will get board from file\n");
				
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
					try {
						BufferedReader br = new BufferedReader(new FileReader(chooser.getSelectedFile().getAbsolutePath()));
						Scanner scan = new Scanner(System.in);
						String line, ls[];
						
						for (int x = 0; (line = br.readLine()) != null; x += 1) {
							for (int y = 0; y < 5; y += 1) {
								ls = line.split(" ");
								if (Integer.parseInt(ls[y], 10) == 0) {
									lights[x][y].setBackground(Color.DARK_GRAY);
									lights[x][y].isOn = false;
								} else {
									lights[x][y].setBackground(Color.WHITE);
									lights[x][y].isOn = true;
								}
							}
							System.out.print("\n");
						}

						br.close();
					} catch(Exception err){ }
				}
			}
		});

		menuPanel.setBackground(Color.DARK_GRAY);

		generateButton.setBackground(Color.BLACK);
		generateButton.setForeground(Color.GREEN);

		statusButton.setBackground(Color.BLACK);
		statusButton.setForeground(Color.GREEN);

		menuPanel.add(generateButton);
		menuPanel.add(statusButton);
	}

	// will search for the solution
    public State BFS (State initialState) {
		LinkedList <State> frontier = new LinkedList <State> ();
		State currentState = null;
		int iterator = 1;
		
		frontier.add(initialState);

		while (frontier.size() != 0) {
			currentState = frontier.removeFirst();
			if (goalTest(currentState) == true) {
				System.out.println("WIN");
				return currentState;
			} else {
				for (Point tmpPoint : action(currentState)) {
					frontier.add(result(currentState, tmpPoint));
				}
			}
			iterator += 1;
		}

		return (new State(new int[5][5]));
	}
	
	// given a state and a point, will output a state where the point is applied
	public State result (State currentState, Point actionPoint) {
		State localState = new State(currentState.getConfig(), currentState.getToggled());
		
		localState.toggleConfigLight(actionPoint.x, actionPoint.y);
		if (actionPoint.x != 4) localState.toggleConfigLight(actionPoint.x+1, actionPoint.y);
		if (actionPoint.x != 0) localState.toggleConfigLight(actionPoint.x-1, actionPoint.y);
		if (actionPoint.y != 4) localState.toggleConfigLight(actionPoint.x, actionPoint.y+1);
		if (actionPoint.y != 0) localState.toggleConfigLight(actionPoint.x, actionPoint.y-1);

		localState.addToggled(actionPoint.x, actionPoint.y);
		
		return localState;
	}
	
	// will output all possible actions to the state
	public LinkedList <Point> action (State stateCheck) {
        LinkedList <Point> tmpList = new LinkedList <Point> (stateCheck.getToggled());
        Point tmpPoint;

        LinkedList <Point> list = new LinkedList <Point> ();
        boolean isToggled = false;

        for (int x = 0; x < 5; x += 1) {
            for (int y = 0; y < 5; y += 1) {
                for (int i = 0; i < stateCheck.getToggled().size(); i += 1) {
                    tmpPoint = tmpList.get(i);
                    if (tmpPoint.x == x && tmpPoint.y == y) {
                        isToggled = true;
                        break;
                    }
                }
                if (isToggled == false) {
                    list.add(new Point(x, y));
                } else {
                    isToggled = false;
                }
            }
        }

        return list;
    }
	
	// will output true if all lights are off
	public boolean goalTest (State stateCheck) {
		boolean isWin = true;
		
		for (int x = 0; x < 5; x += 1) {
            for (int y = 0; y < 5; y += 1) {
                if (stateCheck.getConfig()[x][y] != 0) {
					isWin = false;
					break;
				}
            }
        }
		
        return isWin;
    }

	// will create a board given the points from the toggled lights
	// assumming goal test has been reached
	public State toToggle (State stateCheck) {
		System.out.println("Solution: \n");

		State tmpState = new State (new int[5][5]);
		tmpState.turnOffAllLights();

		for (Point tmpPoint: stateCheck.getToggled()) {
			tmpState.toggleConfigLight(tmpPoint.x, tmpPoint.y);
		}

		tmpState.printConfig();
		return tmpState;
	}
}
