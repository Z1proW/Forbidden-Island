package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.Tresor;

import java.util.ArrayList;
import java.util.Collections;

public class PileCartes {
    private ArrayList<Tresor> tresors;
    private ArrayList<Tresor> defausse;
    PileCartes(){
        tresors = new ArrayList<>();
        defausse = new ArrayList<>();
        for(int i=0; i<4; i++){
            tresors.add(Tresor.TERRE);
            tresors.add(Tresor.AIR);
            tresors.add(Tresor.FEU);
            tresors.add(Tresor.EAU);
        }
        for(int i=0; i<3; i++){
            tresors.add(Tresor.HELICOT);
            tresors.add(Tresor.MONTEEDESEAUX);
        }
        tresors.add(Tresor.BACDESABLE);
        tresors.add(Tresor.BACDESABLE);
        Collections.shuffle(tresors);
    }

    public Tresor getTresor(){
        Tresor tresor = tresors.remove(tresors.size()-1);
        if(tresor == Tresor.MONTEEDESEAUX)
            defausse.add(tresor);
        return tresor;
    }

    public void reset(){
        if(tresors.isEmpty()){
            Collections.shuffle(defausse);
            tresors = defausse;
            defausse = new ArrayList<>();
        }
    }

}
