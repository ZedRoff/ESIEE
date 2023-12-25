import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.lang.Class;
import java.lang.reflect.Method;
import java.lang.NoSuchMethodException;
import java.lang.SecurityException;
import java.lang.Exception;
/**
 * Classe permettant de cr�er une fen�tre repr�sentant un plan.<br>
 * Le plan affich� s'�tend des coordonn�es [-6,6] sur les axes x et y.<br>
 * Il est possible de dessiner des points, des fl�ches et des segments sur un plan.<br>
 * 
 * 
 * @author Benjamin Perret
 * @version 13/09/2012
 */
public class Plan 
{
    // min et max du rep�re monde
    private double xMax=11;
    private double yMax=11;
    
    // largeur et hauteur de la zone de dessin en pixel
    private int largeurPix=600;
    private int hauteurPix=600;

    // buffer de dessin et graphics sur ce buffer
    private BufferedImage buff;
    private Graphics2D gBuff;
    
    // faut-il dessiner le buffer
    private boolean repere=true;
    
    // quelques tests basiques
    private boolean debug=false;
   
    //zone de dessin
    private MonJPanel panel;
  
    
    /**
     * Cr�� un plan et l'affiche dans une fen�tre
     */
    public Plan()
    {
        JFrame frame = new JFrame("Plan2D");
        frame.setSize(largeurPix,hauteurPix+20);
        frame.setResizable(false);
        
        panel=new MonJPanel();
        panel.setSize(largeurPix,hauteurPix);
        frame.add(panel);
        
        buff = new BufferedImage(largeurPix,hauteurPix,BufferedImage.TYPE_INT_ARGB);
        gBuff = buff.createGraphics();
        effacer();
        frame.setVisible(true);
        
        if(debug)
        {
            dessinerPointEn2d(1,2,Color.CYAN);
            dessinerPointEn2d(1,-2);
            dessinerPointEn2d(-1,2);
            dessinerPointEn2d(-1,-2);
            dessinerFlecheEn2d(3,5);
            dessinerFlecheEn2d(-3,5);
            dessinerFlecheEn2d(3,-5);
            dessinerFlecheEn2d(-3,-5);
            dessinerSegmentEn2d(3,5,-3,5);
            dessinerSegmentEn2d(3,5,3,-5,Color.ORANGE);
            dessinerSegmentEn2d(3,-5,-3,-5);
            dessinerSegmentEn2d(-3,-5,-3,5);
            
            dessinerFlecheEn2d(1,3,Color.ORANGE);
            dessinerFlecheEn2d(1,3,3,5,Color.CYAN);
            
        }
    }

    
   
    
    /**
     * Conversion coordonn�e en x dans le rep�re monde vers le rep�re �cran
     * @param x coordonn�e en x dans le rep�re monde
     * @return coordonn�e dans le rep�re �cran
     */
    private int repereVersEcranX(double x)
    {
        return (int)((x/xMax)*largeurPix/2)+largeurPix/2;
    }
    
    /**
     * Conversion coordonn�e en y dans le rep�re monde vers le rep�re �cran
     * @param y coordonn�e en y dans le rep�re monde
     * @return coordonn�e dans le rep�re �cran
     */
    private int repereVersEcranY(double y)
    {
        return (int)((-y/yMax)*hauteurPix/2)+hauteurPix/2;
    }
    
    /**
     * Dessine le rep�re du monde dans le syst�me de coordonn�es �cran
     * @param g objet graphics sur la zone de dessin
     */
    private void dessineRepere(Graphics g)
    {
        int nbStep=6;
        g.setColor(Color.GRAY);
        g.drawLine(0, hauteurPix/2,largeurPix,hauteurPix/2);
        g.drawLine(largeurPix/2,0,largeurPix/2,hauteurPix);
        for(int i=0;i<nbStep;i++)
        {
            int xx=repereVersEcranX(i*xMax/nbStep);
            g.drawLine(xx, hauteurPix/2,xx,hauteurPix/2+5);
            int xx2=repereVersEcranX(-i*xMax/nbStep);
            g.drawLine(xx2, hauteurPix/2,xx2,hauteurPix/2+5);
            
            int yy=repereVersEcranX(i*yMax/nbStep);
            g.drawLine(largeurPix/2,yy, largeurPix/2+5,yy);
            int yy2=repereVersEcranX(-i*yMax/nbStep);
            g.drawLine(largeurPix/2, yy2,largeurPix/2+5,yy2);
        }
        
        g.drawString("(0,0)",largeurPix/2+3,hauteurPix/2+15);
        g.drawString(""+(nbStep-1)*xMax/nbStep,repereVersEcranX((nbStep-1.0)*xMax/nbStep)-8,hauteurPix/2+18);
        g.drawString(""+ (-1*(nbStep-1)*xMax/nbStep),repereVersEcranX(-(nbStep-1.0)*xMax/nbStep)-8,hauteurPix/2+18);
        g.drawString(""+(nbStep-1)*yMax/nbStep,largeurPix/2+8,repereVersEcranY((nbStep-1.0)*yMax/nbStep)+5);
        g.drawString(""+(-(nbStep-1)*yMax/nbStep),largeurPix/2+8,repereVersEcranY(-(nbStep-1.0)*yMax/nbStep)+5);
    }
    

    
    /**
     * Effacer le contenu du plan
     */
    public void effacer()
    {     
        for(int x = 0; x < buff.getWidth(); x++)
        {
            for(int y = 0; y < buff.getHeight(); y++)
            {
                buff.setRGB(x, y, (0xFF));
            }
        }
        panel.repaint();
    }
    
