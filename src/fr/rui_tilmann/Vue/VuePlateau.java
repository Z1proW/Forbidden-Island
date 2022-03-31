package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Joueur;
import fr.rui_tilmann.Modele.Modele;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VuePlateau extends JPanel implements Observer
{

	private final Modele modele;
	public final static int P = 80; // pixels par case
	private final VueJoueurs vueJoueurs;

	public VuePlateau(Modele modele)
	{
		this.modele = modele;
		modele.addObserver(this);

		this.vueJoueurs = new VueJoueurs(modele);

		int s = P * Modele.LENGTH;
		this.setPreferredSize(new Dimension(s, s));
		this.setBackground(new Color(0, 0, 0));
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
		switch(c.getEtat())
		{
			case SECHE:   	g.setColor(new Color(200, 100, 50)); break;
			case INONDEE: 	g.setColor(new Color(0, 150, 200)); break;
			case SUBMERGEE: g.setColor(new Color(50, 50, 200)); break;
		}
		g.fillRect(x, y, P, P);

		switch(c.getType())
		{
			case HELIPORT: g.setColor(Color.BLACK); break;
			case AIR:	   g.setColor(Color.WHITE); break;
			case EAU:	   g.setColor(Color.BLUE); break;
			case FEU:	   g.setColor(Color.RED); break;
			case TERRE:	   g.setColor(Color.ORANGE); break;
		}
		g.drawRoundRect(x, y, P-1, P-1, P, P);

		vueJoueurs.draw(g, c);
	}

}
