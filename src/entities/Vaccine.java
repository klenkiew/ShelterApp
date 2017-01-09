package entities;

/**
 * Created by Kamil on 28.12.2016.
 */
public class Vaccine
{
    private int id;
    private int howManyMonthsPerDose;
    private boolean isObligatory;
    private int diseaseId;
    private String diseaseName;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getHowManyMonthsPerDose()
    {
        return howManyMonthsPerDose;
    }

    public void setHowManyMonthsPerDose(int howManyMonthsPerDose)
    {
        this.howManyMonthsPerDose = howManyMonthsPerDose;
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

    public String getDiseaseName()
    {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName)
    {
        this.diseaseName = diseaseName;
    }
}
