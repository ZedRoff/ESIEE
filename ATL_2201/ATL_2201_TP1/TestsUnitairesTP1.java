 


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.*;
import java.util.Random;

/**
 * The test class TestsUnitairesTP1.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */

public class TestsUnitairesTP1
{

    private String nomClasse="VecteurEn2d";
    private Class<?> classe;
    private Constructor<?> ctr;
    private Method mGetX;
    private Method mGetY;
    private Random rnd = new Random();
    /**
     * Default constructor for test class TestsUnitairesTP1
     */
    public TestsUnitairesTP1()
    {

    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        try { classe = Class.forName(nomClasse); } catch (Exception e) {}
        if (classe!=null){
            try{ ctr=classe.getDeclaredConstructor(double.class,double.class);} catch (Exception e) {}
            try{ mGetX=classe.getDeclaredMethod("getX");} catch (Exception e) {}
            try{ mGetY=classe.getDeclaredMethod("getY");} catch (Exception e) {}
        }

    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void Attributs(){
        assert classe!=null : "Impossible de trouver la classe " +nomClasse ; 
        Field[] fl =classe.getDeclaredFields();
        assert fl.length==2: "La classe" + nomClasse +" doit posséder 2 attributs";
        for( Field f: fl)
        {
            assert double.class.equals(f.getType()): "L'attribut " + f.getName() + " doit être de type double";
            assert Modifier.isPrivate(f.getModifiers()): "L'attribut " + f.getName()  + " doit être privé";
        }
    }

    @Test
    public void Constructeur(){
        assert classe!=null : "Impossible de trouver la classe " +nomClasse ; 
        assert ctr!=null: "Impossible de trouver le constructeur naturel de " +nomClasse ; 
        assert Modifier.isPublic(ctr.getModifiers()): "Le constructeur de " + nomClasse + " doit être public";

    }

    @Test
    public void Accesseur(){
        testBase();
        Method [] accs={mGetX,mGetY};
        for(Method m : accs){
            assert Modifier.isPublic(m.getModifiers()): "L'accesseur " + m.getName() + " doit être public";
            assert m.getReturnType().equals(double.class): "L'accesseur " + m.getName() + " doit retourner un double";
        }
        double v1=rnd.nextDouble();
        double v2=rnd.nextDouble();
        Object inst = helpCreateInstance(v1,v2);
        assert helpGetX(inst).equals(v1): "L'accesseur getX n'a pas retourné la bonne valeur";
        assert helpGetY(inst).equals(v2): "L'accesseur getY n'a pas retourné la bonne valeur";
    }

    @Test
    public void Norme(){
        testBase(); 
        Method m= null;
        try{ m=classe.getDeclaredMethod("norme");} catch (Exception e) {}
        assert m!=null: "Impossible de trouver la méthode norme()" ; 
        assert Modifier.isPublic(m.getModifiers()): "La méthode " + m.getName() + " doit être public";
        assert m.getReturnType().equals(double.class): "La méthode " + m.getName() + " doit retourner un double";
        double v1=rnd.nextDouble();
        double v2=rnd.nextDouble();
        double res=Math.sqrt(v1*v1+v2*v2);
        Object inst = helpCreateInstance(v1,v2);

        Object r=null;
        try {r= m.invoke(inst);}
        catch (Exception e) {
            assert false : "L'appel à  la méthode "+ m.getName() +" a échoué. L'erreur retournée est : " +e.getCause();
        }
        assert r.equals(res): "La méthode "+ m.getName() +" n'a pas retourné la bonne valeur";

    }

    @Test
    public void MultiplicationScalaire(){
        testBase();
        Method m= null;
        try{ m=classe.getDeclaredMethod("multiplicationScalaire",double.class);} catch (Exception e) {}
        assert m!=null: "Impossible de trouver la méthode multiplicationScalaire(double)" ; 
        assert Modifier.isPublic(m.getModifiers()): "La méthode " + m.getName() + " doit être public";
        assert m.getReturnType().equals(Void.TYPE): "La méthode " + m.getName() + " ne doit rien retourner (void)";
        double v1=rnd.nextDouble();
        double v2=rnd.nextDouble();
        double v3=rnd.nextDouble();

        Object inst = helpCreateInstance(v1,v2);

        Object r=null;
        try {r= m.invoke(inst,v3);}
        catch (Exception e) {
            assert false : "L'appel à  la méthode "+ m.getName() +" a échoué. L'erreur retournée est : " +e.getCause();
        }
        assert helpGetX(inst).equals(v1*v3): "La méthode "+ m.getName() +" n'a pas correctement modifié l'abscisse du vecteur.";
        assert helpGetY(inst).equals(v2*v3): "La méthode "+ m.getName() +" n'a pas correctement modifié l'ordonnée du vecteur.";
    }

    @Test
    public void SommeVectorielle(){
        testBase();
        Method m= null;
        try{ m=classe.getDeclaredMethod("sommeVectorielle",classe);} catch (Exception e) {}
        assert m!=null: "Impossible de trouver la méthode sommeVectorielle(VecteurEn2d)" ; 
        assert Modifier.isPublic(m.getModifiers()): "La méthode " + m.getName() + " doit être public";
        assert m.getReturnType().equals(Void.TYPE): "La méthode " + m.getName() + " ne doit rien retourner (void)";
        double v1=rnd.nextDouble();
        double v2=rnd.nextDouble();
        double v3=rnd.nextDouble();
        double v4=rnd.nextDouble();
        Object i1 = helpCreateInstance(v1,v2);
        Object i2 = helpCreateInstance(v3,v4);

        try { m.invoke(i1,i2);}
        catch (Exception e) {
            assert false : "L'appel à  la méthode "+ m.getName() +" a échoué. L'erreur retournée est : " +e.getCause();
        }
        assert helpGetX(i1).equals(v1+v3): "La méthode "+ m.getName() +" n'a pas correctement modifié l'abscisse du vecteur.";
        assert helpGetY(i1).equals(v2+v4): "La méthode "+ m.getName() +" n'a pas correctement modifié l'ordonnée du vecteur.";
    }

    @Test
    public void ProduitScalaire(){
        testBase();
        Method m= null;
        try{ m=classe.getDeclaredMethod("produitScalaire",classe);} catch (Exception e) {}
        assert m!=null: "Impossible de trouver la méthode produitScalaire(VecteurEn2d)" ; 
        assert Modifier.isPublic(m.getModifiers()): "La méthode " + m.getName() + " doit être public";
        assert m.getReturnType().equals(double.class): "La méthode " + m.getName() + " doit retourner un double";
        double v1=rnd.nextDouble();
        double v2=rnd.nextDouble();
        double v3=rnd.nextDouble();
        double v4=rnd.nextDouble();
        Object i1 = helpCreateInstance(v1,v2);
        Object i2 = helpCreateInstance(v3,v4);
        Object res=null;
        try { res=m.invoke(i1,i2);}
        catch (Exception e) {
            assert false : "L'appel à  la méthode "+ m.getName() +" a échoué. L'erreur retournée est : " +e.getCause();
        }
        assert res.equals(v1*v3+v2*v4): "La méthode "+ m.getName() +" n'a pas correctement calculée le produis scalaire des deux vecteurs.";

    }

    @Test
    public void EstOrthogonal(){
        testBase();
        Method m= null;
        try{ m=classe.getDeclaredMethod("estOrthogonal",classe);} catch (Exception e) {}
        assert m!=null: "Impossible de trouver la méthode estOrthogonal(VecteurEn2d)" ; 
        assert Modifier.isPublic(m.getModifiers()): "La méthode " + m.getName() + " doit être public";
        assert m.getReturnType().equals(boolean.class): "La méthode " + m.getName() + " doit retourner une valeur booléenne";

        Object i1 = helpCreateInstance(1,2);
        Object i2 = helpCreateInstance(0,0);
        Object res=null;
        try { res=m.invoke(i1,i2);}
        catch (Exception e) {
            assert false : "L'appel à  la méthode "+ m.getName() +" a échoué. L'erreur retournée est : " +e.getCause();
        }
        assert res.equals(true): "La méthode "+ m.getName() +" n'a pas donnée une valeur correcte (les vecteurs (1,2) et (0,0) sont orthogonaux).";

        i1 = helpCreateInstance(3,8);
        i2 = helpCreateInstance(1,0);
        res=null;
        try { res=m.invoke(i1,i2);}
        catch (Exception e) {
            assert false : "L'appel à  la méthode "+ m.getName() +" a échoué. L'erreur retournée est : " +e.getCause();
        }
        assert res.equals(false): "La méthode "+ m.getName() +" n'a pas donnée une valeur correcte (les vecteurs (3,8) et (1,0) ne sont pas orthogonaux).";

        i1 = helpCreateInstance(2,4);
        i2 = helpCreateInstance(-2,1);
        res=null;
        try { res=m.invoke(i1,i2);}
        catch (Exception e) {
            assert false : "L'appel à  la méthode "+ m.getName() +" a échoué. L'erreur retournée est : " +e.getCause();
        }
        assert res.equals(true): "La méthode "+ m.getName() +" n'a pas donnée une valeur correcte (les vecteurs (2,4) et (-2,1) sont orthogonaux).";
    }

    @Test
    public void ObtenirVectOrthogonal(){
        testBase();
        Method m= null;
        try{ m=classe.getDeclaredMethod("obtenirVectOrthogonal");} catch (Exception e) {}
        assert m!=null: "Impossible de trouver la méthode obtenirVectOrthogonal()" ; 
        assert Modifier.isPublic(m.getModifiers()): "La méthode " + m.getName() + " doit être public";
        assert m.getReturnType().equals(classe): "La méthode " + m.getName() + " doit retourner un VecteurEn2d";
        double v1=rnd.nextDouble();
        double v2=rnd.nextDouble();

        Object i1 = helpCreateInstance(v1,v2);
        Object res=null;
        try { res=m.invoke(i1);}
        catch (Exception e) {
            assert false : "L'appel à  la méthode "+ m.getName() +" a échoué. L'erreur retournée est : " +e.getCause();
        }
        double v3=helpGetX(res);
        double v4=helpGetY(res);
        assert Math.abs(v1*v3+v2*v4)<0.00001: "Le vecteur donné par "+ m.getName() +" (" + v3 +"," +v4 +") n'est pas orthogonal au vecteur (" +v1 +"," + v2 +").";

    }

    
     @Test
    public void EstColineaire(){
        testBase();
        Method m= null;
        try{ m=classe.getDeclaredMethod("estColineaire",classe);} catch (Exception e) {}
        assert m!=null: "Impossible de trouver la méthode estColineaire(VecteurEn2d)" ; 
        assert Modifier.isPublic(m.getModifiers()): "La méthode " + m.getName() + " doit être public";
        assert m.getReturnType().equals(boolean.class): "La méthode " + m.getName() + " doit retourner une valeur booléenne";

        Object i1 = helpCreateInstance(1,2);
        Object i2 = helpCreateInstance(-4,-8);
        Object res=null;
        try { res=m.invoke(i1,i2);}
        catch (Exception e) {
            assert false : "L'appel à  la méthode "+ m.getName() +" a échoué. L'erreur retournée est : " +e.getCause();
        }
        assert res.equals(true): "La méthode "+ m.getName() +" n'a pas donnée une valeur correcte (les vecteurs (1,2) et (-4,-8) sont colinéaires).";

        i1 = helpCreateInstance(3,8);
        i2 = helpCreateInstance(1,0);
        res=null;
        try { res=m.invoke(i1,i2);}
        catch (Exception e) {
            assert false : "L'appel à  la méthode "+ m.getName() +" a échoué. L'erreur retournée est : " +e.getCause();
        }
        assert res.equals(false): "La méthode "+ m.getName() +" n'a pas donnée une valeur correcte (les vecteurs (3,8) et (1,0) ne sont pas colinéaires).";

        i1 = helpCreateInstance(1,0);
        i2 = helpCreateInstance(0,1);
        res=null;
        try { res=m.invoke(i1,i2);}
        catch (Exception e) {
            assert false : "L'appel à  la méthode "+ m.getName() +" a échoué. L'erreur retournée est : " +e.getCause();
        }
        assert res.equals(false): "La méthode "+ m.getName() +" n'a pas donnée une valeur correcte (les vecteurs (0,1) et (1,0) ne sont pas colinéaires).";
    }
    
    private void testBase(){
        assert classe!=null : "Impossible de trouver la classe " +nomClasse ; 
        assert ctr!=null: "Impossible de trouver le constructeur naturel de " +nomClasse ; 
        assert mGetX!=null: "Impossible de trouver l'accesseur getX()" ; 
        assert mGetY!=null: "Impossible de trouver l'accesseur getY()" ; 
    }

    private Object helpCreateInstance(double x, double y){
        Object inst=null;
        try { inst = ctr.newInstance(x,y); } 
        catch (Exception e) {
            assert false : "La création d'un nouveau vecteur a échouée lors de l'appel du constructeur. L'erreur retournée est : " +e.getCause();
        }
        return inst;
    }

    private Double helpGetX(Object i){
        Object res=null;
        try {res= mGetX.invoke(i);}
        catch (Exception e) {
            assert false : "L'appel à  l'accesseur getX a échoué. L'erreur retournée est : " +e.getCause();
        }
        return (Double)res;
    }

    private Double helpGetY(Object i){
        Object res=null;
        try {res= mGetY.invoke(i);}
        catch (Exception e) {
            assert false : "L'appel à  l'accesseur getY a échoué. L'erreur retournée est : " +e.getCause();
        }
        return (Double)res;
    }

}
