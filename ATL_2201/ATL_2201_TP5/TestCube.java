
/**
 * Write a description of class TestA here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TestCube
{

    /**
     * Constructor for objects of class Test
     */
    private TestCube() {}
    
    /**
     * Crée les sommets d'un cube centré sur l'origine et les mets das un tableau.
     * @return Tableau de Vecteur
     */
    private static Vecteur[] creerSommets() throws Exception
    {
        Vecteur p1=new Vecteur(3);   // sommet arrière gauche bas
        p1.setCoordonnee(0,-1);
        p1.setCoordonnee(1,-1);
        p1.setCoordonnee(2,-1); 
        Vecteur p2=new Vecteur(3);   // sommet arrière gauche haut 
        p2.setCoordonnee(0,-1);
        p2.setCoordonnee(1,1);
        p2.setCoordonnee(2,-1); 
        Vecteur p3=new Vecteur(3);   // sommet arrière droite haute
        p3.setCoordonnee(0,1);
        p3.setCoordonnee(1,1);
        p3.setCoordonnee(2,-1); 
        Vecteur p4=new Vecteur(3);   //  sommet arrière droite bas
        p4.setCoordonnee(0,1);
        p4.setCoordonnee(1,-1);
        p4.setCoordonnee(2,-1); 
        Vecteur p5=new Vecteur(3);   // sommet avant gauche bas
        p5.setCoordonnee(0,-1);
        p5.setCoordonnee(1,-1);
        p5.setCoordonnee(2,1); 
        Vecteur p6=new Vecteur(3);   // sommet avant gauche haut 
        p6.setCoordonnee(0,-1); 
        p6.setCoordonnee(1,1);
        p6.setCoordonnee(2,1); 
        Vecteur p7=new Vecteur(3);   // sommet avant droite haute
        p7.setCoordonnee(0,1);
        p7.setCoordonnee(1,1);
        p7.setCoordonnee(2,1); 
        Vecteur p8=new Vecteur(3);   // sommet avant droite bas
        p8.setCoordonnee(0,1);
        p8.setCoordonnee(1,-1);
        p8.setCoordonnee(2,1); 
        
        return new Vecteur[]{p1,p2,p3,p4,p5,p6,p7,p8};
    }
    
        /**
     * Dessine sur le Plan les arêtes du cube dont les sommets sont dans le tableau de Vecteur;
     * @param pPlan : plan ou effectuer le dessin
     * @param pSommets : sommets du cube
     */
    private static void dessinerCube(final Plan pPlan, final Vecteur[] pSommets)
    {
        pPlan.effacer();
        //(p1,p2,p3,p4); // face arriere
        //(p5,p6,p7,p8); // face avant
        //(p1,p2,p6,p5); // face gauche
        //(p3,p4,p8,p7); // face droite
        //(p2,p3,p7,p6); // face haut
        //(p1,p4,p8,p5); // face bas
        
        // face arrière
        pPlan.dessinerSegmentEn2d(pSommets[0],pSommets[1]);
        pPlan.dessinerSegmentEn2d(pSommets[1],pSommets[2]);
        pPlan.dessinerSegmentEn2d(pSommets[2],pSommets[3]);
        pPlan.dessinerSegmentEn2d(pSommets[3],pSommets[0]);
        
        // face avant 
        pPlan.dessinerSegmentEn2d(pSommets[4],pSommets[5]);
        pPlan.dessinerSegmentEn2d(pSommets[5],pSommets[6]);
        pPlan.dessinerSegmentEn2d(pSommets[6],pSommets[7]);
        pPlan.dessinerSegmentEn2d(pSommets[7],pSommets[4]);
        
        // on relie la face avant à la face arrière
        pPlan.dessinerSegmentEn2d(pSommets[0],pSommets[4]);
        pPlan.dessinerSegmentEn2d(pSommets[1],pSommets[5]);
        pPlan.dessinerSegmentEn2d(pSommets[2],pSommets[6]);
        pPlan.dessinerSegmentEn2d(pSommets[3],pSommets[7]);
    }
    
     /**
     * Applique la transformation linéaire à l'ensemble des vecteurs du tableau
     * @param pVecteurs : tableau de Vecteur à transformer
     * @param pTransformation : transformation linéaire à appliquer
     */
    public static void appliquer(final Vecteur[] pVecteurs, final Matrice pTransformation) throws Exception
    {
       for(int i=0;i<pVecteurs.length;i++) {
           pVecteurs[i] = pTransformation.multiplicationVectorielle(pVecteurs[i]);
       }
    }
        
    /**
     * Procédure de test
     */
    public static void test()  throws Exception
    {
       
        // On crée un objet plan
        Plan p = new Plan();
        // On crée les sommets du cube
        Vecteur [] cube= creerSommets();
        
        // le test de l'homothetie
        //Matrice vMatriceHomothetie = new Matrice(3,3);
        //vMatriceHomothetie.setHomothetie(5);
        //appliquer(cube, vMatriceHomothetie);
        
        // le test de la rotation
        /*Matrice vMatriceRotation = new Matrice(3,3);
        vMatriceRotation.setRotation2d(Math.PI/4);
        appliquer(cube, vMatriceRotation);
        dessinerCube(p,cube);*/
        Matrice vMatriceResultante = new Matrice(3,3);
        vMatriceResultante = getRotation(0.01,0.01,0.01);
       int pas = 1000;
        double alpha = 2*Math.PI/pas;

        for(;;) {
        appliquer(cube, vMatriceResultante);
        // On affiche le cube
        dessinerCube(p,cube);
        }
        
        
        
    }
    public static Matrice getRotation(final double a1, final double a2, final double a3) {
      
        Matrice vMatrice = new Matrice(3,3);
        Matrice vMatriceTemp = new Matrice(3,3);
        vMatriceTemp.setRotation3dOx(a1);
        vMatrice = vMatriceTemp;
        vMatriceTemp.setRotation3dOy(a2);
       vMatrice = vMatrice.produitMatriciel(vMatriceTemp);
       vMatriceTemp.setRotation3dOz(a3);
       vMatrice = vMatrice.produitMatriciel(vMatriceTemp);
       return vMatrice;
    }
}
