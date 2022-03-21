package fr.rui_tilmann.Jeu;

import fr.rui_tilmann.Enums.Direction;
import fr.rui_tilmann.Enums.Etat;
import fr.rui_tilmann.MVC.Modele;

public class Case
{
	private Modele modele;

	public Etat etat;
	private final int x, y;

	public Case(Modele modele, int x, int y)
	{
		this.modele = modele;
		this.etat = Etat.SECHE;
		this.x = x;
		this.y = y;
	}

	public void adjacente(Direction direction)
	{
		switch(direction)
		{
			case Direction.NORD:	return case;
			case Direction.SUD:		return case;
			case Direction.OUEST:	return case;
			case Direction.EST:		return case;
			case Direction.AUCUNE:	return case;
		}
	}

}
