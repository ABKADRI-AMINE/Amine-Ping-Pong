import java.awt.*;

public class Player1ScoreDecorator extends ScoreDecoratorImpl {

    //Constructeur pour initialiser le décorateur avec un autre décorateur
    public Player1ScoreDecorator(ScoreDecorator decoratedScore) {
        super(decoratedScore);
    }

    //Méthode pour dessiner le score du joueur 1
    @Override
    public void drawScore(Graphics g, int player1Score, int player2Score) {

        //Appelle la méthode drawScore() du décorateur précédent
        super.drawScore(g, player1Score, player2Score);

        //Si le score du joueur 1 est impair, la couleur rouge est utilisée, sinon la couleur blanche est utilisée
        if (player1Score % 2 == 1) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(Color.BLACK);
        }

        //Dessine un rectangle avec la couleur choisie
        g.fillRect(Score.GAME_WIDTH/2-120, 10, 100, 80);

        //Couleur noire utilisée pour écrire le score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.PLAIN, 40));

        //Ecrire le score du joueur 1 à l'intérieur du rectangle
        g.drawString(String.valueOf(player1Score), Score.GAME_WIDTH/2-85, 60);
    }
}
