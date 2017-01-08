package core.binders;

import entities.Coop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mkk-1 on 08/01/2017.
 */
public class CoopModelBinder implements ModelBinder<Coop>
{
    private ModelBinderBase<Coop> modelBinderBase;

    // column name, field name
    private final Map<String, String> columnsToFieldsMappings = new HashMap<>();
    {
        columnsToFieldsMappings.put("Id", "id");
        columnsToFieldsMappings.put("PowierzchniaM2", "areaM2");
        columnsToFieldsMappings.put("IleMiesciPsow", "capacity");
        columnsToFieldsMappings.put("IleMieszkaPsow", "numberOfDogs");
        columnsToFieldsMappings.put("PomieszczenieId", "roomId");
    }

    public CoopModelBinder()
    {
        modelBinderBase = new ModelBinderBase<>(columnsToFieldsMappings, () -> new Coop());
    }

    @Override
    public String getTableName()
    {
        return "Kojec";
    }

    @Override
    public List<Object> getAllParameters(Coop model) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(null);  // id - auto-increment in database
        parameters.add(model.getAreaM2());
        parameters.add(model.getCapacity());
        parameters.add(model.getNumberOfDogs());
        parameters.add(model.getRoomId());

        return parameters;
    }

    @Override
    public Coop bindModel(HashMap<String, Object> data) throws NoSuchFieldException, IllegalAccessException
    {
        return modelBinderBase.bindModel(data);
    }
}