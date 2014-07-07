import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

/**
 * Muestra en pantalla todos los cambios ocurridos interiormente en el juego
 * 
 * @author Amador Navarro Lucas
 * @version 1.0         22/04/2014
 */
public class MapCanvas extends JPanel
{    
    // Pixeles por celda
    private int pixels;
    
    // Entorno gráfico
    private Graphics2D graphics;
    
    // Matriz con los valores iniciales de las celdas
    private int rows;
    private int columns;
    private int[][] cellsValue;
    
    // Puntuación
    private int score;

    // ArrayList con los datos de los personajes
    private ArrayList<PlayerLocation> locations;
    
    // Flags para cuando se ha terminado la partida
    private boolean gameOver;
    private boolean pacManWin;
    
    /**
     * Inicializa el lienzo del mapa y lo preparamos para pintarle el mapa encima.
     * 
     * @param rows                  El número de lineas que tiene el mapa.
     * @param columns               El número de columnas.
     * @param pixels                El número de pixeles de ancho y alto de cada celda.
     */
    public MapCanvas(int rows, int columns,int pixels, int[][] cellValues)
    {
        super();
        this.rows = rows;
        this.columns = columns;
        this.pixels = pixels;
        this.cellsValue = cellValues;
        gameOver = false;
        pacManWin = false;
        locations = new ArrayList<PlayerLocation>();
        
        setDoubleBuffered(true);
        setBackground(Color.BLACK);
    }
    
    /**
     * Establece la nueva puntuación
     * 
     * @param score             La nueva puntuación
     */
    public void setScore(int score)
    {
        this.score = score;
    }

    /**
     * Pinta el mapa inicial y guarda el contexto gráfico para reutilizarlo
     * 
     * @param g                     El contexto gráfico
     */
    public void paint(Graphics g)
    {
        super.paint(g);
        
        Graphics2D g2D = (Graphics2D) g;
        graphics = g2D;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                                               RenderingHints.VALUE_ANTIALIAS_OFF);
                                               
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2D.setRenderingHints(rh);
        
        int mid = pixels / 2;
        int quarter = mid / 2;
        int eighth = quarter / 2;
        int qPlusE = quarter + eighth;
        int mPlusE = mid + eighth;
        int mPlusQ = mid + quarter;
        int mPlusQE = mid + qPlusE;
        
