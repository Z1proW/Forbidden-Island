package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Modele;

import javax.swing.*;
import java.awt.*;

public class VuePlateau extends JPanel implements Observer
{

	private Modele modele;
	public final static int P = 80; // pixels par case

	public VuePlateau(Modele modele)
	{
		this.modele = modele;
		modele.addObserver(this);

		int s = P * Modele.LENGTH;
		this.setPreferredSize(new Dimension(s, s));
	}

	@Override
	public void update() {repaint();}

	public void paintComponent(Graphics g)
	{
		super.repaint();

		for(int x = 0; x < Modele.LENGTH; x++)
			for(int y = 0; y < Modele.LENGTH; y++)
				paint(g, modele.getCase(x, y), x * P, y * P);
	}

	private void paint(Graphics g, Case c, int x, int y)
	{
		switch(c.etat)
		{
			case SECHE:   	g.setColor(Color.ORANGE); break;
			case INONDEE: 	g.setColor(Color.CYAN); break;
			case SUBMERGEE: g.setColor(Color.BLUE); break;
		}
		g.fillRect(x, y, P, P);
	}

}
