package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.Direction;
import fr.rui_tilmann.Modele.Enums.Etat;
import fr.rui_tilmann.Modele.Enums.Zone;

import java.nio.file.DirectoryStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Case
{

	private final Modele modele;
	private Etat etat;
	private Zone type;
	private final int x, y;

	public Case(Modele modele, int x, int y)
	{
		this.modele = modele;
		this.etat = Etat.SECHE;
		this.type = Zone.NORMALE;
		this.x = x; this.y = y;
	}

	public Etat getEtat() {return etat;}
	public void setEtat(Etat etat) {this.etat = etat;}

	public Zone getType() {return type;}
	public void setType(Zone type) {this.type = type;}

	public int getX() {return x;}
	public int getY() {return y;}

	public List<Joueur> getJoueurs()
	{
		return modele.getJoueurs().stream().filter(j -> j.getPosition() == this).collect(Collectors.toList());
	}

	public Case adjacente(Direction direction)
	{
		int x = this.x;
		int y = this.y;

		switch(direction)
		{
			case NORD : y--; break;
			case SUD: 	y++; break;
			case OUEST: x--; break;
			case EST: 	x++; break;
		}
		return modele.getPlateau().getCase(x, y);
	}

	public boolean estAdjacente(Case c, boolean diago)
	{
		int x = c.getX(); int y = c.getY();

		return (this.x == x || this.x == x-1 || this.x == x+1)
			&& (this.y == y || this.y == y-1 || this.y == y+1)
			&& (diago || this.x == x || this.y == y);
	}

	public boolean estValide()
	{
		return 0 <= x && x < Plateau.LENGTH
			&& 0 <= y && y < Plateau.LENGTH;
	}

	public String toString()
	{
		return "case " + x + ", " + y + ", " + etat + ", " + type;
	}

}
