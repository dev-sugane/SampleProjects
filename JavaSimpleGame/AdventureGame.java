import java.util.Scanner;
import java.util.Random;

public class AdventureGame {
    private static final String[] PLAYER_ART = {
        "  ⚔️  ",
        " /|\\  ",
        " / \\  "
    };
    
    private static final String[] MONSTER_ART = {
        "  👹  ",
        " /|\\  ",
        " / \\  "
    };
    
    private static final String[] TREASURE_ART = {
        "  💎  ",
        " ╱│╲  ",
        " ╱ ╲  "
    };
    
    private static final String[] CAVE_ART = {
        "    /\\    ",
        "   /  \\   ",
        "  /____\\  ",
        " |      | ",
        " |  🕯️  | ",
        " |______| "
    };
    
    private static final String[] FOREST_ART = {
        "    🌳    ",
        "   /|\\    ",
        "  / | \\   ",
        " /  |  \\  ",
        "    |     ",
        "   / \\    "
    };
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        System.out.println("=== 冒険ゲーム ===");
        System.out.println("あなたは勇敢な冒険者です。宝物を探して旅に出ましょう！");
        System.out.println();
        
        boolean playAgain = true;
        
        while (playAgain) {
            // ゲームの初期化
            int playerHealth = 100;
            int playerGold = 0;
            int playerLevel = 1;
            int currentLocation = 0;
            String[] locations = {"森", "洞窟", "城", "地下迷宮"};
            
            System.out.println("冒険が始まります！");
            System.out.println("あなたのステータス:");
            System.out.println("❤️ 体力: " + playerHealth);
            System.out.println("💰 ゴールド: " + playerGold);
            System.out.println("⭐ レベル: " + playerLevel);
            System.out.println();
            
            // メインゲームループ
            while (playerHealth > 0 && playerGold < 100) {
                // 現在地の表示
                displayLocation(locations[currentLocation]);
                System.out.println("現在地: " + locations[currentLocation]);
                System.out.println("❤️ 体力: " + playerHealth + " | 💰 ゴールド: " + playerGold + " | ⭐ レベル: " + playerLevel);
                System.out.println();
                
                // アクション選択
                System.out.println("何をしますか？");
                System.out.println("1. 探索する");
                System.out.println("2. 休憩する");
                System.out.println("3. 次の場所へ移動");
                System.out.println("4. ゲーム終了");
                System.out.print("選択 (1-4): ");
                
                int choice = scanner.nextInt();
                System.out.println();
                
                switch (choice) {
                    case 1:
                        // 探索
                        exploreLocation(playerHealth, playerGold, playerLevel, currentLocation, random);
                        int[] result = exploreLocation(playerHealth, playerGold, playerLevel, currentLocation, random);
                        playerHealth = result[0];
                        playerGold = result[1];
                        playerLevel = result[2];
                        break;
                        
                    case 2:
                        // 休憩
                        System.out.println("🏕️  キャンプで休憩中...");
                        animateRest();
                        playerHealth = Math.min(100, playerHealth + 20);
                        System.out.println("❤️ 体力が回復しました！現在の体力: " + playerHealth);
                        break;
                        
                    case 3:
                        // 移動
                        currentLocation = (currentLocation + 1) % locations.length;
                        System.out.println("🚶 " + locations[currentLocation] + " へ移動中...");
                        animateMovement();
                        playerHealth -= 5; // 移動で体力消費
                        break;
                        
                    case 4:
                        playerHealth = 0; // ゲーム終了
                        break;
                        
                    default:
                        System.out.println("1から4までの数字を入力してください！");
                        break;
                }
                
                System.out.println();
                
                // レベルアップチェック
                if (playerGold >= playerLevel * 20) {
                    playerLevel++;
                    System.out.println("🎉 レベルアップ！レベル " + playerLevel + " になりました！");
                    System.out.println("❤️ 体力が全回復しました！");
                    playerHealth = 100;
                }
                
                // ゲームオーバーチェック
                if (playerHealth <= 0) {
                    System.out.println("💀 あなたは力尽きてしまいました...");
                    System.out.println("ゲームオーバー");
                } else if (playerGold >= 100) {
                    System.out.println("🏆 おめでとうございます！");
                    System.out.println("あなたは宝物を集め、伝説の冒険者になりました！");
                    System.out.println("最終スコア: レベル " + playerLevel + ", ゴールド " + playerGold);
                }
            }
            
            // 再プレイの確認
            System.out.print("もう一度プレイしますか？ (y/n): ");
            String playAgainInput = scanner.next().toLowerCase();
            playAgain = playAgainInput.equals("y") || playAgainInput.equals("yes");
            System.out.println();
        }
        
