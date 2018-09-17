package mainGameData;

import java.util.Random;

public class GameModel {
	private Coordinator master = null;
	private int tickcount = 0;
	private int sun = 0;
	private int maxSun = 100;
	private int totalLandSearched = 0;
	private int landSearchCost = 1;
	private int searchesTillCostIncrease = 8;
	private int searchCostIncreaseFactor = 1;
	private int land = 0;
	private int logiteMines = 0;
	private int ironMines = 0;
	private int astoriumMines = 0;
	private int gatherCost = 10;
	private int iron = 0;
	private int astorium = 0;
	private int logite = 0;
	private int solarPanels = 0;
	private int solarPanelIronCost = 2;
	private int solarPanelAstoriumCost = 3;
	private int solarPanelLogiteCost = 1;
	private int extraSunTicks = 0;
	private int batteries = 0;
	private int batteryIronCost = 5;
	private int batteryAstoriumCost = 1;
	private int batteryLogiteCost = 1;
	private int controllers = 0;
	private int controllerIronCost = 3;
	private int controllerAstoriumCost = 3;
	private int controllerLogiteCost = 5;
	private Random r;
	//private int dust = 0;
	//private int cats = 0;
	
	public GameModel(Coordinator candidate)
	{
		master = candidate;
		r = new Random(System.currentTimeMillis());
	}
	
	public void tick()
	{
		tickcount += 1;
		if (tickcount % 3 == 0)
		{
			this.tenthSecond();
		}
		if (tickcount % 15 == 0)
		{
			this.halfSecond();
		}
		if (tickcount >= 30)
		{
			tickcount = 0;
			this.second();
		}
	}
	
	private void tenthSecond()
	{
		//dust++;
	}
	
	private void halfSecond()
	{
		//cats++;
	}
	
	private void second()
	{
		addSun();
	}
	private void addSun()
	{
		if (sun < maxSun)
		{
			sun += solarPanels+1;
			if (sun > maxSun)
			{
				sun = maxSun;
			}
			/*  Not sure if I want this in here.
			sun++;
			extraSunTicks++;
			int oddPanels = solarPanels % 10;
			if (oddPanels + extraSunTicks >= 10 && oddPanels != 0)
			{
				sun++;
				extraSunTicks = 0;
			}
			if (oddPanels == 0)
			{
				extraSunTicks = 0;
			}
			sun += solarPanels / 10;
			*/
		}
	}
	public int getSun()
	{
		return sun;
	}
	public void buyLand()
	{
		if (sun >= landSearchCost)
		{
			sun -= landSearchCost;
			double spin = r.nextDouble();
			if (spin < .05)
			{
				ironMines++;
			}
			else if (spin < .1)
			{
				astoriumMines++;
			}
			else if (spin < .15)
			{
				logiteMines++;
			}
			else
			{
				land++;
			}
			totalLandSearched++;
			if (totalLandSearched == searchesTillCostIncrease)
			{
				searchCostIncreaseFactor++;
				searchesTillCostIncrease += searchCostIncreaseFactor * 8;
				landSearchCost++;
			}
		}
	}
	public int getLand()
	{
		return land;
	}
	public int[] getData()
	{
		int[] data = {sun, maxSun, land, landSearchCost, ironMines, astoriumMines, logiteMines, iron, 
				astorium, logite, solarPanels, solarPanelIronCost, solarPanelAstoriumCost, solarPanelLogiteCost,
				batteries, batteryIronCost, batteryAstoriumCost, batteryLogiteCost, controllers, controllerIronCost,
				controllerAstoriumCost, controllerLogiteCost};
		return data;
	}
	public void gatherLogite()
	{
		if (sun >= gatherCost && logiteMines > 0)
		{
			logite++;
			sun -= gatherCost;
		}
	}
	public void gatherIron()
	{
		if (sun >= gatherCost && ironMines > 0)
		{
			iron++;
			sun -= gatherCost;
		}
	}
	public void gatherAstorium()
	{
		if (sun >= gatherCost && astoriumMines > 0)
		{
			astorium++;
			sun -= gatherCost;
		}
	}
	public void buySolarPanel()
	{
		if (iron >= solarPanelIronCost && astorium >= solarPanelAstoriumCost && logite >= solarPanelLogiteCost && land >= 2)
		{
			solarPanels++;
			iron -= solarPanelIronCost;
			astorium -= solarPanelAstoriumCost;
			logite -= solarPanelLogiteCost;
			solarPanelIronCost++;
			solarPanelAstoriumCost += 5;
			land -= 2;
		}
	}
	public void buyBattery()
	{
		if (iron >= batteryIronCost && astorium >= batteryAstoriumCost && logite >= batteryLogiteCost && land >= 5)
		{
			batteries++;
			iron -= batteryIronCost;
			astorium -= batteryAstoriumCost;
			logite -= batteryLogiteCost;
			batteryIronCost = (batteryIronCost * 2) - 2;
			batteryAstoriumCost += 3;
			batteryLogiteCost++;
			land -= 5;
			maxSun = (int) (100 * Math.pow(2, batteries));
		}
	}
	public void buyController()
	{
		if (iron >= controllerIronCost && astorium >= controllerAstoriumCost && logite >= controllerLogiteCost && land >= 3)
		{
			controllers++;
			iron -= controllerIronCost;
			astorium -= controllerAstoriumCost;
			logite -= controllerLogiteCost;
			land -= 3;
			controllerIronCost += controllerIronCost/2;
			controllerAstoriumCost += 3;
			controllerLogiteCost = controllerLogiteCost*2 - controllerLogiteCost/10;
		}
	}
}
