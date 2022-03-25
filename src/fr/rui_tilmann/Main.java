package fr.rui_tilmann;

import fr.rui_tilmann.Controleur.ControleurClic;
import fr.rui_tilmann.Modele.Modele;
import fr.rui_tilmann.Vue.Vue;

import java.awt.*;

public class Main
{

    public static void main(String[] args)
    {
        EventQueue.invokeLater(() ->
        {
            Modele modele = new Modele();
            new Vue(modele);
        });
    }

}
