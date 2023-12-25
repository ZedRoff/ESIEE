int lotoVerif(String numeros_entrees, String numeros_sortis) {
    String[] numeros_entrees_tab=numeros_entrees.split("-");
    String[] numeros_sortis_tab=numeros_sortis.split("-");
    int cpt=0;
   for(int i=0;i<numeros_entrees_tab.length;i++) {
        int a = (int)(convertirEnEntier(numeros_entrees_tab[i]));

       for(int j=0;j<numeros_sortis_tab.length;j++) {
             int b = (int)(convertirEnEntier(numeros_sortis_tab[j]));
  if(a==b) {
            cpt += 1;
        }
       }
     
    }
    return cpt;
}