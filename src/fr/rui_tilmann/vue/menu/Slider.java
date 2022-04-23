package fr.rui_tilmann.vue.menu;

import javax.swing.*;
import java.awt.*;

class Slider extends JSlider
{

	Slider(int min, int max, int valeur, int y)
	{
		setMinimum(min);
		setMaximum(max);
		setValue(valeur);
		setSize(new Dimension(200, 50));
		setLocation(500, y);
		setBackground(new Color(0, 0, 0, 0));
		setFocusable(false);
	}

}
