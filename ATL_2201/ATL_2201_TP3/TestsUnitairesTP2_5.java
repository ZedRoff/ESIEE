
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.extensions.TestSetup;
import java.lang.reflect.*;
import java.util.*;

/**
 * The test class TestsUnitairesTP2_5.
 *
 * @author  Benjamin Perret & Romain Negrel
 * @version 2023/2024
 */
public class TestsUnitairesTP2_5 
{

    private Random rnd = new Random(1);

    @Before
    public void setUp()
    {
    }


    @After
    public void tearDown()
    {
    }

    @Test
    public void ClasseVecteur(){
        VecteurProxy.TestBase();
        for(int i=1;i<5;i++)
        {
            double [] op = randomTab(i);
            VecteurProxy v = new VecteurProxy(op);
            assert v.size()==i:"L'accesseur getDimension n'a pas retourné la bonne valeur";
            assert v.equals(op):"Les accesseurs getCoordonnee et setCoordonnee ne fonctionnent pas correctement";
        }
    }

    @Test
    public void Dessiner(){
        VecteurProxy.TestBase(); 

        Object [] refs= {new VecteurProxy(2,1),new VecteurProxy(3,2,1),new VecteurProxy(4,3,2,1)};
        Object [][] inArgs= {{},{},{}};
        Object [] expX = {2.,3.,4.};
        Object [] expY = {1.,2.,3.};

        testMethod(VecteurProxy.classeVec, "getX", double.class, refs, inArgs, expX,false);
        testMethod(VecteurProxy.classeVec, "getY", double.class, refs, inArgs, expY,false);
    }

    @Test
    public void MultiplicationScalaire(){
        VecteurProxy.TestBase();
        Object [] refs= {new VecteurProxy(5,4,3,2,1,0)};
        Object [][] inArgs= {{2.}};
        Object [] exp = {new VecteurProxy(10,8,6,4,2,0)};

        testMethod(VecteurProxy.classeVec, "multiplicationScalaire", void.class, refs, inArgs, exp,true, double.class);
    }

    @Test
    public void Norme(){
        VecteurProxy.TestBase();

        Object [] refs= new Object[5];
        Object [][] inArgs= new Object[refs.length][];
        Object [] exp =new Object[refs.length];
        for(int i=0;i<refs.length;i++)
        {
            VecteurProxy v=new VecteurProxy(randomTab(i*2+1));
            refs[i]=v;
            inArgs[i]=new Object[]{};
            exp[i]=helpNrm(v.get());
        }
        testMethod(VecteurProxy.classeVec, "norme", double.class, refs, inArgs, exp,false);

    }

    @Test
    public void Normalisation(){

        VecteurProxy.TestBase();

        Object [] refs= new Object[5];
        Object [][] inArgs= new Object[refs.length][];
        Object [] exp =new Object[refs.length];
        for(int i=0;i<refs.length;i++)
        {
            double [] t=randomTab(i*2+1);
            VecteurProxy v=new VecteurProxy(t);
            refs[i]=v;
            inArgs[i]=new Object[]{};
            double k=helpNrm(v.get());
            for(int j=0;j<t.length;j++)
                t[j]/=k;
            exp[i]=new VecteurProxy(t);
        }
        testMethod(VecteurProxy.classeVec, "normaliser", void.class, refs, inArgs, exp,true);
    }

    @Test
    public void SommeVectorielle(){
        VecteurProxy.TestBase();

        Object [] refs= new Object[5];
        Object [][] inArgs= new Object[refs.length][];
        Object [] exp =new Object[refs.length];
        for(int i=0;i<refs.length;i++)
        {
            double [] t1=randomTab(i*2+1);
            double [] t2=randomTab(i*2+1);
            double [] t3=new double[i*2+1];
            VecteurProxy v1=new VecteurProxy(t1);
            VecteurProxy v2=new VecteurProxy(t2);
            refs[i]=v1;
            inArgs[i]=new Object[]{v2};

            for(int j=0;j<t3.length;j++)
                t3[j]=t1[j]+t2[j];
            exp[i]=new VecteurProxy(t3);
        }
        testMethod(VecteurProxy.classeVec, "sommeVectorielle", void.class, refs, inArgs, exp,true,VecteurProxy.classeVec);
    }

