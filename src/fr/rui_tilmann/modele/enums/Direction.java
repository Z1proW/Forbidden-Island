package fr.rui_tilmann.modele.enums;

public enum Direction
{
	//!\\ Ordre important, intervertir pas diagonale et diagonale
	NORD,
	NORD_EST,
	EST,
	SUD_EST,
	SUD,
	SUD_OUEST,
	OUEST,
	NORD_OUEST,
	AUCUNE;

	public boolean estDiagonale()
	{
		return (ordinal() & 1) != 0;
	}
}
