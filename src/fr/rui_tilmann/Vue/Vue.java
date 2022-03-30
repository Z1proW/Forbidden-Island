package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Modele;

import javax.swing.*;
import java.awt.*;

public class Vue
{

	public Vue(Modele modele)
	{
		initFrame("Jeu de l'île Interdite", false, true, new VuePlateau(modele));
	}

	private JFrame initFrame(String title, boolean resizable, boolean centered, JPanel... panels)
	{
		JFrame f = new JFrame();

		for(JPanel p : panels)
			f.add(p);

		f.setTitle(title);
		f.setResizable(resizable);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(centered) f.setLocationRelativeTo(null);
		f.setVisible(true);
		return f;
	}

}
