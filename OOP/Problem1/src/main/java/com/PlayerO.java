package com;

import javax.swing.JFrame;

public class PlayerO {
	
	public static void main(String[] args) {
		JFrame myWindow = new GameBoard(GameManager.O);		
		myWindow.setVisible(true);
	}

}
