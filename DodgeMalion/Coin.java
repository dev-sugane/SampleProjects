import java.awt.*;

public class Coin {
    private int x, y;
    private int width = 20;
    private int height = 20;
    private int animationFrame = 0;
    
    public Coin(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void update() {
        // アニメーション用のフレーム更新
        animationFrame = (animationFrame + 1) % 20;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    public void draw(Graphics2D g2d) {
        // コインの回転アニメーション
        int scale = 10 + (animationFrame / 2);
        
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(x + (width - scale) / 2, y + (height - scale) / 2, scale, scale);
        
        // コインの縁取り
        g2d.setColor(Color.ORANGE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(x + (width - scale) / 2, y + (height - scale) / 2, scale, scale);
        
        // コインの模様
        g2d.setColor(Color.ORANGE);
        g2d.fillOval(x + (width - scale) / 2 + 2, y + (height - scale) / 2 + 2, scale - 4, scale - 4);
        
        // コインの中心
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(x + (width - scale) / 2 + 4, y + (height - scale) / 2 + 4, scale - 8, scale - 8);
    }
} 