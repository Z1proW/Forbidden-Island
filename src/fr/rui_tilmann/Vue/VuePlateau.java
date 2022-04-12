package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Enums.Etat;
import fr.rui_tilmann.Modele.Modele;

import javax.swing.*;
import java.awt.*;

public class VuePlateau extends JPanel implements Observer
{

	private final Modele modele;
	public final static int P = 80; // pixels par case
	private final VueJoueurs vueJoueurs;

	public VuePlateau(Modele modele, VueJoueurs vueJoueurs)
	{
		this.modele = modele;
		this.vueJoueurs = vueJoueurs;
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
		g.drawImage(c.getEtat().getImage(), x, y, null);

		g.drawImage(c.getType().getImage(), x + 5, y + 5, null);

		vueJoueurs.draw(g, c);
	}

}
