package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Enums.Direction;
import fr.rui_tilmann.Modele.Modele;

import javax.swing.*;

public class VueCommandes extends JPanel
{

	private Modele modele;

	public VueCommandes(Modele modele)
	{
		this.modele = modele;

		JButton[] jButtons = {new JButton("^"), new JButton("v"), new JButton("<"), new JButton(">")};

		for(JButton b : jButtons)
			this.add(b);

		jButtons[0].addActionListener(e -> modele.avance(Direction.NORD));
		jButtons[1].addActionListener(e -> modele.avance(Direction.SUD));
		jButtons[2].addActionListener(e -> modele.avance(Direction.OUEST));
		jButtons[3].addActionListener(e -> modele.avance(Direction.EST));
	}

}