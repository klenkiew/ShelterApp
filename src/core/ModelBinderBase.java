package core;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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
            field.set(model, column.getValue());
        }
        return model;
    }
}
