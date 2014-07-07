
/**
 * Enumeration class WallType - define el tipo de dibujo que representa este muro dependiendo de
 * su situación en el mapa. Guarda una referencia para poder devolver el tipo adecuado.
 * 
 * @author Amador Navarro Lucas
 * @version 1.0         23/04/2014
 */
public enum WallType
{
    DOUBLE_EXTERIOR_TOP_CORNER_RIGHT(50),
    DOUBLE_INTERIOR_TOP_CORNER_RIGHT(49), 
    DOUBLE_EXTERIOR_TOP_CORNER_LEFT(26),
    DOUBLE_INTERIOR_TOP_CORNER_LEFT(25), 
    DOUBLE_EXTERIOR_BOTTOM_CORNER_RIGHT(176), 
    DOUBLE_INTERIOR_BOTTOM_CORNER_RIGHT(175), 
    DOUBLE_EXTERIOR_BOTTOM_CORNER_LEFT(152), 
    DOUBLE_INTERIOR_BOTTOM_CORNER_LEFT(151), 
    DOUBLE_TOP_HORIZONTAL(56), 
    DOUBLE_BOTTOM_HORIZONTAL(55),
    DOUBLE_VERTICAL_LEFT(146), 
    DOUBLE_VERTICAL_RIGHT(145), 
    DOUBLE_TOP_TURN_FROM_LEFT(59),
    DOUBLE_TOP_TURN_FROM_RIGHT(62), 
    DOUBLE_LEFT_TURN_FROM_TOP(182), 
    DOUBLE_LEFT_TURN_FROM_BOTTOM(434),
    DOUBLE_RIGHT_TURN_FROM_TOP(155), 
    DOUBLE_RIGHT_TURN_FROM_BOTTOM(218), 
    EXTERIOR_TOP_CORNER_RIGHT(54),
    EXTERIOR_TOP_CORNER_LEFT(27), 
    INTERIOR_TOP_CORNER_RIGHT(507),
    INTERIOR_TOP_CORNER_LEFT(510),    
    TOP_HORIZONTAL(63), 
    BOTTOM_HORIZONTAL(504), 
    VERTICAL_LEFT(219), 
    VERTICAL_RIGHT(438),
    EXTERIOR_BOTTOM_CORNER_RIGHT(432), 
    EXTERIOR_BOTTOM_CORNER_LEFT(216),
    INTERIOR_BOTTOM_CORNER_RIGHT(255), 
    INTERIOR_BOTTOM_CORNER_LEFT(447), 
    POINT(1), 
    BIG_POINT(2), 
    NONE(0);
    
    public static WallType getTypeWithReference(int ref)
    {
        WallType wallType = WallType.NONE;
        for (WallType wall : WallType.values()) {
            if (wall.getReference() == ref) {
                wallType = wall;
            }
        }
        return wallType;
    }
    
    private final int reference;
    
    WallType(int ref)
    {
        reference = ref;
    }
    
    public int getReference()
    {
        return reference;
    }
}
