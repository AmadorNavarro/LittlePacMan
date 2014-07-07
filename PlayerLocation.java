
/**
 * Guarda la localización en el canvas de un Player y el nombre de su archivo de imagen
 * 
 * @author Amador Navarro Lucas
 * @version 1.0         14/04/2014
 */
public class PlayerLocation
{
    // ejes x e y del canvas donde se encuentra nuestro Player
    private int x;
    private int y;
    
    // nombre de la imagen del Player
    private String imageName;

    /**
     * Constructor de los objetos de PlayerLocation
     */
    public PlayerLocation(int x, int y, String image)
    {
        this.x = x;
        this.y = y;
        imageName = image;
    }

    /**
     * Devuelve x
     * 
     * @return     el valor de x
     */
    public int getX()
    {
        return x;
    }
    
    /**
     * Devuelve y
     * 
     * @return     el valor de y
     */
    public int getY()
    {
        return y;
    }
    
    /**
     * Devuelve imageName
     * 
     * @return     el nombre de la imagen
     */
    public String getImageName()
    {
        return imageName;
    }
}