    @Test
    public void ProduitScalaire(){
        VecteurProxy.TestBase();

        Object [] refs= new Object[5];
        Object [][] inArgs= new Object[refs.length][];
        Object [] exp =new Object[refs.length];
        for(int i=0;i<refs.length;i++)
        {
            double [] t1=randomTab(i*2+1);
            double [] t2=randomTab(i*2+1);

            VecteurProxy v1=new VecteurProxy(t1);
            VecteurProxy v2=new VecteurProxy(t2);
            refs[i]=v1;
            inArgs[i]=new Object[]{v2};
            double k=0;
            for(int j=0;j<t1.length;j++)
                k+=t1[j]*t2[j];
            exp[i]=k;
        }
        testMethod(VecteurProxy.classeVec, "produitScalaire", double.class, refs, inArgs, exp,false,VecteurProxy.classeVec);
    }

    @Test
    public void ProduitVectoriel(){
        VecteurProxy.TestBase();

        Object [] refs= new Object[5];
        Object [][] inArgs= new Object[refs.length][];
        Object [] exp =new Object[refs.length];
        refs[0]=new VecteurProxy(1,2);
        inArgs[0]=new Object[]{new VecteurProxy(2,2)};
        exp[0]=null;
        for(int i=1;i<refs.length;i++)
        {
            double [] op1=randomTab(3);
            double [] op2=randomTab(3);
            double [] r = {op1[1]*op2[2]-op2[1]*op1[2],op1[2]*op2[0]-op1[0]*op2[2],op1[0]*op2[1]-op1[1]*op2[0]};
            VecteurProxy v1=new VecteurProxy(op1);
            VecteurProxy v2=new VecteurProxy(op2);
            refs[i]=v1;
            inArgs[i]=new Object[]{v2};
            exp[i]=new VecteurProxy(r);;
        }
        testMethod(VecteurProxy.classeVec, "produitVectoriel3d", VecteurProxy.classeVec, refs, inArgs, exp,false,VecteurProxy.classeVec);
    }

    @Test
    public void EstOrthogonal(){
        VecteurProxy.TestBase();

        Object [] refs= { new VecteurProxy(0,1), new VecteurProxy(1,1,0), new VecteurProxy(2,0,1,4)};
        Object [][] inArgs= { {new VecteurProxy(2,0)}, {new VecteurProxy(0,-1,2)},{new VecteurProxy(0,2,-8,2)}};
        Object [] exp = {true, false, true};

        testMethod(VecteurProxy.classeVec, "estOrthogonal", boolean.class, refs, inArgs, exp,false,VecteurProxy.classeVec);
    }

    @Test
    public void EstColineaire(){
        VecteurProxy.TestBase();

        Object [] refs= { new VecteurProxy(0,1), new VecteurProxy(1,1,0), new VecteurProxy(2,0,1,4)};
        Object [][] inArgs= { {new VecteurProxy(0,0)}, {new VecteurProxy(-4,-4,0)},{new VecteurProxy(0,2,0,2)}};
        Object [] exp = {true, true, false};

        testMethod(VecteurProxy.classeVec, "estColineaire", boolean.class, refs, inArgs, exp,false,VecteurProxy.classeVec);
    }

    @Test
    public void EstCoplanaire3d(){
        VecteurProxy.TestBase();

        Object [] refs= { new VecteurProxy(0,5,0), new VecteurProxy(1,1,1), new VecteurProxy(2,0,1)};
        Object [][] inArgs= { {new VecteurProxy(4,0,0),new VecteurProxy(8,8,0)}, {new VecteurProxy(0,1,0),new VecteurProxy(1,0,1)},{new VecteurProxy(0,2,0),new VecteurProxy(0,2,5)}};
        Object [] exp = {true, true, false};

        testMethod(VecteurProxy.classeVec, "estCoplanaire3d", boolean.class, refs, inArgs, exp,false,VecteurProxy.classeVec,VecteurProxy.classeVec);
    }

