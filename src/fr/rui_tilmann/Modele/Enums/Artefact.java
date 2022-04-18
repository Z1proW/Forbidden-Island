package fr.rui_tilmann.Modele.Enums;

import javax.swing.*;
import java.awt.*;

public enum Artefact
{
	AIR("air.png"),
	EAU("eau.png"),
	TERRE("terre.png"),
	FEU("feu.png");

	private final ImageIcon img;
	private final ImageIcon ctr;

	Artefact(String fileName)
	{
		this.img = new ImageIcon("src/fr/rui_tilmann/images/artefact/" + fileName);
		this.ctr = new ImageIcon("src/fr/rui_tilmann/images/artefact/c_" + fileName);
	}

	public Image getImage()
	{
		return img.getImage();
	}

	public Image getContour()
	{
		return ctr.getImage();
	}
}
