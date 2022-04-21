package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Controleur.ControleurCartes;
import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Enums.Carte;
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

	//TODO pour les actions spéciale peut mieux faire je pense
	public boolean actionSpeNavigateur = false;
	public boolean actionSpeIngenieur = false;
	public int caseDeplace = 0;
	public boolean actionSpePlongeur = false;

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
			g.drawImage(c.getType().getImage(), x, y, null);

		if(c == hoveredCase || c == modele.getCurrentJoueur().getPosition())
		{
			g.setColor(new Color(255, 255, 255, 100));
			g.fillRect(x, y, P, P);
		}

		if(ControleurCartes.joueurEnfonce != null && ControleurCartes.carteEnfoncee != -1
		&& ControleurCartes.joueurEnfonce.getCartes().get(ControleurCartes.carteEnfoncee) == Carte.HELICOPTERE)
		{
			g.setColor(new Color(100, 255, 100, 100));
			g.fillOval(x, y, P, P);
		}

		vueJoueurs.draw(g, c);
	}

}
