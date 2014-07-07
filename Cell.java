
/**
 * Representa una celda del mapa, contiene toda la información necesaria sobre ella y su entorno
 * 
 * @author Amador Navarro Lucas
 * @version 1.0      07/04/2014
 */
public class Cell
{
    // Situación de la celda en el mapa
    private int row;
    private int column;
    
    // Representa la situación anterior con una cadena de texto, 
    // Ejemplo: "0110" indica que hay pared a la derecha y abajo de la celda
    private String environment;
    
    // Indica el contenido de la celda: -1 pared, 0 nada, 1 punto pequeño, 2 punto grande
    private int content;

    /**
     * Constructor de celdas, recibe las coordenadas, el contenido y su entorno.
     * 
     * @param row           Linea dentro del mapa
     * @param column        Columna dentro del mapa
     * @param environment   Cadena de texto con las paredes de alrededor
     * @param content       Indica el contenido de la celda
     */
    public Cell(int row, int column, String environment, int content)
    {
        this.row = row;
        this.column = column;
        this.environment = environment;
        this.content = content;
    }

    /**
     * Modifica el contenido de la celda, simplemente la vacia y devuelve el contenido que tuviera.
     * 
     * @return              El contenido de la celda
     */
    public int cleanCell()
    {
        int cont = content;
        content = 0;
        return cont;
    }
    
    /**
     * Devuelve true si la celda es una pared, false en caso contrario.
     * 
     * @return              true si la celda es una pared, false en caso contrario.
     */
    public boolean isWall()
    {
        return content == -1;
    }
    
    /**
     * Devuelve su entorno en formato String
     * 
     * @return              Su entorno en formato String
     */
    public String getEnvironment()
    {
        return environment;
    }
    
    /**
     * Devuelve el contenido de la celda.
     * 
     * @return              El contenido de la celda.
     */
    public int getContent()
    {
        return content;
    }
}
