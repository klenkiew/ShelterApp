package core.binders;

import entities.Breed;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mkk-1 on 08/01/2017.
 */
public class BreedModelBinder implements ModelBinder<Breed>
{
    private ModelBinderBase<Breed> modelBinderBase;

    // column name, field name
    private final Map<String, String> columnsToFieldsMappings = new HashMap<>();
    {
        columnsToFieldsMappings.put("Id", "id");
        columnsToFieldsMappings.put("Nazwa", "name");
        columnsToFieldsMappings.put("RodzajSiersci", "hairType");
        columnsToFieldsMappings.put("SredniaDlugoscZycia", "averageLifespan");
        columnsToFieldsMappings.put("Wielkosc", "size");
    }

    private final Map<String, String> appColumnsToDatabaseColumnsMappings = new HashMap<>();

    public BreedModelBinder()
    {
        modelBinderBase = new ModelBinderBase<>(columnsToFieldsMappings, appColumnsToDatabaseColumnsMappings, () -> new Breed());
    }

    @Override
    public String getTableName()
    {
        return "Rasa";
    }

    @Override
    public List<Object> getAllParameters(Breed model) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(null);  // id - auto-increment in database
        parameters.add(model.getName());
        parameters.add(model.getHairType());
        parameters.add(model.getAverageLifespan());
        parameters.add(model.getSize());

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
    public Breed bindModel(HashMap<String, Object> data) throws NoSuchFieldException, IllegalAccessException
    {
        return modelBinderBase.bindModel(data);
    }
}