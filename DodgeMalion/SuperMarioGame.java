import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class SuperMarioGame extends JPanel implements ActionListener, KeyListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PLAYER_SIZE = 30;
    private static final int ENEMY_SIZE = 25;
    private static final int COIN_SIZE = 20;
    private static final int GROUND_Y = HEIGHT - 100;
    
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Coin> coins;
    private Timer timer;
    private Random random;
    private int score = 0;
    private boolean gameOver = false;
    private boolean gameWon = false;
    
    public SuperMarioGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(135, 206, 235)); // 空色
        setFocusable(true);
        addKeyListener(this);
        
        initGame();
        
        timer = new Timer(16, this); // 約60FPS
        timer.start();
    }
    
    private void initGame() {
        player = new Player(50, GROUND_Y - PLAYER_SIZE);
        enemies = new ArrayList<>();
        coins = new ArrayList<>();
        random = new Random();
        
        // 敵を配置
        for (int i = 0; i < 5; i++) {
            int x = 200 + i * 120;
            int y = GROUND_Y - ENEMY_SIZE;
            enemies.add(new Enemy(x, y));
        }
        
        // コインを配置
        for (int i = 0; i < 8; i++) {
            int x = 150 + i * 80;
            int y = GROUND_Y - COIN_SIZE - 50;
            coins.add(new Coin(x, y));
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 地面を描画
        g2d.setColor(new Color(34, 139, 34)); // 緑色
        g2d.fillRect(0, GROUND_Y, WIDTH, HEIGHT - GROUND_Y);
        
        // プレイヤーを描画
        player.draw(g2d);
        
        // 敵を描画
        for (Enemy enemy : enemies) {
            enemy.draw(g2d);
        }
        
        // コインを描画
        for (Coin coin : coins) {
            coin.draw(g2d);
        }
        
        // スコアを表示
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("コイン: " + score + "/6", 10, 30);
        
        // ゲームオーバーまたはクリアメッセージ
        if (gameOver) {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 40));
            String gameOverText = "ゲームオーバー！";
            FontMetrics fm = g2d.getFontMetrics();
            int x = (WIDTH - fm.stringWidth(gameOverText)) / 2;
            int y = HEIGHT / 2;
            g2d.drawString(gameOverText, x, y);
            
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            String restartText = "Rキーでリスタート";
            fm = g2d.getFontMetrics();
            x = (WIDTH - fm.stringWidth(restartText)) / 2;
            g2d.drawString(restartText, x, y + 40);
        }
        
        if (gameWon) {
            g2d.setColor(Color.YELLOW);
            g2d.setFont(new Font("Arial", Font.BOLD, 40));
            String winText = "クリア！";
            FontMetrics fm = g2d.getFontMetrics();
            int x = (WIDTH - fm.stringWidth(winText)) / 2;
            int y = HEIGHT / 2;
            g2d.drawString(winText, x, y);
            
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            String restartText = "Rキーでリスタート";
            fm = g2d.getFontMetrics();
            x = (WIDTH - fm.stringWidth(restartText)) / 2;
            g2d.drawString(restartText, x, y + 40);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver && !gameWon) {
            // プレイヤーの更新
            player.update();
            
            // 敵の更新
            for (Enemy enemy : enemies) {
                enemy.update();
            }
            
            // コインとの衝突判定
            for (int i = coins.size() - 1; i >= 0; i--) {
                Coin coin = coins.get(i);
                if (player.getBounds().intersects(coin.getBounds())) {
                    coins.remove(i);
                    score++;
                    if (score >= 6) {
                        gameWon = true;
                    }
                }
            }
            
            // 敵との衝突判定
            for (Enemy enemy : enemies) {
                if (player.getBounds().intersects(enemy.getBounds())) {
                    gameOver = true;
                }
            }
        }
        
        repaint();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver || gameWon) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                restartGame();
            }
            return;
        }
        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                player.setMovingLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                player.setMovingRight(true);
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                player.jump();
                break;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                player.setMovingLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                player.setMovingRight(false);
                break;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    private void restartGame() {
        score = 0;
        gameOver = false;
        gameWon = false;
        initGame();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("スーパーマリオ風ゲーム");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(new SuperMarioGame());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
} 