package fr.rui_tilmann.Modele.Role;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Joueur;
import fr.rui_tilmann.Modele.Modele;

import java.awt.*;

public class Pilote extends Joueur
{

	public Color color;

	public Pilote(Modele modele, Role role, Case pos)
	{
		super(modele, role, pos);
		this.color = Color.BLACK;
	}

}
