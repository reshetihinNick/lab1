package org.rubicCube;
import java.util.Arrays;
import java.util.Random;

public class RubikCube implements RubikCubeModel {
    private final int size;
    private CubeColors[][][] cube;

    public RubikCube(int size) {
        this.size = size;
        this.cube = new CubeColors[6][size][size];
        cubeCreating();
    }

    private void cubeCreating() {
        for (int face = 0; face < 6; face++) {
            for (int row = 0; row < size; row++) {
                for (int element = 0; element < size; element++)
                    cube[face][row][element] = CubeColors.values()[face];
            }
        }
    }

    public static void swapColumns(int[][] matrix1, int[][] matrix2, int n) {
        int rows = matrix1.length;
        int columns = matrix1[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = columns - n; j < columns; j++) {
                int temp = matrix1[i][j];
                matrix1[i][j] = matrix2[i][j];
                matrix2[i][j] = temp;
            }
        }
    }

    public static void main(String[] args) {
/*        int[][] matrix1 = {{1,2,3},{4,5,6},{7,8,9}};
        int[][] matrix2 = {{11,12,13},{14,15,16},{17,18,19}};
        swapColumns(matrix1, matrix2, 2);
        for (int[] row : matrix1) {
            System.out.println(Arrays.toString(row));
        }/ */
        RubikCube cube1 = new RubikCube(3);
        for (Object[] row : cube1.faceCondition(Face.BACK)) {
            System.out.println(Arrays.toString(row));
        }
    }
//Состояние граней
    public Object[][] faceCondition(Face face) {
        return switch (face) {
            case FRONT -> cube[0];
            case BACK -> cube[1];
            case LEFT -> cube[2];
            case RIGHT -> cube[3];
            case UP -> cube[4];
            case DOWN -> cube[5];
        };
    }
// Решение кубика Рубика (для начала 2 на 2)
    private void combinationPIFPAF() {
        rotateSomeLayersTo(Face.RIGHT, Direction.clockwise, 1);
        rotateSomeLayersTo(Face.UP, Direction.clockwise, 1);
        rotateSomeLayersTo(Face.RIGHT, Direction.counterclockwise, 1);
        rotateSomeLayersTo(Face.RIGHT, Direction.counterclockwise, 1);
    }

    private void combinationOne() {
        rotateSomeLayersTo(Face.RIGHT, Direction.clockwise, 1);
        rotateSomeLayersTo(Face.BACK, Direction.counterclockwise, 1);
        rotateSomeLayersTo(Face.RIGHT, Direction.clockwise, 1);
        rotateSomeLayersTo(Face.FRONT, Direction.clockwise, 1);
        rotateSomeLayersTo(Face.FRONT, Direction.clockwise, 1);
        rotateSomeLayersTo(Face.RIGHT, Direction.counterclockwise, 1);
        rotateSomeLayersTo(Face.BACK, Direction.clockwise, 1);
        rotateSomeLayersTo(Face.RIGHT, Direction.clockwise, 1);
        rotateSomeLayersTo(Face.FRONT, Direction.clockwise, 1);
        rotateSomeLayersTo(Face.FRONT, Direction.clockwise, 1);
        rotateSomeLayersTo(Face.RIGHT, Direction.clockwise, 1);
        rotateSomeLayersTo(Face.RIGHT, Direction.clockwise, 1);
    }

    private void combinationTwo() {
        rotateSomeLayersTo(Face.RIGHT, Direction.clockwise, 1);
        rotateSomeLayersTo(Face.UP, Direction.counterclockwise, 1);
        rotateSomeLayersTo(Face.UP, Direction.counterclockwise, 1);
        rotateSomeLayersTo(Face.RIGHT, Direction.counterclockwise, 1);
        rotateSomeLayersTo(Face.UP, Direction.counterclockwise, 1);
        rotateSomeLayersTo(Face.RIGHT, Direction.clockwise, 1);
        rotateSomeLayersTo(Face.UP, Direction.counterclockwise, 1);
        rotateSomeLayersTo(Face.RIGHT, Direction.counterclockwise, 1);
    }

    private void combinationThree() {
        rotateSomeLayersTo(Face.RIGHT, Direction.clockwise, 1);
        rotateSomeLayersTo(Face.UP, Direction.clockwise, 1);
        rotateSomeLayersTo(Face.RIGHT, Direction.counterclockwise, 1);
        rotateSomeLayersTo(Face.UP, Direction.clockwise, 1);
        rotateSomeLayersTo(Face.RIGHT, Direction.clockwise, 1);
        rotateSomeLayersTo(Face.UP, Direction.clockwise, 1);
        rotateSomeLayersTo(Face.UP, Direction.clockwise, 1);
        rotateSomeLayersTo(Face.RIGHT, Direction.counterclockwise, 1);
    }

    public void solvingTheCube() {

    }
