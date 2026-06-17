
public class GamePanel extends javax.swing.JPanel {

    int bx = 0; // ブロックのx座標
    int by = 0; // ブロックのy座標
    final int blockSize = 30; // ブロックのサイズ

    // タイマー（ループ）を仕込む
    // コンストラクタの中でタイマーを作成し、ループさせる
    // Processingで言うvoid setup()の中でタイマーを作成するイメージ

    // Prosessingで言うvoid drow()箇所
    public GamePanel() {

        javax.swing.Timer timer = new javax.swing.Timer(1000, e -> {
            // ここにループさせたい処理を書く
            // 今は1000ミリ秒ごとに画面を再描画
            bx++; // ブロックのx座標を1増やす
            by++; // ブロックのy座標を1増やす
            System.out.println("x:" + bx + " y:" + by);
            System.out.println(" w:" + getWidth() + " h:" + getHeight());
            repaint(); // 画面を再描画する
        });
        timer.start();
    }

    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        // ゲーム画面の背景を黒に設定
        g.setColor(java.awt.Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        for (int i = 0; i < getWidth() / blockSize; i++) {
            for (int j = 0; j < getHeight() / blockSize; j++) {
                g.setColor(java.awt.Color.GRAY);
                g.drawRect(i * blockSize, j * blockSize, blockSize, blockSize); // グリッドを描画する
            }
        }
        drawBlock(g); // ブロックを描画する
    }

    // ブロックを描画する
    public void drawBlock(java.awt.Graphics g) {
        g.setColor(java.awt.Color.RED);
        g.fillRect(bx, by, blockSize, blockSize); // ブロックのサイズは30x30
    }

    // Prosessingで言うvoid keyPressed()箇所
    public void keyPressed(java.awt.event.KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == java.awt.event.KeyEvent.VK_LEFT) {
            bx -= blockSize; // 左に移動
        } else if (keyCode == java.awt.event.KeyEvent.VK_RIGHT) {
            bx += blockSize; // 右に移動
        } else if (keyCode == java.awt.event.KeyEvent.VK_DOWN) {
            by += blockSize; // 下に移動
        }
        repaint(); // 画面を再描画する
    }

}