    @Test
    public void ClasseMatrice(){
        MatriceProxy.TestBase();

        for(int i=1;i<5;i++)
            for(int j=1;j<5;j++)
            {
                double [][] op = randomTab(i,j);
                MatriceProxy m = new MatriceProxy(op);

                assert m.sizeL()==i:"L'accesseur getNbLignes n'a pas retourné la bonne valeur";
                assert m.sizeC()==j:"L'accesseur getNbColonnes n'a pas retourné la bonne valeur";
                assert m.equals(op): "Les accesseurs getCoefficients et setCoefficients ne fonctionnent pas correctement";
        }
    }

    @Test
    public void GetVecteurLigne(){
        MatriceProxy.TestBase();
        for(int i=1;i<5;i++)
            for(int j=1;j<5;j++)
            {
                double [][] op = randomTab(i,j);
                MatriceProxy m= new MatriceProxy(op);
                Object [] refs= new Object[i];
                Object [][] inArgs= new Object[i][];
                Object [] exp = new Object[i];
                for(int z=0;z<i;z++)
                {
                    refs[z]=m;
                    inArgs[z]=new Object[]{z};
                    exp[z]=new VecteurProxy(op[z]);
                }
                testMethod(MatriceProxy.classeM, "getVecteurLigne", VecteurProxy.classeVec, refs, inArgs, exp,false,int.class);
        }
    }

    @Test
    public void MutiplicationVectorielle(){
        MatriceProxy.TestBase();
        for(int i=1;i<5;i++)
            for(int j=1;j<5;j++)
            {
                double [][] op = randomTab(i,j);
                MatriceProxy m= new MatriceProxy(op);

                double [] op1 = randomTab(j);
                VecteurProxy v=new VecteurProxy(op1);

                double [] z = helpMV(op,op1);

                Object [] refs= {m};
                Object [][] inArgs= {{v}};
                Object [] exp = {new VecteurProxy(z)};

                testMethod(MatriceProxy.classeM, "multiplicationVectorielle", VecteurProxy.classeVec, refs, inArgs, exp,false,VecteurProxy.classeVec);
        }

    }

    @Test
    public void Identite(){
        MatriceProxy.TestBase();
        Object [] refs= {new MatriceProxy(randomTab(5,5))};
        Object [][] inArgs= {{}};
        Object [] exp = {new MatriceProxy(new double[][]{
                        {1,0,0,0,0},{0,1,0,0,0},{0,0,1,0,0},{0,0,0,1,0},{0,0,0,0,1}
                    })};

        testMethod(MatriceProxy.classeM, "setIdentite", void.class, refs, inArgs, exp,true);
    }

    @Test
    public void SetHomothetie(){
        MatriceProxy.TestBase();
        Object [] refs= {new MatriceProxy(randomTab(5,5))};
        Object [][] inArgs= {{2.0}};
        Object [] exp = {new MatriceProxy(new double[][]{
                        {2,0,0,0,0},{0,2,0,0,0},{0,0,2,0,0},{0,0,0,2,0},{0,0,0,0,2}
                    })};

        testMethod(MatriceProxy.classeM, "setHomothetie", void.class, refs, inArgs, exp,true,double.class);
    }

    @Test
    public void SetSymetrieCentrale(){
        MatriceProxy.TestBase();
        Object [] refs= {new MatriceProxy(randomTab(5,5))};
        Object [][] inArgs= {{}};
        Object [] exp = {new MatriceProxy(new double[][]{
                        {-1,0,0,0,0},{0,-1,0,0,0},{0,0,-1,0,0},{0,0,0,-1,0},{0,0,0,0,-1}
                    })};

        testMethod(MatriceProxy.classeM, "setSymetrieCentrale", void.class, refs, inArgs, exp,true);
    }

