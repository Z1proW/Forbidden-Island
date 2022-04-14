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
		switch(direction)
		{
			default: case AUCUNE: return modele.getPlateau().getCase(x, y);
			case NORD : return modele.getPlateau().getCase(x, y - 1);
			case SUD: 	return modele.getPlateau().getCase(x, y + 1);
			case OUEST: return modele.getPlateau().getCase(x - 1, y);
			case EST: 	return modele.getPlateau().getCase(x + 1, y);
		}
	}

	public boolean estAdjacente(Case c, boolean diagonales)
	{
		List<Direction> directions = Arrays.asList(Direction.NORD, Direction.EST, Direction.SUD, Direction.OUEST);

		//if(diagonales) directions.add();

		for(Direction direction : directions)
			if(adjacente(direction) == c)
				return true;
		return false;
	}

	public String toString()
	{
		return "case " + x + ", " + y + ", " + etat + ", " + type;
	}

}
