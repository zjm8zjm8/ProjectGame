package mainGameData;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameView extends JFrame implements ActionListener, MouseListener
{
	private GamePanel gamePanel = new GamePanel();
	private JButton buildButton = new JButton("Build");
	private JButton quitButton = new JButton("Quit");
	private JButton pauseButton = new JButton("Pause");
	private boolean running = false;
	private boolean paused = false;
	private Coordinator master = null;
	private boolean buildMode = false;
	  
	private int sun = 0;
	private int maxSun = 0;
	private int land = 0;
	private int landCost = 0;
	private int landSearchCost = 0;
	private int gatherCost = 10;
	private int ironMines = 0;
	private int astoriumMines = 0;
	private int logiteMines = 0;
	private int iron = 0;
	private int astorium = 0;
	private int logite = 0;
	private int solarPanels = 0;
	private int solarPanelIronCost = 0;
	private int solarPanelAstoriumCost = 0;
	private int solarPanelLogiteCost = 0;
	private int batteries = 0;
	private int batteryIronCost = 0;
	private int batteryAstoriumCost = 0;
	private int batteryLogiteCost = 0;
	private int controllers = 0;
	private int controllerIronCost = 3;
	private int controllerAstoriumCost = 3;
	private int controllerLogiteCost = 5;
	
	public GameView(Coordinator candidate)
	{
		super("Some Game");
		master = candidate;
		Container cp = getContentPane();
    	cp.setLayout(new BorderLayout());
    	JPanel p = new JPanel();
    	p.setLayout(new GridLayout(1,2));
    	p.add(buildButton);
    	p.add(pauseButton);
    	p.add(quitButton);
    	cp.add(gamePanel, BorderLayout.CENTER);
    	cp.add(p, BorderLayout.SOUTH);
    	setSize(500, 500);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	gamePanel.addMouseListener(this);
    	buildButton.addActionListener(this);
    	quitButton.addActionListener(this);
    	pauseButton.addActionListener(this);
	}
	
	public void updateData(int[] d)
	{
		sun = d[0];
		maxSun = d[1];
		land = d[2];
		landCost = d[3];
		ironMines = d[4];
		astoriumMines = d[5];
		logiteMines = d[6];
		iron = d[7];
		astorium = d[8];
		logite = d[9];
		solarPanels = d[10];
		solarPanelIronCost = d[11];
		solarPanelAstoriumCost = d[12];
		solarPanelLogiteCost = d[13];
		batteries = d[14];
		batteryIronCost = d[15];
		batteryAstoriumCost = d[16];
		batteryLogiteCost = d[17];
		controllers = d[18];
		controllerIronCost = d[19];
		controllerAstoriumCost = d[20];
		controllerLogiteCost = d[21];
	}
	
	public void actionPerformed(ActionEvent e)
	  {
	     Object s = e.getSource();
	     if (s == buildButton)
	     {
	        buildMode = !buildMode;
	        if (buildMode)
	        {
	           buildButton.setText("Resources");
	           //master.runGameLoop();
	        }
	        else
	        {
	           buildButton.setText("Build");
	        }
	     }
	     else if (s == pauseButton)
	     {
	       paused = !paused;
	       master.changePauseState();
	        if (paused)
	        {
	           pauseButton.setText("Unpause");
	        }
	        else
	        {
	           pauseButton.setText("Pause");
	        }
	     }
	     else if (s == quitButton)
	     {
	        System.exit(0);
	     }
	     
	  }
	  
	  void updateGame()
	  {
	     gamePanel.update();
	  }
	  
	  void drawGame(float interpolation)
	  {
	     gamePanel.setInterpolation(interpolation);
	     gamePanel.repaint();
	  }
	private class GamePanel extends JPanel
	  {
	     float interpolation;
	     float ballX, ballY, lastBallX, lastBallY;
	     int ballWidth, ballHeight;
	     float ballXVel, ballYVel;
	     float ballSpeed;
	     
	     int lastDrawX, lastDrawY;
	     
	     public GamePanel()
	     {
	        ballX = lastBallX = 100;
	        ballY = lastBallY = 100;
	        ballWidth = 25;
	        ballHeight = 25;
	        ballSpeed = 25;
	        ballXVel = (float) Math.random() * ballSpeed*2 - ballSpeed;
	        ballYVel = (float) Math.random() * ballSpeed*2 - ballSpeed;
	     }
	     
	     public void setInterpolation(float interp)
	     {
	        interpolation = interp;
	     }
	     
	     public void update()
	     {
	        lastBallX = ballX;
	        lastBallY = ballY;
	        
	        ballX += ballXVel;
	        ballY += ballYVel;
	        
	        if (ballX + ballWidth/2 >= getWidth())
	        {
	           ballXVel *= -1;
	           ballX = getWidth() - ballWidth/2;
	          // ballYVel = (float) Math.random() * ballSpeed*2 - ballSpeed;
	        }
	        else if (ballX - ballWidth/2 <= 0)
	        {
	           ballXVel *= -1;
	           ballX = ballWidth/2;
	        }
	        
	        if (ballY + ballHeight/2 >= getHeight())
	        {
	           ballYVel *= -1;
	           ballY = getHeight() - ballHeight/2;
	           //ballXVel = (float) Math.random() * ballSpeed*2 - ballSpeed;
	        }
	        else if (ballY - ballHeight/2 <= 0)
	        {
	           ballYVel *= -1;
	           ballY = ballHeight/2;
	        }
	     }
	     
	     public void paintComponent(Graphics g)
	     {
	        super.paintComponent(g);
	    	 //BS way of clearing out the old rectangle to save CPU.
	        //g.setColor(getBackground());
	        //g.fillRect(lastDrawX-1, lastDrawY-1, ballWidth+2, ballHeight+2);
	        //g.fillRect(5, 0, 75, 30);
	        
	        
	        g.setColor(Color.RED);
	        int drawX = (int) ((ballX - lastBallX) * interpolation + lastBallX - ballWidth/2);
	        int drawY = (int) ((ballY - lastBallY) * interpolation + lastBallY - ballHeight/2);
	        g.fillOval(drawX, drawY, ballWidth, ballHeight);
	        
	        lastDrawX = drawX;
	        lastDrawY = drawY;
	        
	        if (!buildMode)
	        {
		        if (sun >= landCost)
		        {
		        	g.setColor(Color.BLUE);
		        }
		        else
		        {
		        	g.setColor(Color.RED);
		        }
		        g.fillRect(150, 20, 50, 15);
		        if (sun >= gatherCost && ironMines > 0)
		        {
		        	g.setColor(Color.BLUE);
		        }
		        else
		        {
		        	g.setColor(Color.RED);
		        }
		        g.fillRect(150, 40, 50, 15);
		        
		        if (sun >= gatherCost && astoriumMines > 0)
		        {
		        	g.setColor(Color.BLUE);
		        }
		        else
		        {
		        	g.setColor(Color.RED);
		        }
		        g.fillRect(150, 60, 50, 15);
		        
		        if (sun >= gatherCost && logiteMines > 0)
		        {
		        	g.setColor(Color.BLUE);
		        }
		        else
		        {
		        	g.setColor(Color.RED);
		        }
		        g.fillRect(150, 80, 50, 15);
		        
		        g.setColor(Color.BLACK);
		        g.drawString("Gather", 150, 10);
		        g.drawString("Sun: " + sun + "/" + maxSun, 5, 10);
		        g.drawString("Land: " + land, 5, 30);
		        g.drawString("Iron Mines: " + ironMines, 5, 50);
		        g.drawString("Astorium Mines: " + astoriumMines, 5, 70);
		        g.drawString("Logite Mines: " + logiteMines, 5, 90);
		        g.drawString("Iron: " + iron, 5, 110);
		        g.drawString("Astorium: " + astorium, 5, 130);
		        g.drawString("Logite: " + logite, 5, 150);
		        g.drawString("Solar Panels: " + solarPanels, 5, 170);
		        g.drawString("Batteries: " + batteries, 5, 190);
		        g.drawString("Controllers: " + controllers, 5, 210);
		        //frameCount++;
	        }
	        else
	        {
	        	if (iron >= solarPanelIronCost && astorium >= solarPanelAstoriumCost && logite >= solarPanelLogiteCost && land >= 2)
		        {
		        	g.setColor(Color.BLUE);
		        }
		        else
		        {
		        	g.setColor(Color.RED);
		        }
		        g.fillRect(150, 60, 50, 15);
		        
		        if (iron >= batteryIronCost && astorium >= batteryAstoriumCost && logite >= batteryLogiteCost && land >= 5)
		        {
		        	g.setColor(Color.BLUE);
		        }
		        else
		        {
		        	g.setColor(Color.RED);
		        }
		        g.fillRect(150, 80, 50, 15);
		        
		        if (iron >= controllerIronCost && astorium >= controllerAstoriumCost && logite >= controllerLogiteCost && land >= 3)
		        {
		        	g.setColor(Color.BLUE);
		        }
		        else
		        {
		        	g.setColor(Color.RED);
		        }
		        g.fillRect(150, 100, 50, 15);
		        
		        g.setColor(Color.BLACK);
		        String solarPanelCost = solarPanelIronCost + "/" + solarPanelAstoriumCost + "/" + solarPanelLogiteCost;
		        String batteryCost = batteryIronCost + "/" + batteryAstoriumCost + "/" + batteryLogiteCost;
		        String controllerCost = controllerIronCost + "/" + controllerAstoriumCost + "/" + controllerLogiteCost;
	        	g.setColor(Color.BLACK);
		        g.drawString("Iron: " + iron, 5, 10);
		        g.drawString("Astorium: " + astorium, 5, 30);
		        g.drawString("Logite: " + logite, 5, 50);
		        g.drawString("Solar Panel: " + solarPanelCost, 5, 70);
		        g.drawString("Battery: " + batteryCost, 5, 90);
		        g.drawString("Controller: " + controllerCost, 5, 110);
	        }
	     }
	  }
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		int mouseX = arg0.getX();
		int mouseY = arg0.getY();
		if (!buildMode)
		{
			//System.out.println("Click @ " + mouseX + " " + mouseY);
			if (mouseX >= 150 && mouseX <= 200 && mouseY >= 20 && mouseY <= 35)
			{
				master.buyLand();
			}
			if (mouseX >= 150 && mouseX <= 200 && mouseY >= 40 && mouseY <= 55)
			{
				master.gatherIron();
			}
			if (mouseX >= 150 && mouseX <= 200 && mouseY >= 60 && mouseY <= 75)
			{
				master.gatherAstorium();
			}
			if (mouseX >= 150 && mouseX <= 200 && mouseY >= 80 && mouseY <= 95)
			{
				master.gatherLogite();
			}
		}
		else
		{
			if (mouseX >= 150 && mouseX <= 200 && mouseY >= 60 && mouseY <= 75)
			{
				master.buySolarPanel();
			}
			if (mouseX >= 150 && mouseX <= 200 && mouseY >= 80 && mouseY <= 95)
			{
				master.buyBattery();
			}
			if (mouseX >= 150 && mouseX <= 200 && mouseY >= 100 && mouseY <= 115)
			{
				master.buyController();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
