package solarsystem2;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

/**
 * Program that displays a solar system model
 * @version 2018-03-21
 * @author Emil.Karpowicz
 */

public class Main 
{
    public static void main(String[] args) 
    {
        EventQueue.invokeLater(()->
        {
            JFrame mFrame = new MainFrame();
            mFrame.setTitle("Solar System Model");
            mFrame.setBounds(0,0,(int)MainFrame.SWIDTH,(int)MainFrame.SHEIGHT);
            mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mFrame.setVisible(true);
            
        });
    }
}

class MainFrame extends JFrame
{
    public static final double SWIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final double SHEIGHT = 0.95 * Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    
    public static double galaxyCenter_XCoordinate = SWIDTH / 2; // The reference point for moving the view of galaxy on the screen
    public static double galaxyCenter_YCoordinate = SHEIGHT / 2; // The reference point for moving the view of galaxy on the screen
    
    private double scSize = 1000;
    private double scDist = 5000000;
    private double eD = 12756 / scSize ; // The diameter of the earth (12 756 km)
    private double aU = 149597871 / scDist; // The astronomical unit which equals dinstans between earth and sun (149 597 871 km)
    
    private Planet slonce = new Planet("Słońce", new Color(255,69,0), 0, 0, eD,0,0,0);
    private Planet merkury = new Planet("Merkury", new Color(238,232,170), 0.3871*aU,0, 0.3825*eD, 1.01/0.2408,0,0);
    private Planet wenus = new Planet("Wenus", new Color(255,140,0),0.7233*aU,0, 0.9489*eD, 1.01/0.6152,0,0);  
    private Planet ziemia = new Planet("Ziemia", new Color(0,191,255), 1*aU,0, eD,1.01,0,0);
    private Planet mars = new Planet("Mars", new Color(220,20,60), 1.5237*aU,0,0.5335*eD, 1.01/1.8808,0,0); 
    private Planet jowisz = new Planet("Jowisz", new Color(255, 239, 213), 5.2034*aU,0, 11.2092*eD, 1.01/11.8637,0,0); 
    private Planet saturn = new Planet("Saturn", new Color(240, 230,140), 9.5371*aU,0, 9.4494*eD, 1.01/29.4484,0,0); 
    private Planet uran = new Planet("Uran", new Color(65,105,225), 19.1913*aU,0, 4.0074*eD, 1.01/84.0711,0,0);
    private Planet neptun = new Planet("Neptun", new Color(0,0,205), 30.0690*aU,0, 3.8827*eD, 1.01/164.8799,0,0); 
    
    
    private static SolarSystem solarSystem = new SolarSystem();
    private static ControlPanel controlPanel = new ControlPanel();
    
    private int referalXCo = 0;
    private int referalYCo = 0;
    
    
    public MainFrame()
    {
        
        solarSystem.addPlanet(slonce);
        solarSystem.addPlanet(merkury);
        solarSystem.addPlanet(wenus);
        solarSystem.addPlanet(ziemia);
        solarSystem.addPlanet(mars);
        solarSystem.addPlanet(jowisz);
        solarSystem.addPlanet(saturn);
        solarSystem.addPlanet(uran);
        solarSystem.addPlanet(neptun);
        
        solarSystem.setBackground(Color.BLACK);

        this.setLayout(new BorderLayout());
        this.add(solarSystem, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.SOUTH);
        
        this.addKeyListener(new KeyAdapter() 
        {
             public void keyPressed(KeyEvent e) 
             {
                 if (e.getKeyCode() == KeyEvent.VK_UP) setGC_YCoordinate(-10);
                 else if (e.getKeyCode() == KeyEvent.VK_DOWN) setGC_YCoordinate(10);
                 else if (e.getKeyCode() == KeyEvent.VK_LEFT) setGC_XCoordinate(-10);
                 else if (e.getKeyCode() == KeyEvent.VK_RIGHT) setGC_XCoordinate(10);
             }
        });
        
        this.addMouseMotionListener(new MouseAdapter() 
        {
            public void mouseDragged(MouseEvent e)
            {
                
                
                if (e.getXOnScreen()<= referalXCo) setGC_XCoordinate(-5);
                if (e.getXOnScreen()> referalXCo) setGC_XCoordinate(5);
                if (e.getYOnScreen()<= referalYCo) setGC_YCoordinate(-5);
                if (e.getYOnScreen()> referalYCo) setGC_YCoordinate(5);
            }
            
        });
        
        this.addMouseListener(new MouseAdapter() 
        {
            public void mousePressed(MouseEvent e) 
            {
                MainFrame.solarSystem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                referalXCo = e.getX();
                referalYCo = e.getY();
                System.out.println(referalXCo + " " + referalYCo);
            }
            
            public void mouseReleased(MouseEvent e) 
            {
                MainFrame.solarSystem.setCursor(Cursor.getDefaultCursor());
            }
            
        });
        
    }
    
    
        
