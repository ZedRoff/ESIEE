//largeur, hauteur, interieur et exterieur sont des variables entières déjà déclarées et initialisées

double mX = (largeur-1) / 2.0;
double mY = (hauteur-1) / 2.0;
setFillColor("red");
for(int i=0;i<largeur;i++) {
    for(int j=0;j<hauteur;j++) {
        double distance = Math.sqrt((i-mX)*(i-mX) + (j-mY)*(j-mY));
        if(distance <= exterieur && distance >= interieur) {
            fillCase(i,j);
        }
    }
}