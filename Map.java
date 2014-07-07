import java.util.ArrayList;

/**
 * Contiene todas las celdas del mapa y gestiona su acceso y sus datos
 * 
 * @author Amador Navarro Lucas
 * @version 1.0     08/04/2014
 */
public class Map
{
    // Dimensiones del mapa
    private static final int ROWS = 33;
    private static final int COLUMNS = 30;
    
    // Mapa de celdas (linea, columna)
    private Cell[][] map;

    /**
     * Constructor de la clase map.
     */
    public Map()
    {
        map = new Cell[ROWS][COLUMNS];
        fillMap();
    }
    
    /**
     * Devuelve el número de lineas del mapa.
     * 
     * @return              El número de lineas del mapa.
     */
    public int getRowLength()
    {
        return ROWS;
    }

    
    /**
     * Devuelve el número de columnas del mapa.
     * 
     * @return              El número de columnas del mapa.
     */
    public int getColumnLength()
    {
        return COLUMNS;
    }
    
    /**
     * Rellena de celdas el mapa.
     */
    private void fillMap()
    {
        ArrayList<String> list = new ArrayList<String>();

        list.add("000000000000000000000000000000");
        list.add("0----------------------------0");
        list.add("0-111111111111--111111111111-0");
        list.add("0-1----1-----1--1-----1----1-0");
        list.add("0-2----1-----1--1-----1----2-0");
        list.add("0-1----1-----1--1-----1----1-0");
        list.add("0-11111111111111111111111111-0");
        list.add("0-1----1--1--------1--1----1-0");
        list.add("0-1----1--1--------1--1----1-0");
        list.add("0-111111--1111--1111--111111-0");
        list.add("0------1-----0--0-----1------0");
        list.add("000000-1-----0--0-----1-000000");
        list.add("000000-1--0000000000--1-000000");
        list.add("000000-1--0---00---0--1-000000");
        list.add("0------1--0-000000-0--1------0");
        list.add("00000001000-000000-00010000000");
        list.add("0------1--0-000000-0--1------0");
        list.add("000000-1--0--------0--1-000000");
        list.add("000000-1--0000000000--1-000000");
        list.add("000000-1--0--------0--1-000000");
        list.add("0------1--0--------0--1------0");
        list.add("0-111111111111--111111111111-0");
        list.add("0-1----1-----1--1-----1----1-0");
        list.add("0-2----1-----1--1-----1----2-0");
        list.add("0-111--1111111001111111--111-0");
        list.add("0---1--1--1--------1--1--1---0");
        list.add("0---1--1--1--------1--1--1---0");
        list.add("0-111111--1111--1111--111111-0");
        list.add("0-1----------1--1----------1-0");
        list.add("0-1----------1--1----------1-0");
        list.add("0-11111111111111111111111111-0");
        list.add("0----------------------------0");
        list.add("000000000000000000000000000000");

        for (int row = 0; row < list.size(); row++) {
            String line = list.get(row);
            for (int column = 0; column < line.length(); column++) {
                String text = line.substring(column, column + 1);
                int type = (text.equals("-")) ? -1 : Integer.parseInt(text);
                
                StringBuffer exits = new StringBuffer(4);
                if (row > 0 && row < list.size() - 1 && column > 0 && column < line.length() - 1) {
                    exits.append((list.get(row - 1).substring(column, column + 1).equals("-")) ? "1" : "0");
                    exits.append((line.substring(column + 1, column + 2).equals("-")) ? "1" : "0");
                    exits.append((list.get(row + 1).substring(column, column + 1).equals("-")) ? "1" : "0");
                    exits.append((line.substring(column - 1, column).equals("-")) ? "1" : "0");
                } else {
                    exits.append("0000");
                }
                map[row][column] = new Cell(row, column, exits.toString(), type);
            }
        }
    }
    
    /**
     * Devuelve el entorno de la celda
     * 
     * @param row           La linea en el mapa de la celda
     * @param column        La columna en el mapa de la celda
     * 
     * @return environment  El texto de las salidas de la celda
     */
    public String getEnvironmentOfCell(int row, int column)
    {
        Cell cell = map[row][column];
        
        return cell.getEnvironment();
    }
    
    /**
     * Devuelve el valor de la celda
     * 
     * @param row           La linea en el mapa de la celda
     * @param column        La columna en el mapa de la celda
     * 
     * @return              El valor de la celda
     */
    public int getCellValue(int row, int column)
    {
        Cell cell = map[row][column];
        
        return cell.getContent();
    }
    
    /**
     * Comprueba que la celda no sea una pared y si no lo es,
     * vacía la celda y devuelve el contenido
     * 
     * @param row           La linea en el mapa de la celda
     * @param column        La columna en el mapa de la celda
     * 
     * @return              El valor de la celda
     */
    public int setCellEmpty(int row, int column)
    {
        int value = -1;
        Cell cell = map[row][column];
        
        if (!cell.isWall()) {
            value = cell.cleanCell();
        }
        return value;
    }
}
