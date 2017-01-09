package gui;

import core.repositories.ModelRepository;
import entities.Disease;

/**
 * Created by Kamil on 09.01.2017.
 */
public class DiseaseModel extends MainModel<Disease>
{
    @Override
    protected String[] getColumnTitles()
    {
        String[] columnTitles = { "Id",
                "Name",
                "Lethality",
                "Number of cases",
                "Number of deaths",
        };

        return columnTitles;
    }

    @Override
    protected Object[] getRowData(Disease obj)
    {
        Object[] rowData = new Object[]{ obj.getId(),
                obj.getName(),
                obj.getLethality(),
                obj.getCaseCount(),
                obj.getDeathCount()
        };

        return rowData;
    }


    public DiseaseModel(ModelRepository<Disease> repository)
    {
        super(repository);
    }
}
