package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.Tresor;

import java.util.*;

public class PileCartes {

    private List<Tresor> tresors;
    private  List<Case> CartesInondation;
    private  List<Case> CartesInondees;
    private List<Tresor> defausse;

    PileCartes() {
        tresors = new ArrayList<>();
        defausse = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            tresors.add(Tresor.TERRE);
            tresors.add(Tresor.AIR);
            tresors.add(Tresor.FEU);
            tresors.add(Tresor.EAU);
        }

        for(int i = 0; i < 3; i++) {
            tresors.add(Tresor.HELICOPTERE);
        }

        for(int i = 0; i < 2; i++)
            tresors.add(Tresor.SAC_DE_SABLE);

        Collections.shuffle(tresors);
    }

    public void defausser(Tresor t)
    {
        defausse.add(t);
    }

    public Tresor getTresor() {
        Tresor tresor = tresors.remove(0);

        //if(tresor == Tresor.MONTEE_DES_EAUX)
            //defausse.add(tresor);

        return tresor;
    }

    // si cette methode est inutile => defausse est inutile
    public void reset() {
        if(!tresors.isEmpty()) return;

        tresors = new ArrayList<>(defausse);
        Collections.shuffle(tresors);
        defausse.clear();
    }
    public void ajoutCarteMDE(){
        for(int i = 0; i < 3; i++) {
            tresors.add(Tresor.MONTEE_DES_EAUX);
        }
        Collections.shuffle(tresors);
    }

}
