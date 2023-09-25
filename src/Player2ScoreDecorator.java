import java.awt.*;

// Définition de la classe
public class Player2ScoreDecorator extends ScoreDecoratorImpl {

    // Constructeur avec un décorateur en argument
    public Player2ScoreDecorator(ScoreDecorator decoratedScore) {
        super(decoratedScore);
    }

    // Implémentation de la méthode d'affichage du score
    @Override
    public void drawScore(Graphics g, int player1Score, int player2Score) {
        // Appel de la méthode d'affichage du score du décorateur parent
        super.drawScore(g, player1Score, player2Score);

        // Si le score du joueur 2 est impair, on utilise la couleur verte, sinon on utilise la couleur blanche
        if (player2Score % 2 == 1) {
            g.setColor(Color.red);
        } else {
            g.setColor(Color.BLACK);
        }

        // Dessin d'un rectangle rempli à l'emplacement du score du joueur 2
        g.fillRect(Score.GAME_WIDTH/2+20, 10, 100, 80);

        // Utilisation de la couleur noire pour le texte
        g.setColor(Color.WHITE);
        // Utilisation de la police Consolas avec une taille de 40 pour le texte
        g.setFont(new Font("Consolas", Font.PLAIN, 40));
        // Affichage du score du joueur 2 dans le rectangle
        g.drawString(String.valueOf(player2Score), Score.GAME_WIDTH/2+55, 60);
    }
}