    /**
    * Returns x coordinate of galaxy center
    * @return galaxyCenter_XCoordinate - x coordinate 
    */

    public static double getGC_XCoordinate()
    {
       return galaxyCenter_XCoordinate;
    }

    /**
    * Changes x coordinate of galaxy center by supplied value
    * @param xChange - value by which x coordinate of galaxy center will be changed
    */

    public static void setGC_XCoordinate(int xChange)
    {
       galaxyCenter_XCoordinate+=xChange;
    }

    /*
    * @return galaxyCenter_YCoordinate - y coordinate 
    */

    public static double getGC_YCoordinate()
    {
       return galaxyCenter_YCoordinate;
    }

    /**
    * Changes y coordinate of galaxy center by supplied value
    * @param yChange - value by which y coordinate of galaxy center will be changed
    */

    public static void setGC_YCoordinate(int yChange)
    {
       galaxyCenter_YCoordinate+=yChange;
    }
}

class ControlPanel extends JPanel
{
    public ControlPanel()
    {
        this.setPreferredSize(new Dimension((int)MainFrame.SWIDTH, 150));
        this.setBorder(BorderFactory.createTitledBorder("Control panel"));
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        JPanel scaleBox = new JPanel();
        JPanel planetsBox = new JPanel();
        scaleBox.setBorder(BorderFactory.createTitledBorder("General settings"));
        planetsBox.setBorder(BorderFactory.createTitledBorder("Planet specific"));
        this.add(scaleBox);
        this.add(planetsBox);
    }
}

class Planet
{
    private String name;    // Planet's name
    private Color color;    // Planet's color
    private double x;   // Planet's center distance from planet's orbit center measured on x axis
    private double y;   // Planet's center distance from planet's orbit center measured on y axis
    private double pDiameter;   // Planet's diameter
    private double oRadius; // Planet's orbit radius
    private double pSpeed;  // Planet's speed
    private double distFromGC_XCo; // Planet's orbit center distance from galaxy center measured on x axis
    private double distFromGC_YCo; // Planet's orbit center distance from galaxy center measured on y axis
    
    public Planet(String name, Color color, double distFromOC_XCo, double distFromOC_YCo, double pDiameter, double pSpeed, double distFromGC_XCo, double distFromGC_YCo)
    {
        this.name = name;
        this.color = color;
        this.x = MainFrame.getGC_XCoordinate()+distFromGC_XCo + distFromOC_XCo;
        this.y = MainFrame.getGC_YCoordinate()+distFromGC_YCo + distFromOC_YCo;
        this.pDiameter = pDiameter;
        this.pSpeed = pSpeed;
        this.distFromGC_XCo = distFromGC_XCo;
        this.distFromGC_YCo = distFromGC_YCo;
        
        this.oRadius = Math.sqrt(Math.pow(distFromOC_XCo, 2)+ Math.pow(distFromOC_YCo, 2));
    }
    
    /**
     * Returns planet's name
     * @return name - planet's name
     */
    public String getPlanetName()
    {
        return name;
    }
    
    /**
     * Set planet's name to supplied value
     * @param name - planet's name
     */
    public void setPlanetName(String name)
    {
        this.name = name;
    }
    
    /**
     * Returns planet's color 
     * @return color - planet's color
     */
    public Color getPlanetColor()
    {
        return color;
    }
    
    /**
     * Sets planet's color to supplied value
     * @param color - planet's color
     */
    public void getPlanetColor(Color color)
    {
        this.color = color;
    }
    
    
    /**
     * Returns planet's center x coordinate
     * @return x - planet's center x coordinate
     */
    public float getPlanetXCo()
    {
        return (float)x;
    }
    
    /**
     * Sets planet's center x coordinate
     * @param xChange - planet's center new x coordinate  
     */
    public void setPlanetXCo(double xChange)
    {
        x+=xChange;
    }
    
    /**
     * Returns planet's center y coordinate
     * @return y - planet's center y coordinate
     */
    public float getPlanetYCo()
    {
        return (float)y;
    }
  
    /**
    * Sets planet's center y coordinate
    * @param yChange - planet's center new y coordinate  
    */
    public void setPlanetYCo(double yChange)
    {
        y+=yChange;
    }
    
    /**
     * Returns planet's speed which equals number of times planet encircle its orbit center 
     * during the time that earth needs to encircle sun   
     * @return pSpeed 
     */
    public double getPlanetSpeed()
    {
        return pSpeed;
    }
    
