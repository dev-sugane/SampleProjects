import java.util.Scanner;

public class GameMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Java コンソールゲーム集 ===");
        System.out.println();
        
        boolean exit = false;
        
        while (!exit) {
            System.out.println("プレイしたいゲームを選択してください：");
            System.out.println("1. 冒険ゲーム");
            System.out.println("2. 終了");
            System.out.print("選択 (1-2): ");
            
            if (!scanner.hasNextInt()) {
                System.out.println("有効な数字を入力してください！");
                scanner.next();
                System.out.println();
                continue;
            }
            
            int choice = scanner.nextInt();
            System.out.println();
            
            switch (choice) {
                case 1:
                    System.out.println("冒険ゲームを開始します...");
                    System.out.println("----------------------------------------");
                    AdventureGame.main(args);
                    System.out.println("----------------------------------------");
                    break;
                    
                case 2:
                    exit = true;
                    System.out.println("ゲーム集を終了します。お疲れ様でした！");
                    break;
                    
                default:
                    System.out.println("1から2までの数字を入力してください！");
                    break;
            }
            
            if (!exit) {
                System.out.println();
            }
        }
        
        scanner.close();
    }
} 