import java.util.Arrays;
import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
class Main {
    public static void main(String[] args) {
        int[][] G = {
            {2, 2, 3, 4, 2},
            {6, 5, 1, 2, 8},
           {1, 1, 10, 1, 1}
        };
        int d = 0;
        System.out.println("grille G:");
          for(int i=G.length-1;i>-1;i--) System.out.printf("G[%s] : %s\n", i, Arrays.toString(G[i]));
        
        System.out.printf("Valeurs des chemins gloutons depuis les cases (0,%s) : Ng = %s",d, Arrays.toString(glouton(G)));
        System.out.println();
        System.out.printf("Programmation dynamique, case de départ (0, %s)\nM :\n",d);
        int[][][] MA = calculerMA(G,d);
        int[][] M = MA[0];
        int[][] A = MA[1];
        for(int i=M.length-1;i>-1;i--) System.out.println(Arrays.toString(M[i]));

        System.out.printf("Un chemin maximum : ");
        acnpm(M,A);
        System.out.printf(" Valeur : "+optimal(G,d));
        d=1;
        System.out.printf("\nProgrammation dynamique, case de départ (0, %s)\nM :\n",d);
     
      MA = calculerMA(G,d);
       M = MA[0];
         A = MA[1];
        for(int i=M.length-1;i>-1;i--) System.out.println(Arrays.toString(M[i]));

        System.out.printf("Un chemin maximum : ");
        acnpm(M,A);
        System.out.printf(" Valeur : "+optimal(G,d));

      
        System.out.println("\n\nChemins max. depuis toutes les cases de départ (0,d)");
        d=4;
        for(int i=0;i<d;i++) {
            System.out.printf("Un chemin maximum : ");
            acnpm(M,A);
            System.out.printf(" Valeur : "+optimal(G,i)+"\n");
        }
    System.out.println("\n");
    System.out.printf("Ng = %s", Arrays.toString(glouton(G)));
    System.out.printf("\nNmax = %s", Arrays.toString(optimal(G)));
    System.out.println("\n");
    int[][] G2 = {{
            2,4,3,9,6
    }, 
{1,10,15,1,2},
{2,4,11,26,66},
{36,34,1,13,30},
{46,2,8,7,15},
{89,27,10,12,3},
{1,72,3,6,6},
{3,1,2,4,5}
};  // pour la question du gain relatif sur tableau


    
    System.out.printf("Gains relatifs = %s", Arrays.toString(gainRelatif(optimal(G), glouton(G))));

    System.out.println("\n");  
    int n = 10000;
    System.out.println("VALIDATION STATISTIQUE");
    System.out.printf("nruns = "+n+"\n");
    System.out.println("L au hasard dans [5:16]");
    System.out.println("C au hasard dans [5:16]");
    System.out.println("Nb. de pucerons / case au hasard dans [0:LxC]");
int taille_g = 0;

ArrayList<float[]> gains = new ArrayList<float[]>();

try {
    File f = new File("histogramme.csv");
    FileWriter fw = new FileWriter(f);
    fw.write("");
    fw.close();
} catch(Exception e) {
    System.out.println(e);
}
    for (int i = 0; i < n; i++) {
           int L = (int) (Math.random() * 12) + 5; 
        int C = (int) (Math.random() * 12) + 5;
     
   if(i % 1000 == 0)   System.out.println("run "+i+"/"+n+", (L,C) = ("+L+","+C+")");
     
        int[][] grille = new int[L][C];
       
        // cas 1 : L+C 
          Random rand = new Random();
        for(int l=0;l<L;l++) {
            for(int c=0;c<C;c++) {
                grille[l][c] = rand.nextInt(L+C);
            }
        }

        /*
         // cas 2 : L*C
        for(int l=0;l<L;l++) {
            for(int c=0;c<C;c++) {
                grille[l][c] = rand.nextInt(L*C);
            }
        }
        // cas 3 : L*C avec permutation
        int cpt = -1; // compteur
        for(int l=0;l<L;l++) { // on parcourt les lignes
            for(int c=0;c<C;c++) { // on parcourt les colonnes
                cpt++; // on incrémente le compteur
                grille[l][c] = cpt; // on stocke la valeur du compteur dans la case actuelle
            }
            for(int l=0;l<grille.length;l++) { // on parcourt les lignes
                grille[l] = permutationAleatoire(grille[l]); // on calcule une permutation aléatoire de la ligne
            }
            
        }
         */
          float[] gain = gainRelatif(optimal(grille),glouton(grille));
            gains.add(gain);
        
      
     File f = new File("histogramme.csv");
  try {
 FileWriter fw = new FileWriter(f, true);

     
     fw.write(Arrays.toString(gain).replace("[", "").replace("]", "")+"\n");
     fw.close();
  } catch(Exception e) {
      System.out.println(e);
    }
    

    
    taille_g+=gain.length;
  
    
   
}
 System.out.println("GAINS.length="+taille_g+", min="+min(gains)+", max="+max(gains)+", mean="+mean(gains)+", mediane="+mediane(gains));

    }
  static float min(ArrayList<float[]> gains) {
        float min = gains.get(0)[0];
        for(int i=0;i<gains.size();i++) {
            for(int j=0;j< gains.get(i).length ;j++) {
                if(gains.get(i)[j] < min) min = gains.get(i)[j];
            }
        }
        return min;
    
  }
  static float max(ArrayList<float[]> gains) {
        float max = gains.get(0)[0];
        for(int i=0;i<gains.size();i++) {
            for(int j=0;j< gains.get(i).length ;j++) {
                if(gains.get(i)[j] > max) max = gains.get(i)[j];
            }
        }
        return max;
    
  }
 static float mean(ArrayList<float[]> gains) {
        float mean = 0;
        for(int i=0;i<gains.size();i++) {
            for(int j=0;j< gains.get(i).length ;j++) {
                mean += gains.get(i)[j];
            }
        }
        return mean/gains.size();
    
  }

static float mediane(ArrayList<float[]> gains) {
    float[] tab = new float[gains.size()];
    for(int i=0;i<gains.size();i++) {
        tab[i] = gains.get(i)[0];
    }
    Arrays.sort(tab);
    return tab[tab.length/2];
}
static int[] permutationAleatoire(int[] T){ 
    int n = T.length;
// Calcule dans T une permutation aleatoire de T et retourne T
Random rand = new Random(); // biblioth`eque java.util.Random
for (int i = n; i > 0; i--){
int r = rand.nextInt(i); // r est au hasard dans [0:i]
permuter(T,r,i-1);
}
return T;
}
static void permuter(int[] T, int i, int j){
    int ti = T[i];
    T[i] = T[j];
    T[j] = ti;
    }
    public static int glouton(int[][] G, int d) {
        int x = d; // colonne de départ
        int y = 0; // ligne de départ
        int nb_manger = G[0][d]; // nombre de pucerons mangés
        while (y != G.length-1) { // tant qu'on est pas à la dernière ligne
            if (x - 1 < 0) { // si on est sur la première colonne
                if (G[y + 1][x + 1] > G[y + 1][x]) x++; // si la case à droite est plus grande que la case actuelle, on va à droite
            } else if (x + 1 > G[0].length-1) { // si on est sur la dernière colonne
                 
                if (G[y + 1][x - 1] > G[y + 1][x]) x--; // si la case à gauche est plus grande que la case actuelle, on va à gauche
            } else { // si on est sur une colonne du milieu
                if (G[y + 1][x + 1] > G[y + 1][x] && G[y + 1][x + 1] > G[y + 1][x - 1]) { // si la case à droite est plus grande que la case actuelle et que la case à droite est plus grande que la case à gauche, on va à droite
                    x++;

                } else { // si la case à gauche est plus grande que la case actuelle et que la case à gauche est plus grande que la case à droite, on va à gauche
                    x--;

                }
              
            }  
            y++; // on descend d'une ligne
            nb_manger += G[y][x]; // on mange les pucerons de la case actuelle

        }

        return nb_manger;
    }

