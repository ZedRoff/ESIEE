//N est un entier deja declaré et initialisé
int[][] tab = new int[N][1];
tab[0][0] = 1;
for(int i=1;i<N;i++) {
    tab[i] = new int[i+1];
}
for(int i=0;i<N;i++) {
    tab[i][i] = 1;
    tab[i][0] = 1;
}
for(int i=2;i<tab.length;i++) {
    for(int j=1;j<tab[i].length-1;j++) {
        tab[i][j] = tab[i-1][j-1] + tab[i-1][j];
    }
}