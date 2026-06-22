import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;

public class GamePanel extends javax.swing.JPanel {

    int imageState = 0; // 0: メニュー画面, 1: ゲーム画面, 2: ゲームオーバー画面
    int selectId = 0; // 0: リトライ, 1: メインメニュー
    int menuSelectId = 0;
    int score = 0; // スコアの初期値を0に設定
    int gridx = 3; // ミノの初期位置を左上に設定
    int gridy = 0; // ミノの初期位置を左上に設定
    final int blockSize = 30; // ブロックのサイズを30ピクセルに設定
    protected boolean spacePressed; // スペースキーが押されているかどうかを追跡するフラグ
    int[][] grid = new int[10][20]; // グリッドの初期化（10列20行）
    // Tetromino[] minos = { new Imino(), new Omino(), new Smino(), new Zmino(), new
    // Jmino(), new Lmino() }; // ミノの配列を初期化
    // private int minoIndex = 0; // 現在のミノのインデックスを追跡する変数

    // 現在のミノと、新しく追加する「ホールド」「Nextキュー」の変数
    private Tetromino holdMino = null; // ホールドされているミノ（最初は空っぽ）
    private boolean canHold = true; // 1落下につき1回だけホールドできるフラグ

    // 💡 先のミノを溜めておくキュー（待機列）
    private java.util.List<Tetromino> nextQueue = new java.util.ArrayList<>();

    // 💡 7バッグ用の「シャッフルする袋」
    private java.util.List<Integer> bag = new java.util.ArrayList<>();
    private Tetromino currentMino; // 現在のミノを初期化

    int level = 1; // 書記レベル
    int speed = 1000; // レベルに応じた速度
    javax.swing.Timer timer; // timerを外からいじれるように

    final int[][][] srsData = {
            // 0(上) -> 1(右) への回転
            { { 0, 0 }, { -1, 0 }, { -1, -1 }, { 0, 2 }, { -1, 2 } },
            // 1(右) -> 2(下) への回転
            { { 0, 0 }, { 1, 0 }, { 1, 1 }, { 0, -2 }, { 1, -2 } },
            // 2(下) -> 3(左) への回転
            { { 0, 0 }, { 1, 0 }, { 1, -1 }, { 0, 2 }, { 1, 2 } },
            // 3(左) -> 0(上) への回転
            { { 0, 0 }, { -1, 0 }, { -1, 1 }, { 0, -2 }, { -1, -2 } }
    };

    final int[][][] srsDataI = {
            // 0(上) -> 1(右) への回転
            { { 0, 0 }, { -2, 0 }, { 1, 0 }, { -2, 1 }, { 1, -2 } },
            // 1(右) -> 2(下) への回転
            { { 0, 0 }, { -1, 0 }, { 2, 0 }, { -1, -2 }, { 2, 1 } },
            // 2(下) -> 3(左) への回転
            { { 0, 0 }, { 2, 0 }, { -1, 0 }, { 2, -1 }, { -1, 2 } },
            // 3(左) -> 0(上) への回転
            { { 0, 0 }, { 1, 0 }, { -2, 0 }, { 1, 2 }, { -2, -1 } }
    };

    public GamePanel() {
        setFocusable(true);
        this.setPreferredSize(new java.awt.Dimension(500, 600));
        resetGame();
        // 1キーボード入力
        addKeyListener(new KeyListener() {
            public void keyPressed(java.awt.event.KeyEvent e) {

                switch (imageState) {
                    case 0:
                        keyMenu(e.getKeyCode());
                        break;
                    case 1:
                        keyGame(e.getKeyCode());
                        break;
                    case 2:
                        keyGameOver(e.getKeyCode());
                        break;
                    case 3:
                        keyHowToPlay(e.getKeyCode());
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE)
                    spacePressed = false;
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });

        timer = new javax.swing.Timer(speed, e -> {
            switch (imageState) {
                case 0:
                    updateMenu();
                    break;
                case 1:
                    updateGame();
                    break;
                case 2:
                    updateGameOver();
                    break;
                case 3:
                    break;
            }
            repaint();
        });
        timer.start();
    }

    @Override
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        switch (imageState) {
            case 0:
                drawMenu(g);
                break;
            case 1:
                drawGame(g);
                break;
            case 2:
                drawGameOver(g);
                break;
            case 3:
                drawHowToPlay(g);
                break;
        }
    }

