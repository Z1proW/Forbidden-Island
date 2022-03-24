package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Modele;

import javax.swing.*;
import java.awt.*;

public class VuePlateau extends JPanel implements Observer
{

	private Modele modele;
	private final static int P = 32; // pixels par case

	public VuePlateau(Modele modele)
	{
		this.modele = modele;
		modele.addObserver(this);

		int s = P * Modele.LENGTH;
		this.setPreferredSize(new Dimension(s, s));
	}

	@Override
	public void update() {super.repaint();}

	public void paintComponent(Graphics g)
	{
		super.repaint();

		for(int x = 1; x <= Modele.LENGTH; x++)
			for(int y = 1; y <= Modele.LENGTH; y++)
				paint(g, modele.getCase(x, y), (x-1)* P, (y-1)* P);
	}

	private void paint(Graphics g, Case c, int x, int y)
	{
		switch(c.etat)
		{
			case SECHE:   			 g.setColor(new Color(120, 100,   0));
			case INONDEE: 			 g.setColor(new Color(100,  80,  20));
			default: case SUBMERGEE: g.setColor(new Color(  0,   0, 255));
		}
		g.fillRect(x, y, P, P);
	}

}