    @Test
    public void SetReflexionOx(){
        MatriceProxy.TestBase();
        Object [] refs= {new MatriceProxy(randomTab(2,2))};
        Object [][] inArgs= {{}};
        Object [] exp = {new MatriceProxy(new double[][]{
                        {1,0},{0,-1}
                    })};

        testMethod(MatriceProxy.classeM, "setReflexionOx", void.class, refs, inArgs, exp,true);
    }

    @Test
    public void SetReflexionOxOy(){
        MatriceProxy.TestBase();
        Object [] refs= {new MatriceProxy(randomTab(3,3))};
        Object [][] inArgs= {{}};
        Object [] exp = {new MatriceProxy(new double[][]{
                        {1,0,0},{0,1,0},{0,0,-1}
                    })};

        testMethod(MatriceProxy.classeM, "setReflexionOxOy", void.class, refs, inArgs, exp,true);
    }

    @Test
    public void SetRotation2d(){
        MatriceProxy.TestBase();
        Object [] refs= {new MatriceProxy(randomTab(2,2))};
        Object [][] inArgs= {{2.0}};
        Object [] exp = {new MatriceProxy(new double[][]{
                        {Math.cos(2.0),-Math.sin(2.0)},  {Math.sin(2.0),Math.cos(2.0)}
                    })};

        testMethod(MatriceProxy.classeM, "setRotation2d", void.class, refs, inArgs, exp,true,double.class);
    }

    @Test
    public void SetRotation3dOx(){
        MatriceProxy.TestBase();
        Object [] refs= {new MatriceProxy(randomTab(3,3))};
        Object [][] inArgs= {{2.0}};
        Object [] exp = {new MatriceProxy(new double[][]{
                        {1,0,0},{ 0,Math.cos(2.0),-Math.sin(2.0)},{  0,Math.sin(2.0),Math.cos(2.0)}
                    })};

        testMethod(MatriceProxy.classeM, "setRotation3dOx", void.class, refs, inArgs, exp,true,double.class);
    }

    @Test
    public void GetVecteurColonne(){
        MatriceProxy.TestBase();
        for(int i=1;i<5;i++)
            for(int j=1;j<5;j++)
            {
                double [][] op = randomTab(i,j);
                MatriceProxy m= new MatriceProxy(op);
                Object [] refs= new Object[j];
                Object [][] inArgs= new Object[j][];
                Object [] exp = new Object[j];
                double [] c = new double[i];
                for(int z=0;z<j;z++)
                {
                    refs[z]=m;
                    inArgs[z]=new Object[]{z};
                    for(int k=0;k<i;k++)
                        c[k]=op[k][z];
                    exp[z]=new VecteurProxy(c);
                }
                testMethod(MatriceProxy.classeM, "getVecteurColonne", VecteurProxy.classeVec, refs, inArgs, exp,false,int.class);
        }
    }

    @Test
    public void ProduitMatriciel(){
        MatriceProxy.TestBase();

        Object [] refs= {new MatriceProxy(new double[][]{{2,1,1},{4,2,3}}), new MatriceProxy(new double[][]{{2,1,1},{4,2,3}})};
        Object [][] inArgs= {{new MatriceProxy(new double[][]{{-1,2},{3,2},{-1,2}})},{new MatriceProxy(new double[][]{{2,1,1},{4,2,3}})}};
        Object [] exp =  {new MatriceProxy(new double[][]{{0,8},{-1,18}}),null};

        testMethod(MatriceProxy.classeM, "produitMatriciel", MatriceProxy.classeM, refs, inArgs, exp,false,MatriceProxy.classeM);
    }

    @Test
    public void NormaliseHomogene(){
        VecteurProxy.TestBase();
        Object [] refs= {new VecteurProxy(-4,2,2)};
        Object [][] inArgs= {{}};
        Object [] exp = {new VecteurProxy(-2,1,1)};

        testMethod(VecteurProxy.classeVec, "normaliseHomogene", void.class, refs, inArgs, exp,true);
    }

