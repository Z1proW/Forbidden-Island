package fr.rui_tilmann.vue;

import fr.rui_tilmann.modele.enums.Artefact;
import fr.rui_tilmann.modele.Modele;

import javax.swing.*;
import java.awt.*;

public class VueArtefact extends JPanel implements Observer
{

	private final Modele modele;
	public JButton boutonFinTour = new JButton("Fin de tour");
	public static JButton boutonJoueur1 = new JButton("1");
	public static JButton boutonJoueur2 = new JButton("2");
	public static JButton boutonJoueur3 = new JButton("3");
	public static JButton boutonJoueur4 = new JButton("4");;
	public static JButton boutonActionSpe = new JButton("Action Speciale");


	public VueArtefact(Modele modele)
	{
		this.modele = modele;
		modele.addObserver(this);

		this.setPreferredSize(new Dimension(5*VueCartes.WIDTH, 8*VuePlateau.P - 4*VueCartes.HEIGHT));

		initButton();

	}

	public void initButton(){

		boutonFinTour.addActionListener(e -> modele.finDeTour());
		boutonFinTour.setFocusable(false);
		boutonFinTour.setSize(200,60);
		boutonFinTour.setLocation(50,125);
		add(boutonFinTour);

		boutonJoueur1.setLocation(50,50);
		boutonJoueur1.setSize(50,50);
		boutonJoueur2.setLocation(100,50);
		boutonJoueur2.setSize(50,50);
		boutonJoueur3.setLocation(150,50);
		boutonJoueur3.setSize(50,50);
		boutonJoueur4.setLocation(200,50);
		boutonJoueur4.setSize(50,50);

		boutonJoueur1.setBackground(Color.RED);
		boutonJoueur2.setBackground(Color.RED);
		boutonJoueur3.setBackground(Color.RED);
		boutonJoueur4.setBackground(Color.RED);

		boutonJoueur1.setFocusable(false);
		boutonJoueur2.setFocusable(false);
		boutonJoueur3.setFocusable(false);
		boutonJoueur4.setFocusable(false);

		add(boutonJoueur1);
		add(boutonJoueur2);
		add(boutonJoueur3);
		add(boutonJoueur4);

		boutonActionSpe.setBounds(250,50,160,80);
		boutonActionSpe.setBackground(Color.RED);
		add(boutonActionSpe);

		JButton text = new JButton("Helico");
		text.setBounds(50,30,200,20);
		add(text);
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