     /**
     * Sets planet's speed which equals number of times planet encircle its orbit center 
     * during the time that earth needs to encircle sun   
     * @param pSpeed 
     */
    public void setPlanetSpeed(double pSpeed)
    {
        this.pSpeed = pSpeed;
    }
    
    /**
     * Returns planet's diameter
     * @return pDiameter - planet's diameter
     */
    public double getPlanetDiameter()
    {
        return pDiameter;
    }
    
    /**
     * Sets planet's diameter
     * @param pDiameter - planet's diameter
     */
    public void setPlanetDiameter(double pDiameter)
    {
        this.pDiameter = pDiameter;
    }
    
    /**
     * Returns planet's orbit radius
     * @return oRadius - planet orbit radius
     */
    public int getOrbitRadius()
    {
        return (int)oRadius;
    }

    /**
     * Sets planet's orbit radius to supplied value
     * @param oRadius - planet orbit radius
     */
    public void setOrbitRadius(double oRadius)
    {
        this.oRadius = oRadius;
    }
    
    /**
     * Returns planet's radius
     * @return pDiameter / 2 - planet radius
     */
    public float getPlanetRadius()
    {
        return (float)pDiameter/2;
    }
    
    /**
     * Sets planet's radius to supplied value
     * @param pRadius - planet radius
     */
    public void setPlanetRadius(double pRadius)
    {
        pDiameter = 2*pRadius;
    }
    
    /**
     * Changes planet position by supplied number of radians
     * @param rad - number by which plant should by moved on the orbit
     */
 
    public void move(double rad)
    {
        x =  (int)((MainFrame.getGC_XCoordinate()+distFromGC_XCo) + oRadius * Math.cos(rad));
        y =  (int)((MainFrame.getGC_YCoordinate()+distFromGC_YCo) + oRadius * Math.sin(rad));
    }
    
    /**
     * Returns ellipse shape in defined by planets variables position and size 
     * @return Ellipse2D - ellipse shape
     */
    public Ellipse2D getPlanet()
    {
        return new Ellipse2D.Double(x-getPlanetRadius(), y-getPlanetRadius(), pDiameter, pDiameter);
    }
    
    /**
    * Returns planet's orbit center distance from galaxy center measured on x axis
    * @return distFromGC_XCo - distance on x axis 
    */

    public double getDistFromGC_XCo()
    {
       return distFromGC_XCo;
    }

    /**
    * Changes planet's orbit center distance from galaxy center measured on x axis
    * @param xChange - value by which distance will change on x axis
    */
    public void setDistFromGC_XCo(int xChange)
    {
       distFromGC_XCo+=xChange;
    }

    /**
    * Returns planet's orbit center distance from galaxy center measured on y axis
    * @return distFromGC_YCo - distance on y axis 
    */
    public double getDistFromGC_YCo()
    {
       return distFromGC_YCo;
    }

    /**
    * Changes planet's orbit center distance from galaxy center measured on y axis
    * @param yChange - value by which distance will change on y axis
    */
    public void getDistFromGC_YCo(int yChange)
    {
       distFromGC_YCo+=yChange;
    }
    
}

class SolarSystem extends JPanel
{
    private ArrayList<Planet> solarPlanets = new ArrayList<>();
    
    public void addPlanet(Planet newPlanet)
    {
        solarPlanets.add(newPlanet);
        Runnable r = () ->
        {
            try
            {
                double rad = 0;
                while (!Thread.currentThread().isInterrupted())
                {
                    rad += newPlanet.getPlanetSpeed()*2*Math.PI/360;
                    if (rad >= 2*Math.PI) rad = 0;
                    newPlanet.move(rad);
                    this.repaint();
                    Thread.sleep(5);
                }
            }
            catch (InterruptedException ex)
            {
                ex.getMessage();
            }
        };
        Thread t = new Thread(r);
        t.start();
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        for (Planet planet: solarPlanets)
        {
            g2.setColor(planet.getPlanetColor());
            g2.fill(planet.getPlanet());
            g2.drawString(planet.getPlanetName(), planet.getPlanetXCo()+planet.getPlanetRadius()+8, planet.getPlanetYCo()-planet.getPlanetRadius()-20);
            g2.drawString("x: "+(int)planet.getPlanetXCo()+" y: "+(int)planet.getPlanetYCo(), planet.getPlanetXCo()+planet.getPlanetRadius()+8, planet.getPlanetYCo()-planet.getPlanetRadius()-5);
            //g2.drawOval((int)Main.xSUN-planet.getOrbitRadius(), (int)Main.ySUN-planet.getOrbitRadius(),2 * planet.getOrbitRadius(),2*planet.getOrbitRadius());
        }
    }
}