    @Test
    public void SetHomothetieHomogene3d(){
        MatriceProxy.TestBase();
        Object [] refs= {new MatriceProxy(randomTab(4,4))};
        Object [][] inArgs= {{2.0}};
        Object [] exp = {new MatriceProxy(new double[][]{
                        {2,0,0,0},{  0,2,0,0},{  0,0,2,0},{  0,0,0,1}
                    })};

        testMethod(MatriceProxy.classeM, "setHomothetieHomogene3d", void.class, refs, inArgs, exp,true,double.class);
    }

    @Test
    public void SetTranslationHomogene3d(){
        MatriceProxy.TestBase();
        Object [] refs= {new MatriceProxy(randomTab(4,4))};
        Object [][] inArgs= {{new VecteurProxy(new double[]{-4,2,2})}};
        Object [] exp = {new MatriceProxy(new double[][]{
                        {1,0,0,-4},  {0,1,0,2},  {0,0,1,2},  {0,0,0,1}
                    })};

        testMethod(MatriceProxy.classeM, "setTranslationHomogene3d", void.class, refs, inArgs, exp,true,VecteurProxy.classeVec);
    }

    @Test
    public void SetRotationOxHomogene3d(){
        MatriceProxy.TestBase();
        Object [] refs= {new MatriceProxy(randomTab(4,4))};
        Object [][] inArgs= {{2.0}};
        Object [] exp = {new MatriceProxy(new double[][]{
                        {1,0,0,0},  { 0,Math.cos(2.0),-Math.sin(2.0),0},  {  0,Math.sin(2.0),Math.cos(2.0),0},  { 0,0,0,1}
                    })};

        testMethod(MatriceProxy.classeM, "setRotationOxHomogene3d", void.class, refs, inArgs, exp,true,double.class);
    }

    @Test
    public void SetRotationOyHomogene3d(){
        MatriceProxy.TestBase();
        Object [] refs= {new MatriceProxy(randomTab(4,4))};
        Object [][] inArgs= {{2.0}};
        Object [] exp = {new MatriceProxy(new double[][]{
                        {Math.cos(2.0),0,Math.sin(2.0),0},  { 0,1,0,0},  {  -Math.sin(2.0),0,Math.cos(2.0),0},  { 0,0,0,1}
                    })};

        testMethod(MatriceProxy.classeM, "setRotationOyHomogene3d", void.class, refs, inArgs, exp,true,double.class);
    }

    @Test
    public void SetRotationOzHomogene3d(){
        MatriceProxy.TestBase();
        Object [] refs= {new MatriceProxy(randomTab(4,4))};
        Object [][] inArgs= {{2.0}};
        Object [] exp = {new MatriceProxy(new double[][]{
                        {Math.cos(2.0),-Math.sin(2.0),0,0},  {   Math.sin(2.0),Math.cos(2.0),0,0},  { 0,0,1,0},  {  0,0,0,1}
                    })};

        testMethod(MatriceProxy.classeM, "setRotationOzHomogene3d", void.class, refs, inArgs, exp,true,double.class);
    }

    @Test
    public void SetProjectionOrthoOxyHomogene3d(){
        MatriceProxy.TestBase();
        Object [] refs= {new MatriceProxy(randomTab(3,4))};
        Object [][] inArgs= {{}};
        Object [] exp = {new MatriceProxy(new double[][]{
                        {1,0,0,0},  {  0,1,0,0},  {  0,0,0,1}
                    })};

        testMethod(MatriceProxy.classeM, "setProjectionOrthoOxyHomogene3d", void.class, refs, inArgs, exp,true);
    }

    @Test
    public void SetProjectionOrthoOxzHomogene3d(){
        MatriceProxy.TestBase();
        Object [] refs= {new MatriceProxy(randomTab(3,4))};
        Object [][] inArgs= {{}};
        Object [] exp = {new MatriceProxy(new double[][]{
                        {1,0,0,0},  {  0,0,1,0},  {  0,0,0,1}
                    })};

        testMethod(MatriceProxy.classeM, "setProjectionOrthoOxzHomogene3d", void.class, refs, inArgs, exp,true);
    }

