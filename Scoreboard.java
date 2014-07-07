
/**
 * Lleva la puntuación de PacMan y el número de puntos que faltan por comer
 * 
 * @author Amador Navarro Lucas 
 * @version 1.0         14/04/2014
 */
public class Scoreboard
{
    // Puntuación
    private int score;
    
    // Puntos restantes en juego
    private int points;

    /**
     * Inicializamos con 0 puntos
     * 
     * @param totalPoints       El número de puntos totales del juego
     */
    public Scoreboard(int totalPoints)
    {
        score = 0;
        points = totalPoints;
    }

    /**
     * Actualiza los campos cada vez que PacMan come un punto.
     */
    public void pointEat()
    {
        score += 10;
        points--;
    }
    
    /**
     * Devuelve la puntuación de PacMan
     * 
     * @return                  La puntuación de PacMan
     */
    public int getScore()
    {
        return score;
    }
    
    /**
     * Devuelve el número de puntos que restan por comer
     * 
     * @return                  El número de puntos sin comer
     */
    public int getPoints()
    {
        return points;
    }
}
