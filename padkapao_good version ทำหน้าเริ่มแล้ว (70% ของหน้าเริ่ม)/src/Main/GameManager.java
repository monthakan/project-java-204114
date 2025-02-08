package Main;

public class GameManager {

	public ActionHandler aHandler = new ActionHandler(this);
    public UI ui = new UI(this);
    public SceneChanger sChanger = new SceneChanger(this);
    public Timers time = new Timers(this);
    
    public static void main(String[] args) {
        
        new GameManager();
    }
    public GameManager() {
    
    }
}