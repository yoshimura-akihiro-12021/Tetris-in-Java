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

    private int[][] rotatedShape = new int[4][4];

    private java.awt.Color color; // ブロックの色を定義するための変数

    // ブロックの形状を取得するための抽象メソッド
    // ブロックの形状を回転させるためのメソッド
    // このメソッドは、二次元配列を90度回転させることで、ブロックの形状を回転させる
    // このメソッドは、getShape()メソッドと異なり、共通する処理のため実装しておく

    public int[][] getRotatedShape(){
        rotatedShape = new int[4][4];
        for(int i = 0;i < shape.length;i++){
            for(int j = 0;j < shape[i].length;j++){
                rotatedShape[j][3 - i] = shape[i][j];
                System.out.print(rotatedShape[i][j] + ", ");
            }
        }

        for(int i = 0;i < shape.length;i++){
            for(int j = 0;j < shape[i].length;j++){
                System.out.print(rotatedShape[i][j] + ", ");
            }

            System.out.println();
        }

        return rotatedShape;
    }
    
    public void rotate(){

        for(int i = 0;i < shape.length;i++){
            for(int j = 0;j < shape[i].length;j++){
                rotatedShape[j][3 - i] = shape[i][j];
                System.out.print(rotatedShape[i][j] + ", ");
            }
        }

        for(int i = 0;i < shape.length;i++){
            for(int j = 0;j < shape[i].length;j++){
                System.out.print(rotatedShape[i][j] + ", ");
            }

            System.out.println();
        }

        shape = rotatedShape;
    }

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
        // I型のブロックの形状を定義する
        shape[1][0] = 1;
        shape[1][1] = 1;
        shape[1][2] = 1;
        shape[1][3] = 1;
        setColor(java.awt.Color.CYAN); // ブロックの色を設定する

        // 0 0 0 0
        // 1 1 1 1
        // 0 0 0 0
        // 0 0 0 0
    }

    
}

class Omino extends Tetromino {

    public Omino() {
        // O型のブロックの形状を定義する
        shape[1][1] = 1;
        shape[1][2] = 1;
        shape[2][1] = 1;
        shape[2][2] = 1;
        setColor(java.awt.Color.YELLOW); // ブロックの色を設定する

        // 0 0 0 0
        // 0 1 1 0
        // 0 1 1 0
        // 0 0 0 0
    }

}

class LongTmino extends Tetromino {

    public LongTmino() {
        // 長いT型のブロックの形状を定義する
        shape[1][0] = 1;
        shape[1][1] = 1;
        shape[1][2] = 1;
        shape[2][1] = 1;
        shape[3][1] = 1;
        Color BLUE = new Color(0, 0, 255); // 青色のRGB値
        setColor(BLUE); // ブロックの色を設定する
        // 0 0 0 0
        // 1 1 1 0
        // 0 1 0 0
        // 0 1 0 0
    }

}

class Zmino extends Tetromino {

    public Zmino() {
        // Z型のブロックの形状を定義する
        shape[1][0] = 1;
        shape[1][1] = 1;
        shape[2][1] = 1;
        shape[2][2] = 1;
        Color RED = new Color(255, 0, 0); // 赤色のRGB値
        setColor(RED); // ブロックの色を設定する

        // 0 0 0 0
        // 1 1 0 0
        // 0 1 1 0
        // 0 0 0 0
    }

}

class Smino extends Tetromino {

    public Smino() {
        // S型のブロックの形状を定義する
        shape[1][1] = 1;
        shape[1][2] = 1;
        shape[2][0] = 1;
        shape[2][1] = 1;
        Color GREEN = new Color(0, 255, 0); // 緑色のRGB値
        setColor(GREEN); // ブロックの色を設定する

        // 0 0 0 0
        // 0 1 1 0
        // 1 1 0 0
        // 0 0 0 0
    }

}

class Jmino extends Tetromino {

    public Jmino() {
        // J型のブロックの形状を定義する
        shape[1][0] = 1;
        shape[1][1] = 1;
        shape[1][2] = 1;
        shape[2][0] = 1;
        Color ORANGE = new Color(255, 165, 0); // オレンジ色のRGB値
        setColor(ORANGE); // ブロックの色を設定する

        // 0 0 0 0
        // 1 1 1 0
        // 1 0 0 0
        // 0 0 0 0
    }

}

class Lmino extends Tetromino {

    public Lmino() {
        // L型のブロックの形状を定義する
        shape[1][0] = 1;
        shape[1][1] = 1;
        shape[1][2] = 1;
        shape[2][2] = 1;
        Color ORANGE = new Color(255, 165, 0); // オレンジ色のRGB値
        setColor(ORANGE); // ブロックの色を設定する

        // 0 0 0 0
        // 1 1 1 0
        // 0 0 1 0
        // 0 0 0 0
    }

}

class TMino extends Tetromino {

    public TMino() {
        // T型のブロックの形状を定義する
        shape[1][0] = 1;
        shape[1][1] = 1;
        shape[1][2] = 1;
        shape[2][1] = 1;
        Color PURPLE = new Color(128, 0, 128); // 紫色のRGB値
        setColor(PURPLE); // ブロックの色を設定する

        // 0 0 0 0
        // 1 1 1 0
        // 0 1 0 0
        // 0 0 0 0
    }

}



