package TicTacToe;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, MouseListener {

    public static final int WIDTH = 300;
    public static final int HEIGHT = 300;
    
    private BufferedImage image;
    private Graphics2D g;
    
    private Thread thread;
    private boolean running;

    private int FPS = 30;
    private int targetTime = 1000 / FPS;

    private int[] position;

    private int x, y;

    private boolean player1Turn;

    private int win;

    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();
        if(thread == null) {
            thread = new Thread(this);
            thread.start();
        }
        addMouseListener(this);
    }

    public void run() {
        init();

        long startTime;
        long urdTime;
        long waitTime;

        while(running) {
            startTime = System.nanoTime();
            update();
            render();
            draw();

            urdTime = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - urdTime;
            if(waitTime < 0) waitTime = targetTime;

            try {
                Thread.sleep(waitTime);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {
        running = true;
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();

        position = new int[9];
        for(int i = 0; i < 9; i++) position[i] = 0;

        player1Turn = true;

        x = -1; y = -1;

        win = -2;
    }

    private boolean check(int player) {
        if(position[0] == player && position[0] == position[1] && position[1] == position[2]) return true;
        else if(position[3] == player && position[3] == position[4] && position[4] == position[5]) return true;
        else if(position[6] == player && position[6] == position[7] && position[7] == position[8]) return true;
        else if(position[0] == player && position[0] == position[3] && position[3] == position[6]) return true;
        else if(position[1] == player && position[1] == position[4] && position[4] == position[7]) return true;
        else if(position[2] == player && position[2] == position[5] && position[5] == position[8]) return true;
        else if(position[0] == player && position[0] == position[4] && position[4] == position[8]) return true;
        else if(position[2] == player && position[2] == position[4] && position[4] == position[6]) return true;
        return false;
    }

    private void update() {
        int location;
        if(player1Turn) 
            location = x + y * 3;
        else {
            Minimax mnx = new Minimax();
            location = mnx.best_solution(position);
        }
        if(location < 0) return;

        if(position[location] == 0) {
            if(player1Turn) {
                position[location] = 1;
            }
            else {
                position[location] = 2;
            }
            player1Turn = !player1Turn;
        }

        if(check(1)) {
            win = 1;
            running = false;
        }
        else if(check(2)) {
            win = 2;
            running = false;
        }
    }

    private void render() {
        g.setColor(new Color(255, 182, 203));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.BLACK);        
        g.setFont(new Font("Arial", Font.PLAIN, 18));

        for(int i = 0; i < 9; i++) {
            g.drawRect(i % 3 * 100, i / 3 * 100, 100, 100);
            if(position[i] == 1) {
                g.drawLine(i%3 * 100 + 20, i/3 * 100 + 20, (i%3+1) * 100 - 20, (i/3+1) * 100 - 20);
                g.drawLine((i%3+1) * 100 - 20, i/3 * 100 + 20, i%3 * 100 + 20, (i/3+1) * 100 - 20);
            }
            else if(position[i] == 2) {
                g.drawOval(i % 3 * 100 + 20, i / 3 * 100 + 20, 60, 60);
            }
        }

        if(win == 1) {
            g.drawString("PLAYER 1 WINS !", 150 - 70, 150 - 20);
        }
        else if(win == 2) {
            g.drawString("PLAYER 2 WINS !", 150 - 70, 150 - 20);
        }
        
    }

    private void draw() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        x = e.getX() / 100;
        y = e.getY() / 100;
        // System.out.println(x + " " + y);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
             
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

}