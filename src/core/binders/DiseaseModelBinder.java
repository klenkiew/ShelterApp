package core.binders;

import entities.Disease;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private final Map<String, String> appColumnsToDatabaseColumnsMappings = new HashMap<>();
    {
        appColumnsToDatabaseColumnsMappings.put("Id", "Id");
        appColumnsToDatabaseColumnsMappings.put("Name", "Nazwa");
        appColumnsToDatabaseColumnsMappings.put("Lethality", "Smiertelnosc");
        appColumnsToDatabaseColumnsMappings.put("Number of cases", "LiczbaZachorowan");
        appColumnsToDatabaseColumnsMappings.put("Number of deaths", "LiczbaZgonow");
    }

    public DiseaseModelBinder()
    {
        modelBinderBase = new ModelBinderBase<>(columnsToFieldsMappings, appColumnsToDatabaseColumnsMappings, () -> new Disease());
    }

    @Override
    public String getTableName() {
        return "Choroba";
    }

    @Override
    public List<Object> getAllParameters(Disease model) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(null);  // id - auto-increment in database
        parameters.add(model.getName());
        parameters.add(model.getLethality());
        parameters.add(model.getSymptoms());
        parameters.add(model.getDescription());
        parameters.add(model.getCaseCount());
        parameters.add(model.getDeathCount());

        return parameters;
    }

    @Override
    public ArrayList<String> getColumnNames()
    {
        return modelBinderBase.getColumnNames();
    }

    @Override
    public String getDatabaseColumnNameFor(String appColumnName)
    {
        return modelBinderBase.getDatabaseColumnNameFor(appColumnName);
    }

    @Override
    public Disease bindModel(HashMap<String, Object> data) throws NoSuchFieldException, IllegalAccessException
    {
        return modelBinderBase.bindModel(data);
    }
}
