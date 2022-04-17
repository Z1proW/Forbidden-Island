package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Enums.Tresor;
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

	public int hoveredJoueur;
	public int hoveredCard;

	public int chosenJouer;
	public int chosenCard;

	public VueCartes(Modele modele)
	{
		this.modele = modele;
		modele.addObserver(this);

		this.setPreferredSize(new Dimension(6*WIDTH, 4*HEIGHT));
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
			g.fillRect(0, i * HEIGHT, getWidth(), HEIGHT);
		}

		for(int y = 0; y < joueurs.size(); y++)
		{
			List<Tresor> tresors = joueurs.get(y).getCartes();

			for(int x = 0; x < joueurs.get(y).getCartes().size(); x++)
			{
				paint(g, tresors.get(x), x, y);
			}
		}
	}

	private void paint(Graphics g, Tresor tresor, int x, int y)
	{
		Image image = tresor.getImage();
		BufferedImage img = new BufferedImage(image.getWidth(null), image.getHeight(null) , Image.SCALE_DEFAULT);
		img.getGraphics().drawImage(tresor.getImage(), 0, 0 , null);
		int PIXEL;
		int ToAlpha;
		// TODO peut mieux faire
		if( chosenJouer == y && chosenCard == x)
			for(int i = 0;i< image.getWidth(null); i++) {
				for (int j = 0; j < image.getHeight(null); j++) {
					PIXEL = img.getRGB(i, j);
					ToAlpha = (80) | (PIXEL & 0xFFFFFF);
					img.setRGB(i, j, ToAlpha);
				}
			}
		if(hoveredJoueur == y && hoveredCard == x)
			g.drawImage(img, x*WIDTH + WIDTH/16 - 5, y*HEIGHT + HEIGHT/16 - 5, null);
		else g.drawImage(img, x*WIDTH + WIDTH/16, y*HEIGHT + HEIGHT/16, null);

	}

}
