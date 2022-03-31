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
		int[] a = new int[4];
		Joueur[] jtab = modele.getJoueurs();
		// Y a plus efficace mais je sais pas faire
		for(int i = 0 ; i < 4; i++)
		{
			a[i] = i;
		}
		for(int i = 0 ; i < 4; i++)
		{
			for(int j = i + 1; j < 4; j++)
			{
				if(jtab[i].getPosition() == jtab[j].getPosition())
				{
					a[j] = i;
					break;
				}

			}
		}
		for(int i = 0 ; i < 4; i++)
		{
			g.setColor(jtab[i].getRole().getColor());
			Case pos = jtab[i].getPosition();
			if(a[i] == i) {

				//System.out.println(i);
				g.fillRect(pos.getX() * P + P / 4, pos.getY() * P + P / 4, P / 2, P / 2);
			}
			else if(a[i] == i - 1)
			{
				//System.out.println(i);
				//Draw a half rectangle on the previous player
				g.fillRect(pos.getX() * P + P / 2, pos.getY() * P + P / 4 , P / 4, P / 2);
			}
			else if(a[i] == i - 2)
			{


				if(a[i-1] == a[i])
				{
					//Draw a quarter rectangle on 2 previous player
					g.fillRect(pos.getX() * P + P / 2 , pos.getY() * P + P / 2 , P / 4, P / 4);
				}
				else
				{
					//Draw a half rectangle on the first or the second player
					g.fillRect(pos.getX() * P + P / 2, pos.getY() * P + P / 4 , P / 4, P / 4);
				}
			}
			else
			{
				boolean b = a[i - 2] == a[i - 1];
				boolean b1 = a[i] == a[i - 3];
				boolean b2 = a[i - 3] == a[i - 2];
				boolean b3 = a[i] == a[i-2];
				boolean b4 = a[i-3] == a[i-1];
				if( b1 && b2 && b && b4) {
					//Draw a quarter rectangle on 2 or 3 previous player
					g.fillRect(pos.getX() * P + P , pos.getY() * P + P , P / 4, P / 4);
				}
				else if((b1 && b2) || (b1 && b4) || (b3 && b))
				{
					g.fillRect(pos.getX() * P + P / 4, pos.getY() * P + P / 2, P / 4, P / 4);
				}
				else
				{
					g.fillRect(pos.getX() * P + P / 4, pos.getY() * P + P / 2, P /4, P / 4);
				}
			}
		}
		// TODO fonctionne mais quand 3 joueurs sont sur la meme case on n'en voit 2
	}

}
