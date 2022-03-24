package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Modele;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Vue
{

	private JFrame f;
	private VuePlateau plateau;
	private VueCommandes commandes;
	private VueJoueurs joueurs;

	public Vue(Modele modele)
	{
		plateau = new VuePlateau(modele);
		commandes = new VueCommandes(modele);
		joueurs = new VueJoueurs(modele);

		f = new JFrame();
		f.setTitle("Jeu de l'île Interdite");
		f.setLayout(new FlowLayout());
		f.add(plateau);
		f.add(commandes);
		f.add(joueurs);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

}
