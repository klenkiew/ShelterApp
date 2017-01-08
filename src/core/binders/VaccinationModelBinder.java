package core.binders;

import entities.Vaccination;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kamil on 04.01.2017.
 */
public class VaccinationModelBinder implements ModelBinder<Vaccination>
{
    private ModelBinderBase<Vaccination> modelBinderBase;

    // column name, field name
    private final Map<String, String> columnsToFieldsMappings = new HashMap<>();
    {
        columnsToFieldsMappings.put("Id", "id");
        columnsToFieldsMappings.put("DataSzczepienia", "vaccinationDate");
        columnsToFieldsMappings.put("PiesId", "dogId");
        columnsToFieldsMappings.put("SzczepionkaId", "vaccineId");
    }

    public VaccinationModelBinder()
    {
        modelBinderBase = new ModelBinderBase<>(columnsToFieldsMappings, () -> new Vaccination());
    }

    @Override
    public String getTableName()
    {
        return "Szczepionka";
    }

    @Override
    public Vaccination bindModel(HashMap<String, Object> data) throws NoSuchFieldException, IllegalAccessException
    {
        return modelBinderBase.bindModel(data);
    }
}
