package entities;

import java.util.Date;

/**
 * Created by Kamil on 28.12.2016.
 */
public class Vaccination
{
    private int id;
    private Date vaccinationDate;

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
}
