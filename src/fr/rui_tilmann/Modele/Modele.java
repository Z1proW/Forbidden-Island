package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.Direction;
import fr.rui_tilmann.Modele.Enums.Etat;
import fr.rui_tilmann.Vue.Observable;

import java.util.Random;

public class Modele extends Observable
{

	public static final int LENGTH = 6;
	private Case[][] cases;

	public Modele()
	{
		cases = new Case[LENGTH][LENGTH];

		for(int x = 0; x < LENGTH; x++)
			for(int y = 0; y < LENGTH; y++)
				cases[x][y] = new Case(this, x, y);

		inonderSixCases();
	}

	private void inonderSixCases()
	{
		for(int i = 0; i < 6; i++)
		{
			int x, y;

			do
			{
				x = new Random().nextInt(LENGTH);
				y = new Random().nextInt(LENGTH);
			}
			while(cases[x][y].etat != Etat.SECHE);

			cases[x][y].etat = Etat.INONDEE;
		}
	}

	public Case getCase(int x, int y) {return cases[x][y];}

	public void avance(Direction d)
	{
		// ...
	}

}
