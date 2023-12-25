//n est une variable déjà déclarée et initialisée
boolean premier = true;
int cpt =1;

if(n == 1 || n == 0) {
    premier = false;
}
for(int i=2;i<n;i++) {
    if(n%i==0){
        premier=false;
    }
}