package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Modele;
import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel implements Observer
{
    private Modele modele;
    private ImageIcon img = new ImageIcon("src/fr/rui_tilmann/images/MainMenu/BackGround MainMenu.png" );

    public MainMenu(Modele modele){
        this.modele = modele;
        //this.setPreferredSize(new Dimension(400,1200));
        modele.addObserver(this);
    }

    @Override
    public void update() {
        repaint();
    }

    public void init(){

    }

    @Override
    public void paintComponents(Graphics g) {
        super.repaint();
        paint(g);
    }
    public void paint(Graphics g){
        Image image = img.getImage();
        g.drawImage(image,0,0,null);
        g.setColor(new Color(128,128,128));
        g.setFont(new Font("SANS_SERIF",0, 30));
        g.drawString("Ile interdite Premium delux", 400, 60);
    }

}
