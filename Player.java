
/**
 * Clase abstracta que define qué es un personaje del juego.
 * 
 * @author Amador Navarro Lucas
 * @version 1.0     08/04/2014
 */
public abstract class Player
{
    // localización en el mapa
    private int row;
    private int column;
    
    // localizacion en el canvas
    private double x;
    private double y;
    
    // pixeles por celda
    private int pixels;
    
    // El nombre del Player
    private String name;
    
    // las salidas de la celda actual
    private String exits;
    
    // Dirección actual:0 arriba, 1 derecha, 2 abajo, 3 izquierda
    private int direction;
    
    // Fotograma acutal
    private int photogram;
    /**
     * Constructor la clase abstracta Player, para evitar que dos Players caigan 
     * en el mismo sitio se obliga a indicarles sus coordenadas en el mapa.
     * Así mismo se les pasa las salidas de la celda y su dirección inicial.
     * 
     * @param row               La linea de inicio
     * @param column            La columna inicial
     * @param exits             La cadena de texto con las salidas de la celda
     * @param direction         La dirección inicial
     * @param pixels            Largo y ancho de las celdas
     */
    public Player(int row, int column, String exits, int direction, int pixels)
    {
        this.row = row;
        this.column = column;
        this.exits = exits;
        this.direction = direction;
        this.pixels = pixels;
        
        setUpLocation();
        photogram = -1;
        nextPhotogram();
    }
    
    /**
     * Ancho del personaje
     */
    abstract public int getWidth();
    
    /**
     * Alto del personaje
     */
    abstract public int getHeight();

    /**
     * Siguiente movimiento
     */
    abstract public void nextMove();
    
    /**
     * Devuelve el número total de fotogramas por dirección de movimiento
     * 
     * @return                  El total de fotogramas
     */
    abstract int getTotalPhotograms();
    
    /**
     * Indica si puede ser comido o no.
     * 
     * @return              true si puede serlo, false si no
     */
    public boolean isEatable()
    {
        return false;
    }
    
    /**
     * Indica la nueva linea en el mapa
     * 
     * @param row               La nueva linea
     */
    public void setRow(int row)
    {
        this.row = row;
    }
    
    /**
     * Devuelve la linea actual en el mapa
     * 
     * @return                  la linea actual.
     */
    public int getRow()
    {
        return row;
    }
    
    /**
     * Indica la nueva columna en el mapa
     * 
     * @param column            La nueva columna
     */
    public void setColumn(int column)
    {
        this.column = column;
    }
    
    /**
     * Devuelve la columna actual en el mapa
     * 
     * @return                  La columna actual
     */
    public int getColumn()
    {
        return column;
    }
    
    /**
     * Devuelve el número de pixeles por celda
     * 
     * @return                  El número de pixeles
     */
    public int getPixels()
    {
        return pixels;
    }
    
    /**
     * Genera y actualiza las coordenadas del jugador según su posición en el mapa
     */
    public void setUpLocation()
    {
        x = column * pixels + pixels / 2;
        y = row * pixels + pixels / 2;
    }
    
    /**
     * Grabamos las salidas de la nueva celda
     * 
     * @param exits             Las nuevas salidas de la celda
     */
    public void setExits(String exits) {
        this.exits = exits;
    }
    
    /**
     * Indica la nueva posición x en el canvas
     * 
     * @param x                 la nueva posición en el canvas
     */
    public void setX(double x)
    {
        this.x = x;
    }
    
    /**
     * Devuelve la posición x en el canvas
     * 
     * @return                  la posición x
     */
    public int getX()
    {
        return (int)x;
    }
    
    /**
     * Indica la nueva posición y en el canvas
     * 
     * @param y                 la nueva posición en el canvas
     */
    public void setY(double y)
    {
        this.y = y;
    }
    
    /**
     * Devuelve la posición y en el canvas
     * 
     * @return                  la posición y
     */
    public int getY()
    {
        return (int)y;
    }
    
    /**
     * Modifica el nombre del personaje
     * 
     * @param name              el nombre del personaje
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * Devuelve el nombre del personaje.
     * 
     * @return                  el nombre del personaje.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Devuelve el nombre de la imagen según su fotograma.
     * 
     * @return                  el nombre de la imagen según su fotograma.
     */
    public String getImageName()
    {
        String firstName = isEatable() ? "eatable" : name;
        StringBuffer imageName =  new StringBuffer(firstName);
        String phot = "" + (isEatable() ? photogram % 4 : photogram);
        
        for (int i = 0; i < 3 - phot.length(); i++) {
            imageName.append("0");
        }
        imageName.append(phot);
        
        return imageName.toString();
    }
    
    /**
     * Indica la nueva dirección del player
     * 
     * @param direction         la nueva dirección
     */
    public void setDirection(int direction)
    {
        this.direction = direction;
    }
    
    /**
     * Devuelve la dirección actual del player
     * 
     * @return                  la dirección del player
     */
    public int getDirection()
    {
        return direction;
    }
    
    /**
     * Cambia al siguiente fotograma del player y lo devuelve
     * 
     * @return                  El nuevo fotograma
     */
    public void nextPhotogram()
    {
        int totalPhotograms = getTotalPhotograms();
        photogram++;
        photogram = photogram % totalPhotograms + direction * totalPhotograms;
    }
    
    /**
     * Modifica la posición según la dirección.
     */
    protected void progress(double pixels)
    {
    switch (getDirection()) {
            case 0: 
                y -= pixels;
                break;
                
            case 1:
                x += pixels;
                break;
                
            case 2:
                y += pixels;
                break;
                
            case 3:
                x -= pixels;
                break;
        }
    }
    
    /**
     * Devuelve si la dirección es válida
     * 
     * @param newDirection      la dirección donde se quiere ir.
     * 
     * @return                  true si hay salida en esa dirección, false si no.
     */
    public boolean isAValidDirection(int newDirection)
    {
        boolean ok = exits.substring(newDirection, newDirection + 1).equals("0");

        if (newDirection == 2 && column > 13 && column < 16 && row == 12) {
            ok = false;
        }

        return ok;
    }
    
    /**
     * Devuelve si es una encrucijada
     * 
     * @return                  true si hay más de dos salidas, false en otro caso
     */
    public boolean isCrossroad()
    {
        int walls = 0;
        for (int i = 0; i < exits.length(); i++) {
            walls += Integer.parseInt(exits.substring(i, i + 1));
        }
        return walls < 2;
    }
    
    /**
     * Devuelve si está fuera del dominio de su celda
     *
     * @param pixels                el ancho y alto en pixeles de la celda
     * 
     * @return                      true si está fuera de la celda, false si está dentro
     */
    public boolean isOutOfCellBounds()
    {
        boolean out = false;
        switch (direction) {
            case 0:
                out = (int)y <= (row - 1) * pixels + pixels / 2;
                break;
            
            case 1:
                out = (int)x >= (column + 1) * pixels + pixels / 2;
                break;
            
            case 2:
                out = (int)y >= (row + 1) * pixels + pixels / 2;
                break;
                
            case 3:
                out = (int)x <= (column - 1) * pixels + pixels / 2;
                break;
        }
        return out;
    }
}
