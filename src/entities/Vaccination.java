package entities;

import java.util.Date;

/**
 * Created by Kamil on 28.12.2016.
 */
public class Vaccination
{
    private int id;
    private Date vaccinationDate;
    private int dogId;
    private int vaccineId;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Date getVaccinationDate()
    {
        return vaccinationDate;
    }

    public void setVaccinationDate(Date vaccinationDate)
    {
        this.vaccinationDate = vaccinationDate;
    }

    public int getDogId()
    {
        return dogId;
    }

    public void setDogId(int dogId)
    {
        this.dogId = dogId;
    }

    public int getVaccineId()
    {
        return vaccineId;
    }

    public void setVaccineId(int vaccineId)
    {
        this.vaccineId = vaccineId;
    }
}
