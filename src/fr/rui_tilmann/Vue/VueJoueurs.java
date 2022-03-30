package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Joueur;
import fr.rui_tilmann.Modele.Modele;

import java.awt.*;

import static fr.rui_tilmann.Vue.VuePlateau.P;

public class VueJoueurs
{

	private final Modele modele;

	public VueJoueurs(Modele modele)
	{
		this.modele = modele;

	}

	public void draw(Graphics g)
	{
		//Joueur[] a;
		for(Joueur j : modele.getJoueurs())
		{
			g.setColor(j.getRole().getColor());
			Case pos = j.getPosition();
			g.fillRect(pos.getX()*P + P/4, pos.getY()*P + P/4, P/2, P/2);
		} // TODO fonctionne mais quand 2 joueurs sont sur la meme case on n'en voit qu'un
	}

}
