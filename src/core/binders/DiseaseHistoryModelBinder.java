package core.binders;

import entities.DiseaseHistoryRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mkk-1 on 08/01/2017.
 */
public class DiseaseHistoryModelBinder implements ModelBinder<DiseaseHistoryRecord>
{
    private ModelBinderBase<DiseaseHistoryRecord> modelBinderBase;

    // column name, field name
    private final Map<String, String> columnsToFieldsMappings = new HashMap<>();
    {
        columnsToFieldsMappings.put("Id", "id");
        columnsToFieldsMappings.put("DataZachorowania", "diseaseBeginningDate");
        columnsToFieldsMappings.put("DataWyzdrowienia", "diseaseEndDate");
        columnsToFieldsMappings.put("PiesId", "dogId");
        columnsToFieldsMappings.put("ChorobaId", "diseaseId");
        columnsToFieldsMappings.put("CzySmiertelna", "isFatal");
    }

    public DiseaseHistoryModelBinder()
    {
        modelBinderBase = new ModelBinderBase<>(columnsToFieldsMappings, () -> new DiseaseHistoryRecord());
    }

    @Override
    public String getTableName() {
        return "HistoriaChorob";
    }

    @Override
    public List<Object> getAllParameters(DiseaseHistoryRecord model) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(null);  // id - auto-increment in database
        parameters.add(model.getDiseaseBeginningDate());
        parameters.add(model.getDiseaseEndDate());
        parameters.add(model.getDogId());
        parameters.add(model.getDiseaseId());
        parameters.add(model.isFatal());

        return parameters;
    }

        @Override
    public DiseaseHistoryRecord bindModel(HashMap<String, Object> data) throws NoSuchFieldException, IllegalAccessException
    {
        return modelBinderBase.bindModel(data);
    }
}
