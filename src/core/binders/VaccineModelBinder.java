package core.binders;

import entities.Vaccine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kamil on 04.01.2017.
 */
public class VaccineModelBinder implements ModelBinder<Vaccine>
{
    private ModelBinderBase<Vaccine> modelBinderBase;

    // column name, field name
    private final Map<String, String> columnsToFieldsMappings = new HashMap<>();
    {
        columnsToFieldsMappings.put("Id", "id");
        columnsToFieldsMappings.put("CoIleMiesiecy", "howManyMonthsPerDose");
        columnsToFieldsMappings.put("CzyObowiazkowa", "isObligatory");
        columnsToFieldsMappings.put("ChorobaId", "diseaseId");
        columnsToFieldsMappings.put("ChorobaNazwa", "diseaseName");
    }

    public VaccineModelBinder()
    {
        modelBinderBase = new ModelBinderBase<>(columnsToFieldsMappings, () -> new Vaccine());
    }

    @Override
    public String getTableName()
    {
        return "Szczepionka";
    }

    @Override
    public List<Object> getAllParameters(Vaccine model) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(null);  // id - auto-increment in database
        parameters.add(model.getHowManyMonthsPerDose());
        parameters.add(model.isObligatory());
        parameters.add(model.getDiseaseId());

        return parameters;
    }

    @Override
    public ArrayList<String> getColumnNames()
    {
        return modelBinderBase.getColumnNames();
    }

    @Override
    public Vaccine bindModel(HashMap<String, Object> data) throws NoSuchFieldException, IllegalAccessException
    {
        return modelBinderBase.bindModel(data);
    }
}
