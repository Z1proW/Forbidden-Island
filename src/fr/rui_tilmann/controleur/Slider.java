package fr.rui_tilmann.controleur;

import javax.swing.*;
import java.awt.*;

public class Slider extends JSlider
{

	public Slider(int min, int max, int valeur, int x, int y, int dx, int dy)
	{
		setMinimum(min);
		setMaximum(max);
		setValue(valeur);
		setLocation(x, y);
		setSize(dx, dy);
		setBackground(new Color(0, 0, 0, 0));
		setFocusable(false);
	}

}
