import java.awt.*;

public abstract class ScoreDecoratorImpl implements ScoreDecorator {
    protected ScoreDecorator decoratedScore;

    // Constructeur
    public ScoreDecoratorImpl(ScoreDecorator decoratedScore) {
        this.decoratedScore = decoratedScore;
    }

    // Méthode pour dessiner le score
    @Override
    public void drawScore(Graphics g, int player1Score, int player2Score) {
        // Appelle la méthode drawScore() de l'objet décoré pour dessiner le score de base
        decoratedScore.drawScore(g, player1Score, player2Score);
    }
}