    @Test
    public void SetProjectionOrthoOyzHomogene3d(){
        MatriceProxy.TestBase();
        Object [] refs= {new MatriceProxy(randomTab(3,4))};
        Object [][] inArgs= {{}};
        Object [] exp = {new MatriceProxy(new double[][]{
                        {0,1,0,0},  {  0,0,1,0},  {  0,0,0,1}
                    })};

        testMethod(MatriceProxy.classeM, "setProjectionOrthoOyzHomogene3d", void.class, refs, inArgs, exp,true);
    }

    @Test
    public void SetProjectionPerspectiveOxyHomogene3d(){
        MatriceProxy.TestBase();
        Object [] refs= {new MatriceProxy(randomTab(3,4))};
        Object [][] inArgs= {{4.0}};
        Object [] exp = {new MatriceProxy(new double[][]{
                        {1,0,0,0},  {  0,1,0,0},  {  0,0,1.0/4.0,0}
                    })};

        testMethod(MatriceProxy.classeM, "setProjectionPerspectiveOxyHomogene3d", void.class, refs, inArgs, exp,true,double.class);
    }

    private double helpNrm(double [] op)
    {
        double r=0;
        for(Double d:op) r+=d*d;
        return Math.sqrt(r);
    }

    private double [] helpMV(double [][] op,double [] op1)
    {
        double [] a=new double[op.length];
        for(int i=0;i<a.length;i++)
            for(int j=0;j<op1.length;j++)
                a[i]+=op[i][j]*op1[j];
        return a;
    }

    private void testMethod(Class<?> c, String mName, Class<?> returnType, Object [] refs, Object [][] inArgs, Object [] expectedResults, boolean inplace, Class<?> ... argTypes)
    {
        Method m=getMethod(c,mName, returnType, argTypes);
        for(int i=0;i<refs.length;i++)
        {
            Object [] args = new Object[inArgs[i].length];
            for(int j=0;j<args.length;j++) 
            {
                if( inArgs[i][j] instanceof Proxy) // should test for clonable , serializable...
                    args[j]= ((Proxy)inArgs[i][j]).copy().o;
                else args[j]=inArgs[i][j];
            }

            Object r;
            if( refs[i] instanceof Proxy)
                r=((Proxy)refs[i]).copy().o;
            else r=refs[i];

            Object res=invoke(m,r,args);

            for(int j=0;j<inArgs[i].length;j++)
            {
                assert inArgs[i][j].equals(args[j]):"La méthode " + m.getName() + " ne doit pas modifier ses paramètres.";
            }
            if(inplace)
            {
                res=r;
            }else{
                assert refs[i].equals(r):"La méthode " + m.getName() + " ne doit pas modifier l'instance courante.";
            }

            if (expectedResults[i]!=null)
            {
                assert expectedResults[i].equals(res): "Le résultat de la méthode " + m.getName() + " est incorrect.";    
            }
            else{
                assert res==null: "Le résultat de la méthode " + m.getName() + " est incorrect.";    
            }
        }
    }

    private static Method getMethod(Class<?> c, String mName, Class<?> returnType, Class<?> ... args)
    {
        Method m = null;
        try{ m=c.getDeclaredMethod(mName,args);} catch (Exception e) {}
        assert m!=null: "Impossible de trouver la méthode " + mName + "(" + Arrays.toString(args) + ") de la classe " + c.getName() ; 
        checkProto(m,returnType);
        return m;
    }

    private static void checkProto(Method m, Class<?> returnType)
    {
        assert Modifier.isPublic(m.getModifiers()): "La méthode " + m.getName() + " doit être publique";
        assert m.getReturnType().equals(returnType): "La méthode " + m.getName() + " doit retourner un " + returnType;
    }

    private static boolean isEqual(double [][] t1, double [][] t2)
    {
        if(t1.length!=t2.length)
            return false;

        for(int i=0;i<t1.length;i++)
        {
            if(!isEqual(t1[i],t2[i]))
                return false;
        }
        return true;
    }

