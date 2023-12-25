///////////////////////////////////////////////
//// ECRIVEZ VOTRE CODE ENTRE DEBUT ET FIN ////
///////////////////////////////////////////////

public static void afficheComplexe( final char pAff, final double pRe, final double pIm, final double pMod, final double pArg )
{ // VOTRE CODE DE L'EXERCICE PRECEDENT :
    // Les variables pAff, pRe, pIm, pMod et pArg sont déjà déclarées et initialisées
switch(pAff) {
    case 'a':
     
        if(pIm == 1) {
            afficheReel(pRe);
            afficheChaine("+");
            afficheChaine("i");
        } else if(estNul(pRe) && !estNul(pIm)) {
            afficheReel(pIm);
            afficheChaine("i");
            }else if(pIm == -1 && !estNul(pRe)){
            afficheReel(pRe);
            afficheChaine("-");
            afficheChaine("i");
        } else if(pIm < 0) {
            afficheReel(pRe);
            afficheReel(pIm);
            afficheChaine("i");
        } else if(estNul(pRe) && estNul(pIm)) {
            afficheReel(pRe);
            }else if(estNul(pRe)) {
            afficheReel(pIm);
            afficheChaine("i");

        } else if(estNul(pIm)) {
            afficheReel(pRe);
        }
            else {
            afficheReel(pRe);
            afficheChaine("+");
            afficheReel(pIm);
            afficheChaine("i");
        }
        
    break;
    case 'e':
    if(estNul(pMod) && !estNul(pArg)) {
           afficheReel(0);
    } else if(!estNul(pMod) && estNul(pArg)) {
        afficheReel(pMod);
    }else if(!estNul(pMod) && !estNul(pArg)) {
        if(pArg == -1) {
            afficheReel(pMod);
            afficheChaine("e^-i");
            
        } else if(pMod == -1) {
            
              afficheChaine("-e^");
              afficheReel(pArg);
            afficheChaine("i");
            }
            else if(pMod == 1 && pArg == 1) {
            afficheChaine("e^i");
        } else if(pMod == 1) {
            afficheChaine("e^");
            afficheReel(pArg);
            afficheChaine("i");
        } else if(pArg == 1) {
            afficheReel(pMod);
            afficheChaine("e^i");
        }else {
            afficheReel(pMod);
        afficheChaine("e^");
        afficheReel(pArg);
        afficheChaine("i");
        }
    
    }else if(estNul(pMod) && estNul(pArg)) {
        afficheReel(0);
    }
        
    break;
    default:
        afficheChaine("<forme inconnue>");
    break;
}
}

public static void main(String[] argv) throws Exception
{
//// DEBUT ////
double a1 = lireReel();
double b1 = lireReel();
char entree = lireChar();
switch(entree) {
    case 'p':
        double pMod = sqrt(a1*a1 + b1*b1);
        double angle_tempo = b1 / (a1 + pMod);
        double pArg = 2*atan(angle_tempo);
        afficheComplexe('e', a1, b1, pMod, pArg);
    break;
    case 'c':
        afficheComplexe('a', a1*cos(b1),a1*sin(b1),0,0);
    break;
    case 'b':
     char entree2 = lireChar();
     if(entree2 == 'a') {
         afficheComplexe('a', a1,-b1,0,0);
     } else {
       pMod = sqrt(a1*a1 + b1*b1);
       angle_tempo = b1 / (a1 + pMod);
        pArg = 2*atan(angle_tempo);
        afficheComplexe('e', 0,0, pMod, -pArg);
     }
            
            break;
        case '+':
             char entree3 = lireChar();

            double a2 = lireReel();
            double b2 = lireReel();
           
          if(entree3 == 'a') {
                  afficheComplexe('a', a1+a2,b1+b2, 0,0);

           }else {
               
                pMod = sqrt((a1+a2)*(a1+a2) + (b1+b2)*(b1+b2));
        angle_tempo = (b1+b2) / ((a1+a2) + pMod);
        pArg = 2*atan(angle_tempo);
               afficheComplexe('e', 0, 0, pMod, pArg);
           }
           
            break;
            case '-':
             char entree4 = lireChar();

           a2 = lireReel();
            b2 = lireReel();

          if(entree4 == 'a') {
                  afficheComplexe('a', a1-a2,b1-b2, 0,0);

           }else {
              pMod = sqrt((a1-a2)*(a1-a2) + (b1-b2)*(b1-b2));
        angle_tempo = (b1-b2) / ((a1-a2) + pMod);
        pArg = 2*atan(angle_tempo);
               afficheComplexe('e', 0, 0, pMod, pArg);
           }
        break;
    case '.':
        char entree5 = lireChar();
   double lambda = lireReel();
    if(entree5 == 'a') {
        afficheComplexe('a', lambda*a1,lambda*b1,0,0);
    } else {
     
              pMod = sqrt((a1*lambda)*(a1*lambda) + (b1*lambda)*(b1*lambda));
        angle_tempo = (b1*lambda) / ((a1*lambda) + pMod);
        pArg = 2*atan(angle_tempo);
               afficheComplexe('e', 0, 0, pMod, pArg);
    }
            break;
            case '*':
                char entree6 = lireChar();
                a2 = lireReel();
                b2 = lireReel();
                if(entree6 == 'a') {
                    afficheComplexe('a',a1*a2-b1*b2,b2*a1+b1*a2,0,0);
                }else {
                    
              pMod = sqrt((a1*a2-b1*b2)*(a1*a2-b1*b2) + (b2*a1+b1*a2)*(b2*a1+b1*a2));
        angle_tempo = (b2*a1+b1*a2) / ((a1*a2-b1*b2) + pMod);
        pArg = 2*atan(angle_tempo);
               afficheComplexe('e', 0, 0, pMod, pArg);
                }
                break;
                case '1':
                    char entree7 = lireChar();
                    if((a1*a1 + b1 * b1) == 0) {
                        System.out.println("<division par zéro>");
                    }else {
                        
                   
                    if(entree7 == 'a') {
                        afficheComplexe('a', a1 / (a1*a1 + b1 * b1), -b1 / (a1*a1 + b1*b1), 0, 0);
                    }else {
                            pMod = sqrt((a1 / (a1*a1 + b1 * b1))*(a1 / (a1*a1 + b1 * b1)) + (-b1 / (a1*a1 + b1*b1))*(-b1 / (a1*a1 + b1*b1)));
        angle_tempo = (-b1 / (a1*a1 + b1*b1)) / ((a1 / (a1*a1 + b1 * b1)) + pMod);
        pArg = 2*atan(angle_tempo);
               afficheComplexe('e', 0, 0, pMod, pArg);
                    }
                     }
                break;
                case '/':
                char machin8 = lireChar();
                a2 = lireReel();
                b2 = lireReel();
                  double d = (a1*a2 + b1*b2) / (a2*a2 + b2*b2);
                    double f = (b1*a2 - a1*b2)/(a2*a2 + b2*b2);
                    if((a2*a2 + b2*b2) == 0) {
                        System.out.println("<division par zéro>");
                    }else {
                        
                    
                if(machin8 == 'a') {
                    
                    afficheComplexe('a', d, f, 0, 0);
                } else {
                  
                    pMod = sqrt(d*d + f*f);
                    angle_tempo = (f / (d + pMod));
                    pArg = 2*atan(angle_tempo);
                    afficheComplexe('e', 0, 0, pMod, pArg);             
                }
                    }
                break;
                default:
                System.out.println("<opération inconnue>");
                break;
}
////  FIN  ////
}