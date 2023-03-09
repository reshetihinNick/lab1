package org.rubicCube;

public interface RubikCubeModel {
    Object[][] faceCondition(Face face);
    void solvingTheCube(); //for cube 2x2x2 only
    void rotateSomeLayersTo(Face face, Direction direction, int layerCount);
    void rotateFaceTo(Face face, Direction direction);
    void cubeMixing();
    int size();
    boolean equals(Object obj);
}
