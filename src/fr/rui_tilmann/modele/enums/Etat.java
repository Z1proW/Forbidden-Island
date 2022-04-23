package fr.rui_tilmann.modele.enums;

import javax.swing.*;
import java.awt.*;

public enum Etat
{
	SECHE("seche.png"),
	INONDEE("inondee.png"),
	SUBMERGEE("submergee.png");

	private final ImageIcon img;

	Etat(String fileName)
	{
		this.img = new ImageIcon("src/fr/rui_tilmann/images/etat/" + fileName);
	}

	public Image getImage() {return img.getImage();}
}
