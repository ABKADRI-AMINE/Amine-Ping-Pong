import java.util.Random;

public class Director {
    private BallBuilder ballBuilder; // Déclaration de la variable ballBuilder de type BallBuilder
    private static final int GAME_WIDTH = 1000; // Largeur du jeu (constante)
    private static final int GAME_HEIGHT = (int) (GAME_WIDTH * (0.5555)); // Hauteur du jeu calculée à partir de la largeur
    private static final int BALL_DIAMETER = 20; // Diamètre de la balle
    private Random random; // Générateur de nombres aléatoires

    public Director(BallBuilder ballBuilder) {
        this.ballBuilder = ballBuilder; // Constructeur de la classe Director, prend un BallBuilder en paramètre
        random = new Random(); // Initialisation du générateur de nombres aléatoires
    }

    public Ball constructBall() {
        // Méthode pour construire une balle en utilisant le BallBuilder

        return ballBuilder
                .setX(GAME_WIDTH / 2 - BALL_DIAMETER / 2) // Définition de la position horizontale de la balle
                .setY(random.nextInt(GAME_HEIGHT - BALL_DIAMETER)) // Définition de la position verticale de la balle (aléatoire)
                .setWidth(BALL_DIAMETER) // Définition de la largeur de la balle
                .setHeight(BALL_DIAMETER) // Définition de la hauteur de la balle
                .build(); // Construction de la balle en utilisant le BallBuilder
    }
}
