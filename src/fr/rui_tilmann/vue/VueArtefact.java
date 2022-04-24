package fr.rui_tilmann.vue;

import fr.rui_tilmann.modele.enums.Artefact;
import fr.rui_tilmann.modele.Modele;
import fr.rui_tilmann.vue.menu.Bouton;

import javax.swing.*;
import java.awt.*;

public class VueArtefact extends JPanel implements Observer
{

	private final Modele modele;
	public Bouton boutonFinTour;
	public Bouton[] boutonJoueur = bJrs();
	public Bouton boutonActionSpe = new Bouton("", 0, 0, 0, 0);
	public static Bouton boutonNbNombreRestant = new Bouton("",0 ,0, 0, 0);


	public VueArtefact(Modele modele)
	{
		this.modele = modele;
		modele.addObserver(this);

		this.setPreferredSize(new Dimension(5*VueCartes.WIDTH, 8*VuePlateau.P - 4*VueCartes.HEIGHT));

		initBoutons();

	}

	private Bouton[] bJrs()
	{
		Bouton[] bts = new Bouton[Modele.NOMBRE_JOUEURS];
		for(int i = 0; i < bts.length; i++)
			bts[i] = new Bouton("", 0, 0, 0, 0);
		return bts;
	}

	private void initBoutons() {
		int width = 192;
		int x = 32;

		Bouton text = new Bouton("Helico", x, 50, width, 30);
		add(text);
		Bouton NBaction = new Bouton("Nombres actions restants:",x  , 130, width, 50);
		add(NBaction);

		boutonActionSpe = new Bouton("Action Speciale", x + width, 50, 192, 80);
		boutonActionSpe.setBackground(Color.RED);
		add(boutonActionSpe);

		boutonFinTour = new Bouton("Fin de tour", x + 2*width, 50, width, 80);
		boutonFinTour.addActionListener(e -> modele.finDeTour());
		add(boutonFinTour);

		boutonNbNombreRestant = new Bouton("3",x+width, 130, 50, 50);
		add(boutonNbNombreRestant);

		for(int i = 0; i < Modele.NOMBRE_JOUEURS; i++)
		{
			int dx = width/Modele.NOMBRE_JOUEURS;
			boutonJoueur[i] = new Bouton(String.valueOf(i + 1), x, 80, dx, 50);
			boutonJoueur[i].setBackground(Color.RED);
			add(boutonJoueur[i]);
			x += dx;
		}

		setLayout(new BorderLayout());
	}

	@Override
	public void update() {repaint();}

	public void paintComponent(Graphics g)
	{
		super.repaint();

		Artefact[] artefacts = Artefact.values();

		for(int i = 0; i < 4; i++)
		{
			Artefact artefact = artefacts[i];

			if(modele.hasArtefact(artefact))
			{
				Image img = artefact.getImage();
				g.drawImage(img, i*(img.getWidth(null) + 5) + 5, 5, null);
			}
			else
			{
				Image img = artefact.getContour();
				g.drawImage(img, i*(img.getWidth(null) + 5) + 5, 5, null);
			}
		}
	}

}
