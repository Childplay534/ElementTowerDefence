import greenfoot.*; 
public class Artillery extends Weapon
{
    String name ;

    public Artillery(int speed, int power,int aoe, int eleId, Enemy target, String element)
    {
        super( speed,  power, aoe, eleId, target, element ,"Shell"); //calls the super classes constructtor, nothign changed
    }

  /**
     * Act - do whatever the Tower wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (active) // move the weapon when active, called from the superclass
        {
            move();
        }
        else
        {
            m.removeObject(this);
        }
    }
}