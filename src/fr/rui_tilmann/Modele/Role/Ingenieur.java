package fr.rui_tilmann.Modele.Role;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Joueur;
import fr.rui_tilmann.Modele.Modele;

import java.awt.*;

public class Ingenieur extends Joueur
{

	public Color color;

	public Ingenieur(Modele modele, Role role, Case pos)
	{
		super(modele, role, pos);
		this.color = Color.RED;
	}

}
