package mainGameData;

public class Coordinator {

	private GameView gv;
	private GameModel gm;
	private GameLoop gl;
	private static Coordinator instance;
	
	private Coordinator()
	{
		
	}
	
	public static Coordinator getInstance()
	{
		if (instance == null)
		{
			instance = new Coordinator();
			return instance;
		}
		else
		{
			return instance;
		}
	}
	public void doNothing()
	{
		return;
	}
	public void registerGameView(GameView g)
	{
		instance.gv = g;
	}
	public void registerGameLoop(GameLoop g)
	{
		instance.gl = g;
	}
	public void registerGameModel(GameModel g)
	{
		instance.gm = g;
	}
	public boolean changePauseState()
	{
		return gl.pause();
	}
	public void updateGame()
	{
		gm.tick();
		gv.updateGame();
		gv.updateData(gm.getData());
		gv.drawGame(1);
	}
	public void buyLand()
	{
		gm.buyLand();
	}
	public void gatherLogite()
	{
		gm.gatherLogite();
	}
	public void gatherIron()
	{
		gm.gatherIron();
	}
	public void gatherAstorium()
	{
		gm.gatherAstorium();
	}
	public void buySolarPanel()
	{
		gm.buySolarPanel();
	}
	public void buyBattery()
	{
		gm.buyBattery();
	}
	public void buyController()
	{
		gm.buyController();
	}
}