    void drawMenu(java.awt.Graphics g) {
        g.setColor(java.awt.Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setFont(new java.awt.Font("MS ゴシック", java.awt.Font.BOLD, 26));

        // タイトル
        g.setColor(java.awt.Color.CYAN);
        g.drawString("TETRIS GAME", getWidth() / 2 - 90, getHeight() / 2 - 80);

        // 選択肢の描画
        g.setFont(new java.awt.Font("MS ゴシック", java.awt.Font.BOLD, 20));

        if (menuSelectId == 0) {
            g.setColor(java.awt.Color.WHITE);
            g.drawString("-> ゲームスタート", getWidth() / 2 - 90, getHeight() / 2);
            g.setColor(java.awt.Color.GRAY);
            g.drawString("   操作説明", getWidth() / 2 - 90, getHeight() / 2 + 40);
        } else {
            g.setColor(java.awt.Color.GRAY);
            g.drawString("   ゲームスタート", getWidth() / 2 - 90, getHeight() / 2);
            g.setColor(java.awt.Color.WHITE);
            g.drawString("-> 操作説明", getWidth() / 2 - 90, getHeight() / 2 + 40);
        }
    }

    void updateMenu() {
        // メインメニュー中に裏でタイマーによって動かしたい処理（今は特になし）
    }

    void keyMenu(int keyCode) {
        if (keyCode == java.awt.event.KeyEvent.VK_UP || keyCode == java.awt.event.KeyEvent.VK_DOWN) {
            // 上下キーで選択肢（0 と 1）を切り替える
            menuSelectId = (menuSelectId == 0) ? 1 : 0;
            repaint();
        } else if (keyCode == java.awt.event.KeyEvent.VK_ENTER) {
            if (menuSelectId == 0) {
                imageState = 1; // ゲーム開始
                resetGame();
            } else {
                imageState = 3; // 操作説明画面へ
            }
            repaint();
        }
    }

    // 操作説明画面の描画
    void drawHowToPlay(java.awt.Graphics g) {
        g.setColor(java.awt.Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setFont(new java.awt.Font("MS ゴシック", java.awt.Font.BOLD, 24));
        g.setColor(java.awt.Color.YELLOW);
        g.drawString("【 操作説明 】", getWidth() / 2 - 70, 80);

        g.setFont(new java.awt.Font("MS ゴシック", java.awt.Font.BOLD, 18));
        g.setColor(java.awt.Color.WHITE);

        int startY = 160;
        int lineGap = 40;

        g.drawString("← / → キー ： ミノの左右移動", 80, startY);
        g.drawString("↓ キー       ： 加速", 80, startY + lineGap);
        g.drawString("↑ キー       ： ハードドロップ", 80, startY + lineGap * 2);
        g.drawString("SPACE キー   ： ミノを回転", 80, startY + lineGap * 3);
        g.drawString("C キー        ： ホールド", 80, startY + lineGap * 4);

        g.setColor(java.awt.Color.GREEN);
        g.setFont(new java.awt.Font("MS ゴシック", java.awt.Font.BOLD, 16));
        g.drawString("Enterを押してメニューに戻る", getWidth() / 2 - 120, 420);
    }

    // 操作説明画面でのキー入力（Enterでメニューに戻る）
    void keyHowToPlay(int keyCode) {
        if (keyCode == java.awt.event.KeyEvent.VK_ENTER) {
            imageState = 0; // メインメニューに戻る
            repaint();
        }
    }

    void drawGame(java.awt.Graphics g) {
        // 背景黒

        g.setColor(java.awt.Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // グリッド線
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                g.setColor(java.awt.Color.GRAY);
                g.drawRect(i * blockSize, j * blockSize, blockSize, blockSize);
            }
        }
        // 固定ブロックの描画
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != 0) {
                    setGridColor(g, grid[i][j]);
                    g.fillRect(i * blockSize + 1, j * blockSize + 1, blockSize - 2, blockSize - 2);
                }
            }
        }

        // 枠線の色（白）
        g.setColor(java.awt.Color.WHITE);
        g.setFont(new java.awt.Font("MS ゴシック", java.awt.Font.BOLD, 16));

        // holdの描画
        g.drawString("HOLD", 320, 40);
        g.drawRect(315, 50, 80, 80); // ホールド用の四角い枠線
        if (holdMino != null) {
            // ホールドされているミノを、枠の中に少し小さめ（1マス20ピクセルなど）にして描画する
            drawPreviewMino(g, 325, 65, holdMino, 18);
        }

        // scoreの描画
        g.drawString("SCORE", 320, 180);
        g.drawString(String.valueOf(score), 320, 205);
        g.drawString("LEVEL", 320, 245);
        g.drawString(String.valueOf(level), 320, 270);

        // 5個先のミノの描画
        g.drawString("NEXT", 420, 40);

        // nextQueueの先頭から5個分をループで縦に並べて描画
        for (int i = 0; i < 5; i++) {
            if (i < nextQueue.size()) {
                Tetromino nextMino = nextQueue.get(i);

                int previewSize = (i == 0) ? 20 : 15;
                int gapY = (i == 0) ? 0 : (i - 1) * 55 + 65; // 縦の間隔を調整
                int startY = 55 + gapY;

                // ミノのプレビューを描画
                drawPreviewMino(g, 425, startY, nextMino, previewSize);
            }
        }

        // 動くミノの描画
        drawMinos(g, gridx, gridy);
    }

    // HOLDやNEXTの枠内にミノを縮小・調整して描画するためのメソッド
    private void drawPreviewMino(java.awt.Graphics g, int drawX, int drawY, Tetromino mino, int size) {
        int[][] shape = mino.getShape();
        g.setColor(mino.getColor());

        // ミノの種類やサイズごとに、中央に見えるように位置を微調整する
        int offsetX = 0;
        int offsetY = 0;

        if (mino.colorId == 2) { // Oミノ（黄色い四角）の場合

            offsetX = size / 2 - 12;
            offsetY = size / 2 - 12;
        } else if (shape.length == 4) { // Iミノなど、4x4配列の場合
            offsetY = -size / 2;
        } else if (shape.length == 3) { // T, S, Z, J, Lなど、3x3配列の場合
            offsetX = size / 4;
            offsetY = size / 4;
        }

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    // 計算した offsetX, offsetY を足して描画する
                    g.fillRect(drawX + (j * size) + offsetX, drawY + (i * size) + offsetY, size - 1, size - 1);
                }
            }
        }
    }

    void updateGame() {
        // 1秒ごとの自動落下
        if (!checkCollision(gridx, gridy + 1, currentMino.shape)) {
            gridy++;
        } else {
            setMino(getGraphics(), gridx, gridy);
            deleteLine();
            spawnNextMino();
            currentMino.resetRotate();
        }
    }

    void keyGame(int keyCode) {
        if (keyCode == java.awt.event.KeyEvent.VK_LEFT) {
            if (!checkCollision(gridx - 1, gridy, currentMino.shape))
                gridx -= 1;
        } else if (keyCode == java.awt.event.KeyEvent.VK_RIGHT) {
            if (!checkCollision(gridx + 1, gridy, currentMino.shape))
                gridx += 1;
        } else if (keyCode == java.awt.event.KeyEvent.VK_DOWN) {
            if (!checkCollision(gridx, gridy + 1, currentMino.shape)) {
                gridy += 1;
            } else {
                // 最下層に到達したので、即座にその場に固定する
                setMino(getGraphics(), gridx, gridy);
                deleteLine();
                spawnNextMino(); // 次のミノを生成してリセット
                currentMino.resetRotate();
            }
        } else if (keyCode == java.awt.event.KeyEvent.VK_UP) {
            // 下に衝突しない間、ひたすら y を下に下げ続ける（一瞬で最下層を見つける）
            while (!checkCollision(gridx, gridy + 1, currentMino.shape)) {
                gridy++;
            }
            // 最下層に到達したので、即座にその場に固定する
            setMino(getGraphics(), gridx, gridy);
            deleteLine();
            spawnNextMino(); // 次のミノを生成してリセット
            currentMino.resetRotate();

        } else if (keyCode == java.awt.event.KeyEvent.VK_SPACE) {
            // SRS壁蹴り実装
            int[][] nextShape = currentMino.getRotatedShape(); // 回転後の形を先読み
            int currentRot = currentMino.currentRotation; // 現在の向き

            // Oミノ（四角）は回転させる必要がないので処理をスキップ
            if (currentMino.colorId != 2) {

                int[][] currentSrsTests = (currentMino.colorId == 1) ? srsDataI[currentRot] : srsData[currentRot];

                boolean rotated = false; // 回転が成功したかどうかのフラグ

                // 5つのテストパターンを順番に試すループ
                for (int i = 0; i < 5; i++) {
                    // srsDataから、今回の回転に対応するXとYのずらし量を取得
                    int kickX = currentSrsTests[i][0];
                    int kickY = currentSrsTests[i][1];

                    // 「もしその分ずらした座標」が、壁やブロックに衝突しないなら
                    if (!checkCollision(gridx + kickX, gridy + kickY, nextShape)) {
                        gridx += kickX; // X座標を補正
                        gridy += kickY; // Y座標を補正
                        currentMino.rotate(); // 実際にミノを回転させる
                        rotated = true;
                        break; // 合格したのでループを抜ける
                    }
                }

                // 5つとも全部ダメだった場合は、rotatedはfalseのままになり回転は拒否
                if (rotated) {
                    repaint();
                }
            }
        } else if (keyCode == java.awt.event.KeyEvent.VK_C) {
            // ホールド機能の実装
            if (canHold) {
                // 現在のミノの回転状態を初期状態にリセット
                currentMino.resetRotate();

                if (holdMino == null) {
                    // まだ一度もホールドしていない場合
                    // 現在のミノをホールドに入れ、Nextキューから次のミノを引き出す
                    holdMino = currentMino;
                    spawnNextMino();
                } else {
                    // すでにホールドにミノがある場合
                    // 現在のミノとホールドのミノを入れ替える
                    Tetromino temp = currentMino;
                    currentMino = holdMino;
                    holdMino = temp;

                    // 位置を中央上部に戻す
                    gridx = 3;
                    gridy = 0;
                }

                // 1回の落下につき1回だけなので、次のミノが固定されるまでホールド禁止にする
                canHold = false;
                repaint();
            }
        }
        repaint();
    }

    void drawGameOver(java.awt.Graphics g) {
        g.setColor(java.awt.Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setFont(new java.awt.Font("MS ゴシック", java.awt.Font.BOLD, 30));
        g.setColor(java.awt.Color.RED);
        g.drawString("Game Over", getWidth() / 2 - 85, getHeight() / 2 - 50);

        g.setFont(new java.awt.Font("MS ゴシック", java.awt.Font.BOLD, 20));
        g.setColor(java.awt.Color.WHITE);
        g.drawString("SCORE: " + score, getWidth() / 2 - 50, getHeight() / 2 - 10);

        g.setFont(new java.awt.Font("MS ゴシック", java.awt.Font.BOLD, 16));
        if (selectId == 0) {
            g.drawString("->Retry", getWidth() / 2 - 45, getHeight() / 2 + 40);
            g.setColor(java.awt.Color.GRAY);
            g.drawString("Main Menu", getWidth() / 2 - 30, getHeight() / 2 + 70);
        } else {
            g.setColor(java.awt.Color.GRAY);
            g.drawString("Retry", getWidth() / 2 - 30, getHeight() / 2 + 40);
            g.setColor(java.awt.Color.WHITE);
            g.drawString("->Main Menu", getWidth() / 2 - 45, getHeight() / 2 + 70);
        }
    }

    void updateGameOver() {
    }

    void keyGameOver(int keyCode) {
        if (keyCode == java.awt.event.KeyEvent.VK_UP || keyCode == java.awt.event.KeyEvent.VK_DOWN) {
            selectId = (selectId == 0) ? 1 : 0;
        } else if (keyCode == java.awt.event.KeyEvent.VK_ENTER) {
            if (selectId == 0) {
                imageState = 1; // リトライ
            } else if (selectId == 1) {
                imageState = 0; // メインメニュー
            }
            resetGame();
        }
        repaint();
    }

    void spawnNextMino() {
        refillQueue(); // 念のため補充

        // キューの先頭から次のミノを取り出す
        currentMino = nextQueue.remove(0);

        refillQueue(); // 取り出して減ったので、5個先を維持するためにすぐ補充

        gridx = 3;
        gridy = 0;
        canHold = true; // 新しいミノになったのでホールド権を復活
        checkGameOver();
        repaint();
    }

    void resetGame() {
        selectId = 0;
        score = 0;
        gridx = 3;
        gridy = 0;
        grid = new int[10][20];

        level = 1;
        speed = 1000;
        if (timer != null) {
            timer.setDelay(speed);
        }

        bag.clear();
        nextQueue.clear();
        holdMino = null;
        canHold = true;

        refillQueue(); // 最初期にキューを満たす
        currentMino = nextQueue.remove(0); // 1個目を現在のミノに
        refillQueue(); // 減った分を即補充
    }

    void setGridColor(java.awt.Graphics g, int id) {
        switch (id) {
            case 1:
                g.setColor(java.awt.Color.CYAN);
                break;
            case 2:
                g.setColor(java.awt.Color.YELLOW);
                break;
            case 3:
                g.setColor(java.awt.Color.BLUE);
                break;
            case 4:
                g.setColor(java.awt.Color.RED);
                break;
            case 5:
                g.setColor(java.awt.Color.GREEN);
                break;
            case 6:
                g.setColor(java.awt.Color.ORANGE);
                break;
            case 7:
                g.setColor(java.awt.Color.ORANGE);
                break;
            case 8:
                g.setColor(new Color(128, 0, 128));
                break;
        }
    }

    // 7バッグ規則で次のミノの「ID（1~8）」を1つ取り出すメソッド
    private int pullFromBag() {
        if (bag.isEmpty()) {
            for (int id = 1; id <= 8; id++) {
                bag.add(id);
            }
            // Javaの標準機能で袋の中身をランダムにシャッフル！
            java.util.Collections.shuffle(bag);
        }
        // 袋の先頭から1個つまみ出して、袋からは消す
        return bag.remove(0);
    }

    // 引いたIDから、新しいミノのインスタンスを生成する
    private Tetromino createMinoById(int id) {
        switch (id) {
            case 1:
                return new Imino();
            case 2:
                return new Omino();
            case 3:
                return new Zmino();
            case 4:
                return new Smino();
            case 5:
                return new Jmino();
            case 6:
                return new Lmino();
            case 7:
                return new TMino();
            default:
                return new TMino();
        }
    }

    private void refillQueue() {
        while (nextQueue.size() < 7) {
            int nextId = pullFromBag();
            nextQueue.add(createMinoById(nextId));
        }
    }

    // ブロックを描画する
    // public void drawBlock(java.awt.Graphics g) {
    // g.setColor(java.awt.Color.RED);
    // g.fillRect(gridx * blockSize, gridy * blockSize, blockSize, blockSize); //
    // ブロックのサイズは30x30
    // }

    // public void drawIMino(java.awt.Graphics g, int x, int y) {
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

    public void drawMinos(java.awt.Graphics g, int x, int y) {
        // ここに、ブロックを描画する処理を書く
        int[][] shape = currentMino.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    g.setColor(currentMino.getColor());
                    g.fillRect((x + j) * blockSize + 1, (y + i) * blockSize + 1, blockSize - 2, blockSize - 2);
                }

            }
        }
    }

    public void checkGameOver() {
        if (checkCollision(gridx, gridy, currentMino.shape)) {
            imageState = 2; // ゲームオーバー画面に切り替える
            repaint(); // 画面を再描画する
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
                    grid[gridX + j][gridY + i] = currentMino.colorId; // ブロックをグリッドに固定する
                }
            }
        }
    }

    void deleteLine() {
        System.out.println("動作");
        int i = grid[0].length - 1;
        int clearedLines = 0; // 消したラインの数をカウントする変数
        while (i > 1) {
            boolean isContinue = false;
            for (int j = 0; j < grid.length; j++) {
                if (grid[j][i] == 0) {
                    isContinue = true;
                    break;
                }
            }
            if (isContinue) {
                i--;
                continue;
            } else {
                clearedLines++; // 消したラインの数を増やす
                for (int k = i; k > 0; k--) {
                    for (int j = 0; j < grid.length; j++) {
                        grid[j][k] = grid[j][k - 1];
                    }
                }

                for (int k = 0; k < grid.length; k++) {
                    grid[k][0] = 0;
                }

                i = grid[0].length - 1;
                System.out.println("zikkou");
            }
            System.out.println("zikkou");

        }

        if (clearedLines > 0) {
            calcScore(clearedLines); // スコアを計算するメソッドを呼び出す
        }

    }

    void calcScore(int clearedLines) {
        int points = 0;
        switch (clearedLines) {
            case 1:
                points = 100;
                break;
            case 2:
                points = 300;
                break;
            case 3:
                points = 500;
                break;
            case 4:
                points = 800;
                break;
        }
        score += points; // スコアに加算する
        System.out.println("Score: " + score); // スコアを表示する（デバッグ用）

        int nextLevel = (score / 1000) + 1;
        if (nextLevel > 10)
            nextLevel = 10;

        if (nextLevel != level) {
            level = nextLevel;
            // レベルが上がるほどミリ秒を短くする
            speed = 1000 - (level - 1) * 100;
            if (speed < 100)
                speed = 100; // 最速でも0.1秒落下までに制限

            timer.setDelay(speed); // タイマーの速度を即座に変更！
            System.out.println("Level: " + level + " Speed: " + speed + "ms");
        }
    }
}
