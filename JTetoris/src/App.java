import javax.swing.JFrame;


public class App {
    
    public static void main(String[] args) throws Exception {

        // JFrameを用いてウィンドウを作成
        JFrame frame = new JFrame("JTetoris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 600);
        frame.setVisible(true);
        // Jpanelを用いてゲーム画面を作成
        // ゲーム画面のサイズを設定
        GamePanel gamePanel = new GamePanel();
        gamePanel.setSize(gamePanel.blockSize * 10, gamePanel.blockSize * 20); // 10x20のブロックサイズに設定
        frame.add(gamePanel);
        frame.setFocusable(true);
        gamePanel.requestFocus();
    }
}
 