    /**
     * Dessine un point rouge
     * @param o : tout objet poss�dant deux fonctions publiques getX() et getY() retournant respectivement les coordonn�es en x et y de l'objet au format double
     */
    public void dessinerPointEn2d(Object o)
    {
        Double ox=getValueFromObject(o,"getX");
        Double oy=getValueFromObject(o,"getY");
        if(ox!=null && oy!=null)
        {
            dessinerPointEn2d(ox,oy);
        }
    }
    
    /**
     * Dessiner un point rouge aux coordonn�es (px,py)
     * @param px Coordonn�e en x
     * @param py Coordonn�e en y
     */
    private void dessinerPointEn2d(double px, double py)
    {
        dessinerPointEn2d(px,py,Color.RED);
    }
    
    /**
     * Dessiner un point aux coordonn�es (px,py)
     * @param px Coordonn�e en x
     * @param py Coordonn�e en y
     */
    private void dessinerPointEn2d(double px, double py, Color couleur)
    {
        gBuff.setColor(couleur);
        int x=repereVersEcranX(px);
        int y=repereVersEcranY(py);
        gBuff.fillRect(x-2,y-2,5,5);
        panel.repaint();
    }
    
    /**
     * Dessiner une fl�che bleue partant de l'origine et allant aux coordonn�es (px,py)
     * @param px Coordonn�e en x de la pointe de la fl�che
     * @param py Coordonn�e en y de la pointe de la fl�che
     */
    private void dessinerFlecheEn2d(double px, double py)
    {
        dessinerFlecheEn2d(0,0,px,py,Color.BLUE);
    }
    
    /**
     * Dessine un vecteur
     * @param o : tout objet poss�dant deux fonctions publiques getX() et getY() retournant respectivement les coordonn�es en x et y de l'objet au format double
     */
    public void dessinerVecteurEn2d(Object o)
    {
        Double ox=getValueFromObject(o,"getX");
        Double oy=getValueFromObject(o,"getY");
        if(ox!=null && oy!=null)
        {
            dessinerFlecheEn2d(ox,oy);
        }
    }
    
    // "To be or not to be" un temps pour l'introspection !
    private Double getValueFromObject(Object o, String method)
    {
        Class<?> c=o.getClass();
        Method m;
        try{
            m=c.getMethod(method,new Class<?>[]{});
            
        } catch (NoSuchMethodException e) 
        {
            System.err.println("Plan: dessinerVecteurEn2d: Impossible de trouver la m�thode "+method+" dans la classe " +c );
            return null;
        } catch (SecurityException e)
        {
            System.err.println("Plan: dessinerVecteurEn2d: La m�thode " +method +" doit �tre publique dans la classe " +c);
            return null;
        }
        
        Object or;
        try{
            or=m.invoke(o,new Object []{});
        }catch(Exception e) {
            System.err.println("Plan: dessinerVecteurEn2d: Erreur lors de l'appel à " +method+ " : " + e);
            return null;
        }
        
         
        if(or==null)
        {
            System.err.println("Plan: dessinerVecteurEn2d: Erreur la m�thode " +method+ " n'a rien retourn�");
            return null;
        }
        
        if(!(or instanceof Double))
        {
            System.err.println("Plan: dessinerVecteurEn2d: Erreur la m�thode " +method+ " doit retourner un double");
            return null;
        }
        
        return (Double)or;
    }
 
