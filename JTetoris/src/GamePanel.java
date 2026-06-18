
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends javax.swing.JPanel {

    int bx = 0; // ブロックのx座標
    int by = 0; // ブロックのy座標
    int gridx = 0; // グリッドのサイズ
    int gridy = 0; // グリッドのサイズ
    final int blockSize = 30; // ブロックのサイズ

    // タイマー（ループ）を仕込む
    // コンストラクタの中でタイマーを作成し、ループさせる
    // Processingで言うvoid setup()の中でタイマーを作成するイメージ

    // Prosessingで言うvoid drow()箇所
    public GamePanel() {

        setFocusable(true);

        addKeyListener(new KeyListener() {
            // Prosessingで言うvoid keyPressed()箇所
            public void keyPressed(java.awt.event.KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == java.awt.event.KeyEvent.VK_LEFT) {
                    gridx -= 1; // 左に移動
                } else if (keyCode == java.awt.event.KeyEvent.VK_RIGHT) {
                    gridx += 1; // 右に移動
                } else if (keyCode == java.awt.event.KeyEvent.VK_DOWN) {
                    gridy += 1; // 下に移動
                }
                repaint(); // 画面を再描画する
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });

        javax.swing.Timer timer = new javax.swing.Timer(1000, e -> {
            // ここにループさせたい処理を書く
            // 今は1000ミリ秒ごとに画面を再描画するようにしている
            gridy++; // ブロックを下に移動させる
            System.out.println("x:" + (gridx * blockSize) + " y:" + (gridy * blockSize));
            System.out.println(" w:" + getWidth() + " h:" + getHeight());
            System.out.println(" gridx:" + gridx + " gridy:" + gridy);

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
        drowIMino(g, gridx, gridy); // ブロックを描画する
    }

    // ブロックを描画する
    // public void drawBlock(java.awt.Graphics g) {
    //     g.setColor(java.awt.Color.RED);
    //     g.fillRect(gridx * blockSize, gridy * blockSize, blockSize, blockSize); // ブロックのサイズは30x30
    // }

    public void drowIMino(java.awt.Graphics g, int x, int y) {
        IMino iMino = new IMino();
        int[][] shape = iMino.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    g.setColor(java.awt.Color.CYAN);
                    g.fillRect((x + j) * blockSize, (y + i) * blockSize, blockSize, blockSize);
                }
            }
        }
    }

}