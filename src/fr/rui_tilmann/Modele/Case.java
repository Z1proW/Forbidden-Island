package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.Direction;
import fr.rui_tilmann.Modele.Enums.Etat;
import fr.rui_tilmann.Modele.Enums.Zone;

public class Case
{

	private final Modele modele;
	public Etat etat;
	public Zone type;
	private final int x, y;

	public Case(Modele modele, int x, int y)
	{
		this.modele = modele;
		this.etat = Etat.SECHE;
		this.type = Zone.AUCUNE;
		this.x = x; this.y = y;
	}

	public Etat getEtat() {return etat;}

	public void setEtat(Etat etat) {this.etat = etat;}

	public int getX() {return x;}
	public int getY() {return y;}

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
