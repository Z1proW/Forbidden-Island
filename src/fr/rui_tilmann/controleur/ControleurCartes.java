package fr.rui_tilmann.controleur;

import fr.rui_tilmann.modele.enums.Carte;
import fr.rui_tilmann.modele.enums.Role;
import fr.rui_tilmann.modele.Joueur;
import fr.rui_tilmann.modele.Modele;
import fr.rui_tilmann.vue.VueCartes;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ControleurCartes implements MouseMotionListener, MouseListener
{

	private final Modele modele;
	private final VueCartes vueCartes;
	private final ControleurJoueur controleurJoueur;

	public static int carteEnfoncee = -1;
	public static Joueur joueurEnfonce = null;

	public ControleurCartes(Modele modele, VueCartes vueCartes, ControleurJoueur controleurJoueur)
	{
		this.modele = modele;
		this.vueCartes = vueCartes;
		this.controleurJoueur = controleurJoueur;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		int numCarte = getNumCarte(e);
		Joueur j = getJoueur(e);
		if(j == null || numCarte == -1) return;

		switch(e.getButton())
		{
			case MouseEvent.BUTTON1:
				controleurJoueur.clickedCard = numCarte;
				controleurJoueur.clickedJoueur = j;
				vueCartes.repaint();
				break;

			case MouseEvent.BUTTON3:
				if(j.getCartes().size() > 5)
				{
					j.defausseCarte(numCarte);
					vueCartes.repaint();
				}
				break;
		}

		if(numCarte < 5)
		{
			if(numCarte >= j.getCartes().size()
			|| modele.getCurrentJoueur() != j) return;

			Carte carte = j.getCartes().get(numCarte);

			switch(carte)
			{
				case FEU: case EAU: case TERRE: case AIR:

				if(e.getButton() == MouseEvent.BUTTON1
				&& j.getPosition().getType().toArtefact() == carte.toArtefact()
				&& j.getCartes().stream().filter(t -> t == carte).count() >= 4)
					j.recupereArtefact(carte);

				break;
			}
		}
	}

	public void mousePressed(MouseEvent e)
	{
		int numCarte = getNumCarte(e);
		Joueur j = getJoueur(e);
		if(j == null || numCarte == -1
		|| numCarte >= j.getCartes().size()
		|| modele.getCurrentJoueur() != j
		|| e.getButton() != MouseEvent.BUTTON1
		|| j.getCartes().get(numCarte) == Carte.HELICOPTERE
		|| j.getCartes().get(numCarte) == Carte.SAC_DE_SABLE)
			return;

		carteEnfoncee = numCarte;
		joueurEnfonce = j;

		// pour avoir la carte a la bonne position quand on a cliqué mais pas encore drag
		mouseDragged(e);
	}

	public void mouseReleased(MouseEvent e)
	{
		Joueur j = getJoueur(e);

		if(j != null && carteEnfoncee != -1
		&& joueurEnfonce == modele.getCurrentJoueur()
		&& joueurEnfonce != j
		&& (joueurEnfonce.getPosition() == j.getPosition()
		|| modele.getCurrentJoueur().getRole() == Role.MESSAGER))
			joueurEnfonce.donneCarte(carteEnfoncee, j);

		carteEnfoncee = -1;
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		int numCarte = getNumCarte(e);
		Joueur j = getJoueur(e);
		if(j == null || numCarte == -1) return;

		if(modele.getCurrentJoueur() == j
		|| numCarte < j.getCartes().size()
		&& (j.getCartes().get(numCarte) == Carte.HELICOPTERE
		|| j.getCartes().get(numCarte) == Carte.SAC_DE_SABLE))
		{
			vueCartes.hoveredCard = numCarte;
			vueCartes.hoveredJoueur = j;
			vueCartes.repaint();
		}
		else mouseExited(e);
	}

	public void mouseDragged(MouseEvent e)
	{

		vueCartes.draggedX = e.getX();
		vueCartes.draggedY = e.getY();

	}

	public void mouseExited(MouseEvent e)
	{
		vueCartes.hoveredJoueur = null;
		vueCartes.hoveredCard = -1;
		vueCartes.repaint();
	}

	private int getNumCarte(MouseEvent e)
	{
		int numCarte = (e.getX() - vueCartes.getX())/VueCartes.WIDTH;
		return 0 <= numCarte && numCarte < Carte.MAX ? numCarte : -1;
	}

	private Joueur getJoueur(MouseEvent e)
	{
		int numJoueur = (e.getY() - vueCartes.getY())/VueCartes.HEIGHT;
		return 0 <= numJoueur && numJoueur < Modele.NOMBRE_JOUEURS ? modele.getJoueur(numJoueur) : null;
	}

	public void mouseEntered(MouseEvent e) {}

}
