public void smiley(String chaine, String couleur, int n1, int n2) {
    char yeux = chaine.charAt(0);
    char bouche = chaine.charAt(1);
       setStrokeWidth(5);
    if(couleur.equals("black")) {
        setStrokeColor("white");
        
        
    }else {
         setStrokeColor("black");

    }
   
    setFillColor(couleur);
    circle(n1, n2, 50);
    
    if(yeux == ':') {
        if(couleur.equals("black")) {
              setFillColor("white");
              setStrokeColor("white");
        }else {
             setFillColor("black");
              setStrokeColor("black");
        }
       
    circle(n1-20,n2-20,10);
    circle(n1+20,n2-20,10);
    }else if(yeux == ';') {
         if(couleur.equals("black")) {
              setFillColor("white");
              setStrokeColor("white");
        }else {
             setFillColor("black");
              setStrokeColor("black");
        }
   
     
        circle(n1+20,n2-20,10);
        line(n1-30,n2-30,n1-10,n2-20);
    } else if(yeux == 'X') {
         line(n1-30,n2-30,n1-10,n2-20);
         line(n1-30,n2-10,n1-10,n2-20);
         line(n1+30,n2-30,n1+10,n2-20);
         line(n1+30,n2-10,n1+10,n2-20);
    }
    
    if(bouche == '|') {
        line(n1-20,n2+20,n1+20,n2+20);
    } else if(bouche == 'P') {
        
        setFillColor("red");
        
        setStrokeWidth(0);
      
        rectangle(n1,n2+20,20,20);
         setStrokeWidth(5);
        line(n1-20,n2+20,n1+20,n2+20);
    } else if(bouche == 'D') {
        setStrokeWidth(0);
        setFillColor("black");
        circle(n1,n2+20,20);
        setFillColor(couleur);
        rectangle(n1-25,n2-5,50,25);
    } else if(bouche == '/') {
        line(n1-20,n2+10,n1+20,n2+30);
    }
   
}