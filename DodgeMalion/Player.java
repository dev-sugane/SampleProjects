import java.awt.*;

public class Player {
    private int x, y;
    private int width = 30;
    private int height = 30;
    private int velocityX = 0;
    private int velocityY = 0;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean onGround = false;
    
    private static final int SPEED = 5;
    private static final int JUMP_VELOCITY = -15;
    private static final int GRAVITY = 1;
    private static final int GROUND_Y = 500; // SuperMarioGameのGROUND_Yと同じ値
    
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void update() {
        // 水平移動
        if (movingLeft) {
            velocityX = -SPEED;
        } else if (movingRight) {
            velocityX = SPEED;
        } else {
            velocityX = 0;
        }
        
        // 重力とジャンプ
        velocityY += GRAVITY;
        
        // 位置更新
        x += velocityX;
        y += velocityY;
        
        // 地面との衝突判定
        if (y >= GROUND_Y - height) {
            y = GROUND_Y - height;
            velocityY = 0;
            onGround = true;
        } else {
            onGround = false;
        }
        
        // 画面端での制限
        if (x < 0) x = 0;
        if (x > 800 - width) x = 800 - width;
    }
    
    public void jump() {
        if (onGround) {
            velocityY = JUMP_VELOCITY;
            onGround = false;
        }
    }
    
    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }
    
    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    public void draw(Graphics2D g2d) {
        // マリオ風のキャラクターを描画
        g2d.setColor(Color.RED);
        g2d.fillRect(x, y, width, height);
        
        // 帽子
        g2d.setColor(Color.RED);
        g2d.fillRect(x, y, width, 8);
        
        // 顔
        g2d.setColor(new Color(255, 200, 150));
        g2d.fillRect(x + 2, y + 8, width - 4, 12);
        
        // 目
        g2d.setColor(Color.BLACK);
        g2d.fillOval(x + 6, y + 10, 4, 4);
        g2d.fillOval(x + 18, y + 10, 4, 4);
        
        // 口
        g2d.setColor(Color.BLACK);
        g2d.fillRect(x + 10, y + 16, 8, 2);
        
        // 服
        g2d.setColor(Color.BLUE);
        g2d.fillRect(x + 2, y + 20, width - 4, 10);
        
        // 足
        g2d.setColor(new Color(139, 69, 19));
        g2d.fillRect(x + 5, y + 25, 6, 5);
        g2d.fillRect(x + 17, y + 25, 6, 5);
    }
} 