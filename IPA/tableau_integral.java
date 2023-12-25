//tab est deja declaré et initialisé
int[] integral = new int[tab.length];

for(int i=0;i<tab.length;i++) {
    int cpt = 0; 
    for(int j=0;j<=i;j++) {
        cpt += tab[j];
        
    }
    integral[i] = cpt;
}
