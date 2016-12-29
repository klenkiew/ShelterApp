package legacy;

/**
 * Created by Kamil on 27.12.2016.
 */
    // model class for testing
public class City
{
    private int ID;
    private String Name;
    private String CountryCode;
    private String District;
    private int Population;

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String name)
    {
        Name = name;
    }

    public String getCountryCode()
    {
        return CountryCode;
    }

    public void setCountryCode(String countryCode)
    {
        CountryCode = countryCode;
    }

    public String getDistrict()
    {
        return District;
    }

    public void setDistrict(String district)
    {
        District = district;
    }

    public int getPopulation()
    {
        return Population;
    }

    public void setPopulation(int population)
    {
        Population = population;
    }
}
