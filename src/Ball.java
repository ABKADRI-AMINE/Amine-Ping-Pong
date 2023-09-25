import java.awt.*;
import java.util.Random;
import java.util.RandomAccess;

public class Ball extends Rectangle{
    //on creer une instance de la class random (pour la position aleatoir du ball fbdya dial game)
    Random random;
    //la vitesse de la ball selon l'axe x et y
    int xVelocity;
    int yVelocity;

    //la vitesse initial du ball
    int initialSpeed = 2;

    //constructor
    Ball(int x,int y,int width,int height){
        super(x,y,width,height);
        random=new Random();
        //la valeur aleatoire est soit 0 soit 1 si 0 la balle commence en se derigant vers le gauche si 1 vers la droite
        int randomXDirection = random.nextInt(2);
        if(randomXDirection == 0)
            randomXDirection --;
            setXDirection(randomXDirection*initialSpeed);

        int randomYDirection = random.nextInt(2);
        if(randomYDirection == 0)
            randomYDirection --;
        setYDirection(randomYDirection*initialSpeed);

    }
    //la position aleatoire qui va prendre la nouvelle ball qu on va creer selon l'axe des x
    public void setXDirection(int randomXDirection){
    xVelocity=randomXDirection;
    }
    //la position aleatoire qui va prendre la nouvelle ball qu on va creer selon l'axe des y
    public void setYDirection(int randomYDirection){
    yVelocity=randomYDirection;
    }
    public void move(){
    x += xVelocity;
    y += yVelocity;
    }
    //dessiner la ball
    public void draw(Graphics g){
        g.setColor(new Color(135, 206, 235));
    //rendre la balle circulaire
        g.fillOval(x,y,height,width);
    }
}
