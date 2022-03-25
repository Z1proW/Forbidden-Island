package fr.rui_tilmann.Modele.Role;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Joueur;
import fr.rui_tilmann.Modele.Modele;

import java.awt.*;

public class Messager extends Joueur
{

	public Color color;

	public Messager(Modele modele, Case pos)
	{
		super(modele, pos);
		this.color = Color.LIGHT_GRAY;
	}

}
