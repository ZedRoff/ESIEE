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