    private static boolean isEqual(double [] t1, double [] t2)
    {
        if(t1.length!=t2.length)
            return false;

        for(int i=0;i<t1.length;i++)
        {
            if(!isEqual(t1[i],t2[i]))
                return false;
        }
        return true;
    }

    private static boolean isEqual(double d1, double d2)
    {
        return Math.abs(d1-d2)<0.000001;   
    }

    private double [] randomTab(int size)
    {
        double [] r=new double[size];
        for(int i=0;i<size;i++)
            r[i]=rnd.nextDouble();
        return r;
    }

    private double [][] randomTab(int l, int c)
    {
        double [][] r=new double[l][c];
        for(int i=0;i<l;i++)
            for(int j=0;j<c;j++)
                r[i][j]=rnd.nextDouble();
        return r;
    }

    private static Object invoke(Method m, Object ref, Object ... args)
    {
        Object res=null;
        try {res= m.invoke(ref,args);}
        catch (Exception e) {
            assert false : "L'appel à la méthode " + m.getName() + " de  la classe " + ref.getClass().getName() +" a échoué. L'erreur retournée est : " + ((e.getCause()!=null)?e.getCause():e);
        }
        return res;
    }

    private static abstract class Proxy{
        Object o;

        Proxy(Object o)
        {
            this.o=o;
        }

        abstract Proxy copy();
    }

    private static class VecteurProxy extends Proxy{

        static String nomClasseVec="Vecteur";
        static Class<?> classeVec;
        static Constructor<?> ctrVec;
        static Method getVec;
        static Method setVec;
        static Method sizeVec;

        static{
            try { classeVec = Class.forName(nomClasseVec); } catch (Exception e) {}
            if (classeVec!=null){
                try{ ctrVec=classeVec.getDeclaredConstructor(int.class);} catch (Exception e) {}
                try{ getVec=classeVec.getDeclaredMethod("getCoordonnee",int.class);} catch (Exception e) {}
                try{ sizeVec=classeVec.getDeclaredMethod("getDimension");} catch (Exception e) {}
                try{ setVec=classeVec.getDeclaredMethod("setCoordonnee",int.class,double.class);} catch (Exception e) {}
            }
        }

        static void TestBase(){
            assert classeVec!=null : "Impossible de trouver la classe " +nomClasseVec ; 
            assert ctrVec!=null: "Impossible de trouver le constructeur de " +nomClasseVec ; 
            assert Modifier.isPublic(ctrVec.getModifiers()): "Le constructeur de " + nomClasseVec + " doit être public";
            assert getVec!=null: "Impossible de trouver l'accesseur getCoordonnee(int) de la classe " +nomClasseVec ; 
            checkProto(getVec,double.class);
            assert sizeVec!=null: "Impossible de trouver l'accesseur getDimension() de la classe " +nomClasseVec;
            checkProto(sizeVec,int.class);
            assert setVec!=null: "Impossible de trouver l'accesseur setCoordonnee(int,double) de la classe " +nomClasseVec ; 
            checkProto(setVec,void.class);
        }

        VecteurProxy(int n)
        {
            super(null);
            try { o = ctrVec.newInstance(n); } 
            catch (Exception e) {
                assert false : "La création d'un nouveau vecteur a échoué lors de l'appel du constructeur. L'erreur retournée est : " +e.getCause();
            }
        }

        VecteurProxy(double ... vals)
        {
            this(vals.length);
            for(int i=0;i<vals.length;i++)
            {
                set(i,vals[i]);
            }
        }

        VecteurProxy(Object o)
        {
            super(o);
        }

        double get(int i){
            Object res=invoke(getVec, o,i);
            return (Double)res;
        }

        int size(){
            Object res=invoke(sizeVec, o);;
            return (Integer)res;
        }

        double[] get(){
            int s=size();
            double [] r= new double[s];
            for(int i=0;i<s;i++)
            {
                r[i]=get(i);
            }
            return r;
        }

        void set(int i, double v){
            invoke(setVec, o, i, v);
        }

        VecteurProxy copy()
        {
            return new VecteurProxy(get());
        }

