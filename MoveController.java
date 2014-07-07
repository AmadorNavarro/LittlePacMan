import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
import java.lang.Math;
import java.util.ArrayList;

/**
 * Es el controlador de todos los movimientos de los personajes, también se encarga de controlar 
 * el tiempo en el que los fantasmas pueden ser comidos y las colisiones.
 * 
 * @author Amador Navarro Lucas
 * @version 1.0         14/04/2014
 */
public class MoveController implements ActionListener
{
    // Indica cuantos milisegundos van a estar los fantasmas en el estado "comestibles"
    private static final int ARE_EATABLES = 3500;
    private static final int HURRY_UP = 1500;
    
    // Array de fantasmas
    private ArrayList<Phantom> phantoms;
    
    // PacMan
    private PacManActor pacMan;
    
    // Temporizador
    private Timer timer;
    private boolean lastSecond;
    
    // Mapa de juego
    private Map map;
    
    // pixeles de alto y ancho por celda
    private int pixels;
    
    // reproductor
    private GamePlayer player;

    /**
     * Preparamos el controlador para poder empezar el juego, recibe el mapa que se ha creado 
     * previamente en el controlador de juego
     */
    public MoveController(Map map, int pixels, GamePlayer player)
    {
        super();
        phantoms = new ArrayList<Phantom>();
        this.map = map;
        this.pixels = pixels;
        this.player = player;
        
        pacMan = new PacManActor(24, 14, map.getEnvironmentOfCell(24,14), 3, pixels);

        phantoms.add(new Blinky(15, 14, map.getEnvironmentOfCell(15, 14), 1, pixels, 0));
        phantoms.add(new Pinky(15, 15, map.getEnvironmentOfCell(15,15), 3, pixels, 50));
        phantoms.add(new Phantom(15, 12, map.getEnvironmentOfCell(15, 12), 1, "Inky", pixels, 100));
        phantoms.add(new Phantom(15, 16, map.getEnvironmentOfCell(15, 16), 3, "Clyde", pixels, 150));
    }
    
    /**
     * Pasa a PacMan la tecla pulsada
     * 
     * @param key                   La tecla pulsada
     */
    public void keyPressed(KeyEvent key) {
        pacMan.keyPressed(key);
    }
    
    /**
     * Cada turno se actualizan los movimientos de los personajes, 
     * si PacMan come un punto devuelve true para actualizar el marcador
     * 
     * @return                      true si PacMan come punto, false si la celda está vacia
     */
    public int move()
    {
        int pointEat = 0;
        pacMan.nextMove();
        
        if (pacMan.isOutOfCellBounds()) {
            pointEat = nextCell(pacMan);
        }
        
        for (Phantom phantom : phantoms) {
            if (phantom.isAValidDirection(phantom.getDirection()) && canExit(phantom)) {
                phantom.nextMove();
            } else {
                phantom.calculateNextDirection(pacMan.getRow(), pacMan.getColumn());
            }

            if (phantom.isOutOfCellBounds()) {
                nextCell(phantom);
            }
        }
        return pointEat;
    }
    
    /**
     * Devuelve si la salida de la casa de los fantasmas está libre
     * 
     * @param phantom                   el fantasma que quiere salir.
     * 
     * @return                          true si está libre, false si no.
     */
    private boolean canExit(Phantom phantom)
    {
        if (!phantom.isAtHome()) {
            return true;
        }
        
        boolean canExit = true;
        for (Phantom ph : phantoms) {
            if (phantom != ph) {
                if (ph.getRow() > 10 && ph.getRow() < 13 && ph.getColumn() > 13 && ph.getColumn() < 16) {
                    canExit = false;
                }
            }
        }
        return canExit;
    }
    
    /**
     * Le pasa los valores de la nueva celda al jugador, si es PacMan y se come un punto
     * devuelve el valor de la celda
     * 
     * @param player                    El jugador que recibe la nueva celda
     * 
     * @return                          true si PacMan come un punto, false si no.
     */
    private int nextCell(Player player)
    {
        int pointEat = 0;
        int row = player.getRow();
        int column = player.getColumn();
        int tunnel = map.getColumnLength() - 1;

        switch (player.getDirection()) {
            case 0:
                row--;
                player.setRow(row);
                break;
            
            case 1:
                column = (column + 1) % tunnel;
                player.setColumn(column);
                break;
            
            case 2:
                row++;
                player.setRow(row);
                break;
            
            case 3:
                column--;
                if (column == 0) {
                    column = tunnel;
                }
                player.setColumn(column);
                break;
        }

        player.setExits(map.getEnvironmentOfCell(row, column));
        if (column > 0 && column < map.getColumnLength() - 1) {
            player.setUpLocation();
        }
        
        if (player instanceof PacManActor) {
            pacMan.setDirection(pacMan.getNextDirection());
            if (map.getCellValue(row, column) > 0) {
                pointEat = map.setCellEmpty(row, column);
            
                if (pointEat == 2) {
                    dinnerTime();
                }
            }
        } else if (player instanceof Phantom) {
            Phantom phantom = (Phantom) player;
            if (phantom.isCrossroad() || !phantom.isAValidDirection(player.getDirection()) || phantom.isAtHome()) {
                
                if (column > 1 && column < map.getColumnLength() - 2) {
                    phantom.calculateNextDirection(pacMan.getRow(), pacMan.getColumn());
                }
            }
        }
        return pointEat;
    }
    
