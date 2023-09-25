import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    //creer une nouvelle instance de gamepanel
    GamePanel panel;
    //constructor
    GameFrame(){

        // Création d'un nouveau panneau de jeu (instance de la classe GamePanel) et stockage dans une variable appelée "panel"
        panel = new GamePanel();

        // Ajout du panneau de jeu créé précédemment à cette instance de JFrame (classe de fenêtre Java)
        this.add(panel);

        // Définition du titre de la fenêtre
        this.setTitle("AMINE PONG-GAME");

        // Empêche la redimensionnement de la fenêtre par l'utilisateur
        this.setResizable(false);

        // Définition de la couleur d'arrière-plan de la fenêtre
        this.setBackground(Color.white);

        // Fermeture de la fenêtre lorsqu'elle est fermée quand on clique sur x
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ajustement automatique de la taille de la fenêtre pour s'adapter au contenu
        this.pack();

        // Rend la fenêtre visible à l'utilisateur
        this.setVisible(true);
        // Définit la position de la fenêtre à afficher par rapport à l'écran. Si l'argument est "null", alors la fenêtre est centrée sur l'écran.
        this.setLocationRelativeTo(null);

    }
}
