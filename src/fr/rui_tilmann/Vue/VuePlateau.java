package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Enums.Etat;
import fr.rui_tilmann.Modele.Modele;
import fr.rui_tilmann.Modele.Plateau;

import javax.swing.*;
import java.awt.*;

public class VuePlateau extends JPanel implements Observer
{

	private final Modele modele;
	public final static int P = 80; // pixels par case
	private final VueJoueurs vueJoueurs;
	public Case hoveredCase;

	public VuePlateau(Modele modele, VueJoueurs vueJoueurs)
	{
		this.modele = modele;
		this.vueJoueurs = vueJoueurs;
		modele.addObserver(this);

		int s = P * Plateau.LENGTH;
		this.setPreferredSize(new Dimension(s, s));
	}

	@Override
	public void update() {repaint();}

	public void paintComponent(Graphics g)
	{
		super.repaint();

		for(int x = 0; x < Plateau.LENGTH; x++)
			for(int y = 0; y < Plateau.LENGTH; y++)
				paint(g, modele.getPlateau().getCase(x, y), x * P, y * P);
	}

	private void paint(Graphics g, Case c, int x, int y)
	{
		g.drawImage(c.getEtat().getImage(), x, y, null);

		if(c.getEtat() != Etat.SUBMERGEE)
			g.drawImage(c.getType().getImage(), x + 5, y + 5, null);

		if(c == hoveredCase)
		{
			g.setColor(new Color(255, 255, 255, 100));
			g.fillRect(x, y, P, P);
		}

		vueJoueurs.draw(g, c);
	}

}
