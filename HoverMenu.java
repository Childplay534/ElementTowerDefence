import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Font;
import java.awt.Color;
/**
 * A menu that pops up displaying information about the <br>
 * item you are hovering over. <br>
 * created when mouse is over object for 1 second.
 * 
 * @author James Lu && Terence Lai
 */
public class HoverMenu extends Actor
{
    private GreenfootImage bg;
    private GreenfootImage menuImg;
    private int width;
    private int height;

    private Font titleFont;
    private Font descFont;

    private Color bgColor;
    private Color titleColor;
    private Color descColor;

    private String[] desc;
    private Map map;
    public HoverMenu(){
        width = 300;
        height = 300;
        bg = new GreenfootImage (width, height);
        menuImg = new GreenfootImage ("UI/menu.png");

        titleFont = new Font ("Vrinda", 1, 40);
        descFont  = new Font ("Vrinda", 1, 20);

        bgColor    = new Color (255, 0, 0);
        titleColor = new Color (255, 255, 255);
        descColor  = new Color (255, 255, 255);

        desc = new String[8];
        for (int i = 0; i< desc.length; i++){
            desc[i] = "";
        }

        refresh();
    }

    private void refresh(){
        bg.clear();

        bg.drawImage (menuImg, 0, 0);

        bg.setFont (titleFont);
        bg.setColor (titleColor);
        bg.drawString (desc[0], 20, 50);
        bg.setFont (descFont);
        bg.drawString (desc[1], 20, 110);
        bg.drawString (desc[2], 20, 140);
        bg.drawString (desc[3], 20, 170);
        bg.drawString (desc[4], 20, 200);
        bg.drawString (desc[5], 20, 230);
        bg.drawString (desc[6], 20, 260);
        bg.drawString (desc[7], 20, 290);

        this.setImage (bg);
    }

    /**
     * Gives the class access to map methods when added to world
     */
    protected void addedToWorld(World world)
    {
        if (map == null){
            map = (Map) world;
        }
    }

    /**
     * Passes an Actor, the actor that the hovermenu is currently displaying <br>
     * It sets the data based on what the actor passed through is
     */
    public void setData(Actor a){
        for (int i = 0; i < desc.length; i++){
            desc[i] = "";
        }

        if (a instanceof SendCreeps){       //if the actor passed is the button to send mobs
            SendCreeps b = (SendCreeps) a;
            Creep c = b.getCreep();         //make a instance of the creep that the button will send

            desc[0] = c.getName();
            desc[1] = "HP: " + Integer.toString (c.getHp());
            desc[2] = "Bounty: " + Integer.toString (c.getMaxHp() / 20);
            desc[3] = "Income: " + Integer.toString (b.getIncome());
            desc[4] = "Armor: " + Integer.toString (c.getArmor());
            desc[5] = "Speed: " + Float.toString (c.getSpeed());
        }
        else if (a instanceof DebuffButton){
            DebuffButton d = (DebuffButton) a;

            Debuff h = new Debuff (d.getId(), d.getLevel()); 
            desc[0] = h.getName(); 
            desc[1] = "Costs: "            + Integer.toString(d.getCost());
            desc[2] = "Damage Over time: " + Float.toString(h.getDOT()) ;
            desc[3] = "Slow: "            + Float.toString(h.getSlow()) + "%";
            desc[4] = "Armor reduction " + Float.toString(h.getRedu());
        }
        else if (a instanceof BuildTowers){
            BuildTowers b = (BuildTowers) a; 
            Tower t = b.getTower(); 

            desc[0] =  t.getName(); 
            desc[1] = "Damage: "      + Integer.toString(t.getDmg()); 
            desc[2] = "Attack Rate: " + Integer.toString(t.getAttackSpeed()); 
            desc[3] = "Range: "       + Integer.toString(t.getRange()); 
            desc[4] = "Cost: "        + Integer.toString(t.getCost()) ;
        }
        else if (a instanceof SellButton){
            desc[0] = "Sells tower";
            Tower t = map.getSelectedTower(); 
            if (t != null){
                desc[1] = "Value: " + Math.round (t.getCurrentCost()*0.75f);
            }
        }
        else if (a instanceof UpgradeButton) {
            Tower t = map.getSelectedTower(); 
            desc[0] = "Upgrade Tower";
            if (t != null)
            {   desc[1] = "CurrentLevel: " +Integer.toString(t.getCurrentLevel())  ;
                desc[2] = "Cost: " + Integer.toString(t.getUpgradeCost()) ; 
            }
            desc[3] = "";
            desc[4] = "Weapon: " + t.getNextType();
            desc[5] = "Power + 20%";
            desc[6] = "Range + 20%"; 
        }
        else if (a instanceof Element){
            Element e = (Element) a;
            int tempId = e.getId();
            if (tempId != 0){
                tempId = tempId -1;
                desc[0] = Data.elementName[tempId];
                desc[1] = "Damage Multiplication Vs:";
                desc[2] = "Air:   " + Data.elementDamage[tempId][0] + "x";
                desc[3] = "Water: " + Data.elementDamage[tempId][1] + "x";
                desc[4] = "Fire:  " + Data.elementDamage[tempId][2] + "x";
                desc[5] = "Earth: " + Data.elementDamage[tempId][3] + "x";
            }
        }
        else if (a instanceof WaveProgress){
            desc[0] = "Wave Progress";
            desc[1] = "";
            desc[2] = "Enemies Left: " + map.getAliveMobNumber();
            desc[3] = "Creeps Sent: " + map.getNumCreeps();
            desc[4] = "Total Enemies:" + map.getNumEnemy();
        }
        else if (a instanceof Reload){
            Reload r = (Reload) a;
            desc[0] = r.getStringId();
            if (r.getId() == 0){              //bullet
                desc[1] = "A regular Bullet";
            }else if (r.getId() == 1){        //laser
                desc[1] = "A pentrating Laser";
            }else{                          //shell
                desc[1] = "An Area of Effect Artillery blast";
            }
            desc[2] = "";
            Tower t = map.getSelectedTower(); 
            if (t != null){
                int tempId = t.getElement();
                desc[3] = "Damage Vs:";
                desc[4] = "Air:   " + Data.elementDamage[0][tempId-1] + "x";
                desc[5] = "Water: " + Data.elementDamage[1][tempId-1] + "x";
                desc[6] = "Fire:  " + Data.elementDamage[2][tempId-1] + "x";
                desc[7] = "Earth: " + Data.elementDamage[3][tempId-1] + "x";
            }
        }
        refresh();
    }

    /**
     * Return the width of the hovermenu
     */
    public int getWidth(){
        return width;
    }

    /**
     * Returns the height of the hover menu
     */
    public int getHeight(){
        return height;
    }
}