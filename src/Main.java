import com.rs2.yz85.Server;

/**
 * @author Yz85Racer
 * Created by IntelliJ IDEA.
 */
public class Main {
    public static void main(String[] args) {
        try {
            Server s = Server.getInstance(43594);
            s.setMaxPlayers(100);
            s.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
