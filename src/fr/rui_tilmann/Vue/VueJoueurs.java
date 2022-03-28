package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Joueur;
import fr.rui_tilmann.Modele.Modele;

import javax.swing.*;
import java.awt.*;

import static fr.rui_tilmann.Vue.VuePlateau.P;

public class VueJoueurs extends JPanel implements Observer
{

	private final Modele modele;

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
		Joueur[] a;
		for(Joueur j : modele.getJoueurs())
		{
			g.setColor(j.getRole().getColor());
			Case pos = j.getPosition();
			g.fillRect(pos.getX()*P + P/4, pos.getY()*P + P/4, P/2, P/2);
		} // TODO fonctionne mais quand 2 joueurs sont sur la meme case on n'en voit qu'un
	}

}
