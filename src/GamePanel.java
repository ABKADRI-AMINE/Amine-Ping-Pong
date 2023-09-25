import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable, TimeObserver {
    static  final int GAME_WIDTH = 1000;
    static  final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    //player 1
    Paddle paddle1;
    //player 2
    Paddle paddle2;
    Ball ball;
    private BallBuilder ballBuilder = new BallBuilder();
    Score score;
    //constructor
    TimeObservable timeObservable;
    JFrame frame;
    // Création d'un objet JLabel pour afficher le chronomètre
    JLabel timerLabel = new JLabel("Time left: 40 seconds");
    GamePanel() {
        add(timerLabel);


        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        timeObservable = new TimeObservable();
        timeObservable.addObserver(this);
        timeObservable.start();
        random = new Random();
        newPaddles();
        newBallBuilder();
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void updateTime() {
        if (timeObservable.getTimeElapsed() == 40) {
            // Fermer la fenêtre actuelle
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            currentFrame.dispose();

            // Ouvrir la fenêtre du message
            JFrame messageFrame = new JFrame("Game Over");
            messageFrame.setSize(new Dimension(1000, 600)); // définition de la taille de la fenêtre
            messageFrame.setLocationRelativeTo(null);
            messageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            String winner;
            if (score.player1 > score.player2) {
                winner = "Player 1 wins ";
            } else if (score.player2 > score.player1) {
                winner = "Player 2 wins ";
            } else {
                winner = "Tie game";
            }

            // Créer un JPanel pour le titre
            JPanel titlePanel = new JPanel();
            titlePanel.setBackground(Color.BLACK);

// Ajouter un JLabel pour le titre "Game Over"
            JLabel gameOverLabel = new JLabel("Game Over - "+winner , SwingConstants.CENTER);
            gameOverLabel.setForeground(Color.WHITE);
            gameOverLabel.setFont(new Font("Arial", Font.BOLD, 30));
            gameOverLabel.setPreferredSize(new Dimension(600, 70));
            gameOverLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));


// Ajouter les JLabels au JPanel
            titlePanel.add(gameOverLabel);
messageFrame.setResizable(false);

// Ajouter le JPanel à la barre de titre de la fenêtre
            messageFrame.getContentPane().add(titlePanel, BorderLayout.NORTH);

// Créer un Timer pour faire clignoter le texte du JLabel en rouge
            Timer timer = new Timer(500, new ActionListener() {
                boolean isRed = false;
                public void actionPerformed(ActionEvent e) {
                    if (isRed) {
                        gameOverLabel.setForeground(Color.WHITE);
//                        winnerLabel.setForeground(Color.WHITE);
                    } else {
                        gameOverLabel.setForeground(Color.RED);
//                        winnerLabel.setForeground(Color.RED);
                    }
                    isRed = !isRed;
                }
            });
            timer.start();



            // Créer un panel pour les boutons
            JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));

            // Créer un bouton pour rejouer
            JButton rejouerButton = new JButton("Replay");
            rejouerButton.setFont(new Font("Arial", Font.BOLD, 20));
            rejouerButton.setBackground(Color.black);
            rejouerButton.setForeground(Color.red);
            rejouerButton.setPreferredSize(new Dimension(200, 50));
            rejouerButton.setBorder(BorderFactory.createLineBorder(Color.white, 2));
            rejouerButton.addMouseListener(new java.awt.event.MouseAdapter() {
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
                            rejouerButton.setBackground(currentColor);
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
                            rejouerButton.setBackground(currentColor);
                            if (currentColor.equals(startColor)) {
                                ((Timer)e.getSource()).stop();
                            }
                        }
                    });
                    timer.start();
                }
            });
            rejouerButton.addActionListener(e -> {
                // Fermer la fenêtre du message
                messageFrame.dispose();

                // Créer une nouvelle fenêtre pour le jeu
                JFrame gameFrame = new JFrame(" Amine Pong Game");
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameFrame.setResizable(false);
                gameFrame.add(new GamePanel());
                gameFrame.pack();
                gameFrame.setLocationRelativeTo(null);
                gameFrame.setVisible(true);
            });
            buttonPanel.add(rejouerButton);

            // Créer un bouton pour quitter
            JButton quitterButton = new JButton("Quit");
            quitterButton.addActionListener(e -> {
                System.exit(0);
            });
            quitterButton.setFont(new Font("Arial", Font.BOLD, 20));
            quitterButton.setBackground(Color.black);
            quitterButton.setForeground(Color.red);
            quitterButton.setPreferredSize(new Dimension(200, 50));
            quitterButton.setBorder(BorderFactory.createLineBorder(Color.white, 2));
            quitterButton.addMouseListener(new java.awt.event.MouseAdapter() {
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
                            quitterButton.setBackground(currentColor);
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
                            quitterButton.setBackground(currentColor);
                            if (currentColor.equals(startColor)) {
                                ((Timer)e.getSource()).stop();
                            }
                        }
                    });
                    timer.start();
                }
            });
            buttonPanel.add(quitterButton);

            JPanel messagePanel = new JPanel(new BorderLayout());

