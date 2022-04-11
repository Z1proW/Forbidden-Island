package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Enums.Tresor;
import fr.rui_tilmann.Modele.Joueur;
import fr.rui_tilmann.Modele.Modele;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VueCartes extends JPanel implements Observer
{

	private final Modele modele;

	public static final int WIDTH = 70;
	private static final int HEIGHT = 100;

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

		for(int y = 0; y < joueurs.size(); y++)
		{
			List<Tresor> tresors = joueurs.get(y).getCartes();

			for(int x = 0; x < joueurs.get(y).getCartes().size(); x++)
			{
				g.setColor(joueurs.get(y).getRole().getColor());
				g.fillRect(WIDTH/4, y*HEIGHT + HEIGHT/4, WIDTH/2, HEIGHT/2);

				paint(g, tresors.get(x), x, y);
			}
		}
	}

	private void paint(Graphics g, Tresor tresor, int x, int y)
	{
		switch(tresor)
		{
			case AIR: g.setColor(Color.WHITE); break;

			case EAU: g.setColor(Color.BLUE); break;

			case TERRE: g.setColor(Color.ORANGE); break;

			case FEU: g.setColor(Color.RED); break;

			case HELICOPTERE: g.setColor(Color.BLACK); break;

			case SAC_DE_SABLE: g.setColor(Color.YELLOW); break;

			case MONTEE_DES_EAUX: g.setColor(Color.CYAN); break;
		}
		g.fillRect((x + 1)*WIDTH + WIDTH/16, y*HEIGHT + HEIGHT/16, WIDTH - WIDTH/8, HEIGHT - HEIGHT/8);
	}

}