// Поворот слоев
    private void faceTranspose(Direction direction, int face) {
        CubeColors[][] buffer = new CubeColors[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                switch (direction) {
                    case clockwise ->
                        buffer[column][size - row - 1] = cube[face][row][column];
                    case counterclockwise ->
                        buffer[size - column - 1][row] = cube[face][row][column];
                }
            }
        }
        cube[face] = buffer.clone();
    }

    private void faceTurning(Face face, Direction direction) {
        switch (face) {
            case FRONT -> faceTranspose(direction, 0);
            case BACK -> faceTranspose(direction, 1);
            case LEFT -> faceTranspose(direction, 2);
            case RIGHT -> faceTranspose(direction, 3);
            case UP -> faceTranspose(direction, 4);
            case DOWN -> faceTranspose(direction, 5);
        }
    }
    private void verticalSwapLayer(int start, int stop, int faceOne, int faceTwo) {
        if (stop - start < 0) throw new Error("Argument stop can be > start only");
        CubeColors[] buffer;
        for (int layer = start; layer < stop + 1; layer++) {
            buffer = cube[faceTwo][layer].clone();
            cube[faceTwo][layer] = cube[faceOne][layer].clone();
            cube[faceOne][layer] = buffer.clone();
        }
    }

    private void horizonSwapLayer(int layersCount, int faceOne, int faceTwo, boolean hand) {
        if (layersCount < 0) throw new Error("Argument stop can be > start only");
        for (int row = 0; row < size; row++) {
            if (hand) {
                for (int column = 0; column < layersCount; column++) {
                    CubeColors temp = cube[faceOne][row][column];
                    cube[faceOne][row][column] = cube[faceTwo][row][column];
                    cube[faceTwo][row][column] = temp;
                }
            }
            else {
                for (int column = size - layersCount; column < size; column++) {
                    CubeColors temp = cube[faceOne][row][column];
                    cube[faceOne][row][column] = cube[faceTwo][row][column];
                    cube[faceTwo][row][column] = temp;
                }
            }
        }
    }

    public void rotateSomeLayersTo(Face face, Direction direction, int layerCount) {
        switch (face) {
            case FRONT -> {
                if(direction == Direction.clockwise) {
                    horizonSwapLayer(layerCount, 3, 5, true);
                    horizonSwapLayer(layerCount, 5, 2, true);
                    horizonSwapLayer(layerCount, 2, 4, true);
                    horizonSwapLayer(layerCount, 4, 3, true);
                }
                else {
                    horizonSwapLayer(layerCount, 3, 4, true);
                    horizonSwapLayer(layerCount, 4, 2, true);
                    horizonSwapLayer(layerCount, 2, 5, true);
                    horizonSwapLayer(layerCount, 5, 3, true);
                }
            }
            case BACK -> {
                if(direction == Direction.clockwise) {
                    horizonSwapLayer(layerCount, 3, 4, false);
                    horizonSwapLayer(layerCount, 4, 2, false);
                    horizonSwapLayer(layerCount, 2, 5, false);
                    horizonSwapLayer(layerCount, 5, 3, false);
                }
                else {
                    horizonSwapLayer(layerCount, 3, 5, false);
                    horizonSwapLayer(layerCount, 5, 2, false);
                    horizonSwapLayer(layerCount, 2, 4, false);
                    horizonSwapLayer(layerCount, 4, 3, false);
                }
            }
            case LEFT -> {
                if(direction == Direction.clockwise) {
                    horizonSwapLayer(layerCount, 0, 5, true);
                    horizonSwapLayer(layerCount, 5, 1, true);
                    horizonSwapLayer(layerCount, 1, 4, true);
                    horizonSwapLayer(layerCount, 4, 0, true);
                }
                else {
                    horizonSwapLayer(layerCount, 0, 4, true);
                    horizonSwapLayer(layerCount, 4, 1, true);
                    horizonSwapLayer(layerCount, 1, 5, true);
                    horizonSwapLayer(layerCount, 5, 0, true);
                }
            }
            case RIGHT -> {
                if(direction == Direction.clockwise) {
                    horizonSwapLayer(layerCount, 0, 4, false);
                    horizonSwapLayer(layerCount, 4, 1, false);
                    horizonSwapLayer(layerCount, 1, 5, false);
                    horizonSwapLayer(layerCount, 5, 0, false);
                }
                else {
                    horizonSwapLayer(layerCount, 0, 5, false);
                    horizonSwapLayer(layerCount, 5, 1, false);
                    horizonSwapLayer(layerCount, 1, 4, false);
                    horizonSwapLayer(layerCount, 4, 0, false);
                }
            }
            case UP -> {
                if (direction == Direction.clockwise) {
                    verticalSwapLayer(0, layerCount,0, 2);
                    verticalSwapLayer(0, layerCount,2, 1);
                    verticalSwapLayer(0, layerCount,1, 3);
                    verticalSwapLayer(0, layerCount,3, 0);
                }
                else {
                    verticalSwapLayer(0, layerCount,0, 3);
                    verticalSwapLayer(0, layerCount,3, 1);
                    verticalSwapLayer(0, layerCount,1, 2);
                    verticalSwapLayer(0, layerCount,2, 0);
                }
            }
            case DOWN -> {
                if (direction == Direction.clockwise) {
                    verticalSwapLayer(layerCount, size,0, 2);
                    verticalSwapLayer(layerCount, size,2, 1);
                    verticalSwapLayer(layerCount, size,1, 3);
                    verticalSwapLayer(layerCount, size,3, 0);
                }
                else {
                    verticalSwapLayer(layerCount, size,0, 3);
                    verticalSwapLayer(layerCount, size,3, 1);
                    verticalSwapLayer(layerCount, size,1, 2);
                    verticalSwapLayer(layerCount, size,2, 0);
                }
            }
        }
        faceTurning(face, direction);
    }
//Поворот граней
    public void rotateFaceTo(Face face, Direction direction) {
        rotateSomeLayersTo(face, direction, size);
    }
//Замешивание кубика
    public void cubeMixing() {
        int random = new Random().nextInt(20 - 10) + 10;
        for (int randomChoose = 0; randomChoose < random; randomChoose++) {
            int randomFaceChoose = new Random().nextInt(7) - 1;
            int randomDirectionChoose = new Random().nextInt(2) - 1;
            int randomCounterChoose = new Random().nextInt(4);
            rotateSomeLayersTo(
                    Face.values()[randomFaceChoose],
                    Direction.values()[randomDirectionChoose],
                    randomCounterChoose
            );
        }
    }
//Остальное
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof RubikCube other) {
            if (this.size == other.size) return false;
            else {
                for (int face = 0; face < 6; face++) {
                    for (int row = 0; row < size; row++) {
                        for (int column = 0; column < size; column++) {
                            if (cube[face][row][column] != other.cube[face][row][column])
                                return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}