package gui;

import core.repositories.ModelRepository;
import entities.DiseaseHistoryRecord;

/**
 * Created by mkk-1 on 08/01/2017.
 */
public class DiseaseHistoryRecordModel extends MainModel<DiseaseHistoryRecord>
{
    @Override
    protected String[] getColumnTitles()
    {
        String[] columnTitles = { "Id",
                "Sickness Start",
                "Sickness End",
                "Dog Id",
                "Disease Id",
                "Fatal"
        };

        return columnTitles;
    }

    @Override
    protected Object[] getRowData(DiseaseHistoryRecord obj)
    {
        Object[] rowData = new Object[]{ obj.getId(),
                obj.getDiseaseBeginningDate(),
                obj.getDiseaseEndDate(),
                obj.getDogId(),
                obj.getDiseaseId(),
                obj.isFatal()
        };

        return rowData;
    }

    public DiseaseHistoryRecordModel(ModelRepository<DiseaseHistoryRecord> repository)
    {
        super(repository);
    }
}