// a, b et c sont des variables deja declarees et initialisees
double delta = b*b - 4*a*c;
if(delta < 0) {
    affiche("pas de racine");
} else if(delta == 0) {
    afficheReel(Math.round((-b / (2*a)) * 1000) / 1000);
}else if(delta > 0) {
    double racine1 = (-b + sqrt(delta)) / (2.0*a);
    double racine2 = (-b - sqrt(delta)) / (2.0*a);
  
    if(racine1 > racine2) {
        afficheReels(Math.round(racine2 * 1000) / 1000, racine1);
    }else {
        afficheReels(Math.round(racine1 * 1000) / 1000, Math.round(racine2));
    }
   }