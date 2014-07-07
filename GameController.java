import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Es el controlador de todo el juego, marca el ritmo de juego y envía la
 * información necesaria para presentarlo gráficamente
 * 
 * @author Amador Navarro Lucas
 * @version 1.1         09/04/2014
 */
public class GameController extends JFrame implements ActionListener
{
    // Indica cuantos milisegundos va a durar cada turno
    private static final int TURN_DURATION = 19;
    
    // Pixeles de ancho y alto de cada celda
    private static final int PIXELS = 16;
    
    // Temporizador
    private Timer timer;
    
    // Mapa de juego
    private Map map;
        
    // Marcador
    private Scoreboard scoreboard;
    
    // Controlador de movimientos
    private MoveController moveController;
    
    // Lienzos del juego
    private MapCanvas mapCanvas;
    
    // Reproductor de sonidos
    private GamePlayer player;

    /**
     * Inicializamos todos los objetos, creamos los fantasmas y a PacMan
     */
    public GameController()
    {
        super("PACMAN BY Amador Navarro");
                
        map = new Map();
        player = new GamePlayer();
        
        int rows = map.getRowLength();
        int columns = map.getColumnLength();
        int[][] cellValues = initCellValues(rows, columns);
        
        scoreboard = new Scoreboard(cellValues[0][0]);
        cellValues[0][0] = 0;
        
        mapCanvas = new MapCanvas(rows, columns, PIXELS, cellValues);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(columns * PIXELS, (rows + 4) * PIXELS);
        setLocationRelativeTo(null);
        
        moveController = new MoveController(map, PIXELS, player);
        mapCanvas.setLocations(moveController.getPlayersLocation());
        
        add(mapCanvas, BorderLayout.CENTER);
        
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent key) {
                if (key.getKeyCode() == 80) {
                    if (timer.isRunning()) {
                        timer.stop();
                    } else {
                        timer.restart();
                    }
                }
                moveController.keyPressed(key);
            }
        });
        setFocusable(true);
        setVisible(true);
        start();
    }

    /**
     * Inicia el juego y la animación 
     */
    private void start()
    {
        player.playTrack(TrackType.START);
        while (player.isPlaying()) {
        }
        timer = new Timer(TURN_DURATION, this);
        timer.start();
    }
    
    /**
     * Devuelve una matriz de enteros con los valores de las celdas.
     * 
     * @param rows          lineas de la matriz.
     * @param cloumns       columnas de la matriz.
     * 
     * @return              la matriz con los valores de las celdas.
     */
    private int[][] initCellValues(int rows, int columns)
    {
        int[][] values = new int[rows][columns];
        int totalPoints = 0;
        
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                int value = map.getCellValue(r, c);
                values[r][c] = value;
                totalPoints += value > 0 ? 1 : 0;
            }
        }
        values[0][0] = totalPoints;
        
        return values;
    }
    
    /**
     * Cada turno se actualiza la posición de los personajes en el canvas
     */
    public void actionPerformed(ActionEvent e)
    {
        int point = moveController.move();
        mapCanvas.setLocations(moveController.getPlayersLocation());
        if (point > 0) {
            scoreboard.pointEat();
            
            if (point == 1) {
                TrackType eat = (TURN_DURATION > 19) ? TrackType.EATING : TrackType.EATINGSHORT;
                player.playTrack(eat);
            } else {
                player.playTrack(TrackType.EATPILL);
            }
                
            mapCanvas.setScore(scoreboard.getScore());
            Point position = moveController.pacManPosition();
            mapCanvas.cleanCell(position.y, position.x);
            if (scoreboard.getPoints() == 0) {
                pacManWin();
                return;
            }
        }
        
        int resultColision = moveController.checkCollisions();
        if (resultColision == 0) {
            pacManIsDead();
        } else if (resultColision == 1) {
            player.playTrack(TrackType.EATGHOST);
        }
    }
    
    /**
     * Muere PacMan y se acaba el juego
     */
    private void pacManIsDead()
    {
        mapCanvas.gameOver();
        timer.stop();
        player.playTrack(TrackType.DIE);
    }
    
    /**
     * PacMan come el último punto y gana la partida
     */
    private void pacManWin()
    {
        mapCanvas.victory();
        timer.stop();
        player.playTrack(TrackType.WIN);
    }
}
