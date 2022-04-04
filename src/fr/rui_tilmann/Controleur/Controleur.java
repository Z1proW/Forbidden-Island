package fr.rui_tilmann.Controleur;

import fr.rui_tilmann.Modele.Modele;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controleur implements ActionListener
{
	private final Modele modele;

	public Controleur(Modele modele) {
		this.modele = modele;
	}

	public void actionPerformed(ActionEvent e) {}
}
