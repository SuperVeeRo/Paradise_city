package Main;

import javax.swing.*;


public class MainGame {
	
	public static void main(String args[]) {
		
		JFrame window = new JFrame("Paradise City");
		
		window.add(new GamePanel());
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
