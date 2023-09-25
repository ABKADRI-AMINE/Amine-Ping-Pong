import java.awt.*;

public class Score extends Rectangle implements ScoreDecorator {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    //LE SCORE DU PLAYER 1 ET 2
    int player1;
    int player2;

    //constructor
    public Score(int GAME_WIDTH, int GAME_HEIGHT) {
        Score.GAME_WIDTH = GAME_WIDTH;
        Score.GAME_HEIGHT = GAME_HEIGHT;
    }
    //dessiner le score
    @Override
    public void drawScore(Graphics g, int player1Score, int player2Score) {
        g.setFont(new Font("Consolas", Font.PLAIN, 60));
        //dessiner la ligne du milieu
        g.drawLine(GAME_WIDTH/2, 0, GAME_WIDTH/2, GAME_HEIGHT);
        //dessiner le resultat du score
        g.drawString(String.valueOf(player1Score/10)+String.valueOf(player1Score%10), (GAME_WIDTH/2)-85, 50);
        g.drawString(String.valueOf(player2Score/10)+String.valueOf(player2Score%10), (GAME_WIDTH/2)+20, 50);
    }

    //Méthode pour dessiner le score des joueurs 1 et 2 sur l'écran
    public void draw(Graphics g) {
        //Créer un objet Player1ScoreDecorator et l'utiliser pour décorer l'objet Score
        ScoreDecorator decoratedScore = new Player2ScoreDecorator(new Player1ScoreDecorator(this));
        //Utiliser la méthode drawScore() de l'objet décoré pour dessiner le score à l'écran
        decoratedScore.drawScore(g, player1, player2);
    }
}