package fr.rui_tilmann.vue.menu;

import javax.swing.*;
import java.awt.*;

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
