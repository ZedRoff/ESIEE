import java.util.Arrays;
class Vecteur {
    private double[] aVecteur;
    public Vecteur(final double[] pVecteur) {
        this.aVecteur = pVecteur;
    }
    public Vecteur(final int pN) {
        this.aVecteur = new double[pN];
    }
    
    public Vecteur(final Vecteur pVecteur) {
           this(pVecteur.getCoordonnees());
    }
    public double[] getCoordonnees() {
        return this.aVecteur;
    }
    public double getCoordonnee(final int pN) {
        return this.aVecteur[pN];
    }
    public void setCoordonnee(final int pI, final double pCoordonnee) {
        this.aVecteur[pI] = pCoordonnee;
    }
    public int getDimension() {
        return this.aVecteur.length;
    }
    // lorsqu'on invoque getCoordonnee(4), on a une erreur Index Out Of Bound => on essaye d'accéder à une valeur qui est a un indice qui n'existe pas dans le tableau. 
    // Si on crée des vecteurs de dimensions 3, on peut pas avoir de quatrième coordonnée entre autre.
    public double getX() {
        return this.aVecteur[0];
    }
    public double getY() {
        return this.aVecteur[1];
    }
    public void multiplicationScalaire(final double pScalaire) {
        for(int i=0;i<this.aVecteur.length;i++) {
            this.aVecteur[i]*=pScalaire;
        }
    }
    public double norme() {
        double norme = 0;
        for(int i=0;i<this.aVecteur.length;i++) {
            norme += Math.pow(this.aVecteur[i], 2);
        }
        return Math.sqrt(norme);
    }
    public void normaliser() {
        double norme = this.norme();
        for(int i=0;i<this.aVecteur.length;i++) {
            this.aVecteur[i] /= norme;
        }
    }
    public void sommeVectorielle(final Vecteur pVecteur) {
        if(pVecteur.getDimension() != this.aVecteur.length) {
            System.out.println("Désolé, les vecteurs n'ont pas la même dimension..");
            return;
        }
        
        for(int i=0;i<pVecteur.getDimension();i++) {
            this.aVecteur[i]+=pVecteur.getCoordonnee(i);
        }
    }
    // si on met des vecteurs de taille différente, en théorie, c'est censer causer un bug. Or, nous avons ici prévu une condition pour éviter cela..
    
    public double produitScalaire(final Vecteur pVecteur) {
        double resultat = 0;
        // si on met des vecteurs de taille différente, en théorie, c'est censer causer un bug
        for(int i=0;i<this.aVecteur.length;i++) {
            resultat += this.aVecteur[i]*pVecteur.getCoordonnee(i);
        }
        return resultat;
    }
    public Vecteur produitVectoriel3d(final Vecteur pVecteur) {
        if(this.aVecteur.length == 3 && pVecteur.getDimension() == 3) {
            double vX = this.aVecteur[1] * pVecteur.getCoordonnee(2) - pVecteur.getCoordonnee(1) * this.aVecteur[2];
            double vY = this.aVecteur[2]*pVecteur.getCoordonnee(0) - pVecteur.getCoordonnee(2)*this.aVecteur[0];
            double vZ = this.aVecteur[0]*pVecteur.getCoordonnee(1) - pVecteur.getCoordonnee(0)*this.aVecteur[1];
            double[] vRes = {vX, vY, vZ};
        
            return new Vecteur(vRes);
    } else {
        return null;
    }
}
    public boolean estOrthogonal(final Vecteur pVecteur) {
        return this.approche(this.produitScalaire(pVecteur), 0, 1e-9,1e-5);
    }
public static boolean approche(final double pA, final double pB, final double pEpsilon_rel, final double pEpsilon_abs) {
        return Math.abs(pA-pB) <= Math.max(     Math.max(    Math.abs(pA) , Math.abs(pB)   )*pEpsilon_rel, pEpsilon_abs);
           
    }
    public boolean estColineaire(final Vecteur pVecteur) {
        return this.approche(Math.abs(this.produitScalaire(pVecteur)), Math.abs(this.norme()*pVecteur.norme()), 1e-9,1e-5);
       
    }
    public boolean estCoplanaire3d(final Vecteur v1, final Vecteur v2) {
         double produitMixte = v1.getCoordonnee(0) * (v2.getCoordonnee(1) * this.getCoordonnee(2) - v2.getCoordonnee(2) * this.getCoordonnee(1))
                           - v1.getCoordonnee(1) * (v2.getCoordonnee(0) * this.getCoordonnee(2) - v2.getCoordonnee(2) * this.getCoordonnee(0))
                           + v1.getCoordonnee(2) * (v2.getCoordonnee(0) * this.getCoordonnee(1) - v2.getCoordonnee(1) * this.getCoordonnee(0));


    return this.approche(produitMixte, 0, 1e-9, 1e-5);    
}
public void normaliseHomogene() {
     double vW = this.getCoordonnee(this.getDimension()-1);
    for(int i=0;i<this.getDimension();i++) {
        this.setCoordonnee(i,this.getCoordonnee(i)/vW);
    }
}
}