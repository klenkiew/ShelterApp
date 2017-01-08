package gui;

import core.repositories.ModelRepository;
import entities.Vaccination;

/**
 * Created by Kamil on 08.01.2017.
 */
public class VaccinationModel extends MainModel<Vaccination>
{
    @Override
    protected String[] getColumnTitles()
    {
        String[] columnTitles = { "Id",
                "Vaccination date",
                "Dog id",
                "Vaccine id",
        };

        return columnTitles;
    }

    @Override
    protected Object[] getRowData(Vaccination obj)
    {
        Object[] rowData = new Object[]{ obj.getId(),
                obj.getVaccinationDate(),
                obj.getDogId(),
                obj.getVaccineId()
        };
        return rowData;
    }

    public VaccinationModel(ModelRepository<Vaccination> repository)
    {
        super(repository);
    }
}
