package entities;

import java.util.Date;

/**
 * Created by Kamil on 28.12.2016.
 */
public class DiseaseHistoryRecord
{
    private Date diseaseBeginningDate;
    private Date diseaseEndDate;
    private boolean isFatal;

    public Date getDiseaseBeginningDate()
    {
        return diseaseBeginningDate;
    }

    public void setDiseaseBeginningDate(Date diseaseBeginningDate)
    {
        this.diseaseBeginningDate = diseaseBeginningDate;
    }

    public Date getDiseaseEndDate()
    {
        return diseaseEndDate;
    }

    public void setDiseaseEndDate(Date diseaseEndDate)
    {
        this.diseaseEndDate = diseaseEndDate;
    }

    public boolean isFatal()
    {
        return isFatal;
    }

    public void setFatal(boolean fatal)
    {
        isFatal = fatal;
    }
}
