package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Modele;

import javax.swing.*;
import java.awt.*;

import static fr.rui_tilmann.Vue.VuePlateau.P;

public class VueJoueurs extends JPanel implements Observer
{

	private Modele modele;

	public VueJoueurs(Modele modele)
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

		// TODO paint chaque joueur
		g.setColor(Color.RED);
		g.fillRect(0+P/4, 0+P/4, P/2, P/2);
	}

}