        public boolean equals(Object a)
        {
            if(a==null)
                return false;

            if(a.getClass().equals(classeVec))
            {
                return isEqual(get(),(new VecteurProxy(a)).get());
            }

            if(a instanceof VecteurProxy)
            {
                return isEqual(get(),((VecteurProxy)a).get());
            }

            if(a instanceof double[])
            {
                return isEqual(get(),(double [])a);
            }

            return false;
        }
    }

    private static class MatriceProxy extends Proxy{

        static String nomClasseM="Matrice";
        static Class<?> classeM;
        static Constructor<?> ctrM;
        static Method getM;
        static Method setM;
        static Method sizeCM;
        static Method sizeLM;

        static{
            try { classeM = Class.forName(nomClasseM); } catch (Exception e) {}
            if (classeM!=null){
                try{ ctrM=classeM.getDeclaredConstructor(int.class,int.class);} catch (Exception e) {}
                try{ getM=classeM.getDeclaredMethod("getCoefficient",int.class,int.class);} catch (Exception e) {}
                try{ sizeCM=classeM.getDeclaredMethod("getNbColonnes");} catch (Exception e) {}
                try{ sizeLM=classeM.getDeclaredMethod("getNbLignes");} catch (Exception e) {}
                try{ setM=classeM.getDeclaredMethod("setCoefficient",int.class,int.class,double.class);} catch (Exception e) {}
            }

        }

        static void TestBase(){

            assert classeM!=null : "Impossible de trouver la classe " +nomClasseM ; 
            assert ctrM!=null: "Impossible de trouver le constructeur de " +nomClasseM ; 
            assert getM!=null: "Impossible de trouver l'accesseur getCoefficient(int,int) de la classe " +nomClasseM ; 
            checkProto(getM,double.class);
            assert sizeCM!=null: "Impossible de trouver l'accesseur getNbColonnes() de la classe " +nomClasseM;
            checkProto(sizeCM,int.class);
            assert sizeLM!=null: "Impossible de trouver l'accesseur getNbLignes() de la classe " +nomClasseM;
            checkProto(sizeLM,int.class);
            assert setM!=null: "Impossible de trouver l'accesseur setCoefficient(int,int,double) de la classe " +nomClasseM ; 
            checkProto(setM,void.class);

        }

        MatriceProxy(int l, int c)
        {
            super(null);
            try { o = ctrM.newInstance(l,c); } 
            catch (Exception e) {
                assert false : "La création d'une nouvelle matrice a échoué lors de l'appel du constructeur. L'erreur retournée est : " +e.getCause();
            }
        }

        MatriceProxy(double [][] vals)
        {
            this(vals.length,vals[0].length);
            for(int i=0;i<vals.length;i++)
                for(int j=0;j<vals[i].length;j++)
                {
                    set(i,j,vals[i][j]);
            }
        }

        MatriceProxy(Object o)
        {
            super(o);
        }

        double get(int l, int c){
            Object res=invoke(getM, o,l,c);
            return (Double)res;
        }

        int sizeL(){
            Object res=invoke(sizeLM, o);;
            return (Integer)res;
        }

        int sizeC(){
            Object res=invoke(sizeCM, o);;
            return (Integer)res;
        }

        double[][] get(){
            int l=sizeL();
            int c=sizeC();
            double [][] r= new double[l][c];

            for(int i=0;i<l;i++)
                for(int j=0;j<c;j++)
                {
                    r[i][j]=get(i,j);
            }
            return r;
        }

        void set(int l, int c, double v){
            invoke(setM, o, l, c, v);
        }

        MatriceProxy copy()
        {
            return new MatriceProxy(get());
        }

        public boolean equals(Object a)
        {
            if(a==null)
                return false;

            if(a.getClass().equals(classeM))
            {
                return isEqual(get(),(new MatriceProxy(a)).get());
            }

            if(a instanceof MatriceProxy)
            {
                return isEqual(get(),((MatriceProxy)a).get());
            }

            if(a instanceof double[][])
            {
                return isEqual(get(),(double [][])a);
            }

            return false;
        }
    }
}
