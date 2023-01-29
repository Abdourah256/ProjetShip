package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PositionSurCarteTest {
    PositionSurCarte positionSurCarte;
    @BeforeEach
    void setUp() {
        positionSurCarte = new PositionSurCarte(1,1);
    }

    @Test
    void avancerHorizontalement() {
        positionSurCarte.avancerHorizontalement();
        assertEquals(2, positionSurCarte.getX());
        assertEquals(1, positionSurCarte.getY());
    }

    @Test
    void avancerVerticalement() {
        positionSurCarte.avancerVerticalement();
        assertEquals(1, positionSurCarte.getX());
        assertEquals(2, positionSurCarte.getY());
    }

    @Test
    void reculerHorizontalement() {
        positionSurCarte.reculerHorizontalement();
        assertEquals(0, positionSurCarte.getX());
        assertEquals(1, positionSurCarte.getY());
    }

    @Test
    void reculerVerticalement() {
        assertEquals(1, positionSurCarte.getX());
        assertEquals(0, positionSurCarte.getY());
    }
}