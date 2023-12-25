 


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
        assert fl.length==2: "La classe" + nomClasse +" doit poss�der 2 attributs";
        for( Field f: fl)
        {
            assert double.class.equals(f.getType()): "L'attribut " + f.getName() + " doit �tre de type double";
            assert Modifier.isPrivate(f.getModifiers()): "L'attribut " + f.getName()  + " doit �tre priv�";
        }
    }

    @Test
    public void Constructeur(){
        assert classe!=null : "Impossible de trouver la classe " +nomClasse ; 
        assert ctr!=null: "Impossible de trouver le constructeur naturel de " +nomClasse ; 
        assert Modifier.isPublic(ctr.getModifiers()): "Le constructeur de " + nomClasse + " doit �tre public";

    }

    @Test
    public void Accesseur(){
        testBase();
        Method [] accs={mGetX,mGetY};
        for(Method m : accs){
            assert Modifier.isPublic(m.getModifiers()): "L'accesseur " + m.getName() + " doit �tre public";
            assert m.getReturnType().equals(double.class): "L'accesseur " + m.getName() + " doit retourner un double";
        }
        double v1=rnd.nextDouble();
        double v2=rnd.nextDouble();
        Object inst = helpCreateInstance(v1,v2);
        assert helpGetX(inst).equals(v1): "L'accesseur getX n'a pas retourn� la bonne valeur";
        assert helpGetY(inst).equals(v2): "L'accesseur getY n'a pas retourn� la bonne valeur";
    }

    @Test
    public void Norme(){
        testBase(); 
        Method m= null;
        try{ m=classe.getDeclaredMethod("norme");} catch (Exception e) {}
        assert m!=null: "Impossible de trouver la m�thode norme()" ; 
        assert Modifier.isPublic(m.getModifiers()): "La m�thode " + m.getName() + " doit �tre public";
        assert m.getReturnType().equals(double.class): "La m�thode " + m.getName() + " doit retourner un double";
        double v1=rnd.nextDouble();
        double v2=rnd.nextDouble();
        double res=Math.sqrt(v1*v1+v2*v2);
        Object inst = helpCreateInstance(v1,v2);

        Object r=null;
        try {r= m.invoke(inst);}
        catch (Exception e) {
            assert false : "L'appel � la m�thode "+ m.getName() +" a �chou�. L'erreur retourn�e est : " +e.getCause();
        }
        assert r.equals(res): "La m�thode "+ m.getName() +" n'a pas retourn� la bonne valeur";

    }

    @Test
    public void MultiplicationScalaire(){
        testBase();
        Method m= null;
        try{ m=classe.getDeclaredMethod("multiplicationScalaire",double.class);} catch (Exception e) {}
        assert m!=null: "Impossible de trouver la m�thode multiplicationScalaire(double)" ; 
        assert Modifier.isPublic(m.getModifiers()): "La m�thode " + m.getName() + " doit �tre public";
        assert m.getReturnType().equals(Void.TYPE): "La m�thode " + m.getName() + " ne doit rien retourner (void)";
        double v1=rnd.nextDouble();
        double v2=rnd.nextDouble();
        double v3=rnd.nextDouble();

        Object inst = helpCreateInstance(v1,v2);

        Object r=null;
        try {r= m.invoke(inst,v3);}
        catch (Exception e) {
            assert false : "L'appel � la m�thode "+ m.getName() +" a �chou�. L'erreur retourn�e est : " +e.getCause();
        }
        assert helpGetX(inst).equals(v1*v3): "La m�thode "+ m.getName() +" n'a pas correctement modifi� l'abscisse du vecteur.";
        assert helpGetY(inst).equals(v2*v3): "La m�thode "+ m.getName() +" n'a pas correctement modifi� l'ordonn�e du vecteur.";
    }

    @Test
    public void SommeVectorielle(){
        testBase();
        Method m= null;
        try{ m=classe.getDeclaredMethod("sommeVectorielle",classe);} catch (Exception e) {}
        assert m!=null: "Impossible de trouver la m�thode sommeVectorielle(VecteurEn2d)" ; 
        assert Modifier.isPublic(m.getModifiers()): "La m�thode " + m.getName() + " doit �tre public";
        assert m.getReturnType().equals(Void.TYPE): "La m�thode " + m.getName() + " ne doit rien retourner (void)";
        double v1=rnd.nextDouble();
        double v2=rnd.nextDouble();
        double v3=rnd.nextDouble();
        double v4=rnd.nextDouble();
        Object i1 = helpCreateInstance(v1,v2);
        Object i2 = helpCreateInstance(v3,v4);

        try { m.invoke(i1,i2);}
        catch (Exception e) {
            assert false : "L'appel � la m�thode "+ m.getName() +" a �chou�. L'erreur retourn�e est : " +e.getCause();
        }
        assert helpGetX(i1).equals(v1+v3): "La m�thode "+ m.getName() +" n'a pas correctement modifi� l'abscisse du vecteur.";
        assert helpGetY(i1).equals(v2+v4): "La m�thode "+ m.getName() +" n'a pas correctement modifi� l'ordonn�e du vecteur.";
    }

    @Test
    public void ProduitScalaire(){
        testBase();
        Method m= null;
        try{ m=classe.getDeclaredMethod("produitScalaire",classe);} catch (Exception e) {}
        assert m!=null: "Impossible de trouver la m�thode produitScalaire(VecteurEn2d)" ; 
        assert Modifier.isPublic(m.getModifiers()): "La m�thode " + m.getName() + " doit �tre public";
        assert m.getReturnType().equals(double.class): "La m�thode " + m.getName() + " doit retourner un double";
        double v1=rnd.nextDouble();
        double v2=rnd.nextDouble();
        double v3=rnd.nextDouble();
        double v4=rnd.nextDouble();
        Object i1 = helpCreateInstance(v1,v2);
        Object i2 = helpCreateInstance(v3,v4);
        Object res=null;
        try { res=m.invoke(i1,i2);}
        catch (Exception e) {
            assert false : "L'appel � la m�thode "+ m.getName() +" a �chou�. L'erreur retourn�e est : " +e.getCause();
        }
        assert res.equals(v1*v3+v2*v4): "La m�thode "+ m.getName() +" n'a pas correctement calcul�e le produis scalaire des deux vecteurs.";

    }

    @Test
    public void EstOrthogonal(){
        testBase();
        Method m= null;
        try{ m=classe.getDeclaredMethod("estOrthogonal",classe);} catch (Exception e) {}
        assert m!=null: "Impossible de trouver la m�thode estOrthogonal(VecteurEn2d)" ; 
        assert Modifier.isPublic(m.getModifiers()): "La m�thode " + m.getName() + " doit �tre public";
        assert m.getReturnType().equals(boolean.class): "La m�thode " + m.getName() + " doit retourner une valeur bool�enne";

        Object i1 = helpCreateInstance(1,2);
        Object i2 = helpCreateInstance(0,0);
        Object res=null;
        try { res=m.invoke(i1,i2);}
        catch (Exception e) {
            assert false : "L'appel � la m�thode "+ m.getName() +" a �chou�. L'erreur retourn�e est : " +e.getCause();
        }
        assert res.equals(true): "La m�thode "+ m.getName() +" n'a pas donn�e une valeur correcte (les vecteurs (1,2) et (0,0) sont orthogonaux).";

        i1 = helpCreateInstance(3,8);
        i2 = helpCreateInstance(1,0);
        res=null;
        try { res=m.invoke(i1,i2);}
        catch (Exception e) {
            assert false : "L'appel � la m�thode "+ m.getName() +" a �chou�. L'erreur retourn�e est : " +e.getCause();
        }
        assert res.equals(false): "La m�thode "+ m.getName() +" n'a pas donn�e une valeur correcte (les vecteurs (3,8) et (1,0) ne sont pas orthogonaux).";

        i1 = helpCreateInstance(2,4);
        i2 = helpCreateInstance(-2,1);
        res=null;
        try { res=m.invoke(i1,i2);}
        catch (Exception e) {
            assert false : "L'appel � la m�thode "+ m.getName() +" a �chou�. L'erreur retourn�e est : " +e.getCause();
        }
        assert res.equals(true): "La m�thode "+ m.getName() +" n'a pas donn�e une valeur correcte (les vecteurs (2,4) et (-2,1) sont orthogonaux).";
    }

    @Test
    public void ObtenirVectOrthogonal(){
        testBase();
        Method m= null;
        try{ m=classe.getDeclaredMethod("obtenirVectOrthogonal");} catch (Exception e) {}
        assert m!=null: "Impossible de trouver la m�thode obtenirVectOrthogonal()" ; 
        assert Modifier.isPublic(m.getModifiers()): "La m�thode " + m.getName() + " doit �tre public";
        assert m.getReturnType().equals(classe): "La m�thode " + m.getName() + " doit retourner un VecteurEn2d";
        double v1=rnd.nextDouble();
        double v2=rnd.nextDouble();

        Object i1 = helpCreateInstance(v1,v2);
        Object res=null;
        try { res=m.invoke(i1);}
        catch (Exception e) {
            assert false : "L'appel � la m�thode "+ m.getName() +" a �chou�. L'erreur retourn�e est : " +e.getCause();
        }
        double v3=helpGetX(res);
        double v4=helpGetY(res);
        assert Math.abs(v1*v3+v2*v4)<0.00001: "Le vecteur donn� par "+ m.getName() +" (" + v3 +"," +v4 +") n'est pas orthogonal au vecteur (" +v1 +"," + v2 +").";

    }

    
     @Test
    public void EstColineaire(){
        testBase();
        Method m= null;
        try{ m=classe.getDeclaredMethod("estColineaire",classe);} catch (Exception e) {}
        assert m!=null: "Impossible de trouver la m�thode estColineaire(VecteurEn2d)" ; 
        assert Modifier.isPublic(m.getModifiers()): "La m�thode " + m.getName() + " doit �tre public";
        assert m.getReturnType().equals(boolean.class): "La m�thode " + m.getName() + " doit retourner une valeur bool�enne";

        Object i1 = helpCreateInstance(1,2);
        Object i2 = helpCreateInstance(-4,-8);
        Object res=null;
        try { res=m.invoke(i1,i2);}
        catch (Exception e) {
            assert false : "L'appel � la m�thode "+ m.getName() +" a �chou�. L'erreur retourn�e est : " +e.getCause();
        }
        assert res.equals(true): "La m�thode "+ m.getName() +" n'a pas donn�e une valeur correcte (les vecteurs (1,2) et (-4,-8) sont colin�aires).";

        i1 = helpCreateInstance(3,8);
        i2 = helpCreateInstance(1,0);
        res=null;
        try { res=m.invoke(i1,i2);}
        catch (Exception e) {
            assert false : "L'appel � la m�thode "+ m.getName() +" a �chou�. L'erreur retourn�e est : " +e.getCause();
        }
        assert res.equals(false): "La m�thode "+ m.getName() +" n'a pas donn�e une valeur correcte (les vecteurs (3,8) et (1,0) ne sont pas colin�aires).";

        i1 = helpCreateInstance(1,0);
        i2 = helpCreateInstance(0,1);
        res=null;
        try { res=m.invoke(i1,i2);}
        catch (Exception e) {
            assert false : "L'appel � la m�thode "+ m.getName() +" a �chou�. L'erreur retourn�e est : " +e.getCause();
        }
        assert res.equals(false): "La m�thode "+ m.getName() +" n'a pas donn�e une valeur correcte (les vecteurs (0,1) et (1,0) ne sont pas colin�aires).";
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
            assert false : "La cr�ation d'un nouveau vecteur a �chou�e lors de l'appel du constructeur. L'erreur retourn�e est : " +e.getCause();
        }
        return inst;
    }

    private Double helpGetX(Object i){
        Object res=null;
        try {res= mGetX.invoke(i);}
        catch (Exception e) {
            assert false : "L'appel � l'accesseur getX a �chou�. L'erreur retourn�e est : " +e.getCause();
        }
        return (Double)res;
    }

    private Double helpGetY(Object i){
        Object res=null;
        try {res= mGetY.invoke(i);}
        catch (Exception e) {
            assert false : "L'appel � l'accesseur getY a �chou�. L'erreur retourn�e est : " +e.getCause();
        }
        return (Double)res;
    }

}
