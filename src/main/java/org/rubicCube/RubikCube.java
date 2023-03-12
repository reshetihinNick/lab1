package org.rubicCube;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.StringJoiner;

public class RubikCube implements RubikCubeModel {
    private final int size;
    private final CubeColors[][][] cube;

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

    public static void main(String[] args) {
        RubikCube cube1 = new RubikCube(2);
        cube1.cubeMixing();
        System.out.println(cube1);
        System.out.println("------------------");
//        cube1.combinationOne();
//        cube1.rotateSomeLayersTo(Face.LEFT, Direction.counterclockwise, 1);
        cube1.solvingTheCube();
//        cube1.combinationPIFPAF();
//        cube1.rotateSomeLayersTo(Face.DOWN, Direction.clockwise, 1);
        System.out.println(cube1);
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
        rotateSomeLayersTo(Face.UP, Direction.counterclockwise, 1);
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

    private CubeColors colorInBox(int face, int row, int column) {
        return cube[face][row][column];
    }

    private boolean ifSingleColorInFace(int face, CubeColors color) {
        for (int row = 0; row < size; row++) {
            if (!ifSingleColorInRow(face, row, color)) return false;
         }
        return true;
    }

    private boolean ifSingleColorInRow(int face, int row, CubeColors color) {
        for (int column = 0; column < size; column++) {
            if (cube[face][row][column] != color) return false;
        }
        return true;
    }

    private boolean ifSomeSingleColorInRow(int face, int row) {
        for (int column = 0; column < size - 1; column++) {
            if (cube[face][row][column] != cube[face][row][column + 1]) return false;
        }
        return true;
    }

    private boolean whiteColorCheck() {
        return cube[0][size - 1][size - 1] == CubeColors.WHITE ||
                cube[0][0][size - 1] == CubeColors.WHITE ||
                cube[4][size - 1][size - 1] == CubeColors.WHITE ||
                cube[3][0][0] == CubeColors.WHITE ||
                cube[3][size - 1][0] == CubeColors.WHITE;
    }
    public void solvingTheCube() {
        while (!ifSingleColorInFace(5, CubeColors.WHITE)) {
            if (whiteColorCheck()) {
                if (cube[5][0][size - 1] != CubeColors.WHITE) {
                    while (cube[5][0][size - 1] != CubeColors.WHITE) {
                        combinationPIFPAF();
                        System.out.println("PIFPAF");
                        System.out.println(this);
                    }
                }
                else {
                    rotateSomeLayersTo(Face.UP, Direction.counterclockwise, 1);
                    rotateFaceTo(Face.UP, Direction.clockwise);
                }
            }
            else rotateFaceTo(Face.UP, Direction.clockwise);
        }
        rotateFaceTo(Face.RIGHT, Direction.clockwise);
        rotateFaceTo(Face.RIGHT, Direction.clockwise);
        System.out.println(this);
        if (ifSomeSingleColorInRow(2, 0))
            rotateFaceTo(Face.UP, Direction.counterclockwise);
        else {
            if (ifSomeSingleColorInRow(3, 0))
                rotateFaceTo(Face.UP, Direction.clockwise);
            else {
                rotateFaceTo(Face.UP, Direction.clockwise);
                rotateFaceTo(Face.UP, Direction.clockwise);
            }
        }
        combinationOne();
        System.out.println(this);
        if (ifSomeSingleColorInRow(2, 0))
            rotateFaceTo(Face.UP, Direction.counterclockwise);
        else {
            if (ifSomeSingleColorInRow(3, 0))
                rotateFaceTo(Face.UP, Direction.clockwise);
            else {
                rotateFaceTo(Face.UP, Direction.clockwise);
                rotateFaceTo(Face.UP, Direction.clockwise);
            }
        }
        System.out.println(this);
        combinationOne();
//        while (!ifSomeSingleColorInRow(0, 0)) {
//            rotateSomeLayersTo(Face.UP, Direction.counterclockwise, 1);
//            System.out.println("While------");
//            System.out.println(this);
//        }
//        System.out.println(this);
//        combinationOne();
        //System.out.println(this);
        //combinationOne();
    }
// Поворот слоев
    private void faceTurning(Face face, Direction direction) {
        int faceInt = 0;
        switch (face) {
            case FRONT -> {
            }
            case BACK -> faceInt = 1;
            case LEFT -> faceInt = 2;
            case RIGHT -> faceInt = 3;
            case UP -> faceInt = 4;
            case DOWN -> faceInt = 5;
        }
        CubeColors[][] buffer = new CubeColors[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                switch (direction) {
                    case clockwise ->
                            buffer[column][size - row - 1] = cube[faceInt][row][column];
                    case counterclockwise ->
                            buffer[size - column - 1][row] = cube[faceInt][row][column];
                }
            }
        }
        cube[faceInt] = buffer.clone();
    }

