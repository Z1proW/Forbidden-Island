package fr.rui_tilmann.Modele.Enums;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

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
		Image img = this.img.getImage();
		img.getScaledInstance(4*img.getHeight(null), 4*img.getHeight(null), Image.SCALE_DEFAULT);
		return img;
	}

	public Image getContour()
	{
		Image img = ctr.getImage();
		img.getScaledInstance(4*img.getHeight(null), 4*img.getHeight(null), Image.SCALE_DEFAULT);
		return img;
	}
}
