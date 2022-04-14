package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Joueur;
import fr.rui_tilmann.Modele.Modele;

import java.awt.*;
import java.util.List;

import static fr.rui_tilmann.Vue.VuePlateau.P;

public class VueJoueurs
{

	private final Modele modele;
	private final TitleBar titleBar;

	public VueJoueurs(Modele modele, TitleBar titleBar)
	{
		this.modele = modele; this.titleBar = titleBar;
	}

	public void draw(Graphics g, Case c)
	{
		Joueur joueur = modele.getJoueur();
		titleBar.setTitle(joueur.getRole().toString(), joueur.getRole().getColor());

		List<Joueur> js = c.getJoueurs();

		if(js.size() == 1)
		{
			Joueur j = js.get(0);
			g.setColor(j.getRole().getColor());
			Case pos = j.getPosition();
			g.fillRect(pos.getX()*P + P/4, pos.getY()*P + P/4, P/2, P/2);
		}
		else if(js.size() > 1)
		{
			int i = 0;

			for(Joueur j : js)
			{
				g.setColor(j.getRole().getColor());
				Case pos = j.getPosition();

				switch(i)
				{
					case 0: g.fillRect(pos.getX()*P + P/8, pos.getY()*P + P/8, P/4, P/4); break;
					case 1: g.fillRect(pos.getX()*P + P/8 + P/2, pos.getY()*P + P/8, P/4, P/4); break;
					case 2: g.fillRect(pos.getX()*P + P/8, pos.getY()*P + P/8 + P/2, P/4, P/4); break;
					case 3: g.fillRect(pos.getX()*P + P/8 + P/2, pos.getY()*P + P/8 + P/2, P/4, P/4); break;
				}

				i++;
			}
		}
	}

}
