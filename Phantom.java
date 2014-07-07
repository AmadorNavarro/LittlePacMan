import java.util.Random;

/**
 * Fantasma es una clase concreta que define el comportamiento genérico de todos los fantasmas,
 * particularmente de Inky y Clyde.
 *
 * @author Amador Navarro Lucas
 * @version 1.0         09/04/2014
 */
public class Phantom extends Player
{
    // número de pixeles anchos y altos
    private static final int WIDTH = 28;
    private static final int HEIGHT = 24;
    
    // situación de la puerta de la casa
    private static final int HOME_ROW = 12;
    private static final int HOME_COLUMN = 14;
    
    // número máximo de fotogramas por dirección
    private static final int TOTAL_PHOTOGRAMS = 4;
    // número de pixels que se desplaza por turno, puede ser fracción.
    private static final double PIXELS = 1;
    
    // puede ser comido
    private boolean isEatable;
    
    // está atascado buscando una salida
    private boolean isStuck;
    
    // está en casa intentando salir
    private boolean isAtHome;
    
    // generador de direcciones aleatorias
    private Random randomGenerator;
    
    // turnos hasta poder salir
    private int exitDelay;

    /**
     * Constructor de la clase fantasma, solo se añade el nombre con respecto
     * a la super clase
     * 
     * @param row               La linea de inicio
     * @param column            La columna inicial
     * @param exits             La cadena de texto con las salidas de la celda
     * @param direction         La dirección inicial
     * @param name              El nombre del fantasma
     * @param pixels            Largo y ancho de la celda
     * @param delay             Turnos de espera antes de empezar
     */
    public Phantom(int row, int column, String exits, int direction, String name, int pixels, int delay)
    {
        super (row, column, exits, direction, pixels);
        setName(name);
        isEatable = false;
        isStuck = false;
        isAtHome = true;
        exitDelay = delay;
        randomGenerator = new Random();
    }

    /**
     * Modificador de isEatable
     * 
     * @param isIt              El nuevo valor
     */
    public void setIsEatable(boolean isIt)
    {
        isEatable = isIt;
    }
    
    /**
     * Devuelve el valor de isEatable
     * 
     * @return                  El valor de isEatable
     */
    public boolean isEatable()
    {
        return isEatable;
    }
    
    /**
     * Modificador de isStuck
     * 
     * @param isIt              El nuevo valor
     */
    public void setIsStuck(boolean isIt)
    {
        isStuck = isIt;
    }
    
    /**
     * Devuelve el valor de isStuck
     * 
     * @return                  El valor isStuck
     */
    public boolean isStuck()
    {
        return isStuck;
    }

    /**
     * Devuelve el valor de isAtHome
     */
    public boolean isAtHome()
    {
        return isAtHome;
    }
    
    /**
     * Devuelve el ancho del fantasma
     * 
     * @return                  El valor de WIDTH
     */
    public int getWidth()
    {
        return WIDTH;
    }
    
    /**
     * Devuelve el alto del fantasma
     * 
     * @return                  El valor de HEIGHT
     */
    public int getHeight()
    {
        return HEIGHT;
    }
    
    /**
     * Da la vuelta al fantasma
     */
    public void swingDirection()
    {
        setDirection((getDirection() + 2) % 4);
    }
    
    /**
     * Mueve al fantasma en la dirección indicada un paso y actualiza su fotograma
     */
    public void nextMove()
    {
        if (exitDelay > 0) {
            exitDelay--;
            return;
        }
        progress(PIXELS);
        nextPhotogram();        
    }
    
    /**
     * Devuelve el número de fotogramas de los fantasmas
     * 
     * @return                  El número de fotogramas
     */
    public int getTotalPhotograms()
    {
        return TOTAL_PHOTOGRAMS;
    }
    
