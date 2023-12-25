//mot1 et mot2 sont déjà déclarées et initialisées
String n_mot1 = mot1.toLowerCase();
String n_mot2 = mot2.toLowerCase();
int cpt3 = 0;
if(n_mot1.length() != n_mot2.length()) {
    affiche("Pas anagrammes");
} else {
    for(int i=0;i<n_mot1.length();i++) {
        int cpt1 = 0;
        int cpt2 = 0;
        for(int j=0;j<n_mot1.length();j++) {
            
        if(n_mot1.charAt(j) == n_mot1.charAt(i)) {
            cpt1+=1;
        }
        
        }
        
         for(int k=0;k<n_mot2.length();k++) {
             
             if(n_mot2.charAt(k) == n_mot2.charAt(i)) {
                 cpt2+=1;
             }
             
         }
          if(cpt1 == cpt2) {
        cpt3+=1;
    } 
        
    }
    if(cpt3 == n_mot1.length()) {
        affiche("Anagrammes");
    } else {
        affiche("Pas anagrammes");
    }
   
}


