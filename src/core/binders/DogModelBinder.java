package core.binders;

import entities.Dog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kamil on 27.12.2016.
 */
public class DogModelBinder implements ModelBinder<Dog>
{
    private ModelBinderBase<Dog> modelBinderBase;

    // column name, field name
    private final Map<String, String> columnsToFieldsMappings = new HashMap<>();
    {
        columnsToFieldsMappings.put("Id", "id");
        columnsToFieldsMappings.put("Imie", "name");
        columnsToFieldsMappings.put("Wiek", "age");
        columnsToFieldsMappings.put("CzyAgresywny", "isAggressive");
        columnsToFieldsMappings.put("CzyOtwarty", "isOpen");
        columnsToFieldsMappings.put("CzyChorowity", "isVulnerable");
        columnsToFieldsMappings.put("KolorSiersci", "hairColor");
        columnsToFieldsMappings.put("Opis", "description");
        columnsToFieldsMappings.put("RasaId", "breedId");
        columnsToFieldsMappings.put("KojecId", "coopId");
    }

    public DogModelBinder()
    {
        modelBinderBase = new ModelBinderBase<>(columnsToFieldsMappings, () -> new Dog());
    }

    @Override
    public String getTableName()
    {
        return "Pies";
    }

    @Override
    public Dog bindModel(HashMap<String, Object> data) throws NoSuchFieldException, IllegalAccessException
    {
        return modelBinderBase.bindModel(data);
    }
}
