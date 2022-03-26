package fr.rui_tilmann.Modele.Role;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Joueur;
import fr.rui_tilmann.Modele.Modele;

import java.awt.*;

public class Pilote extends Joueur
{

	private final Color color;

	public Pilote(Modele modele, Case pos)
	{
		super(modele, pos);
		this.color = Color.BLACK;
	}

	public Color getColor() {return color;}

}
