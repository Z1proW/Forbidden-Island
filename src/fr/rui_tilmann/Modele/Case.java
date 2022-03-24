package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.Direction;
import fr.rui_tilmann.Modele.Enums.Etat;

public class Case
{

	private Modele modele;
	public Etat etat;
	private final int x, y;

	public Case(Modele modele, int x, int y)
	{
		this.modele = modele;
		this.etat = Etat.SECHE;
		this.x = x; this.y = y;
	}

	public Case adjacente(Direction direction)
	{
		return switch(direction)
		{
			case AUCUNE -> modele.getCase(x, y);
			case NORD 	-> modele.getCase(x, y - 1);
			case SUD 	-> modele.getCase(x, y + 1);
			case OUEST 	-> modele.getCase(x - 1, y);
			case EST 	-> modele.getCase(x + 1, y);
		};
	}

}
