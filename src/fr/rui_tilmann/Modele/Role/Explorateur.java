package fr.rui_tilmann.Modele.Role;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Joueur;
import fr.rui_tilmann.Modele.Modele;

import java.awt.*;

public class Explorateur extends Joueur
{

	public Color color;

	public Explorateur(Modele modele, Case pos)
	{
		super(modele, pos);
		this.color = Color.GREEN;
	}

}
