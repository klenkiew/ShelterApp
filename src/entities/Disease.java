package entities;

/**
 * Created by Kamil on 28.12.2016.
 */
public class Disease
{
    private int id;
    private String name;
    private String lethality;
    private String symptoms;
    private String description;
    private int caseCount;
    private int deathCount;

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

    public String getLethality()
    {
        return lethality;
    }

    public void setLethality(String lethality)
    {
        this.lethality = lethality;
    }

    public String getSymptoms()
    {
        return symptoms;
    }

    public void setSymptoms(String symptoms)
    {
        this.symptoms = symptoms;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getCaseCount()
    {
        return caseCount;
    }

    public void setCaseCount(int caseCount)
    {
        this.caseCount = caseCount;
    }

    public int getDeathCount()
    {
        return deathCount;
    }

    public void setDeathCount(int deathCount)
    {
        this.deathCount = deathCount;
    }
}
