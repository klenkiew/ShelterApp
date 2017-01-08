package core.binders;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Kamil on 27.12.2016.
 */
public interface ModelBinder<ModelType>
{
    String getTableName();

    List<Object> getAllParameters(ModelType model);

    ModelType bindModel(HashMap<String, Object> data) throws NoSuchFieldException, IllegalAccessException;
}
