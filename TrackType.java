
/**
 * Enumeration class TrackType - Guarda la ruta de cada archivo de música
 * 
 * @author Amador Navarro Lucas
 * @version 1.0     05/05/2014
 */
public enum TrackType
{
    DIE("audio/die.mp3"),
    EATGHOST("audio/eatghost.mp3"),
    EATING("audio/eating.mp3"),
    EATINGSHORT("audio/eatingshort.mp3"),
    EATPILL("audio/eatpill.mp3"),
    WIN("audio/pacmanwin.mp3"),
    START("audio/start.mp3"),
    TIMEOUT("audio/siren.mp3");
    
    private String trackURL;
    
    TrackType(String track)
    {
        trackURL = track;
    }
    
    public String getTrackURL() 
    {
        return trackURL;
    }
}
