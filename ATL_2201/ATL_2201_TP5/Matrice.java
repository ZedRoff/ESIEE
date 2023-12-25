public class Matrice {
    
    private double[][] aMatrice;
    public Matrice(final int pm, final int pn) {
        this.aMatrice = new double[pm][pn];
    }
    
    public double getCoefficient(final int pi, final int pj) {
        return this.aMatrice[pi][pj];
    }
    public int getNbColonnes() {
        return aMatrice[0].length;
    }
    public int getNbLignes() {
        return aMatrice.length;
    }
    public void setCoefficient(final int pi, final int pj, final double pCoefficient) {
        aMatrice[pi][pj] = pCoefficient;
    } 
    public Vecteur getVecteurLigne(final int pIndice) {
        Vecteur vVecteur = new Vecteur(this.getNbColonnes());
        for(int i=0;i<this.getNbColonnes();i++) {
            vVecteur.setCoordonnee(i, this.getCoefficient(pIndice, i));
        }
        return vVecteur;
    }
    public Vecteur multiplicationVectorielle(final Vecteur pVecteur) {
        Vecteur vVecteur = new Vecteur(this.getNbLignes());
        for(int i=0;i<vVecteur.getDimension();i++) {
            for(int j=0;j<this.getVecteurLigne(i).getDimension();j++) {
                vVecteur.setCoordonnee(i, vVecteur.getCoordonnee(i) + this.getCoefficient(i,j) * pVecteur.getCoordonnee(j) );
            }
            
        }
        return vVecteur;
    }
    public void setIdentite() {
        for(int i=0;i<this.getNbLignes();i++) {
            for(int j=0;j<this.getNbColonnes();j++) {
                if(i == j) this.setCoefficient(i,j, 1);
                else this.setCoefficient(i,j, 0);
            }
        }
    }
    public void setHomothetie(final double k) {
        this.setIdentite();
        for(int i=0;i<this.getNbLignes();i++) {
            this.setCoefficient(i,i,k);
        }
    }
    public void setSymetrieCentrale() {
       this.setHomothetie(-1);
    }
    public void setReflexionOx() {
       
       this.setIdentite();
       this.setCoefficient(1,1,-1);
    }
    public void setReflexionOxOy() {
       this.setIdentite();
       this.setCoefficient(this.getNbLignes()-1, this.getNbColonnes()-1,-1);
    }
    public void setRotation2d(final double alpha) {
        double[][] vMatriceRotation = {
                {Math.cos(alpha), -Math.sin(alpha)},
                {Math.sin(alpha), Math.cos(alpha)}
        };
        for(int i=0;i<2;i++) {
            for(int j=0;j<2;j++) {
                this.setCoefficient(i,j,vMatriceRotation[i][j]);
            }
        }
          
        
    
    }
    public void setRotation3dOx(final double alpha) {
         double[][] vMatriceRotation = {
                {1, 0, 0},
                {0, Math.cos(alpha), -Math.sin(alpha)},
                {0, Math.sin(alpha), Math.cos(alpha)}
        };
         for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                this.setCoefficient(i,j,vMatriceRotation[i][j]);
            }
        }
    }
 public void setRotation3dOy(final double alpha) {
         double[][] vMatriceRotation = {
            {Math.cos(alpha), 0, Math.sin(alpha)},
                {0, 1, 0},
                {-Math.sin(alpha), 0, Math.cos(alpha)}
        };
         for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                this.setCoefficient(i,j,vMatriceRotation[i][j]);
            }
        }
    }    
    public void setRotation3dOz(final double alpha) {
         double[][] vMatriceRotation = {
           {Math.cos(alpha), -Math.sin(alpha), 0},
                {Math.sin(alpha), Math.cos(alpha), 0},
                {0, 0, 1}
        };
         for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                this.setCoefficient(i,j,vMatriceRotation[i][j]);
            }
        }
    } 
    public static void tracerDroite(final Vecteur pu) {
        Plan vPlan = new Plan();
       
        vPlan.dessinerSegmentEn2d(new Vecteur(new double[] {pu.getX()*6, pu.getY()*6}), new Vecteur(new double[] {pu.getX()*-6, pu.getY()*-6}));
        
    }
    public static void tracerCercle(final double pRayon) {
        Plan vPlan = new Plan();
        Vecteur vPoint = new Vecteur(new double[] {pRayon,0});
        Matrice n = new Matrice(2,2);
        
        int pas = 100;
        double alpha = 2*Math.PI/pas;
        n.setRotation2d(alpha);
        for(int i=0;i<pas;i+=1) {
            vPlan.dessinerPointEn2d(vPoint);
            vPoint = n.multiplicationVectorielle(vPoint);            
        }
    }
    
    public Vecteur getVecteurColonne(final int pN) {
        Vecteur vVecteur = new Vecteur(this.getNbLignes());
        for(int i=0;i<vVecteur.getDimension();i++) {
            vVecteur.setCoordonnee(i,this.aMatrice[i][pN]);
        }
        return vVecteur;
    }
    public Matrice produitMatriciel(final Matrice pMatrice) {
        
        if(this.getNbColonnes() == pMatrice.getNbLignes()) {
            Matrice vMatrice = new Matrice(this.getNbLignes(), pMatrice.getNbColonnes());
            for(int i=0;i<this.getNbLignes();i++) {
                Vecteur vVecteur1 = this.getVecteurLigne(i);
                for(int j=0;j<pMatrice.getNbColonnes();j++) {
                    Vecteur vVecteur2 = pMatrice.getVecteurColonne(j);
                    vMatrice.setCoefficient(i,j,vVecteur1.produitScalaire(vVecteur2));
                    
                }
            }
            return vMatrice;
        } else {
            return null;
        }
    }
    public void setHomothetieHomogene3d(final double k) {
        this.setHomothetie(k);
        this.setCoefficient(this.getNbLignes()-1,this.getNbColonnes()-1,1);
    }
    public void setTranslationHomogene3d(final Vecteur t) {
        this.setIdentite();
        for(int i=0;i<this.getNbLignes()-1;i++) {
            this.setCoefficient(i,this.getNbColonnes()-1,t.getCoordonnee(i));
        }
        this.setCoefficient(this.getNbLignes()-1,this.getNbColonnes()-1,1);
   
    }
    public void setRotationOzHomogene3d(final double alpha) {
         this.setIdentite();

        double cosAlpha = Math.cos(alpha);
        double sinAlpha = Math.sin(alpha);

        this.setCoefficient(0,0, cosAlpha);
        this.setCoefficient(0,1, -sinAlpha);
        this.setCoefficient(1,0, sinAlpha);
        this.setCoefficient(1,1, cosAlpha);
    }
    public void setRotationOxHomogene3d(final double alpha) {
         this.setIdentite();

        double cosAlpha = Math.cos(alpha);
        double sinAlpha = Math.sin(alpha);

        this.setCoefficient(1,1, cosAlpha);
        this.setCoefficient(1,2, -sinAlpha);
        this.setCoefficient(2,1, sinAlpha);
        this.setCoefficient(2,2, cosAlpha);
    }
    public void setRotationOyHomogene3d(final double alpha) {
         this.setIdentite();

        double cosAlpha = Math.cos(alpha);
        double sinAlpha = Math.sin(alpha);

        this.setCoefficient(0,0, cosAlpha);
        this.setCoefficient(0,2, sinAlpha);
        this.setCoefficient(2,0, -sinAlpha);
        this.setCoefficient(2,2, cosAlpha);
    }
    public Matrice getRotationHomogene3d(final double alphaX, final double alphaY, final double alphaZ) {
         Matrice vMatrice = new Matrice(4,4);
        Matrice vMatriceTemp = new Matrice(4,4);
        vMatriceTemp.setRotationOxHomogene3d(alphaX);
        vMatrice = vMatriceTemp;
        vMatriceTemp.setRotationOyHomogene3d(alphaY);
       vMatrice = vMatrice.produitMatriciel(vMatriceTemp);
       vMatriceTemp.setRotationOzHomogene3d(alphaZ);
       vMatrice = vMatrice.produitMatriciel(vMatriceTemp);
       return vMatrice;
        
    }
    public void setProjectionOrthoOxyHomogene3d() {
        this.setIdentite();
        this.setCoefficient(2,0,0.0);
        this.setCoefficient(2,1,0.0);
        this.setCoefficient(2,2,0.0);
        this.setCoefficient(2,3,1.0);
        
    }
    public void setProjectionOrthoOxzHomogene3d() {
        this.setIdentite();
        this.setCoefficient(2,0,0.0);
        this.setCoefficient(2,1,0.0);
        this.setCoefficient(2,2,0.0);
        this.setCoefficient(2,3,1.0);
    }
}