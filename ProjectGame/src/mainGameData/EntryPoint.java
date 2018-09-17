package mainGameData;

public class EntryPoint {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Coordinator cc = Coordinator.getInstance();
		System.out.println(cc);
		GameLoop gl = new GameLoop(cc);
		GameView gv = new GameView(cc);
		GameModel gm = new GameModel(cc);
		cc.registerGameModel(gm);
		cc.registerGameView(gv);
		cc.registerGameLoop(gl);
		gl.runGameLoop();
		boolean running = true;
		gv.setVisible(true);
		while(running)
		{
			
		}
	}

}
