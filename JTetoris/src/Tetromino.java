public abstract class Tetromino {
    // ブロックの形状を定義するための抽象クラス
    // 具体的なブロックの形状は、このクラスを継承したサブクラスで定義する
    // このクラスでは、ブロックの形状を格納するための二次元配列を定義する
    // ブロックの形状を格納する二次元配列を4x4のサイズで定義する
    protected int[][] shape = new int[4][4];

    // ブロックの形状を取得するための抽象メソッド
    // このメソッドは、サブクラスで実装する必要がある
    public abstract int[][] getShape();

    // ブロックの形状を回転させるためのメソッド
    // このメソッドは、二次元配列を90度回転させることで、ブロックの形状を回転させる
    // このメソッドは、getShape()メソッドと異なり、共通する処理のため実装しておく
    // 後で書く

    // shapeのゲッター
    public int[][] getShapeArray() {
        return shape;
    }

    // shapeのセッター
    public void setShapeArray(int[][] shape) {
        this.shape = shape;
    }
}

class IMino extends Tetromino 
{
    public IMino() {
        // I型のブロックの形状を定義する
        shape[1][0] = 1;
        shape[1][1] = 1;
        shape[1][2] = 1;
        shape[1][3] = 1;
    }

    @Override
    public int[][] getShape() {
        return shape;
    }
};

