import java.awt.*;

public class Enemy {
    private int x, y;
    private int width = 25;
    private int height = 25;
    private int velocityX = -2;
    private int direction = -1; // -1: 左, 1: 右
    private int moveRange = 100;
    private int startX;
    
    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        this.startX = x;
    }
    
    public void update() {
        // 移動範囲内での移動
        if (x <= startX - moveRange || x >= startX + moveRange) {
            direction *= -1;
        }
        
        velocityX = 2 * direction;
        x += velocityX;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    public void draw(Graphics2D g2d) {
        // クリーチャー風の敵を描画
        g2d.setColor(new Color(139, 69, 19)); // 茶色
        g2d.fillRect(x, y, width, height);
        
        // 目
        g2d.setColor(Color.RED);
        g2d.fillOval(x + 4, y + 6, 6, 6);
        g2d.fillOval(x + 15, y + 6, 6, 6);
        
        // 目の光
        g2d.setColor(Color.WHITE);
        g2d.fillOval(x + 5, y + 7, 2, 2);
        g2d.fillOval(x + 16, y + 7, 2, 2);
        
        // 口
        g2d.setColor(Color.BLACK);
        g2d.fillRect(x + 8, y + 16, 9, 3);
        
        // 角
        g2d.setColor(Color.BLACK);
        g2d.fillRect(x + 2, y + 2, 3, 6);
        g2d.fillRect(x + 20, y + 2, 3, 6);
    }
} 