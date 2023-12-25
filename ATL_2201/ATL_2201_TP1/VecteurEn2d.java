 

public class VecteurEn2d {
    private double aX;
    private double aY;
    public VecteurEn2d(final double pX, final double pY) {
        this.aX = pX;
        this.aY = pY;
    }
    public VecteurEn2d(final VecteurEn2d pVecteur) {
        this(pVecteur.getX(), pVecteur.getY());
    }
    public double getX() {
        return this.aX;
    }
    public double getY() {
        return this.aY;
    }
    public double norme() {
        return Math.sqrt(this.aX*this.aX + this.aY*this.aY);
    }
    public void multiplicationScalaire(final double pScalaire) {
        this.aX*=pScalaire;
        this.aY*=pScalaire;
    }
    public void sommeVectorielle(final VecteurEn2d pVecteur)  {
        this.aX+=pVecteur.getX();
        this.aY+=pVecteur.getY();
    }
    // rappel de la formule du produit scalaire en dimension 2 : (x,y) scalaire (a,b) = xa + yb
    public double produitScalaire(final VecteurEn2d pVecteur) {
     
        return (this.aX*pVecteur.getX() + this.aY*pVecteur.getY());
    }
    public boolean estOrthogonal(final VecteurEn2d pVecteur) {
        return this.approche(this.produitScalaire(pVecteur), 0, 1e-9, 1e-5);
    }
    public VecteurEn2d obtenirVectOrthogonal() {
        return new VecteurEn2d(this.aY,-1*this.aX); 
    }
    // apr?s test, le r?sultat n'est pas conforme apr?s la multiplication par le scalaire 0.1 de v, car on multiplie 2 floats entre eux,
    // et le r?sultat de la multiplication par 0.1*0.1 par exemple, n'est pas 0.1, mais 0.1000000002
    public static boolean approche(final double pA, final double pB, final double pEpsilon_rel, final double pEpsilon_abs) {
        return Math.abs(pA-pB) <= Math.max(     Math.max(    Math.abs(pA) , Math.abs(pB)   )*pEpsilon_rel, pEpsilon_abs);
           
    }
    public boolean estColineaire(final VecteurEn2d pVecteur) {
        
        if((this.getX() == 0 & this.getY() == 0) || (pVecteur.getX() == 0 & pVecteur.getY() == 0)) {
            return true;
        }
        else if(this.getX() == 0 || this.getY() == 0) {
            return false;
        }
        return this.approche(pVecteur.getX() / this.getX(), pVecteur.getY() / this.getY(), 1e-9, 1e-5);
    }
    public double distance(final VecteurEn2d pVecteur) {
        return Math.sqrt( Math.pow( pVecteur.getX()-this.getX() ,2) + Math.pow( pVecteur.getY()-this.getY() ,2) );
    }
    public boolean segmentsSontOrthogonaux(final VecteurEn2d pVecteurA, final VecteurEn2d pVecteurC) {
        
        this.multiplicationScalaire(-1.0);
        pVecteurC.sommeVectorielle( this );
        VecteurEn2d vCprime = pVecteurC;
        
        pVecteurA.sommeVectorielle( this );
        VecteurEn2d vAprime = pVecteurA;
        
        return vCprime.estOrthogonal(vAprime);
        
    }
    
}