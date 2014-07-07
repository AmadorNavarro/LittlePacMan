import java.awt.event.KeyEvent;

/**
 * En esta clase implementamos el comportamiento de PacMan
 * 
 * @author Amador Navarro Lucas
 * @version 1.0         09/04/2014
 */
public class PacManActor extends Player
{
    // número de pixeles anchos y altos
    private static final int WIDTH = 24;
    private static final int HEIGHT = 24;
    
    // número máximo de fotogramas por dirección
    private static final int TOTAL_PHOTOGRAMS = 6;
    // número de pixels que se desplaza por turno, puede ser fracción.
    private static final double PIXELS = 1;
    
    // Dirección solicitada por el usuario
    private int nextDirection;
    
    // Indica si PacMan está moviéndose
    private boolean isMove;

    /**
     * Constructor de PacMan, se le dan las coordenadas iniciales, 
     * la dirección y las salidas de la celda. Empieza quieto.
     * 
     * @param row               La linea de inicio
     * @param column            La columna inicial
     * @param exits             La cadena de texto con las salidas de la celda
     * @param direction         La dirección inicial
     * @param pixels            Largo y ancho de cada celda
     */
    public PacManActor(int row, int column, String exits, int direction, int pixels)
    {
         super (row, column, exits, direction, pixels);
         
         // desplazamos media celda a PacMan para que empiece centrado
         setX(getX() + pixels / 2);
         isMove = false;
         setName("PacMan");
    }

    /**
     * Mueve a PacMan en la dirección adecuada un paso y actualiza el fotograma
     */
    public void nextMove()
    {
        int direction = getDirection();
        if (isMove && isAValidDirection(direction)) {
            progress(PIXELS);        
            nextPhotogram();
        }
        
        if (!isAValidDirection(direction) && nextDirection != direction) {
            setDirection(nextDirection);
        }
    }
    
    /**
     * Devuelve el número total de fotogramas por dirección
     * 
     * @return                  El número total de fotogramas por dirección
     */
    public int getTotalPhotograms()
    {
        return TOTAL_PHOTOGRAMS;
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
     * Devuelve la siguiente dirección de PacMan
     * 
     * @return              La siguiente dirección
     */
    public int getNextDirection()
    {
        return nextDirection;
    }
    
    /**
     * Cambia la dirección de PacMan y actualiza el valor de move.
     * 
     * @param newDirection      La nueva dirección a la que mira PacMan
     */
    public void setDirection(int direction)
    {
        super.setDirection(direction);
        nextPhotogram();
    }
    
    /**
     * recoge el valor de la tecla pulsada y actualiza la dirección
     * 
     * @param key
     */
    public void keyPressed(KeyEvent key)
    {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                nextDirection = 3;
                break;
                
            case KeyEvent.VK_RIGHT:
                nextDirection = 1;
                break;
                
            case KeyEvent.VK_UP:
                nextDirection = 0;
                break;
                
            case KeyEvent.VK_DOWN:
                nextDirection = 2;
                break;
        }
        
        if (!isMove) {
            setDirection(nextDirection);
            isMove = true;
        }
    }
}
