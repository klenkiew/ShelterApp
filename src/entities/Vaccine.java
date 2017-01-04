package entities;

/**
 * Created by Kamil on 28.12.2016.
 */
public class Vaccine
{
    private int id;
    private int howManyTimesPerMonth;
    private boolean isObligatory;
    private int diseaseId;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getHowManyTimesPerMonth()
    {
        return howManyTimesPerMonth;
    }

    public void setHowManyTimesPerMonth(int howManyTimesPerMonth)
    {
        this.howManyTimesPerMonth = howManyTimesPerMonth;
    }

    public boolean isObligatory()
    {
        return isObligatory;
    }

    public void setObligatory(boolean obligatory)
    {
        isObligatory = obligatory;
    }

    public int getDiseaseId()
    {
        return diseaseId;
    }

    public void setDiseaseId(int diseaseId)
    {
        this.diseaseId = diseaseId;
    }
}