    public static int[] glouton(int[][] G) {
        int[] Ng = new int[G[0].length]; // tableau des chemins gloutons
        for(int d=0;d<G[0].length;d++) Ng[d] = glouton(G,d); // on calcule le chemin glouton pour chaque case de départ
        return Ng;
    }
    public static int[][][] calculerMA(int[][] G, int d) {
        int C = G[0].length; // nombre de colonnes
        int L = G.length; // nombre de lignes
        int[][] M = new int[L][C]; // tableau des valeurs maximales
        int[][] A = new int[L][C]; // tableau des colonnes d'arrivée
        M[0][d] = G[0][d]; // on initialise la première ligne avec les valeurs de la grille
        for (int c = 0; c < C; c++) { // on met à -1 les cases qui ne peuvent pas être atteintes
            if (c != d) M[0][c] = -1; 
        }
        
        for (int l = 1; l < L; l++) { // on parcourt les lignes
            for (int c = 0; c < C; c++) { // on parcourt les colonnes
                if (c + 1 > C - 1) { // si on est sur la dernière colonne
                    if ((M[l-1][c-1] == -1) && (M[l-1][c] == -1)) M[l][c] = -1; // si les deux cases au-dessus sont à -1, on met à -1
                    else { // sinon on calcule la valeur maximale
                        M[l][c] = G[l][c] + Math.max(M[l-1][c-1], M[l-1][c]); 
                        A[l][c] = (M[l-1][c-1] > M[l-1][c]) ? c-1 : c; // on stocke la colonne d'arrivée
                    }
                } else if (c - 1 < 0) { // si on est sur la première colonne
                    if ((M[l-1][c+1] == -1) && (M[l-1][c] == -1)) M[l][c] = -1; // si les deux cases au-dessus sont à -1, on met à -1
                    else { // sinon on calcule la valeur maximale
                        
                        M[l][c] = G[l][c] + Math.max(M[l-1][c+1], M[l-1][c]); // on calcule la valeur maximale
                        A[l][c] = (M[l-1][c+1] > M[l-1][c]) ? c+1 : c;
                    }
                } else { // si on est sur une colonne du milieu
                    if ((M[l-1][c+1] == -1) &&  (M[l-1][c-1] == -1) && (M[l-1][c] == -1)) M[l][c] = -1; // si les trois cases au-dessus sont à -1, on met à -1
                    else { // sinon on calcule la valeur maximale
                        M[l][c] = G[l][c] + Math.max(M[l-1][c], Math.max(M[l-1][c-1], M[l-1][c+1])); // on calcule la valeur maximale
                  
                        // recherche de l'indice a valeur maximum qui a été sélectionnée par le maximum
                        if(M[l-1][c+1] > M[l-1][c] && M[l-1][c+1] > M[l-1][c-1]) A[l][c] = c+1; 
                        else if(M[l-1][c-1] > M[l-1][c+1] && M[l-1][c-1] > M[l-1][c]) A[l][c] = c-1;
                        else A[l][c] = c;
                    };
                } 
            }
        }
        return new int[][][] {M, A}; // on retourne les deux tableaux
    }
    static int argMax(int[] tab) { 
        
        int arg = 0; // indice de la valeur maximale
         
        int val = tab[0]; // valeur maximale
        for(int i=1;i<tab.length;i++) { 
            if(tab[i] > val) { // si la valeur est plus grande que la valeur maximale
                val = tab[i];   // on met à jour la valeur maximale
                arg = i;       // on met à jour l'indice de la valeur maximale
            }
        }
        return arg;
    }
    static void acnpm(int[][] M, int[][] A){
        int L = M.length;
       int cStar = argMax(M[L-1]) ; // colonne d’arrivee du chemin max. d’origine (0,d)
        
       acnpm(A, L-1, cStar); // affichage du chemin maximum de (0,d) `a (L-1, cStar)
        }
        static void acnpm(int[][] A, int l, int c) {
            if(l==0){ // si on est sur la première ligne, c'est que le chemin est terminé
                 System.out.printf("(%s,%s)",l,c); // on affiche la case actuelle
            } else { // sinon, on va afficher les coordonnées empruntées sur le chemin
            acnpm(A,l-1,A[l][c]); // on affiche le chemin maximum de la case d'arrivée
             System.out.printf("(%s,%s)",l,c); // on affiche la case actuelle
            }
          
        }
public static int optimal(int[][] G, int d) {
    int M[][] = calculerMA(G,d)[0]; // on calcule le tableau des valeurs maximales
    return M[G.length-1][argMax(M[G.length-1])]; // on retourne la valeur maximale de la dernière ligne
}
public static int[] optimal(int[][] G) {
    int[] Nmax = new int[G[0].length]; // tableau des chemins max.
    for(int i=0;i<G[0].length;i++) { 
        Nmax[i] = optimal(G,i); // on calcule le chemin maximum pour chaque case de départ
    }
    return Nmax;
}

public static float[] gainRelatif(int[] Nmax, int[] Ng) {
    float[] Gain = new float[Nmax.length];  // tableau des gains relatifs
    for(int i=0;i<Nmax.length;i++) { // on parcourt les chemins
        Gain[i] = (int)((((float)Nmax[i] - (float)Ng[i]) / (float)Ng[i])*1000)/1000f ; // on calcule le gain relatif
    }
    return Gain;
}
}