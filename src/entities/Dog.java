package entities;

/**
 * Created by Kamil on 27.12.2016.
 */

public class Dog
{
    private int id;
    private String name;
    private int age;
    private boolean isAggressive;
    private boolean isOpen;
    private boolean isVulnerable;
    private String hairColor;
    private String description;
    private int breedId;
    private int coopId;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getBreedId()
    {
        return breedId;
    }

    public void setBreedId(int id)
    {
        this.breedId = id;
    }

    public int getCoopId()
    {
        return coopId;
    }

    public void setCoopId(int id)
    {
        this.coopId = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
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

    public boolean isAggressive()
    {
        return isAggressive;
    }

    public void setAggressive(boolean aggressive)
    {
        isAggressive = aggressive;
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
