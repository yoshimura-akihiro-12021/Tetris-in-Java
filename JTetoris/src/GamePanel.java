
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends javax.swing.JPanel {

    int gridx = 0; // グリッドのサイズ
    int gridy = 0; // グリッドのサイズ
    final int blockSize = 30; // ブロックのサイズ
    protected boolean spacePressed;

    int[][] grid = new int[10][20];

    Tetromino[] minos = { new Imino(),
            new Omino(), new Smino(), new Zmino(), new Jmino(), new Lmino()
    };

    private int minoIndex = 0; // 現在のブロックのインデックス

    private Tetromino currentMino = minos[minoIndex];

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
                    if (!checkCollision(gridx - 1, gridy, currentMino.shape)) {
                        gridx -= 1; // 左に移動
                    }
                } else if (keyCode == java.awt.event.KeyEvent.VK_RIGHT) {
                    if (!checkCollision(gridx + 1, gridy, currentMino.shape)) {
                        gridx += 1; // 右に移動
                    }
                } else if (keyCode == java.awt.event.KeyEvent.VK_DOWN) {
                    if (!checkCollision(gridx, gridy + 1, currentMino.shape)) {
                        gridy += 1; // 下に移動
                    } else {
                        setMino(getGraphics(), gridx, gridy);
                        deleteLine();
                        minoIndex = (minoIndex + 1) % minos.length; // 次のブロックに切り替える
                        currentMino = minos[minoIndex]; // currentMinoを更新する
                        gridx = 0; // ブロックのx座標をリセットする
                        gridy = 0; // ブロックのy座標をリセットする
                        repaint(); // 画面を再描画する
                    }
                } else if (keyCode == java.awt.event.KeyEvent.VK_SPACE) {
                    // spaceキーが押されたときの処理
                    if (!checkCollision(gridx, gridy, currentMino.getRotatedShape())) {
                        currentMino.rotate();
                        repaint(); // 画面を再描画する
                    }

                }
                repaint(); // 画面を再描画する
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE) {
                    spacePressed = false;
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });

        javax.swing.Timer timer = new javax.swing.Timer(1000, e -> {
            // ここにループさせたい処理を書く
            // 今は1000ミリ秒ごとに画面を再描画するようにしている
            if (!checkCollision(gridx, gridy + 1, currentMino.shape)) {
                gridy++; // ブロックを下に移動させる
            } else {
                setMino(getGraphics(), gridx, gridy);
                deleteLine();
                minoIndex = (minoIndex + 1) % minos.length; // 次のブロックに切り替える
                currentMino = minos[minoIndex]; // currentMinoを更新する
                gridx = 0; // ブロックのx座標をリセットする
                gridy = 0; // ブロックのy座標をリセットする
                repaint(); // 画面を再描画する
            }
            System.out.println("x:" + (gridx * blockSize) + " y:" + (gridy * blockSize));
            System.out.println(" w:" + getWidth() + " h:" + getHeight());
            System.out.println(" gridx:" + gridx + " gridy:" + gridy);
            System.out.println(" blockSize:" + blockSize);
            System.out.println(" currentMino:" + currentMino);
            System.out.println(spacePressed);
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
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                if (grid[i][j] == 1) {
                    g.setColor(currentMino.getColor());
                    g.fillRect((i) * blockSize, (j) * blockSize, blockSize, blockSize);
                }
            }
        }
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(grid[j][i] + ",");
            }
            System.out.println();
        }

        drowMinos(g, gridx, gridy); // ブロックを描画する
    }

    // ブロックを描画する
    // public void drawBlock(java.awt.Graphics g) {
    // g.setColor(java.awt.Color.RED);
    // g.fillRect(gridx * blockSize, gridy * blockSize, blockSize, blockSize); //
    // ブロックのサイズは30x30
    // }

    // public void drowIMino(java.awt.Graphics g, int x, int y) {
    // IMino iMino = new IMino();
    // int[][] shape = iMino.getShape();

    // for (int i = 0; i < shape.length; i++) {
    // for (int j = 0; j < shape[i].length; j++) {
    // if (shape[i][j] == 1) {
    // g.setColor(iMino.getColor());
    // g.fillRect((x + j) * blockSize, (y + i) * blockSize, blockSize, blockSize);

    // }
    // }
    // }
    // }

    public void drowMinos(java.awt.Graphics g, int x, int y) {
        // ここに、ブロックを描画する処理を書く
        int[][] shape = currentMino.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    g.setColor(currentMino.getColor());
                    g.fillRect((x + j) * blockSize, (y + i) * blockSize, blockSize, blockSize);
                }

            }
        }
    }

    // 衝突判定のメソッド
    public boolean checkCollision(int nextX, int nextY, int[][] checkShape) {

        for (int i = 0; i < checkShape.length; i++) {
            for (int j = 0; j < checkShape[i].length; j++) {
                if (checkShape[i][j] == 1) {
                    int actualX = nextX + j;
                    int actualY = nextY + i;
                    if (actualX < 0 || actualX >= 10 || actualY >= 20 || grid[actualX][actualY] != 0) {
                        System.out.println("衝突検知！ actualX: " + actualX + ", actualY: " + actualY);
                        return true; // 衝突あり
                    }
                }
            }
        }
        return false; // 衝突なし
    }

    // ミノを固定(設置する)メソッド
    void setMino(java.awt.Graphics g, int gridX, int gridY) {
        int[][] shape = currentMino.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    grid[gridX + j][gridY + i] = 1;
                }
            }
        }
    }

    void deleteLine() {
        System.out.println("動作");
        for (int i = grid[0].length - 1; i > 1; i--) {
            boolean isContinue = false;
            for (int j = 0; j < grid.length; j++) {
                if (grid[j][i] == 0) {
                    isContinue = true;
                    break;
                }
            }
            if (isContinue) {
                continue;
            } else {
                for (int j = 0; j < grid.length; j++) {
                    for (int k = grid[0].length - 1; k > 1; k--) {
                        grid[j][k] = grid[j][k - 1];
                    }
                }
                for (int k = grid[0].length - 1; k > 1; k--) {
                    grid[0][k] = 0;
                }

                i = grid[0].length - 1;
                System.out.println("zikkou");
            }
            System.out.println("zikkou");

        }

    }
}
