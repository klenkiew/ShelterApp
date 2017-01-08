package entities;

/**
 * Created by Kamil on 08.01.2017.
 */
public class Breed
{
    private int id;
    private String name;
    private String hairType;
    private int averageLifespan;
    private String size;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getHairType()
    {
        return hairType;
    }

    public void setHairType(String hairType)
    {
        this.hairType = hairType;
    }

    public int getAverageLifespan()
    {
        return averageLifespan;
    }

    public void setAverageLifespan(int averageLifespan)
    {
        this.averageLifespan = averageLifespan;
    }

    public String getSize()
    {
        return size;
    }

    public void setSize(String size)
    {
        this.size = size;
    }
}
