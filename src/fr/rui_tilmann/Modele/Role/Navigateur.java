package fr.rui_tilmann.Modele.Role;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Joueur;
import fr.rui_tilmann.Modele.Modele;

import java.awt.*;

public class Navigateur extends Joueur
{

	private final Color color;

	public Navigateur(Modele modele, Case pos)
	{
		super(modele, pos);
		this.color = Color.YELLOW;
	}

	public Color getColor() {return color;}

}