    public void reverseArray(int face, int row) {
        CubeColors temp;
        for (int column = 0; column < size / 2; column++) {
            temp = cube[face][row][column];
            cube[face][row][column] = cube[face][row][size - 1 - column];
            cube[face][row][size - 1 - column] = temp;
        }
    }
    private void verticalSwapLayer(int start, int stop, int faceOne,  int faceTwo, boolean reverse) {
        if (stop - start < 0) throw new Error("Argument stop can be > start only");
        CubeColors[] buffer;
        for (int layer = start; layer < stop; layer++) {
            if (!reverse) {
                buffer = cube[faceTwo][layer].clone();
                cube[faceTwo][layer] = cube[faceOne][layer].clone();
            }
            else {
                reverseArray(faceTwo, size - 1 - layer);
                buffer = cube[faceTwo][size - 1 - layer];
                reverseArray(faceOne, layer);
                cube[faceTwo][size - 1 - layer] = cube[faceOne][layer].clone();
            }
            cube[faceOne][layer] = buffer.clone();
        }
    }

    private void horizonSwapLayer(int layersCount, int faceOne, int faceTwo, boolean hand) {
        if (layersCount < 0) throw new Error("Argument stop can be > start only");
        int start;
        int stop;
        int swapRow;
        if (hand) {
            start = 0;
            stop = layersCount;
        }
        else {
            start = size - layersCount;
            stop = size;
        }
        for (int row = 0; row < size; row++) {
            for (int column = start; column < stop; column++) {
                CubeColors temp = cube[faceOne][row][column];
                cube[faceOne][row][column] = cube[faceTwo][row][column];
                cube[faceTwo][row][column] = temp;
            }
        }
    }

    private void swapColumnToRow(int layersCount, int faceOne, int faceTwo, boolean hand, boolean revers) {
        if (layersCount < 0) throw new Error("Argument stop can be > start only");
        CubeColors[] temp;
        int start;
        int stop;
        if (hand) {
            start = size - layersCount;
            stop = size;
        }
        else {
            start = 0;
            stop = layersCount;
        }
        for (int layer = start; layer < stop; layer++) {
            temp = cube[faceOne][layer].clone();
            for (int box = 0; box < size; box++) {
                if (!revers) {
                    cube[faceOne][layer][box] = cube[faceTwo][size - 1 - box][layer];
                }
                else cube[faceOne][layer][size - 1 - box] = cube[faceTwo][size - 1 - box][size - 1 - layer];
            }
            for (int box = 0; box < size; box++) {
                if (!revers) cube[faceTwo][size - 1 - box][layer] = temp[box];
                else cube[faceTwo][size - 1 - box][size - 1 - layer] = temp[size - 1 - box];
            }
        }
    }

