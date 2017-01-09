package core.binders;

import entities.Vaccination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        return "Szczepienie";
    }

    @Override
    public List<Object> getAllParameters(Vaccination model) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(null);  // id - auto-increment in database
        parameters.add(model.getVaccinationDate());
        parameters.add(model.getDogId());
        parameters.add(model.getVaccineId());

        return parameters;
    }

    @Override
    public ArrayList<String> getColumnNames()
    {
        return modelBinderBase.getColumnNames();
    }

    @Override
    public Vaccination bindModel(HashMap<String, Object> data) throws NoSuchFieldException, IllegalAccessException
    {
        return modelBinderBase.bindModel(data);
    }
}
