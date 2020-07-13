package top.nololiyt.worldpermissions.entitiesandtools;

public class WorldInfo
{
    private String display;
    private double thrust;
    private boolean controlled;
    
    public String getDisplay()
    {
        return display == null ? "Nameless World" : display;
    }
    
    public double getThrust()
    {
        return thrust;
    }
    
    public boolean isControlled()
    {
        return controlled;
    }
    
    public WorldInfo(String display, double thrust, boolean controlled)
    {
        this.display = display;
        this.thrust = thrust;
        this.controlled = controlled;
    }
}