    public void rotateSomeLayersTo(Face face, Direction direction, int layerCount) {
        switch (face) {
            case FRONT -> {
                if (direction == Direction.clockwise) {
                    swapColumnToRow(layerCount, 4, 3, true, true);
                    verticalSwapLayer(size - layerCount, size, 4, 5, true);
                    swapColumnToRow(layerCount, 4, 2, true, false);
                }
                else {
                    swapColumnToRow(layerCount, 4, 2, true, false);
                    verticalSwapLayer(size - layerCount, size, 4, 5, true);
                    swapColumnToRow(layerCount, 4, 3, true, true);
                }
            }
            case BACK -> {
                if (direction == Direction.clockwise) {
                    swapColumnToRow(layerCount, 4, 2, false, false);
                    verticalSwapLayer(0, layerCount, 4, 5, true);
                    swapColumnToRow(layerCount, 4, 3, false, true);
                }
                else {
                    swapColumnToRow(layerCount, 4, 3, false, true);
                    verticalSwapLayer(0, layerCount, 4, 5, true);
                    swapColumnToRow(layerCount, 4, 2, false, false);
                }
            }
            case LEFT -> {
                if(direction == Direction.clockwise) {
                    horizonSwapLayer(layerCount, 0, 5, true);
                    horizonSwapLayer(layerCount, 0, 1, true);
                    horizonSwapLayer(layerCount, 0, 4, true);
                }
                else {
                    horizonSwapLayer(layerCount, 0, 4, true);
                    horizonSwapLayer(layerCount, 0, 1, true);
                    horizonSwapLayer(layerCount, 0, 5, true);
                }
            }
            case RIGHT -> {
                if(direction == Direction.clockwise) {
                    horizonSwapLayer(layerCount, 0, 4, false);
                    horizonSwapLayer(layerCount, 0, 1, false);
                    horizonSwapLayer(layerCount, 0, 5, false);
                }
                else {
                    horizonSwapLayer(layerCount, 0, 5, false);
                    horizonSwapLayer(layerCount, 0, 1, false);
                    horizonSwapLayer(layerCount, 0, 4, false);
                }
            }
            case UP -> {
                if (direction == Direction.clockwise) {
                    verticalSwapLayer(0, layerCount,0, 2, false);
                    verticalSwapLayer(0, layerCount,0, 1, true);
                    verticalSwapLayer(0, layerCount,0, 3, false);
                }
                else {
                    verticalSwapLayer(0, layerCount,0, 3, false);
                    verticalSwapLayer(0, layerCount,0, 1, true);
                    verticalSwapLayer(0, layerCount,0, 2, false);
                }
            }
            case DOWN -> {
                if (direction == Direction.clockwise) {
                    verticalSwapLayer(size - layerCount, size,0, 3, false);
                    verticalSwapLayer(size - layerCount, size,0, 1, true);
                    verticalSwapLayer(size - layerCount, size,0, 2, false);
                }
                else {
                    verticalSwapLayer(size - layerCount, size,0, 2, false);
                    verticalSwapLayer(size - layerCount, size,0, 1, true);
                    verticalSwapLayer(size - layerCount, size,0, 3, false);
                }
            }
        }
        if (layerCount == size) {
            switch (face) {
                case FRONT -> {
                    if (direction == Direction.clockwise)
                        faceTurning(Face.BACK, Direction.counterclockwise);
                    else faceTurning(Face.BACK, Direction.clockwise);
                }
                case BACK -> {
                    if (direction == Direction.clockwise)
                        faceTurning(Face.FRONT, Direction.counterclockwise);
                    else faceTurning(Face.FRONT, Direction.clockwise);
                }
                case LEFT -> {
                    if (direction == Direction.clockwise)
                        faceTurning(Face.RIGHT, Direction.counterclockwise);
                    else faceTurning(Face.RIGHT, Direction.clockwise);
                }
                case RIGHT -> {
                    if (direction == Direction.clockwise)
                        faceTurning(Face.LEFT, Direction.counterclockwise);
                    else faceTurning(Face.LEFT, Direction.clockwise);
                }
                case UP -> {
                    if (direction == Direction.clockwise)
                        faceTurning(Face.DOWN, Direction.counterclockwise);
                    else faceTurning(Face.DOWN, Direction.clockwise);
                }
                case DOWN -> {
                    if (direction == Direction.clockwise)
                        faceTurning(Face.UP, Direction.counterclockwise);
                    else faceTurning(Face.UP, Direction.clockwise);
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
            int randomFace = new Random().nextInt(6);
            int randomDirection = new Random().nextInt(2);
            int randomCounter = new Random().nextInt(2) + 1;
            rotateSomeLayersTo(
                    Face.values()[randomFace],
                    Direction.values()[randomDirection],
                    randomCounter
            );
        }
    }
//Остальное
    public int size() {
        return size;
    }
    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.deepHashCode(cube);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RubikCube rubikCube = (RubikCube) o;
        return size == rubikCube.size && Arrays.deepEquals(cube, rubikCube.cube);
    }

    @Override
    public String toString() {
        StringJoiner RubikCubeToString = new StringJoiner("");
        for (int row = 0; row < size; row++) {
            RubikCubeToString.add(" ".repeat(size * 2));
            for (int column = 0; column < size; column++) {
                RubikCubeToString.add(cube[4][row][column].toString().charAt(0) + " ");
            }
            RubikCubeToString.add("\n");
        }
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                RubikCubeToString.add(cube[2][row][column].toString().charAt(0) + " ");
            }
            for (int column = 0; column < size; column++) {
                RubikCubeToString.add(cube[0][row][column].toString().charAt(0) + " ");
            }
            for (int column = 0; column < size; column++) {
                RubikCubeToString.add(cube[3][row][column].toString().charAt(0) + " ");
            }
            RubikCubeToString.add("\n");
        }
        for (int row = 0; row < size; row++) {
            RubikCubeToString.add(" ".repeat(size * 2));
            for (int column = 0; column < size; column++) {
                RubikCubeToString.add(cube[5][row][column].toString().charAt(0) + " ");
            }
            RubikCubeToString.add("\n");
        }
        for (int row = 0; row < size; row++) {
            RubikCubeToString.add(" ".repeat(size * 2));
            for (int column = 0; column < size; column++) {
                RubikCubeToString.add(cube[1][row][column].toString().charAt(0) + " ");
            }
            RubikCubeToString.add("\n");
        }
        return RubikCubeToString.toString();
    }
}