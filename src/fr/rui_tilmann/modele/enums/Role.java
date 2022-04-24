package fr.rui_tilmann.modele.enums;

import java.awt.*;

public enum Role
{

	EXPLORATEUR(Color.GREEN),
	INGENIEUR(Color.RED),
	MESSAGER(Color.LIGHT_GRAY),
	NAVIGATEUR(Color.YELLOW),
	PILOTE(Color.BLACK),
	PLONGEUR(Color.BLUE);

	private final Color couleur;

	Role(Color color)
	{
		this.couleur = color;
	}

	public Color getCouleur() {
		return couleur;
	}

}