        System.out.println("冒険を終了します。お疲れ様でした！");
        scanner.close();
    }
    
    private static int[] exploreLocation(int health, int gold, int level, int location, Random random) {
        int event = random.nextInt(4);
        
        switch (event) {
            case 0:
                // モンスターとの戦い
                System.out.println("⚔️ モンスターが現れた！");
                displayBattle();
                int damage = random.nextInt(20) + 10;
                int reward = random.nextInt(15) + 5;
                health -= damage;
                gold += reward;
                System.out.println("💥 " + damage + " のダメージを受けた！");
                System.out.println("💰 " + reward + " ゴールドを獲得した！");
                break;
                
            case 1:
                // 宝箱発見
                System.out.println("🎁 宝箱を発見した！");
                displayTreasure();
                int treasure = random.nextInt(25) + 10;
                gold += treasure;
                System.out.println("💰 " + treasure + " ゴールドを獲得した！");
                break;
                
            case 2:
                // 何も見つからない
                System.out.println("🌿 何も見つからなかった...");
                health -= 5;
                System.out.println("💔 体力を少し消耗した");
                break;
                
            case 3:
                // 回復の泉
                System.out.println("💧 回復の泉を発見した！");
                displayFountain();
                health = Math.min(100, health + 30);
                System.out.println("❤️ 体力が回復した！");
                break;
        }
        
        return new int[]{health, gold, level};
    }
    
    private static void displayLocation(String location) {
        System.out.println();
        switch (location) {
            case "森":
                for (String line : FOREST_ART) {
                    System.out.println(line);
                }
                break;
            case "洞窟":
                for (String line : CAVE_ART) {
                    System.out.println(line);
                }
                break;
            case "城":
                System.out.println("    🏰    ");
                System.out.println("   /|\\    ");
                System.out.println("  / | \\   ");
                System.out.println(" /  |  \\  ");
                System.out.println("    |     ");
                System.out.println("   / \\    ");
                break;
            case "地下迷宮":
                System.out.println("    🕳️     ");
                System.out.println("   /|\\    ");
                System.out.println("  / | \\   ");
                System.out.println(" /  |  \\  ");
                System.out.println("    |     ");
                System.out.println("   / \\    ");
                break;
        }
        System.out.println();
    }
    
    private static void displayBattle() {
        System.out.println("⚔️ 戦闘開始！ ⚔️");
        System.out.println();
        for (int i = 0; i < PLAYER_ART.length; i++) {
            System.out.println(PLAYER_ART[i] + "  vs  " + MONSTER_ART[i]);
        }
        System.out.println();
    }
    
    private static void displayTreasure() {
        System.out.println();
        for (String line : TREASURE_ART) {
            System.out.println(line);
        }
        System.out.println();
    }
    
    private static void displayFountain() {
        System.out.println("    💧    ");
        System.out.println("   ╱│╲   ");
        System.out.println("  ╱ │ ╲  ");
        System.out.println(" ╱  │  ╲ ");
        System.out.println("    │     ");
        System.out.println("   ╱ ╲   ");
        System.out.println();
    }
    
    private static void animateRest() {
        String[] frames = {"😴", "😪", "😴", "😪"};
        for (String frame : frames) {
            System.out.print("\r" + frame + " 休憩中...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }
    
    private static void animateMovement() {
        String[] frames = {"🚶", "🏃", "🚶", "🏃"};
        for (String frame : frames) {
            System.out.print("\r" + frame + " 移動中...");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }
} 