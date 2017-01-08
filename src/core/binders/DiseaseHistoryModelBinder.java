package core.binders;

import entities.DiseaseHistoryRecord;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mkk-1 on 08/01/2017.
 */
public class DiseaseHistoryModelBinder  implements ModelBinder<DiseaseHistoryRecord>
{
    private ModelBinderBase<DiseaseHistoryRecord> modelBinderBase;

    // column name, field name
    private final Map<String, String> columnsToFieldsMappings = new HashMap<>();
    {
        columnsToFieldsMappings.put("Id", "id");
        columnsToFieldsMappings.put("DataZachorowania", "startDate");
        columnsToFieldsMappings.put("DataWyzdrowienia", "endDate");
        columnsToFieldsMappings.put("PiesId", "dogId");
        columnsToFieldsMappings.put("ChorobaId", "diseaseId");
        columnsToFieldsMappings.put("CzySmiertelna", "isMortal");
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
    public DiseaseHistoryRecord bindModel(HashMap<String, Object> data) throws NoSuchFieldException, IllegalAccessException
    {
        return modelBinderBase.bindModel(data);
    }
}