    /**
     * Dessiner une fl�che partant de l'origine et allant aux coordonn�es (px,py)
     * @param px Coordonn�e en x de la pointe de la fl�che
     * @param py Coordonn�e en y de la pointe de la fl�che
     * @param couleur Couleur de la fl�che (voir classe java.awt.Color)
     */
    private void dessinerFlecheEn2d(double px, double py, Color couleur)
    {
        dessinerFlecheEn2d(0,0,px,py,couleur);
    }
    
    /**
     * Dessiner une fl�che bleue partant de (px1,py1) et allant aux coordonn�es (px2,py2)
     * @param px1 Coordonn�e en x de l'origine de la fl�che
     * @param py1 Coordonn�e en y de l'origine de la fl�che
     * @param px2 Coordonn�e en x de la pointe de la fl�che
     * @param py2 Coordonn�e en y de la pointe de la fl�che
     * @param couleur Couleur de la fl�che (voir classe java.awt.Color)
     */
    private void dessinerFlecheEn2d(double px1, double py1, double px2, double py2, Color couleur)
    {
        dessinerSegmentEn2d(px1,py1,px2,py2,couleur);
        int x=repereVersEcranX(px2);
        int y=repereVersEcranY(py2);
        gBuff.translate(x, y);
        double angle = Math.atan2(py2-py1,px2-px1);
        gBuff.rotate(-angle);
        gBuff.drawLine(0, 0, -10, 5);
        gBuff.drawLine(0, 0, -10, -5);
        gBuff.rotate( angle);
        gBuff.translate( -x, -y);
        panel.repaint();
    }
   
    /**
     * Dessine un segment vert
     * @param o1 : origine du segment : tout objet poss�dant deux fonctions publiques getX() et getY() retournant respectivement les coordonn�es en x et y de l'objet au format double
     * @param o2 : extr�mit� du segment : tout objet poss�dant deux fonctions publiques getX() et getY() retournant respectivement les coordonn�es en x et y de l'objet au format double
     */
    public void dessinerSegmentEn2d(Object o1, Object o2)
    {
        Double ox1=getValueFromObject(o1,"getX");
        Double oy1=getValueFromObject(o1,"getY");
        Double ox2=getValueFromObject(o2,"getX");
        Double oy2=getValueFromObject(o2,"getY");
        if(ox1!=null && oy1!=null && ox2!=null && oy2!=null)
        {
            dessinerSegmentEn2d(ox1,oy1,ox2,oy2);
        }
    }
    
    /**
     * Dessiner un segment vert allant du point  (px1,py1) au point (px2,py2)
     * @param px1 Coordonn�e en x de l'origine du segment
     * @param py1 Coordonn�e en y de l'origine du segment
     * @param px2 Coordonn�e en x de la pointe du segment
     * @param py2 Coordonn�e en y de la pointe du segment
     */
    private void dessinerSegmentEn2d(double px1, double py1, double px2, double py2)
    {
        dessinerSegmentEn2d(px1,py1,px2,py2,Color.GREEN);
    }
    
    /**
     * Dessiner un segment  allant du point  (px1,py1) au point (px2,py2)
     * @param px1 Coordonn�e en x de l'origine du segment
     * @param py1 Coordonn�e en y de l'origine du segment
     * @param px2 Coordonn�e en x de la pointe du segment
     * @param py2 Coordonn�e en y de la pointe du segment
     * @param couleur Couleur du segment (voir classe java.awt.Color)
     */
    private void dessinerSegmentEn2d(double px1, double py1, double px2, double py2, Color couleur)
    {
        int x1=repereVersEcranX(px1);
        int y1=repereVersEcranY(py1);
        int x2=repereVersEcranX(px2);
        int y2=repereVersEcranY(py2);
        gBuff.setColor(couleur);
        gBuff.drawLine(x1,y1,x2,y2);
        panel.repaint();
    }
    /**
     * D�finit si il faut afficher le rep�re
     * @param f si vrai le repere est affich�, sinon il ne l'est pas.
     */
    public void afficheRepere(boolean f)
    {
        repere=f; 
        panel.repaint();
    }
    
    // "Cachez cette classe que je ne saurais voir !"
    private class MonJPanel extends JPanel
    {
         @Override
         public void paint(Graphics g) 
         {
             g.setColor(Color.WHITE);
             g.clearRect(0, 0, largeurPix,hauteurPix);
             if(repere) dessineRepere(g);
             g.drawImage(buff,0,0,null);
            }
    }
}
