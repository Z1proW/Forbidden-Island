package fr.rui_tilmann.Controleur;

import fr.rui_tilmann.Modele.Enums.Tresor;
import fr.rui_tilmann.Modele.Joueur;
import fr.rui_tilmann.Modele.Modele;
import fr.rui_tilmann.Vue.VueCartes;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.TreeMap;

public class ControleurCartes implements MouseMotionListener
{

	private Modele modele;
	private VueCartes vueCartes;

	private int numCarte;
	private int numJoueur;

	public ControleurCartes(Modele modele, VueCartes vueCartes)
	{
		this.modele = modele;
		this.vueCartes = vueCartes;
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		numCarte = (e.getX() - vueCartes.getX())/vueCartes.WIDTH;
		numJoueur = (e.getY() - vueCartes.getY())/vueCartes.HEIGHT;

		if(0 <= numCarte && numCarte < 5
		&& 0 <= numJoueur && numJoueur < 4)
		{
			vueCartes.hoveredCard = numCarte;
			vueCartes.hoveredJoueur = numJoueur;
			vueCartes.repaint();
		}
		else
		{
			vueCartes.hoveredJoueur = -1;
			vueCartes.hoveredCard = -1;
			vueCartes.repaint();
		}
	}

	public void mouseDragged(MouseEvent e) {}

}
