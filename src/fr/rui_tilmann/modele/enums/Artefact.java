package fr.rui_tilmann.modele.enums;

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
		String path = "src/fr/rui_tilmann/images/artefact/";
		this.img = new ImageIcon(path + fileName);
		this.ctr = new ImageIcon(path + "c_" + fileName);
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
