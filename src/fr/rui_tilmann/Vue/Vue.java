package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Modele;

import javax.swing.*;

public class Vue
{

	public Vue(Modele modele)
	{
		JFrame f = new JFrame();
		f.add(new VuePlateau(modele));
		f.setTitle("Jeu de l'île Interdite");
		f.setResizable(false);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
