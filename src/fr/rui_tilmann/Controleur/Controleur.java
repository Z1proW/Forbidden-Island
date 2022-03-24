package fr.rui_tilmann.Controleur;

import fr.rui_tilmann.Modele.Enums.Direction;
import fr.rui_tilmann.Modele.Modele;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controleur implements ActionListener
{
	private Modele modele;

	public Controleur(Modele modele) { this.modele = modele; }
	public void actionPerformed(ActionEvent e) {}
}

/*
ne sert a rien pour le moment car on utilise des boutons dans VueCommandes
on pourra clicker sur les cases plus tard
 */