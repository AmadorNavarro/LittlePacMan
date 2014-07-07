import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * Encargado de reproducir el sonido pedido por el juego
 * 
 * @author Amador Navarro Lucas
 * @version 1.0         05/05/2014
 */
public class GamePlayer
{
    // Reproductor
    private Player player;
    
    // Tipo de la pista
    private TrackType type;

    /**
     * Inicializa el reproductor a nulo
     */
    public GamePlayer()
    {
        player = null;
    }

    /**
     * Reproduce una pista
     * 
     * @param track             La pista a reproducir
     */
    public void playTrack(TrackType track)
    {
        try {
            player = new Player(new BufferedInputStream(new FileInputStream(track.getTrackURL())));
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        // Lo reproducimos en un nuevo hilo para que no corte el movimiento
        new Thread() {
            public void run()
            {
                try {    
                    player.play();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }.start();
    }
    
    /**
     * Devuelve si se está reproduciendo alguna pista o no
     * 
     * @return                  true si se está reproduciendo alguna pista, false si no.
     */
    public boolean isPlaying()
    {
        boolean playing = false;
        if (player != null && !player.isComplete()) {
            playing = true;
        }
        return playing;
    }
    
    /**
     * Para toda reproducción que esté sonando
     */
    public void stop()
    {
        if (player != null) {
            player.close();
            player = null;
        }
    }
}
