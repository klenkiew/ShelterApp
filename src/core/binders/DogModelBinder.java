package core.binders;

import entities.Dog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private final Map<String, String> appColumnsToDatabaseColumnsMappings = new HashMap<>();
    {
        appColumnsToDatabaseColumnsMappings.put("Id", "Id");
        appColumnsToDatabaseColumnsMappings.put("Name", "Imie");
        appColumnsToDatabaseColumnsMappings.put("Age", "Wiek");
        appColumnsToDatabaseColumnsMappings.put("A", "CzyAgresywny");
        appColumnsToDatabaseColumnsMappings.put("O", "CzyOtwarty");
        appColumnsToDatabaseColumnsMappings.put("V", "CzyChorowity");
//        appColumnsToDatabaseColumnsMappings.put("KolorSiersci", "Hair Color");
    }


    public DogModelBinder()
    {
        modelBinderBase = new ModelBinderBase<>(columnsToFieldsMappings, appColumnsToDatabaseColumnsMappings, () -> new Dog());
    }

    @Override
    public String getTableName()
    {
        return "Pies";
    }

    @Override
    public List<Object> getAllParameters(Dog model) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(null);  // id - auto-increment in database
        parameters.add(model.getName());
        parameters.add(model.isAggressive());
        parameters.add(model.getBreedId());
        parameters.add(model.getCoopId());
        parameters.add(model.getAge());
        parameters.add(model.isOpen());
        parameters.add(model.isVulnerable());
        parameters.add(model.getHairColor());
        parameters.add(model.getDescription());

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
    public Dog bindModel(HashMap<String, Object> data) throws NoSuchFieldException, IllegalAccessException
    {
        return modelBinderBase.bindModel(data);
    }
}
