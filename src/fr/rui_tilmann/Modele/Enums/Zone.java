package fr.rui_tilmann.Modele.Enums;

import javax.swing.*;
import java.awt.*;

public enum Zone {
    NORMALE(""),
    AIR("air.png"),
    EAU("eau.png"),
    TERRE("terre.png"),
    FEU("feu.png"),
    HELIPORT("heliport.png");

    private final ImageIcon img;

    Zone(String fileName)
    {
        this.img = new ImageIcon("src/fr/rui_tilmann/images/zone/" + fileName);
    }

    public Image getImage() {return img.getImage();}

}
