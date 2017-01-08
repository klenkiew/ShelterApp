package core.binders;

import core.Factory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Kamil on 27.12.2016.
 */
public class ModelBinderBase<ModelType> implements ModelBinder<ModelType>
{
    private final Map<String, String> columnsToFieldsMappings;
    private Factory<ModelType> factory;

    public ModelBinderBase(Map<String, String> columnsToFieldsMappings, Factory<ModelType> factory)
    {
        this.columnsToFieldsMappings = columnsToFieldsMappings;
        this.factory = factory;
    }

    @Override
    public String getTableName()
    {
        return new String();
    }

    @Override
    public List<Object> getAllParameters(ModelType model)
    {
        return new ArrayList();
    }

    @Override
    public ModelType bindModel(HashMap<String, Object> data) throws NoSuchFieldException, IllegalAccessException
    {
        ModelType model = factory.getInstance();
        for (Map.Entry<String, Object> column : data.entrySet())
        {
            String columnName = column.getKey();
            String mapping = columnsToFieldsMappings.get(columnName);
            // default convention: column name is same as model field name
            // if there's no corresponding entry in the map
            String fieldName = mapping == null ? columnName : mapping;
            Field field = model.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            if (field.getType() == boolean.class)
            {
                boolean value = !Objects.equals(column.getValue(), "0");
                field.set(model, value);
            }
            else
                field.set(model, column.getValue());
        }
        return model;
    }
}
