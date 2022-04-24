package fr.rui_tilmann.controleur;

import fr.rui_tilmann.modele.Case;
import fr.rui_tilmann.modele.Joueur;
import fr.rui_tilmann.modele.Modele;
import fr.rui_tilmann.modele.enums.Carte;
import fr.rui_tilmann.modele.enums.Direction;
import fr.rui_tilmann.modele.enums.Etat;
import fr.rui_tilmann.modele.enums.Role;
import fr.rui_tilmann.vue.VueArtefact;
import fr.rui_tilmann.vue.VuePlateau;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static fr.rui_tilmann.vue.VuePlateau.P;

public class ControleurJoueur extends MouseAdapter implements KeyListener
{

	private final Modele modele;
	private final VuePlateau vuePlateau;

	public Joueur clickedJoueur = null;
	public int clickedCard = -1;

	private Case caseHelico = null;

	private boolean actionSpeNavigateurOuPilote = false;
	private boolean actionSpePlongeur = false;
	private final boolean[] jSelect = new boolean[Modele.NOMBRE_JOUEURS];
	private int joueurDeplace = 0;

	private int caseDeplace = 0;

	public ControleurJoueur(Modele modele, VuePlateau vuePlateau, VueArtefact vueArtefact)
	{
		this.modele = modele;
		this.vuePlateau = vuePlateau;

		for(int i = 0; i < Modele.NOMBRE_JOUEURS; i++)
		{
			jSelect[i] = false;

			int finalI = i;
			vueArtefact.boutonJoueur[i].addActionListener(e -> {
				AbstractButton button = (AbstractButton)e.getSource();
				Color color = button.getBackground();

				if(color == Color.RED) {
					vueArtefact.boutonJoueur[finalI].setBackground(Color.GREEN);
					jSelect[finalI] = true;
				}
				else {
					vueArtefact.boutonJoueur[finalI].setBackground(Color.RED);
					jSelect[finalI] = false;
				}
			});
		}

		vueArtefact.boutonActionSpe.addActionListener(e -> {
			if(!actionSpeNavigateurOuPilote) {
				vueArtefact.boutonActionSpe.setBackground(Color.GREEN);
				actionSpeNavigateurOuPilote = true;
			}
			else {
				vueArtefact.boutonActionSpe.setBackground(Color.RED);
				actionSpeNavigateurOuPilote = false;
			}
		});
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		vuePlateau.setFocusable(true);
		Case c = getCase(e);
		if(c == null) return;

		Joueur joueur = clickedJoueur;

		if(e.getButton() == MouseEvent.BUTTON3)
			caseHelico = c;

		if(0 <= clickedCard && clickedCard < clickedJoueur.getCartes().size()
		&& e.getButton() == MouseEvent.BUTTON1)
		{
			switch(clickedJoueur.getCartes().get(clickedCard))
			{
				case HELICOPTERE:
					if(c.getEtat() == Etat.SUBMERGEE) return;

					if(caseHelico == null)
						caseHelico = clickedJoueur.getPosition();

					if(c != caseHelico)
					{
						boolean aBienTransporte = false;
						for(Joueur jh : caseHelico.getJoueurs()) {
							for(int j = 0; j < modele.getJoueurs().size(); j++) {
								if(jh == modele.getJoueur(j) && jSelect[j]) {
									jh.deplace(c, false);
									aBienTransporte = true;
								}
							}
						}
						if(aBienTransporte)
							joueur.defausseCarte(clickedCard);
						clickedJoueur = null;
						clickedCard = -1;
					}
					caseHelico = null;
					break;

				case SAC_DE_SABLE:
					if(c.getEtat() == Etat.INONDEE)
					{
						joueur.asseche(c, true);
						joueur.defausseCarte(clickedCard);
					}
					break;
			}
			return;
		}

		if(modele.getCurrentJoueur().getRole() == Role.PILOTE
		&& !modele.actionUtiliseePilote && actionSpeNavigateurOuPilote
		&& e.getButton() == MouseEvent.BUTTON1)
		{
			modele.getCurrentJoueur().deplace(c);
			modele.actionUtiliseePilote = true;
			return;
		}

		boolean diago = modele.getCurrentJoueur().getRole() == Role.EXPLORATEUR;
		Joueur j = modele.getCurrentJoueur();

		if(c.estAdjacente(j.getPosition(), diago)) {
			switch (e.getButton())
			{
				case 1: // gauche
					if(j.getRole() == Role.INGENIEUR && modele.actionSpeIngenieur) {
						modele.actionSpeIngenieur = false;
						modele.useAction();
						if(!modele.actionsRestantes()) return;
					}
					if(j.getRole() == Role.PLONGEUR && c.getEtat() != Etat.SECHE)
						actionSpePlongeur = true;
					else
						actionSpePlongeur = false;
					j.deplace(c,!actionSpePlongeur);
					break;

				case 3: // droit
					if(actionSpePlongeur){
						actionSpePlongeur = false;
						modele.useAction();
					}
					if(j.getRole() == Role.INGENIEUR && !modele.actionSpeIngenieur) {
						j.asseche(c, true);
						modele.actionSpeIngenieur = true;
					} else {
						j.asseche(c);
						modele.actionSpeIngenieur = false;
					}
			}
		}

		if(c == j.getPosition() && e.getButton() == MouseEvent.BUTTON1) {
			long occurences = j.getCartes().stream().filter(carte -> carte.toArtefact() == c.getType().toArtefact()).count();

			if(occurences > 3)
				for(Carte carte : j.getCartes())
					if(carte.toArtefact() == c.getType().toArtefact()) {
						if(j.getRole() == Role.INGENIEUR && modele.actionSpeIngenieur) {
							modele.actionSpeIngenieur = false;
							modele.useAction();
							if(!modele.actionsRestantes())return;
						}
						modele.getCurrentJoueur().recupereArtefact(carte);
						break;
					}
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		Direction d = Direction.AUCUNE;

		switch(e.getKeyCode())
		{
			// déplacement
			case KeyEvent.VK_UP: 	d = Direction.NORD;  break;
			case KeyEvent.VK_RIGHT: d = Direction.EST; 	 break;
			case KeyEvent.VK_DOWN: 	d = Direction.SUD; 	 break;
			case KeyEvent.VK_LEFT: 	d = Direction.OUEST; break;

			// TODO trouver d'autre moyen pour changer
			case KeyEvent.VK_N:
				actionSpeNavigateurOuPilote = !actionSpeNavigateurOuPilote;
				break;

			case KeyEvent.VK_M:
				caseDeplace = (caseDeplace + 1) % 2;
				break;

			case  KeyEvent.VK_SPACE:
				joueurDeplace = (joueurDeplace + 1) % 4;

			case  KeyEvent.VK_R:
				clickedCard = -1;
				clickedJoueur = null;
		}

		// TODO à gerer les cas out of bounds afin qu'il utilise pas d'action

		if(modele.getCurrentJoueur().getRole() == Role.INGENIEUR && modele.actionSpeIngenieur) {
			modele.actionSpeIngenieur = false;
			modele.useAction();
			if(!modele.actionsRestantes()) return;
		}

		if(modele.getCurrentJoueur().getRole() == Role.NAVIGATEUR
		&& d != Direction.AUCUNE
		&& actionSpeNavigateurOuPilote) {

			switch (caseDeplace){
				case 0: modele.getJoueur(joueurDeplace).deplace(d);break;
				case 1:
					modele.getJoueur(joueurDeplace).deplace(d, false);
					modele.getJoueur(joueurDeplace).deplace(d);break;
			}
		}
		else modele.getCurrentJoueur().deplace(d);
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		Case c = getCase(e);

		if(c != null)
		{
			vuePlateau.hoveredCase = c;
			vuePlateau.repaint();
		}
		else mouseExited(e);
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		mouseMoved(e);
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		vuePlateau.hoveredCase = null;
		vuePlateau.repaint();
	}

	private Case getCase(MouseEvent e)
	{
		int x = (e.getX() - vuePlateau.getX());
		int y = (e.getY() - vuePlateau.getY());

		if(0 <= x && x < vuePlateau.getWidth()
		&& 0 <= y && y < vuePlateau.getHeight())
			return modele.getPlateau().getCase(x/P, y/P);

		return null;
	}

	public void keyPressed(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

}
