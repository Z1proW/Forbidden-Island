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
	private final ImageIcon ombre;

	Artefact(String fileName)
	{
		String path = "src/fr/rui_tilmann/images/artefact/";
		this.img = new ImageIcon(path + fileName);
		this.ombre = new ImageIcon(path + "c_" + fileName);
	}

	public Image getImage()
	{
		return img.getImage();
	}

	public Image getOmbre()
	{
		return ombre.getImage();
	}
}
