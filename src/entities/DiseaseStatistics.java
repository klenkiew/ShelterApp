package entities;

/**
 * Created by Kamil on 11.01.2017.
 */
public class DiseaseStatistics
{
    private String diseaseName;
    private int year;
    private int[] numbersOfCases = new int[13]; // 12 months + total number for entire year as 0th element
    private int[] numbersODeaths = new int[13]; // 12 months + total number for entire year as 0th element

    public String getDiseaseName()
    {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName)
    {
        this.diseaseName = diseaseName;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public int[] getNumbersOfCases()
    {
        return numbersOfCases;
    }

    public void setNumbersOfCases(int[] numbersOfCases)
    {
        this.numbersOfCases = numbersOfCases;
    }

    public int[] getNumbersODeaths()
    {
        return numbersODeaths;
    }

    public void setNumbersODeaths(int[] numbersODeaths)
    {
        this.numbersODeaths = numbersODeaths;
    }
}