// Créez un label pour contenir l'image
            JLabel imageLabel = new JLabel();

// Chargez l'image depuis le fichier
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/img/img5.png"));

// Affichez l'image dans le label
            imageLabel.setIcon(imageIcon);

// Créez un panel pour centrer l'image
            JPanel centerPanel = new JPanel(new GridBagLayout());
            centerPanel.add(imageLabel);

// Ajoutez le panel centré à messagePanel
            messagePanel.add(centerPanel, BorderLayout.CENTER);

// Ajoutez les boutons en bas de messagePanel
            messagePanel.add(buttonPanel, BorderLayout.SOUTH);

            messageFrame.getContentPane().add(messagePanel, BorderLayout.CENTER);
            messageFrame.setVisible(true);

// Centrer les boutons dans la fenêtre
            Dimension messageSize = messagePanel.getPreferredSize();
            int x = (messageFrame.getWidth() - messageSize.width) / 2;
            int y = (messageFrame.getHeight() - messageSize.height) / 2;
            buttonPanel.setBounds(x, y, messageSize.width, messageSize.height);

        }
    }





    //a chaque fois que nous appelons cette methode on creera une nouvelle ball sur l'ecran
    private void newBallBuilder() {
        Director director = new Director(new BallBuilder());
        ball = director.constructBall();
    }

    //a chaque fois que nous appelons cette methode on creera une nouvelle Paddle sur l'ecran
    public void newPaddles(){
        //on creer les 2 paddles en passant en argument la position de chaque paddle
        paddle1= new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
        paddle2= new Paddle(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);


    }
    //pour que nous puission peindre des choses sur l'ecran
    public void paint(Graphics g) {
        // Création d'une nouvelle image à partir de la taille de l'élément graphique
        image = createImage(getWidth(), getHeight());

        // Récupération d'un objet "Graphics" associé à l'image nouvellement créée
        graphics = image.getGraphics();

        // Dessin du fond du jeu
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, getWidth(), getHeight());

        // Dessin des éléments du jeu (paddle, ball, etc.)
        draw(graphics);

        // get the remaining time from TimeObservable
        // get the remaining time from TimeObservable
        long timeLeft = 40 - timeObservable.getTimeElapsed();

// convert the remaining time to a String in the format of mm:ss
        String timeLeftStr = String.format("%02d:%02d", timeLeft / 60, timeLeft % 60);

// set the text of the timerLabel to the remaining time String
        timerLabel.setText(timeLeftStr);

// make the timerLabel blink red if there are 5 seconds or less left


        // set the text of the timerLabel to the remaining time String
        timerLabel.setText(timeLeftStr);

        // get the preferred size of the timerLabel
        Dimension size = timerLabel.getPreferredSize();

        // set the color of the text to black
