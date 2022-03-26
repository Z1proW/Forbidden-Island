package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.Direction;
import fr.rui_tilmann.Modele.Enums.Etat;

public class Case
{

	private final Modele modele;
	private Etat etat;
	private final int x, y;

	public Case(Modele modele, int x, int y)
	{
		this.modele = modele;
		this.etat = Etat.SECHE;
		this.x = x; this.y = y;
	}

	public Etat getEtat() {return etat;}

	public void setEtat(Etat etat) {this.etat = etat;}

	public Case adjacente(Direction direction)
	{
		switch(direction)
		{
			default: case AUCUNE: return modele.getCase(x, y);
			case NORD : return modele.getCase(x, y - 1);
			case SUD: 	return modele.getCase(x, y + 1);
			case OUEST: return modele.getCase(x - 1, y);
			case EST: 	return modele.getCase(x + 1, y);
		}
	}

}
