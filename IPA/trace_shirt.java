double multiplicateur = 0.0;
switch(taille) {
    case 'S':
       multiplicateur = 0.8; 
    break;
    case 'L':
        multiplicateur = 1.1;
    break;
    case 'M':
        multiplicateur = 1.0;
    break;
}

String color;
if(couleur == 'g' || couleur == 'G') {
    color = "green";
}else if(couleur == 'y' || couleur == 'Y') {
    color = "yellow";
} else if(couleur == 'b' || couleur == 'B') {
    color = "blue";
} else if(couleur == 'r' || couleur == 'R') {
    color = "red";
} else if(couleur == 'w' || couleur == 'W') {
    color = "white";
}
else {
    color = "black";
}




setFillColor(color);
startPolygon();
addPoint((int)(0*multiplicateur),(int)(120*multiplicateur));
addPoint((int)(120*multiplicateur),(int)(0*multiplicateur));
addPoint((int)(160*multiplicateur),(int)(0*multiplicateur));
addPoint((int)(240*multiplicateur),(int)(40*multiplicateur));
addPoint((int)(320*multiplicateur),(int)(0*multiplicateur));
addPoint((int)(360*multiplicateur),(int)(0*multiplicateur));
addPoint((int)(480*multiplicateur),(int)(120*multiplicateur));
addPoint((int)(400*multiplicateur),(int)(200*multiplicateur));
addPoint((int)(360*multiplicateur),(int)(160*multiplicateur));
addPoint((int)(360*multiplicateur),(int)(360*multiplicateur));
addPoint((int)(120*multiplicateur),(int)(360*multiplicateur));
addPoint((int)(120*multiplicateur),(int)(160*multiplicateur));
addPoint((int)(80*multiplicateur),(int)(200*multiplicateur));
addPoint((int)(0*multiplicateur),(int)(120*multiplicateur));
stopPolygon();
String button_color;
    if(color == "black")  {
        button_color = "white";
    }else {
        button_color = "black";
    }
    
    String col_color;
    if(color == "white") {
        col_color = "black";
    }else {
        col_color = "white";
    }
if(chemise) {
     setFillColor(col_color);

   
    startPolygon();
    addPoint((int)(160*multiplicateur),(int)(multiplicateur*0));
    addPoint((int)(140*multiplicateur),(int)(multiplicateur*20));
    addPoint((int)(220*multiplicateur),(int)(multiplicateur*60));
    addPoint((int)(240*multiplicateur),(int)(multiplicateur*40));
    stopPolygon();
    startPolygon();
    addPoint((int)(320*multiplicateur),(int)(multiplicateur*0));
    addPoint((int)(340*multiplicateur),(int)(multiplicateur*20));
    addPoint((int)(260*multiplicateur),(int)(multiplicateur*60));
    addPoint((int)(240*multiplicateur),(int)(multiplicateur*40));
    stopPolygon();
 setFillColor(button_color);

   circle((int)(multiplicateur * 240),(int)(multiplicateur * 80),(int)(multiplicateur * 5));
circle((int)(multiplicateur * 240),(int)(multiplicateur * 160),(int)(multiplicateur * 5));
circle((int)(multiplicateur * 240),(int)(multiplicateur * 240),(int)(multiplicateur * 5));
circle((int)(multiplicateur * 240),(int)(multiplicateur * 320),(int)(multiplicateur * 5));

line((int)(multiplicateur * 260),(int)(multiplicateur * 60),(int)(multiplicateur * 260),(int)(multiplicateur * 360));

}
if(poche) {
     setFillColor(col_color);

    startPolygon();
    addPoint((int)(multiplicateur * 280), (int)(multiplicateur * 80));
    addPoint((int)(multiplicateur * 320), (int)(multiplicateur * 80));
    addPoint((int)(multiplicateur * 320), (int)(multiplicateur * 140));
    addPoint((int)(multiplicateur * 280), (int)(multiplicateur * 140));
    addPoint((int)(multiplicateur * 280), (int)(multiplicateur * 80));
    stopPolygon();
}