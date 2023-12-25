// les variables center, pX1,pY1, pX2,pY2, pX,pY, pM,pA, pOp,pAff sont déjà déclarées et initialisées

if (pOp != 'c' && pOp != 'p') {
    setFillColor("red");

    line(center, center, center + (int)(20 * pX1), center - (int)(20 * pY1));

    if (pOp != 'b' && pOp != '1') {
        setFillColor("green");

        line(center, center, center + (int)(20 * pX2), center - (int)(20 * pY2));

    }

}
setFillColor("blue");

if (pAff == 'e') {
    line(center, center, center + (int)(20 * pM * cos(pA)), center - (int)(20 * pM * sin(pA)));

} else {
    line(center, center, center + (int)(20 * pX), center - (int)(20 * pY));

}