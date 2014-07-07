
/**
 * Esta clase contiene el método main de la aplicación para ejecutarla
 * 
 * @author Amador Navarro Lucas
 * @version 1.0             14/05/2014
 */
public class Pacman
{
    private GameController game;
    /**
     * Contructor, instancia una nueva partida
     */
    public Pacman()
    {
        game = new GameController();
    }
    
    /**
     * main de la aplicación
     */
    public void main(String[] args) {
        new Pacman();
    }
}
