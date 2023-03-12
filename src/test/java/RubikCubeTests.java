import org.junit.jupiter.api.Test;
import org.rubicCube.Direction;
import org.rubicCube.Face;
import org.rubicCube.RubikCube;
import static org.junit.jupiter.api.Assertions.*;

public class RubikCubeTests {
    RubikCube cubeA = new RubikCube(3);
    RubikCube cubeB = new RubikCube(3);
    RubikCube cubeC = new RubikCube(3);
    RubikCube cubeD = new RubikCube(4);

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
    public void rotateSomeLayersTo() {
        cubeB.rotateSomeLayersTo(Face.FRONT, Direction.clockwise, 1);
        assertEquals(cubeA.faceCondition(Face.FRONT), cubeB.faceCondition(Face.FRONT));
        //assertEquals(cubeB.faceCondition(Face.UP)[], );
    }
}
