package core.repositories;

import core.Database;
import core.binders.ModelBinder;
import entities.DiseaseHistoryRecord;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Kamil on 08.01.2017.
 */
public class DiseaseHistoryRepository extends ModelRepository<DiseaseHistoryRecord>
{
    public DiseaseHistoryRepository(Database database, ModelBinder<DiseaseHistoryRecord> binder)
    {
        super(database, binder);
    }

    public void add(DiseaseHistoryRecord diseaseHistoryRecord) throws SQLException
    {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(null);  // id - auto-increment in database
        parameters.add(diseaseHistoryRecord.getDiseaseBeginningDate());
        parameters.add(diseaseHistoryRecord.getDiseaseEndDate());
        parameters.add(diseaseHistoryRecord.getDogId());
        parameters.add(diseaseHistoryRecord.getDiseaseId());
        parameters.add(diseaseHistoryRecord.isFatal());
        database.update("insert into " + binder.getTableName() + " values (?, ?, ?, ?, ?, ?)", parameters);
    }
}
