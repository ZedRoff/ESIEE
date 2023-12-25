int jourInv(String val) {
    int jour = convertirEnEntier(val.split(" ")[0]);
    String mois = val.split(" ")[1];
    int[] cor = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    String[] cor2 = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
    int cpt=0;
    for(int i=0;i<cor2.length;i++){
        if(!cor2[i].equals(mois)) {
            cpt+=cor[i];
        
        }else {
            break;
        }
    }
    cpt+=jour;
    return cpt;
}