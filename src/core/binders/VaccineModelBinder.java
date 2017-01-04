package core.binders;

import entities.Vaccine;

import java.util.HashMap;
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
        columnsToFieldsMappings.put("coIleMiesiecy", "howManyTimesPerMonth");
        columnsToFieldsMappings.put("czyObowiazkowa", "isObligatory");
        columnsToFieldsMappings.put("Choroba_Id", "diseaseId");
    }

    public VaccineModelBinder()
    {
        modelBinderBase = new ModelBinderBase<>(columnsToFieldsMappings, () -> new Vaccine());
    }

    @Override
    public Vaccine bindModel(HashMap<String, Object> data) throws NoSuchFieldException, IllegalAccessException
    {
        return modelBinderBase.bindModel(data);
    }
}
