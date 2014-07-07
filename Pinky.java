/**
 * Esta clase representa la implementacion de Pinky
 * 
 * @author Amador Navarro Lucas
 * @version 1.0         09/04/2014
 */
public class Pinky extends Phantom
{
    /**
     * Constructor de Pinky, recibe los parámetros necesarios para inicializarlo en la superclase
     * 
     * @param row               La linea de inicio
     * @param column            La columna inicial
     * @param exits             La cadena de texto con las salidas de la celda
     * @param direction         La dirección inicial
     * @param pixels            Alto y ancho de la celda
     * @param delay             Tiempo de retardo antes de empezar
     */
    public Pinky(int row, int column, String exits, int direction, int pixels, int delay)
    {
        super (row, column, exits, direction, "Pinky", pixels, delay);
    }

    /**
     * Calcula la nueva dirección de Pinky según donde se encuentre PacMan,
     * para ello se le pasan las coordenadas donde se encuentra él.
     * 
     * @param PacManRow         La linea donde está PacMan
     * @param PacManColumn      La columna donde está PacMan
     */
    public void calculateNextDirection(int pacManRow, int pacManColumn)
    {
        if (isEatable() || isAtHome()) {
            super.calculateNextDirection(pacManRow, pacManColumn);
            return;
        }
        
        if (isStuck()) {
            if (!isGoodCrossroad(pacManRow, pacManColumn)) {
                return;
            } else {
                setIsStuck(false);
            }
        }
        
        int newDirection = calculateHorizontalDirection(pacManColumn);
        if (newDirection < 0 || !isAValidDirection(newDirection)) {
            
            newDirection = calculateVerticalDirection(pacManRow);
            if (newDirection < 0) {
                setIsStuck(true);
                newDirection = isAValidDirection(0) ? 0 : 2;
            } 
            
            if (!isAValidDirection(newDirection)) {
                setIsStuck(true);
                newDirection = isAValidDirection(1) ? 1 : 3;
            }
        }
        setDirection(newDirection);
    }
}