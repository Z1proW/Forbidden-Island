package fr.rui_tilmann.vue.menu;

import javax.swing.*;
import java.awt.*;

class Bouton extends JButton
{

	Bouton(String text, int y)
	{
		setText(text);
		setSize(new Dimension(200, 50));
		setLocation(500, y);
		setFocusable(false);
	}

}
