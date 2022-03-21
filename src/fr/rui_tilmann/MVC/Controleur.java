package fr.rui_tilmann.MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controleur implements ActionListener
{
	Modele modele;

	public Controleur(Modele modele) { this.modele = modele; }
	public void actionPerformed(ActionEvent e) { modele.avance(); }
}
