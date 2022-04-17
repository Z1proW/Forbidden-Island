package fr.rui_tilmann.Controleur;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Enums.Role;
import fr.rui_tilmann.Modele.Joueur;
import fr.rui_tilmann.Modele.Modele;
import fr.rui_tilmann.Vue.VueCartes;

import java.awt.event.*;

import static fr.rui_tilmann.Vue.VuePlateau.P;

public class ControleurCartes extends MouseAdapter implements MouseMotionListener
{

	private Modele modele;
	private VueCartes vueCartes;

	private int numCarteHover;
	private int numJoueurHover;
	private int numCarte;
	private int numJoueur;
	private boolean utiliserCartes = false;

	public ControleurCartes(Modele modele, VueCartes vueCartes)
	{
		this.modele = modele;
		this.vueCartes = vueCartes;
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		int numCarte = (e.getX() - vueCartes.getX())/vueCartes.WIDTH;
		int numJoueur = (e.getY() - vueCartes.getY())/vueCartes.HEIGHT;
		if(0 <= numCarte && numCarte < 10
				&& 0 <= numJoueur && numJoueur < 4 && e.getButton() == MouseEvent.BUTTON1)
		{
			vueCartes.chosenCard = numCarte;
			vueCartes.chosenJouer = numJoueur;
			vueCartes.repaint();
		}
		//A la place de getJoueur car c'est plus de travailler si on prend celui directement via numJoueur
		if(e.getButton() == MouseEvent.BUTTON3){
			Joueur j = modele.getJoueurs().get(numJoueur);
			if(j.getCartes().size() > 5) {
				modele.getJoueurs().get(numJoueur).discardTresor(numCarte);
				vueCartes.repaint();
			}
		}



	}
	@Override
	public void mouseMoved(MouseEvent e)
	{
		numCarteHover = (e.getX() - vueCartes.getX())/vueCartes.WIDTH;
		numJoueurHover = (e.getY() - vueCartes.getY())/vueCartes.HEIGHT;

		if(0 <= numCarteHover && numCarteHover < 10
		&& 0 <= numJoueurHover && numJoueurHover < 4)
		{
			vueCartes.hoveredCard = numCarteHover;
			vueCartes.hoveredJoueur = numJoueurHover;
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

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			utiliserCartes = !utiliserCartes;
		}
	}

}
