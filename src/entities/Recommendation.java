package entities;

/**
 * Created by Kamil on 28.12.2016.
 */
public class Recommendation
{
    private int id;
    private String frequency;
    private String treatmentLength;
    private String comment;
    private String foodType;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getFrequency()
    {
        return frequency;
    }

    public void setFrequency(String frequency)
    {
        this.frequency = frequency;
    }

    public String getTreatmentLength()
    {
        return treatmentLength;
    }

    public void setTreatmentLength(String treatmentLength)
    {
        this.treatmentLength = treatmentLength;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getFoodType()
    {
        return foodType;
    }

    public void setFoodType(String foodType)
    {
        this.foodType = foodType;
    }
}