        for (int row = 1; row < rows - 1; row++) {
            for (int column = 1; column < columns - 1; column++) {
                int x = column * pixels;
                int y = row * pixels;
                g2D.setColor(Color.BLUE);

                switch (getWallType(row,column)) {
                    case DOUBLE_EXTERIOR_TOP_CORNER_RIGHT:
                        
                        g2D.fillRect(x, y, mPlusQ, eighth);
                        g2D.fillRect(x + mPlusQ, y + eighth,eighth, eighth);
                        g2D.fillRect(x + mPlusQE, y + quarter, eighth, mPlusQ);
                        g2D.fillRect(x, y + quarter, mPlusE, eighth);
                        g2D.fillRect(x + mPlusE, y + qPlusE, eighth, mPlusE);
                        break;

                    case DOUBLE_INTERIOR_TOP_CORNER_RIGHT:
                        
                        g2D.fillRect(x, y + mPlusE, eighth, eighth);
                        g2D.fillRect(x + eighth, y + mPlusQ, eighth, eighth);
                        g2D.fillRect(x + quarter, y + mPlusQE, eighth, eighth);
                        break;
                        
                    case DOUBLE_EXTERIOR_TOP_CORNER_LEFT:
                        
                        g2D.fillRect(x + quarter, y, mPlusQ, eighth);
                        g2D.fillRect(x + eighth, y + eighth, eighth, eighth);
                        g2D.fillRect(x, y + quarter, eighth, mPlusQ);
                        g2D.fillRect(x + qPlusE, y + quarter, mPlusE, eighth);
                        g2D.fillRect(x + quarter, y + qPlusE, eighth, mPlusE);
                        break;
                    
                    case DOUBLE_INTERIOR_TOP_CORNER_LEFT:
                        
                        g2D.fillRect(x + mPlusQE, y + mPlusE, eighth, eighth);
                        g2D.fillRect(x + mPlusQ, y + mPlusQ, eighth, eighth);
                        g2D.fillRect(x + mPlusE, y + mPlusQE, eighth, eighth);
                        break;
                    
                    case DOUBLE_EXTERIOR_BOTTOM_CORNER_RIGHT:
                        
                        g2D.fillRect(x, y + mPlusQE, mPlusQ, eighth);
                        g2D.fillRect(x + mPlusQ, y + mPlusQ, eighth, eighth);
                        g2D.fillRect(x + mPlusQE, y, eighth, mPlusQ);
                        g2D.fillRect(x, y + mPlusE, mPlusE, eighth);
                        g2D.fillRect(x + mPlusE, y, eighth, mPlusE);
                        break;

                    case DOUBLE_INTERIOR_BOTTOM_CORNER_RIGHT:
                        
                        g2D.fillRect(x + quarter, y, eighth, eighth);
                        g2D.fillRect(x + eighth, y + eighth, eighth, eighth);
                        g2D.fillRect(x, y + quarter, eighth, eighth);
                        break;
                        
                    case DOUBLE_EXTERIOR_BOTTOM_CORNER_LEFT:
                    
                        g2D.fillRect(x + quarter, y, eighth, mPlusE);
                        g2D.fillRect(x + qPlusE, y + mPlusE, mPlusE, eighth);
                        g2D.fillRect(x, y, eighth, mPlusQ);
                        g2D.fillRect(x + eighth, y + mPlusQ, eighth, eighth);
                        g2D.fillRect(x + quarter, y + mPlusQE, mPlusQ, eighth);
                        break;
                    
                    case DOUBLE_INTERIOR_BOTTOM_CORNER_LEFT:
                    
                        g2D.fillRect(x + mPlusE, y, eighth, eighth);
                        g2D.fillRect(x + mPlusQ, y + eighth, eighth, eighth);
                        g2D.fillRect(x + mPlusQE, y + quarter, eighth, eighth);
                        break;
                        
                    case DOUBLE_TOP_HORIZONTAL:
                    
                        g2D.fillRect(x, y, pixels, eighth);
                        g2D.fillRect(x, y + quarter, pixels, eighth);
                        break;
                    
                    case DOUBLE_BOTTOM_HORIZONTAL:
                        
                        g2D.fillRect(x, y + mPlusE, pixels, eighth);
                        g2D.fillRect(x, y + mPlusQE, pixels, eighth);
                        break;
                    
                    case DOUBLE_VERTICAL_LEFT:
                    
                        g2D.fillRect(x, y, eighth, pixels);
                        g2D.fillRect(x + quarter, y, eighth, pixels);
                        break;
                    
                    case DOUBLE_VERTICAL_RIGHT:
                    
                        g2D.fillRect(x + mPlusE, y, eighth, pixels);
                        g2D.fillRect(x + mPlusQE, y, eighth, pixels);
                        break;
                    
                    case DOUBLE_TOP_TURN_FROM_LEFT:
                    
                        g2D.fillRect(x, y, mPlusE, eighth);
                        g2D.fillRect(x, y + quarter, eighth, eighth);
                        g2D.fillRect(x + eighth, y + qPlusE, eighth, eighth);
                        g2D.fillRect(x + quarter, y + mid, eighth, mid);
                        break;
                    
                    case DOUBLE_TOP_TURN_FROM_RIGHT:
                        
                        g2D.fillRect(x, y, pixels, eighth);
                        g2D.fillRect(x + mPlusQE, y + quarter, eighth, eighth);
                        g2D.fillRect(x + mPlusQ, y + qPlusE, eighth, eighth);
                        g2D.fillRect(x + mPlusE, y + mid, eighth, mid);
                        break;
                    
                    case DOUBLE_LEFT_TURN_FROM_TOP:
                        
                        g2D.fillRect(x + mPlusQE, y, eighth, pixels);
                        g2D.fillRect(x + mPlusE, y, eighth, eighth);
                        g2D.fillRect(x + mid, y + eighth, eighth, eighth);
                        g2D.fillRect(x, y + quarter, mid, eighth);
                        break;
                    
                    case DOUBLE_LEFT_TURN_FROM_BOTTOM:
                        
                        g2D.fillRect(x + mPlusQE, y, eighth, pixels);
                        g2D.fillRect(x + mPlusE, y + mPlusQE, eighth, eighth);
                        g2D.fillRect(x + mid, y + mPlusQ, eighth, eighth);
                        g2D.fillRect(x, y + mPlusE, mid, eighth);
                        break;
                    
                    case DOUBLE_RIGHT_TURN_FROM_TOP:
                        
                        g2D.fillRect(x, y, eighth, pixels);
                        g2D.fillRect(x + quarter, y, eighth, eighth);
                        g2D.fillRect(x + qPlusE, y + eighth, eighth, eighth);
                        g2D.fillRect(x + mid, y + quarter, mid, eighth);
                        break;
                    
                    case DOUBLE_RIGHT_TURN_FROM_BOTTOM:
                        
                        g2D.fillRect(x, y, eighth, pixels);
                        g2D.fillRect(x + quarter, y + mPlusQE, eighth, eighth);
                        g2D.fillRect(x + qPlusE, y + mPlusQ, eighth, eighth);
                        g2D.fillRect(x + mid, y + mPlusE, mid, eighth);
                        break;
                    
                    case EXTERIOR_TOP_CORNER_RIGHT:
                    
                        g2D.fillRect(x, y + quarter, mid, eighth);
                        g2D.fillRect(x + mid, y + qPlusE, eighth, eighth);
                        g2D.fillRect(x + mPlusE, y + mid, eighth, mid);
                        break;
                        
                    case INTERIOR_TOP_CORNER_RIGHT:
                    
                        g2D.fillRect(x, y + mPlusE, eighth, eighth);
                        g2D.fillRect(x + eighth, y + mPlusQ, eighth, eighth);
                        g2D.fillRect(x + quarter, y + mPlusQE, eighth, eighth);
                        break;
                    
                    case EXTERIOR_TOP_CORNER_LEFT:
                        
                        g2D.fillRect(x + mid, y + quarter, mid, eighth);
                        g2D.fillRect(x + qPlusE, y + qPlusE, eighth, eighth);
                        g2D.fillRect(x + quarter, y + mid, eighth, mid);
                        break;
                    
                    case INTERIOR_TOP_CORNER_LEFT:
                        
                        g2D.fillRect(x + mPlusE, y + mPlusQE, eighth, eighth);
                        g2D.fillRect(x + mPlusQ, y + mPlusQ, eighth, eighth);
                        g2D.fillRect(x + mPlusQE, y + mPlusE, eighth, eighth);
                        break;
                    
                    case TOP_HORIZONTAL:
                    
                        g2D.fillRect(x, y + quarter, pixels, eighth);
                        break;
                    
                    case BOTTOM_HORIZONTAL:
                    
                        g2D.fillRect(x, y + mPlusE, pixels, eighth);
                        break;
                    
                    case VERTICAL_LEFT:
                    
                        g2D.fillRect(x + quarter, y, eighth, pixels);
                        break;
                    
                    case VERTICAL_RIGHT:
                    
                        g2D.fillRect(x + mPlusE, y, eighth, pixels);
                        break;
                    
                    case EXTERIOR_BOTTOM_CORNER_RIGHT:
                    
                        g2D.fillRect(x, y + mPlusE, mid, eighth);
                        g2D.fillRect(x + mid, y + mid, eighth, eighth);
                        g2D.fillRect(x + mPlusE, y, eighth, mid);
                        break;

                    case INTERIOR_BOTTOM_CORNER_RIGHT:
                    
                        g2D.fillRect(x + quarter, y, eighth, eighth);
                        g2D.fillRect(x + eighth, y + eighth, eighth, eighth);
                        g2D.fillRect(x, y + quarter, eighth, eighth);
                        break;
                        
                    case EXTERIOR_BOTTOM_CORNER_LEFT:
                    
                        g2D.fillRect(x + mid, y + mPlusE, mid, eighth);
                        g2D.fillRect(x + qPlusE, y + mid, eighth, eighth);
                        g2D.fillRect(x + quarter, y, eighth, mid);
                        break;
                    
                    case INTERIOR_BOTTOM_CORNER_LEFT:
                    
                        g2D.fillRect(x + mPlusE, y, eighth, eighth);
                        g2D.fillRect(x + mPlusQ, y + eighth, eighth, eighth);
                        g2D.fillRect(x + mPlusQE, y + quarter, eighth, eighth);
                        break;
                        
                    case POINT:
                    
                        g2D.setColor(Color.YELLOW);
                        g2D.fillRect(x + qPlusE, y + qPlusE, quarter, quarter);
                        break;

                    case BIG_POINT:
                    
                        g2D.setColor(Color.YELLOW);
                        g2D.fillRect(x + quarter, y + eighth, mid, mPlusQ);
                        g2D.fillRect(x + eighth, y + quarter, mPlusQ, mid);
                        break;
                        
                    default:
                        break;
                }
            }
        }
        g2D.setColor(Color.WHITE);
        g2D.setFont(new Font(Font.SANS_SERIF, Font.BOLD, pixels));
        g2D.drawString("Puntuación: " + score, pixels, (int) getSize().getHeight() - pixels);
        paintPlayers(g);
    }

    /**
     * pinta los personajes
     *
     * @param g                     El entorno gráfico
     */
    private void paintPlayers(Graphics g)
    {
        Graphics2D g2D = (Graphics2D) g;
        for (PlayerLocation location : locations) {
            String imageName = "/images/" + location.getImageName() + ".png";
            int x = location.getX();
            
            if (x > (columns - 3) * pixels) {
                x = x - (columns - 2) * pixels;
            }
            
            try {
                Image image = new ImageIcon(this.getClass().getResource(imageName)).getImage();
                if (x > -17) {
                    g2D.drawImage(image, x, location.getY(), this);
                }
                
                if (x < pixels) {
                    g2D.drawImage(image, x + (columns - 2) * pixels, location.getY(), this);
                    g2D.setColor(Color.BLACK);
                    int height = (int) getSize().getHeight();
                    g2D.fillRect(0, 0, pixels, height);
                    g2D.fillRect((columns - 1) * pixels, 0, pixels, height);
                }
            } catch (NullPointerException error) {
                System.out.println("no carga la imagen: " + imageName);
            }    
        }
        
        if (gameOver) {
            paintGameOver(g2D);
        }
        
        if (pacManWin) {
            paintVictory(g2D);
        }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    /**
     *  Recoge los datos y ordena repintar el panel
     * 
     * @param newLocations              ArrayList con las nuevas posiciones.
     */
    public void setLocations(ArrayList<PlayerLocation> newLocations)
    {
        locations = newLocations;
        repaint();
    }
    
    /**
     * Limpia una celda
     * 
     * @param row              Linea de la celda a limpiar
     * @param column           Columna de la celda a limpiar 
     */
    public void cleanCell(int row, int column)
    {
        cellsValue[row][column] = 0;
        graphics.setColor(Color.BLACK);
        graphics.fillRect((column) * pixels, (row) * pixels, pixels, pixels);
    }
    
    /**
     * Devuelve el tipo de muro que hay que pintar.
     * 
     * @return              El TypeWall del muro a pintar.
     */
    private WallType getWallType(int row, int column) 
    {    
        WallType wall = WallType.NONE;
        switch (cellsValue[row][column]) {
            case -1:
                int cellValue = simplifyDecimalValue(row, column);
                
                if (cellValue == 56) {
                    int prevCell = getWallType(row, column - 1).getReference();
                    if (prevCell == 152 || prevCell == 55 || prevCell == 25) { 
                        cellValue--;
                    }
                } else if (cellValue == 55) {
                    int prevCell = getWallType(row, column - 1).getReference();
                    if (prevCell == 151 || prevCell == 175 || prevCell == 56 ) {
                    cellValue++;
                    }
                } else if (cellValue == 146) {
                    int prevCell = getWallType(row - 1, column).getReference();
                    if (prevCell == 50 || prevCell == cellValue - 1 || prevCell == 25) {
                        cellValue--;
                    }
                } else if (cellValue == 145) {
                    int prevCell = getWallType(row - 1, column).getReference();
                    if (prevCell == 49 || prevCell == cellValue + 1) {
                        cellValue++;
                    }
                } else if (cellValue == 50) {
                    int prevCell = getWallType(row, column - 1).getReference();
                    if (prevCell == 55) {
                        cellValue--;
                    }
                } else if (cellValue == 176) {
                    int prevCell = getWallType(row - 1, column).getReference();
                    if (prevCell == 146) {
                        cellValue--;
                    }
                } else if (cellValue == 26) {
                    if (column > 5 || cellsValue[row][column - 1] == 1) {
                        cellValue--;
                    } 
                } else if (cellValue == 152) {
                    int prevCell = getWallType(row - 1, column).getReference();
                    if (prevCell == 145) {
                        cellValue--;
                    }
                } else if (cellValue == 24) {
                    int r = row;
                    int newValue = 0;
                    do {
                        r--;
                        newValue = getWallType(r, column).getReference();
                    } while (newValue == 0);
                    if (newValue == 152) {
                        cellValue = 56;
                    } else {
                        cellValue = 55;
                    }
                } else if (cellValue == 48) {
                    cellValue = getWallType(row, column - 1).getReference();
                }
                wall = WallType.getTypeWithReference(cellValue);
                break;
                
            case 0:
                wall = WallType.NONE;
                break;
                
            case 1:
                wall = WallType.POINT;
                break;
                
            case 2:
                wall = WallType.BIG_POINT;
                break;
                
            default:
                System.out.println("Error en valor de celda " + cellsValue[row][column]);
                break;
        }
        return wall;
    }
    
    /**
     * Según el entorno del muro pasamos el código del tipo de muro a pintar
     * 
     * @param row       La linea de la celda
     * @param column    La columna de la celda
     * 
     * @return          La referencia de tipo.
     */
    private int simplifyDecimalValue(int row, int column)
    {
        String binaryCell = getBinaryFromCell(row, column);
        int cellValue = getDecimalValueFromBinary(binaryCell);
        int realValue = cellValue;
        if (cellValue == 60 || cellValue == 57) {
            realValue = 56;
        } else if (cellValue == 312 || cellValue == 120) {
            realValue = 55;
        } else if (cellValue == 210 || cellValue == 147) {
            realValue = 146;
        } else if (cellValue == 402 || cellValue == 150) {
            realValue = 145;
        } else if (cellValue == 508 || cellValue == 505) {
            realValue = 504;
        } else if (cellValue == 319 || cellValue == 127) {
            realValue = 63;
        } else if (cellValue == 475 || cellValue == 223) {
            realValue = 219;
        } else if (cellValue == 502 || cellValue == 439) {
            realValue = 438;
        }
        return realValue;
    }
    
    /**
     * Devuelve un string con la representacion en binario del entorno de la celda.
     * 
     * @return              La representacion en binario del entorno de la celda.
     */
    private String getBinaryFromCell(int row, int column)
    {
        String binary = "";
        int newValue;
        for (int r = -1; r < 2; r++) {
            for (int c = -1; c < 2; c++) {
                newValue = cellsValue[row + r][column + c] < 0 ? 1 : 0;
                binary = binary + newValue;
            }
        }
        return binary;
    }
    
    /**
     * Devuelve un entero con el valor decimal del binario pasado en un String
     * 
     * @return              El valor decimal
     */
    private int getDecimalValueFromBinary(String binary)
    {
        int value = 0;
        int length = binary.length();
        int idx = length;
        for (int i = 0; i < length; i++) {
            idx--;
            value += Integer.parseInt(binary.substring(idx, idx + 1)) * Math.pow(2, i);
        }
        return value;
    }
    
    /**
     * Marca la bandera como fin de partida y repinta la pantalla
     */
    public void gameOver()
    {
        gameOver = true;
        repaint();
    }
    
    /**
     * Marca la bandera como PacMan vencedor y repinta la pantalla
     */
    public void victory()
    {
        pacManWin = true;
        repaint();
    }
    
    /**
     * Pintamos en pantalla fin de la partida por muerte de PacMan
     */
    private void paintGameOver(Graphics2D g2D)
    {
        g2D.setColor(Color.WHITE);
        g2D.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 64));
        g2D.drawString("GAME OVER", 40, (int) getSize().getHeight() / 2);
        
    }
    
    /**
     * Pintamos en la pantalla el mensaje de victoria
     */
    private void paintVictory(Graphics2D g2D)
    {
        g2D.setColor(Color.WHITE);
        g2D.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 64));
        g2D.drawString("YOU WIN!!", 70, (int) getSize().getHeight() / 2);
    }
}