    /**
     * Pone a los fantasmas en el estado de "comestibles" y inicia el temporizador del tiempo
     * en el que van a estar.
     */
    private void dinnerTime()
    {
        lastSecond = true;
        timer = new Timer(ARE_EATABLES, this);
        timer.start();
        for (Phantom phantom : phantoms) {
            phantom.setIsEatable(true);
        }
    }
    
    /**
     * Devuelve un ArrayList con la posición y nombre de cada Personaje.
     * 
     * @return                  un ArrayList con la posición y nombre de cada Personaje.
     */
    public ArrayList<PlayerLocation> getPlayersLocation()
    {
        ArrayList<PlayerLocation> list = new ArrayList<PlayerLocation>();
        
        list.add(new PlayerLocation(pacMan.getX() - pixels, pacMan.getY() - pixels, pacMan.getImageName()));
        
        for (Phantom phantom : phantoms) {
            list.add(new PlayerLocation(phantom.getX() - pixels, phantom.getY() - pixels, phantom.getImageName()));
        }
        return list;
    }
    
    /**
     * Devuelve un punto donde y es la linea donde se encuentra PacMan y x es la columna
     * 
     * @return                  un punto con la posición de PacMan
     */
    public Point pacManPosition()
    {
        return new Point(pacMan.getColumn(), pacMan.getRow());
    }
    
    /**
     * Una vez han pasado el tiempo en el que se puede comer a los fantasmas, estos vuelven
     * a su estado normal y paramos el temporizador
     */
    public void actionPerformed(ActionEvent e)
    {
        timer.stop();
        if (lastSecond) {
            lastSecond = false;
            player.playTrack(TrackType.TIMEOUT);
            timer = new Timer(HURRY_UP, this);
            timer.start();
        } else {
            for (Phantom phantom : phantoms) {
                phantom.setIsEatable(false);
            }
        }
    }
    
    /**
     * Detecta y trata las colisiones entre los personajes, devuelve 0 si PacMan ha sido comido
     * 
     * @return                          0 si PacMan es capturado, 1 si es un fantasma el capturado
     */
    public int checkCollisions()
    {
        int check = -1;
        // Comprobamos primero que PacMan no haya chocado y si choca si se puede comer al fantasma
        for (Phantom phantom : phantoms) {
            if (checkTouch(pacMan, phantom)) {
                if (phantom.isEatable()) {
                    phantom.hasBeenEaten();
                    phantom.setExits(map.getEnvironmentOfCell(phantom.getRow(), phantom.getColumn()));
                    check = 1;
                } else {
                    return 0;
                }
            }
        }
        
        // Comprobamos si los fantasmas chocan entre si
        for (int i = 0; i < phantoms.size() - 1; i++) {
            for (int j = i + 1; j < phantoms.size(); j++) {
                Phantom first = phantoms.get(i);
                Phantom second = phantoms.get(j);
                
                if (!first.isAtHome() && !second.isAtHome() && directionsValids(first, second) &&
                    checkTouch(first, second)) {
                        first.swingDirection();
                        second.swingDirection();
                }
            }
        }
        return check;
    }
    
    /**
     * Devuelve true si los personajes tienen direcciones validas para chocar
     * 
     * @return                  true si son direcciones de choque
     */
    private boolean directionsValids(Phantom first, Phantom second)
    {
        if (first.getDirection() == second.getDirection()) {
            return false;
        }
        
        boolean valid = true;
        switch (first.getDirection()) {
            case 0:
                if (second.getDirection() == 2 && first.getY() < second.getY()) {
                    valid = false;
                }
                break;
            case 1:
                if (second.getDirection() == 3 && first.getX() > second.getX()) {
                    valid = false;
                }
                break;
            case 2:
                if (second.getDirection() == 0 && first.getY() > second.getY()) {
                    valid = false;
                }
                break;
            case 3:
                if (second.getDirection() == 1 && first.getX() < second.getX()) {
                    valid = false;
                }
                break;
        }
        return valid;
    }
    
    /**
     * Devuelve true si los dos personajes han colisionado
     * 
     * @param Player1                   Primer personaje
     * @param Player2                   Segundo personaje
     * 
     * @return                          true si han colisionado, false si no lo han hecho      
     */
    private boolean checkTouch(Player player1, Player player2) 
    {
        boolean collision = false;
        Integer widthPlayer1 = player1.getX() + player1.getWidth();
        Integer heightPlayer1 = player1.getY() + player1.getHeight();
        Integer widthPlayer2 = player2.getX() + player2.getWidth();
        Integer heightPlayer2 = player2.getY() + player2.getHeight();
        
        Integer width = Math.abs(widthPlayer1 - widthPlayer2) + 4;
        Integer height = Math.abs(heightPlayer1 - heightPlayer2) + 4;
        
        if (width < Math.max(player1.getWidth(), player2.getWidth()) &&
            height < Math.max(player1.getHeight(), player2.getHeight())) {
            collision = true;
        }
        
        return collision;
    }
}
