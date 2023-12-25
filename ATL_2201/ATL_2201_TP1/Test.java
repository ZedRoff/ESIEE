 


/**
 * D?crivez votre classe Test ici.
 *
 * @author (votre nom)
 * @version (un num?ro de version ou une date)
 */
public class Test
{
    // variables d'instance - remplacez l'exemple qui suit par le v?tre
    private int x;

    /**
     * Constructeur d'objets de classe Test
     */
    public Test()
    {
        // initialisation des variables d'instance
        x = 0;
    }

    /**
     * Un exemple de m?thode - remplacez ce commentaire par le v?tre
     *
     * @param  y   le param?tre de la m?thode
     * @return     la somme de x et de y
     */
    public int sampleMethod(int y)
    {
        // Ins?rez votre code ici
        return x + y;
    }
    
    public static void test() {
        VecteurEn2d a = new VecteurEn2d(-1,2);
        VecteurEn2d b = new VecteurEn2d(2,9);
        VecteurEn2d c = new VecteurEn2d(9,5.5);
        VecteurEn2d d = new VecteurEn2d(6,-1.5);
        
        VecteurEn2d AB = new VecteurEn2d(3,7);
        VecteurEn2d BC = new VecteurEn2d(7,5.-4);
        VecteurEn2d CD = new VecteurEn2d(-3,-7);
        VecteurEn2d DA = new VecteurEn2d(-7,3.5);
        
        VecteurEn2d AC = new VecteurEn2d(10,3.5);
        
        VecteurEn2d BD = new VecteurEn2d(4,-10.5);
        
        Plan p = new Plan();
        p.dessinerSegmentEn2d(a,b);
        p.dessinerSegmentEn2d(b,c);
        p.dessinerSegmentEn2d(c,d);
        p.dessinerSegmentEn2d(d,a);
        
        System.out.println("|AB| = "+AB.norme());
        System.out.println("|BC| = "+BC.norme());
        System.out.println("|CD| = "+CD.norme());
        System.out.println("|DA| = "+DA.norme());
        
        System.out.println("AC.BD = "+AC.produitScalaire(BD));
     
        System.out.println("AB colin?aire avec CD : "+AB.estColineaire(CD));
        System.out.println("BC colin?aire avec DA : "+BC.estColineaire(DA));
        
    }
}
