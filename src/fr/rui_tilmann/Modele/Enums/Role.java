package fr.rui_tilmann.Modele.Enums;

import java.awt.*;

public enum Role
{
	EXPLORATEUR(Color.GREEN),
	INGENIEUR(Color.RED),
	MESSAGER(Color.LIGHT_GRAY),
	NAVIGATEUR(Color.YELLOW),
	PILOTE(Color.BLACK),
	PLONGEUR(Color.BLUE);

	private final Color color;

	Role(Color color) {this.color = color;}

	public Color getColor() {return color;}
}
