import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomeFrame extends JFrame {

    private JPanel panel;
    private JLabel welcomeLabel;
    private JButton playButton;
    private JLabel imageLabel;

    public WelcomeFrame() {
        panel = new JPanel(new BorderLayout());

        // Créez un label pour contenir l'image
        imageLabel = new JLabel();

        // Chargez l'image depuis le fichier
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/img/imga.png"));

        // Affichez l'image dans le label
        imageLabel.setIcon(imageIcon);

        // Ajoutez l'image au panel
        panel.add(imageLabel, BorderLayout.CENTER);

        JPanel playPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 50));
        // Définir les couleurs pour l'animation
        Color color1 = new Color(0, 6, 48);
        Color color2 = new Color(255, 215, 0);

// Créer un Timer pour l'animation de couleur
        Timer timer = new Timer(10, new ActionListener() {
            float step = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                // Mettre à jour la couleur du fond en fonction du temps
                step += 0.01f;
                if (step >= 1.0f) {
                    step = 0.0f;
                }
                Color newColor = new Color(
                        (int) (color1.getRed() + (color2.getRed() - color1.getRed()) * step),
                        (int) (color1.getGreen() + (color2.getGreen() - color1.getGreen()) * step),
                        (int) (color1.getBlue() + (color2.getBlue() - color1.getBlue()) * step)
                );
                playPanel.setBackground(newColor);
            }
        });
        timer.start();
// Bleu marin
        playButton = new JButton("Play");
        playButton.setFont(new Font("Arial", Font.BOLD, 20));
        playButton.setBackground(Color.white);
        playButton.setForeground(Color.black);
        playButton.setPreferredSize(new Dimension(200, 50));
        playButton.setBorder(BorderFactory.createLineBorder(Color.white, 2)); // Bordure blanche de 2 pixels
        // Animation de la couleur de fond du playButton
        playButton.addMouseListener(new java.awt.event.MouseAdapter() {
            Color startColor = Color.white;
            Color endColor = new Color(255, 215, 0);
            Color currentColor = startColor;
            float[] startHSB = Color.RGBtoHSB(startColor.getRed(), startColor.getGreen(), startColor.getBlue(), null);
            float[] endHSB = Color.RGBtoHSB(endColor.getRed(), endColor.getGreen(), endColor.getBlue(), null);
            float[] currentHSB = startHSB;
            float step = 0.1f;

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Timer timer = new Timer(20, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentHSB[0] += step;
                        currentHSB[1] += step;
                        currentHSB[2] += step;
                        currentColor = new Color(Color.HSBtoRGB(currentHSB[0], currentHSB[1], currentHSB[2]));
                        playButton.setBackground(currentColor);
                    }
                });
                timer.start();
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                Timer timer = new Timer(20, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentHSB[0] -= step;
                        currentHSB[1] -= step;
                        currentHSB[2] -= step;
                        currentColor = new Color(Color.HSBtoRGB(currentHSB[0], currentHSB[1], currentHSB[2]));
                        playButton.setBackground(currentColor);
                        if (currentColor.equals(startColor)) {
                            ((Timer)e.getSource()).stop();
                        }
                    }
                });
                timer.start();
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        playPanel.add(playButton);

        panel.add(playPanel, BorderLayout.SOUTH);

        // Ajoutez du style au panel
        panel.setBackground(new Color(227, 231, 239));

        // Redimensionnez la fenêtre à une taille de 800x600
        this.setPreferredSize(new Dimension(1000, 600));
        this.add(panel);
        this.setTitle("Amine Ping-Pong Game");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        // Utilisez un ComponentListener pour redimensionner l'image
        // lorsque la fenêtre est redimensionnée
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // Récupérez la taille de la fenêtre
                int width = getWidth();
                int height = getHeight();

                // Redimensionnez l'image à la taille de la fenêtre moins la hauteur du titre de la fenêtre
                int newWidth = (int) (width * 1.2); // Augmenter la longueur de l'image de 20%
                int newHeight = height ;
                Image scaledImage = imageIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

                // Créez une nouvelle instance de ImageIcon avec l'image redimensionnée
                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                // Affichez l'image redimensionnée dans le label
                imageLabel.setIcon(scaledIcon);
            }

        });
    }

    public void startGame() {
        // Ajoutez ici le code pour lancer le jeu
        System.out.println("Starting game...");
        GameFrame frame= new GameFrame();
        this.dispose();
    }

    public static void main(String[] args)
    {
        // Créez une instance de la classe WelcomeFrame
        WelcomeFrame welcomeFrame = new WelcomeFrame();
    }
}
