import org.junit.jupiter.api.Test;
import org.rubicCube.CubeColors;
import org.rubicCube.Direction;
import org.rubicCube.Face;
import org.rubicCube.RubikCube;

import static org.junit.jupiter.api.Assertions.*;

public class RubikCubeTests {
    int cubeA_Size = 3;
    int cubeB_Size = 3;
    int cubeC_Size = 3;
    int cubeD_Size = 4;
    RubikCube cubeA = new RubikCube(cubeA_Size);
    RubikCube cubeB = new RubikCube(cubeB_Size);
    RubikCube cubeC = new RubikCube(cubeC_Size);
    RubikCube cubeD = new RubikCube(cubeD_Size);
    @Test
    public void equals() {
        assertEquals(cubeA, cubeA);
        assertEquals(cubeB, cubeA);
        assertEquals(cubeA, cubeB);
        assertTrue(cubeA.equals(cubeB) == cubeA.equals(cubeC) == cubeB.equals(cubeC));
        assertNotEquals(cubeA, cubeD);
        cubeC.cubeMixing();
        assertNotEquals(cubeA, cubeC);
    }

    @Test
    public void cubeToString() {
        System.out.println(cubeA.toString());
    }

    @Test
    public void cubeSolving() {
        cubeA = new RubikCube(2);
        cubeA.cubeMixing();
        System.out.println(cubeA);
        System.out.println("Cube is solved with " + cubeA.solvingTheCube() + " steps");
        System.out.println(cubeA);
    }

    private CubeColors[] getFaceColumn(CubeColors[][] face, int column) {
        CubeColors[] faceColumn = new CubeColors[face.length];
        for (int row = 0; row < face.length; row++) {
            faceColumn[row] = face[row][column];
        }
        return faceColumn;
    }

    @Test
    public void rotateSomeLayersOrFacesTo() {
        cubeA.cubeMixing();
        CubeColors[][] frontCopy = cubeA.faceCondition(Face.FRONT);
        CubeColors[][] backCopy = cubeA.faceCondition(Face.BACK);
        CubeColors[][] leftCopy = cubeA.faceCondition(Face.LEFT);
        CubeColors[][] rightCopy = cubeA.faceCondition(Face.RIGHT);
        CubeColors[][] upCopy = cubeA.faceCondition(Face.UP);
        CubeColors[][] downCopy = cubeA.faceCondition(Face.DOWN);
        int layersToRotate = 1;
        int lastIndex = cubeA.size() - 1;
        cubeA.rotateSomeLayersTo(Face.FRONT, Direction.CLOCKWISE, layersToRotate);
        System.out.println(cubeA);
        for (int swapLayers = 0; swapLayers < layersToRotate; swapLayers++) {
            for (int box = 0; box < cubeA.size(); box++) {
                assertEquals(
                        upCopy[lastIndex - swapLayers][box],
                        getFaceColumn(cubeA.faceCondition(Face.RIGHT), swapLayers)[box]
                );
                assertEquals(
                        getFaceColumn(rightCopy, swapLayers)[box],
                        cubeA.faceCondition(Face.DOWN)[swapLayers][lastIndex - box]
                );
                assertEquals(
                        getFaceColumn(cubeA.faceCondition(Face.LEFT), lastIndex - swapLayers)[box],
                        downCopy[swapLayers][box]
                );
                assertEquals(
                        getFaceColumn(leftCopy, lastIndex - swapLayers)[box],
                        cubeA.faceCondition(Face.UP)[lastIndex - swapLayers][lastIndex - box]
                );
                assertEquals(
                        getFaceColumn(cubeA.faceCondition(Face.FRONT), lastIndex - swapLayers)[box],
                        frontCopy[swapLayers][box]
                );
                if (layersToRotate == lastIndex) {
                    assertEquals(
                            getFaceColumn(cubeA.faceCondition(Face.BACK), swapLayers)[box],
                            cubeB.faceCondition(Face.BACK)[swapLayers][lastIndex - box]
                    );
                }
            }
        }
        cubeA.rotateSomeLayersTo(Face.FRONT,Direction.COUNTERCLOCKWISE, layersToRotate);
        for (int row = 0; row < cubeA.size(); row++) {
            for (int box = 0; box < cubeA.size(); box++) {
                assertEquals(cubeA.faceCondition(Face.FRONT)[row][box], frontCopy[row][box]);
                assertEquals(cubeA.faceCondition(Face.BACK)[row][box], backCopy[row][box]);
                assertEquals(cubeA.faceCondition(Face.LEFT)[row][box], leftCopy[row][box]);
                assertEquals(cubeA.faceCondition(Face.RIGHT)[row][box], rightCopy[row][box]);
                assertEquals(cubeA.faceCondition(Face.UP)[row][box], upCopy[row][box]);
                assertEquals(cubeA.faceCondition(Face.DOWN)[row][box], downCopy[row][box]);
            }
        }
    }
}
