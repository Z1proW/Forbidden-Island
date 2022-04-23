package fr.rui_tilmann.vue;

import fr.rui_tilmann.controleur.ControleurCartes;
import fr.rui_tilmann.modele.Case;
import fr.rui_tilmann.modele.enums.Carte;
import fr.rui_tilmann.modele.enums.Etat;
import fr.rui_tilmann.modele.Joueur;
import fr.rui_tilmann.modele.Modele;
import fr.rui_tilmann.modele.Plateau;
import fr.rui_tilmann.controleur.ControleurJoueur;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

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

		modele.getPlateau().forEachCase(c ->
				paint(g, c, c.getX() * P, c.getY() * P));
	}

	private void paint(Graphics g, Case c, int x, int y)
	{
		if(c.getEtat() == Etat.INONDEE)
			g.drawImage(Etat.SUBMERGEE.getImage(), x, y, null);

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
