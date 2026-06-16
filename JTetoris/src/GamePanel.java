public class GamePanel extends javax.swing.JPanel {

    int bx = 0; // ブロックのx座標
    int by = 0; // ブロックのy座標
    // タイマー（ループ）を仕込む
    // コンストラクタの中でタイマーを作成し、ループさせる
    // Processingで言うvoid setup()の中でタイマーを作成するイメージ

    public GamePanel() {
        javax.swing.Timer timer = new javax.swing.Timer(1000, e -> {
            // ここにループさせたい処理を書く
            // 今は1000ミリ秒ごとに画面を再描画
            bx++; // ブロックのx座標を1増やす
            by++; // ブロックのy座標を1増やす
            repaint(); // 画面を再描画する
        });
        timer.start();
    }

    // Prosessingで言うvoid drow()
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        // ゲーム画面の背景を黒に設定
        g.setColor(java.awt.Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        drawBlock(g); // ブロックを描画する
    }

    // ブロックを描画する
    public void drawBlock(java.awt.Graphics g) {
        g.setColor(java.awt.Color.RED);
        g.fillRect(bx, by, 30, 30); // ブロックのサイズは30x30
    }

}