    /**
     * Calcula la nueva dirección del fantasma siempre que sea posible. 
     * Recibe las coordenadas donde se encuentra PacMan para ser usadas por Blinky y Pinky
     * 
     * @param row               La linea donde se encuentra PacMan
     * @param column            La columna donde se encuentra PacMan
     */
    public void calculateNextDirection(int row, int column)
    {
        if (isAtHome) {
            calculateToExit();
        } else if (isCrossroad()) {
            calculateNextRandomDirection();
        } else {
            int newDirection = getDirection();
            while (!isAValidDirection(newDirection) || newDirection == (getDirection() + 2) % 4) {
                newDirection = (newDirection + 1) % 4;
            }
            setDirection(newDirection);
        }
    }
    
    /**
     * El fantasma busca la ruta para salir de casa
     */
    private void calculateToExit()
    {
        int newDirection = 0;
        if (getColumn() > HOME_COLUMN) {
            newDirection = 3;
        } else if (getColumn() < HOME_COLUMN) {
            newDirection = 1;
        } else if (getRow() > HOME_ROW) {
            newDirection = 0;
        } else {
            isAtHome = false;
        }
        setDirection(newDirection);
    }
    
    /**
     * Calcula una nueva dirección aleatoria, si no es válida escoge la siguiente en
     * el sentido de las agujas del reloj hasta que encuentra una válida.
     */
    private void calculateNextRandomDirection()
    {
        int newDirection = randomGenerator.nextInt(4);
        while (!isAValidDirection(newDirection)) {
            newDirection = (newDirection + 1) % 4;
        }
        setDirection(newDirection);
    }
    
    /**
     * Calcula una nueva dirección vertical según donde esté PacMan.
     * 0 si PacMan está más arriba del fantasma
     * 2 si está más abajo
     * -1 si está en la misma linea
     * 
     * @param pacManRow         La linea donde se encuentra PacMan
     * 
     * @return                  La nueva dirección
     */
    protected int calculateVerticalDirection(int pacManRow)
    {
        int newDirection;
        int row = getRow();
        if (row > pacManRow) {
            newDirection = 0;
        } else if (row < pacManRow) {
            newDirection = 2;
        } else {
            newDirection = -1;
        }
        return newDirection;
    }
    
    /**
     * Calcula una nueva dirección horizontal según donde esté PacMan.
     * 1 si PacMan está a la derecha del fantasma
     * 3 si está a la izquierda
     * -1 si está en la misma columna
     * 
     * @param pacManColumn      La columna donde se encuentra PacMan
     * 
     * @return                  La nueva dirección
     */
    protected int calculateHorizontalDirection(int pacManColumn)
    {
        int newDirection;
        int column = getColumn();
        if (column < pacManColumn) {
            newDirection = 1;
        } else if (column > pacManColumn) {
            newDirection = 3;
        } else {
            newDirection = -1;
        }
        return newDirection;
    }
    
    /**
     * Manda al fantasma comido a casa y lo inicializa
     */
    public void hasBeenEaten()
    {
        setRow(14);
        setColumn(14);
        setUpLocation();
        setDirection(0);
        isEatable = false;
        isAtHome = true;
        nextPhotogram();
    }
    
    /**
     * Indica si la encrucijada es ventajosa para el fantasma
     * 
     * @param pacManRow         La linea donde se encuentra PacMan
     * @param pacManColumn      La columna donde se encuentra PacMan
     * 
     * @return                  True si puede aprovechar la encrucijada, false si no
     */
    protected boolean isGoodCrossroad(int pacManRow, int pacManColumn)
    {
        boolean isGood = false;
        int direction = getDirection();
        if (direction == 1 || direction == 3) {
            if (isAValidDirection(2)) {
                isGood = pacManRow > getRow() ? true : false;
            }
            if (isAValidDirection(0)) {
                isGood = pacManRow < getRow() ? true : false;
            }
        }
        
        if (direction == 0 || direction == 2) {
            if (isAValidDirection(1)) {
                isGood = pacManColumn > getColumn() ? true : false;
            }
            if (isAValidDirection(3)) {
                isGood = pacManColumn < getColumn() ? true : false;
            }
        }
        return isGood;
    }
}
