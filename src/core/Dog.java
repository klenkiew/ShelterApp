package core;

/**
 * Created by Kamil on 27.12.2016.
 */
public class Dog
{
    private int id;
    private int name;
    private int age;
    private boolean isAggresive;
    private boolean isOpen;
    private boolean isVulnerable;
    private String hairColor;
    private String description;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getName()
    {
        return name;
    }

    public void setName(int name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public boolean isAggresive()
    {
        return isAggresive;
    }

    public void setAggresive(boolean aggresive)
    {
        isAggresive = aggresive;
    }

    public boolean isOpen()
    {
        return isOpen;
    }

    public void setOpen(boolean open)
    {
        isOpen = open;
    }

    public boolean isVulnerable()
    {
        return isVulnerable;
    }

    public void setVulnerable(boolean vulnerable)
    {
        isVulnerable = vulnerable;
    }

    public String getHairColor()
    {
        return hairColor;
    }

    public void setHairColor(String hairColor)
    {
        this.hairColor = hairColor;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
