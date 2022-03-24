package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Modele;

import javax.swing.*;
import java.awt.*;

public class Vue
{

	private JFrame f;
	private VuePlateau plateau;
	private VueCommandes commandes;
	private VueJoueurs joueurs;

	public Vue(Modele modele)
	{
		f = new JFrame();
		f.setTitle("Jeu de l'île Interdite");
		f.setLayout(new FlowLayout());

		plateau = new VuePlateau(modele);
		f.add(plateau);
		commandes = new VueCommandes(modele);
		f.add(commandes);
		joueurs = new VueJoueurs(modele);
		f.add(joueurs);

		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

}
