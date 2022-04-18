package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Enums.Carte;
import fr.rui_tilmann.Modele.Joueur;
import fr.rui_tilmann.Modele.Modele;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class VueCartes extends JPanel implements Observer
{

	private final Modele modele;

	public static final int WIDTH = 80;
	public static final int HEIGHT = 110;

	public int hoveredJoueur = -1;
	public int hoveredCard = -1;

	public int chosenJoueur;
	public int chosenCard;

	public VueCartes(Modele modele)
	{
		this.modele = modele;
		modele.addObserver(this);

		this.setPreferredSize(new Dimension(10*WIDTH, 4*HEIGHT));
	}

	@Override
	public void update() {repaint();}

	public void paintComponent(Graphics g)
	{
		super.repaint();

		List<Joueur> joueurs = modele.getJoueurs();

		for(int i = 0; i < joueurs.size(); i++)
		{
			g.setColor(joueurs.get(i).getRole().getColor());
			g.fillRect(0, i*HEIGHT, getWidth(), HEIGHT);
		}

		for(int y = 0; y < joueurs.size(); y++)
		{
			List<Carte> tresors = joueurs.get(y).getCartes();

			for(int x = 0; x < joueurs.get(y).getCartes().size(); x++)
			{
				paint(g, tresors.get(x), x, y);
			}

			// noircir les autres cartes
			if(modele.getIdJoueur() != joueurs.get(y))
			{
				g.setColor(new Color(0, 0, 0, 127));
				g.fillRect(0, y*HEIGHT, getWidth(), HEIGHT);
			}
		}
	}

	private void paint2(Graphics g, Carte tresor, int x, int y)
	{
		if(hoveredJoueur == y && hoveredCard == x)
			g.drawImage(tresor.getImage(), x*WIDTH + WIDTH/16 - 5, y*HEIGHT + HEIGHT/16 - 5, null);
		else g.drawImage(tresor.getImage(), x*WIDTH + WIDTH/16, y*HEIGHT + HEIGHT/16, null);
	}

	// TODO peut mieux faire
	private void paint(Graphics g, Carte carte, int x, int y)
	{
		Image image = carte.getImage();
		BufferedImage img = new BufferedImage(image.getWidth(null), image.getHeight(null) , Image.SCALE_DEFAULT);
		img.getGraphics().drawImage(carte.getImage(), 0, 0 , null);
		int pixelColor;
		int toAlpha;

		if(chosenJoueur == y && chosenCard == x) {
			for (int i = 0; i < image.getWidth(null); i++) {
				for (int j = 0; j < image.getHeight(null); j++) {
					pixelColor = img.getRGB(i, j);
					toAlpha = (200) | (pixelColor & 0xFFFFFF);
					img.setRGB(i, j, toAlpha);
				}
			}
		}

		if(hoveredJoueur == y && hoveredCard == x)
			g.drawImage(img, x*WIDTH + WIDTH/16 - 5, y*HEIGHT + HEIGHT/16 - 5, null);
		else g.drawImage(img, x*WIDTH + WIDTH/16, y*HEIGHT + HEIGHT/16, null);
	}

}
