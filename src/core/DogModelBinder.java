package core;

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
        columnsToFieldsMappings.put("imie", "name");
        columnsToFieldsMappings.put("wiek", "age");
        columnsToFieldsMappings.put("czyAgresywny", "isAggressive");
        columnsToFieldsMappings.put("czyOtwarty", "isOpen");
        columnsToFieldsMappings.put("czyChorowity", "isVulnerable");
        columnsToFieldsMappings.put("kolorSiersci", "hairColor");
        columnsToFieldsMappings.put("opis", "description");
        columnsToFieldsMappings.put("Rasa_Id", "breedId");
        columnsToFieldsMappings.put("Kojec_Id", "coopId");
    }

    public DogModelBinder()
    {
        modelBinderBase = new ModelBinderBase<>(columnsToFieldsMappings, () -> new Dog());
    }

    @Override
    public Dog bindModel(HashMap<String, Object> data) throws NoSuchFieldException, IllegalAccessException
    {
        return modelBinderBase.bindModel(data);
    }
}
