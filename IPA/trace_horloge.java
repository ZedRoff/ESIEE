package IPA;
public setFillColor("black");
circle(250, 250, 250);
double angleMinutes = (minutes / 60.0) * 2*PI;
double angleHeures = (hours / 12.0) * 2*PI;
double angleRajout = (minutes / 60.0) * (PI / 6.0);
setFillColor("white");
line(250, 250, (int)(250 + 200 * sin(angleMinutes)), (int)(250 - 200 * cos(angleMinutes)));
setFillColor("grey");
line(250, 250, (int)(250 + 150 * sin(angleHeures + angleRajout)), (int)(250 - 150 * cos(angleHeures + angleRajout))); trace_horloge {
    
}
