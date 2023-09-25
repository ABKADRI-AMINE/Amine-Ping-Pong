public class MoveCommand implements ICommand {

    // Déclaration des variables membres de la classe
    private Paddle paddle;  // Objet Paddle qui représente la raquette à bouger
    private int yDirection; // Direction de mouvement de la raquette (vers le haut ou le bas) ina direction ghn7rkoha

    // Constructeur de la classe, prend en paramètre l'objet Paddle à bouger et la direction de mouvement
    public MoveCommand(Paddle paddle, int yDirection) {
        this.paddle = paddle;
        this.yDirection = yDirection;
    }

    // Implémentation de la méthode execute() de l'interface ICommand
    @Override
    public void execute() {
        // Met à jour la direction de mouvement de la raquette
        paddle.setYDirection(yDirection);
        // Déplace la raquette d'une unité dans la direction choisie
        paddle.move();
    }
}
