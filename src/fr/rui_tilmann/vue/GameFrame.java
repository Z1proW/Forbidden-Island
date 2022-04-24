package fr.rui_tilmann.vue;

import fr.rui_tilmann.controleur.ControleurCartes;
import fr.rui_tilmann.controleur.ControleurJoueur;
import fr.rui_tilmann.modele.Modele;
import fr.rui_tilmann.modele.enums.Difficulte;
import fr.rui_tilmann.vue.menu.MenuFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.RoundRectangle2D;

public class GameFrame extends JFrame
{

	public GameFrame(Difficulte difficulte, int nbJoueurs)
	{
		Modele modele = new Modele(difficulte, nbJoueurs, this);

		setTitle("L'île Interdite");
		setLayout(new BorderLayout());

		VueJoueurs vueJoueurs = new VueJoueurs(modele);

		VuePlateau vuePlateau = new VuePlateau(modele, vueJoueurs);
		add(vuePlateau, BorderLayout.WEST);

		VueArtefact vueArtefact = new VueArtefact(modele);

		ControleurJoueur controleurJoueur = new ControleurJoueur(modele, vuePlateau, vueArtefact);
		addMouseListener(controleurJoueur);
		addKeyListener(controleurJoueur);
		addMouseMotionListener(controleurJoueur);

		add(new VueEau(modele), BorderLayout.CENTER);

		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BorderLayout());
		eastPanel.setBackground(new Color(0, 0, 0, 0));

		VueCartes vueCartes = new VueCartes(modele);
		eastPanel.add(vueCartes, BorderLayout.NORTH);

		ControleurCartes controleurCartes = new ControleurCartes(modele, vueCartes, controleurJoueur);
		eastPanel.addMouseListener(controleurCartes);
		eastPanel.addMouseMotionListener(controleurCartes);

		eastPanel.add(vueArtefact);

		add(eastPanel, BorderLayout.EAST);

		addWindowListener(new WindowListener()
		{
			@Override
			public void windowClosed(WindowEvent e)
			{
				new MenuFrame();
			}

			public void windowOpened(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
		});

		setResizable(false);
		pack();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
