package fr.rui_tilmann.controleur;

import javax.swing.*;

public class Bouton extends JButton
{

	public Bouton(String text, int x, int y, int dx, int dy)
	{
		setText(text);
		setLocation(x, y);
		setSize(dx, dy);
		setFocusable(false);
	}

}
