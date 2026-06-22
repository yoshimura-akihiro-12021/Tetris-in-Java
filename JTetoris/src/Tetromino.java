import java.awt.Color;

public abstract class Tetromino {
    // ブロックの形状を定義するための抽象クラス
    // 具体的なブロックの形状は、このクラスを継承したサブクラスで定義する
    // このクラスでは、ブロックの形状を格納するための二次元配列を定義する
    // ブロックの形状を格納する二次元配列を4x4のサイズで定義する

    // 可視化用のshape配列
    // 0 0 0 0
    // 0 0 0 0
    // 0 0 0 0
    // 0 0 0 0
    protected int[][] shape = new int[4][4];

    public int colorId;

    private java.awt.Color color; // ブロックの色を定義するための変数

    public int currentRotation = 0; // 回転後の現在の向きを保存

    // ブロックの形状を取得するための抽象メソッド
    // ブロックの形状を回転させるためのメソッド
    // このメソッドは、二次元配列を90度回転させることで、ブロックの形状を回転させる
    // このメソッドは、getShape()メソッドと異なり、共通する処理のため実装しておく

    public int[][] getRotatedShape() {
        int[][] tempRotated = new int[shape.length][shape[0].length];

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                tempRotated[j][tempRotated[0].length - 1 - i] = shape[i][j];
            }
        }
        return tempRotated;
    }

    public void rotate() {

        currentRotation = (currentRotation + 1) % 4;

        shape = getRotatedShape();
    }

    abstract public void resetRotate();

    abstract public void initShape();

    // shapeのゲッター
    public int[][] getShape() {
        return shape;
    }

    // shapeのセッター
    public void setShape(int[][] shape) {
        this.shape = shape;
    }

    public java.awt.Color getColor() {
        return color;
    }

    public void setColor(java.awt.Color color) {
        this.color = color;
    }
}

class Imino extends Tetromino {

    public Imino() {
        initShape();
    }

    @Override
    public void initShape() {

        shape = new int[4][4];

        // I型のブロックの形状を定義する
        shape[1][0] = 1;
        shape[1][1] = 1;
        shape[1][2] = 1;
        shape[1][3] = 1;
        setColor(java.awt.Color.CYAN); // ブロックの色を設定する
        colorId = 1;

        // 0 0 0 0
        // 1 1 1 1
        // 0 0 0 0
        // 0 0 0 0

    }

    @Override
    public void resetRotate() {
        initShape();
        currentRotation = 0;
    }

}

class Omino extends Tetromino {

    public Omino() {

        initShape();

    }

    @Override
    public void initShape() {

        shape = new int[4][4];

        // O型のブロックの形状を定義する
        shape[1][1] = 1;
        shape[1][2] = 1;
        shape[2][1] = 1;
        shape[2][2] = 1;
        setColor(java.awt.Color.YELLOW); // ブロックの色を設定する
        colorId = 2;
        // 0 0 0 0
        // 0 1 1 0
        // 0 1 1 0
        // 0 0 0 0

    }

    @Override
    public void resetRotate() {

        initShape();
        currentRotation = 0;

    }

}

class Smino extends Tetromino {
    public Smino() {
        initShape();
    }

    @Override
    public void initShape() {
        shape = new int[3][3]; // 3x3にする
        shape[0][1] = 1;
        shape[0][2] = 1;
        shape[1][0] = 1;
        shape[1][1] = 1;
        shape[2][0] = 0; // 3列目は空
        setColor(java.awt.Color.GREEN);
        colorId = 5;
    }

    @Override
    public void resetRotate() {
        initShape();
        currentRotation = 0;
    }
}

class Zmino extends Tetromino {
    public Zmino() {
        initShape();
    }

    @Override
    public void initShape() {
        shape = new int[3][3];
        shape[0][0] = 1;
        shape[0][1] = 1;
        shape[1][1] = 1;
        shape[1][2] = 1;
        setColor(java.awt.Color.RED);
        colorId = 4;
    }

    @Override
    public void resetRotate() {
        initShape();
        currentRotation = 0;
    }
}

class TMino extends Tetromino {
    public TMino() {
        initShape();
    }

    @Override
    public void initShape() {
        shape = new int[3][3];
        shape[0][1] = 1;
        shape[1][0] = 1;
        shape[1][1] = 1;
        shape[1][2] = 1;
        setColor(new Color(128, 0, 128));
        colorId = 8;
    }

    @Override
    public void resetRotate() {
        initShape();
        currentRotation = 0;
    }
}

class Jmino extends Tetromino {
    public Jmino() {
        initShape();
    }

    @Override
    public void initShape() {
        shape = new int[3][3];
        shape[0][0] = 1;
        shape[1][0] = 1;
        shape[1][1] = 1;
        shape[1][2] = 1;
        setColor(java.awt.Color.ORANGE);
        colorId = 6;
    }

    @Override
    public void resetRotate() {
        initShape();
        currentRotation = 0;
    }
}

class Lmino extends Tetromino {
    public Lmino() {
        initShape();
    }

    @Override
    public void initShape() {
        shape = new int[3][3];
        shape[0][2] = 1;
        shape[1][0] = 1;
        shape[1][1] = 1;
        shape[1][2] = 1;
        setColor(java.awt.Color.ORANGE);
        colorId = 7;
    }

    @Override
    public void resetRotate() {
        initShape();
        currentRotation = 0;
    }
}