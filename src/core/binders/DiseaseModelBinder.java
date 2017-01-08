package core.binders;

import entities.Disease;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kamil on 08.01.2017.
 */
public class DiseaseModelBinder implements ModelBinder<Disease>
{
    private ModelBinderBase<Disease> modelBinderBase;

    // column name, field name
    private final Map<String, String> columnsToFieldsMappings = new HashMap<>();
    {
        columnsToFieldsMappings.put("Id", "id");
        columnsToFieldsMappings.put("Nazwa", "name");
        columnsToFieldsMappings.put("Smiertelnosc", "lethality");
        columnsToFieldsMappings.put("Objawy", "symptoms");
        columnsToFieldsMappings.put("Opis", "description");
        columnsToFieldsMappings.put("LiczbaZachorowan", "caseCount");
        columnsToFieldsMappings.put("LiczbaZgonow", "deathCount");
    }

    public DiseaseModelBinder()
    {
        modelBinderBase = new ModelBinderBase<>(columnsToFieldsMappings, () -> new Disease());
    }

    @Override
    public String getTableName() {
        return "Choroba";
    }

    @Override
    public Disease bindModel(HashMap<String, Object> data) throws NoSuchFieldException, IllegalAccessException
    {
        return modelBinderBase.bindModel(data);
    }
}
