package gui;

import core.repositories.ModelRepository;
import entities.Vaccine;

/**
 * Created by Kamil on 08.01.2017.
 */
public class VaccineModel extends MainModel<Vaccine>
{
    @Override
    protected String[] getColumnTitles()
    {
        String[] columnTitles = { "Id",
                "Months per dose",
                "Is obligatory",
                "Disease id",
                "Disease name",
        };

        return columnTitles;
    }

    @Override
    protected Object[] getRowData(Vaccine obj)
    {
        Object[] rowData = new Object[]{ obj.getId(),
                obj.getHowManyMonthsPerDose(),
                obj.isObligatory(),
                obj.getDiseaseId(),
                obj.getDiseaseName()
        };

        return rowData;
    }


    public VaccineModel(ModelRepository<Vaccine> repository)
    {
        super(repository);
    }
}
