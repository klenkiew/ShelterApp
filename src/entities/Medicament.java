package entities;

/**
 * Created by Kamil on 28.12.2016.
 */
public class Medicament
{
    private int id;
    private String name;
    private String type;
    private boolean isHarmful;

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

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public boolean isHarmful()
    {
        return isHarmful;
    }

    public void setHarmful(boolean harmful)
    {
        isHarmful = harmful;
    }
}