//
        if (timeLeft <= 5) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.BLACK);
        }

        // draw the timerLabel on the graphics context
        g.setFont(timerLabel.getFont());
        g.drawString(timeLeftStr, getWidth() - size.width - 10, getHeight() - size.height - 10);

        // Dessin de l'image sur l'élément graphique en utilisant la méthode "drawImage" de l'objet Graphics "g".
        // Les coordonnées de l'image sont spécifiées (0,0) et "this" est utilisé pour indiquer que l'élément graphique
        // actuel doit être utilisé comme observateur de l'événement de dessin. Cela permet de mettre à jour l'élément
        // graphique à chaque fois que l'image est redessinée.
        g.drawImage(image, 0, 0, this);
        g.drawString(timeLeftStr,20,20);
    }



    public void draw(Graphics g){
    //pour dessiner les 2 raquette et la ball
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
    }
    //pour rendre le deplacement de la raquette/ball un peu souple et rapide
    public void move(){
paddle1.move();
paddle2.move();
ball.move();

    }
    public void checkCollision(){
        //empecher la ball de depasser le cadre du jeu (haut + bas)
        if(ball.y<=0){
            ball.setYDirection(-ball.yVelocity);
        }
        if(ball.y>=GAME_HEIGHT-BALL_DIAMETER){
            ball.setYDirection(-ball.yVelocity);
        }

        //empecher la balle de depasser le cadre du jeu (gauche+droit) quand il conffronte la raquette
        if(ball.intersects(paddle1)) {
            //renverser la vitesse de la ball pour quand il intersecte avec la raquette il prend une direction opposee
       ball.xVelocity = Math.abs(ball.xVelocity);
       //quand la ball heurte la raquette il change sa direction et sa vitesse augmente
            ball.xVelocity++;

            if(ball.yVelocity>0)
                ball.yVelocity++;//de meme augemntez la vitesse pour rendre le jeu plus difficile
                else
                    ball.yVelocity--;
                ball.setXDirection(ball.xVelocity);
                ball.setYDirection(ball.yVelocity);

        }

        if(ball.intersects(paddle2)) {
            //renverser la vitesse de la ball pour quand il intersecte avec la raquette il prend une direction opposee
            ball.xVelocity = Math.abs(ball.xVelocity);
            //quand la ball heurte la raquette il change sa direction et sa vitesse augmente
            ball.xVelocity++;

            if(ball.yVelocity>0)
                ball.yVelocity++;//de meme augemntez la vitesse pour rendre le jeu plus difficile
            else
                ball.yVelocity--;
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);

        }

//empecher les raquettes de depasser le cadre du jeu
        //pour le cote haut de la raquette1
        if(paddle1.y<=0)
            paddle1.y=0;
        //pour le cote bas de la raquette1
        if(paddle1.y>=(GAME_HEIGHT-PADDLE_HEIGHT))
            paddle1.y=GAME_HEIGHT-PADDLE_HEIGHT;
        //pour le cote haut de la raquette2
        if(paddle2.y<=0)
            paddle2.y=0;
        //pour le cote bas de la raquette2
        if(paddle2.y>=(GAME_HEIGHT-PADDLE_HEIGHT))
            paddle2.y=GAME_HEIGHT-PADDLE_HEIGHT;

        //give a player 1 point and creates new paddles and ball
//        si le player 2 marque
        if(ball.x<=0){
        score.player2++;
        newPaddles();
            newBallBuilder();
        System.out.println("Player 2:"+score.player2);
        }
//si le player 1 marque
        if(ball.x>=GAME_WIDTH-BALL_DIAMETER){
            score.player1++;
            newPaddles();
            newBallBuilder();
            System.out.println("Player 1:"+score.player1);

        }
    }

    public void run() {
        // Définition d'une méthode publique "run" pour exécuter la boucle de jeu

        //game loop
        long lastTime = System.nanoTime();
        // Initialisation du temps de la dernière mise à jour de la boucle de jeu

        double amountOfTicks = 60.0;
        // Définition du nombre de mises à jour par seconde que la boucle de jeu doit effectuer

        double ns = 1000000000 / amountOfTicks;
        // Calcul de la durée en nanosecondes que chaque mise à jour doit durer

        double delta = 0;

        // TimeObservable instance
        TimeObservable timeObservable = new TimeObservable();
        timeObservable.addObserver(() -> {
            // check if 2 minutes have elapsed
            if (timeObservable.getTimeElapsed() >= 40) {
                // end the game
                gameThread.interrupt();
            }
        });
        timeObservable.start();
        // Initialisation du temps écoulé depuis la dernière mise à jour

        while (true) {
            long now = System.nanoTime();
            // Obtenir le temps actuel pour calculer la durée de chaque mise à jour

            delta += (now - lastTime) / ns;
            // Ajouter le temps écoulé depuis la dernière mise à jour au delta

            lastTime = now;
            // Mettre à jour le temps de la dernière mise à jour

            if(delta>=1){
                // Si le temps écoulé dépasse la durée de mise à jour d'une image

                move();
                // Appeler une méthode pour déplacer les objets du jeu

                checkCollision();
                // Appeler une méthode pour vérifier les collisions entre les objets

                repaint();
                // Appeler la méthode "repaint" pour mettre à jour l'affichage du jeu

                delta--;
                // Soustraire la durée d'une mise à jour de l'horloge delta
            }
        }
    }

    public class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e){
        paddle1.keyPressed(e);
        paddle2.keyPressed(e);
        }
        public void keyReleased(KeyEvent e){
        paddle1.keyReleased(e);
        paddle2.keyReleased(e);
        }
    }
}
