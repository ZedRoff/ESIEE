//joueur1 et joueur2 sont déjà déclarées et initialisées
String c1 = joueur1.toLowerCase();
String c2 = joueur2.toLowerCase();

if((c1.equals("pierre") && c2.equals("ciseau")) 
|| (c1.equals("papier") && c2.equals("pierre")) ||
(c1.equals("ciseau") && c2.equals("papier"))
){
    affiche("Joueur 1 gagne");
}

else if(c2.equals(c1)) {
    affiche("Match nul");
}else  {
    affiche("Joueur 2 gagne");
}