package core;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kamil on 27.12.2016.
 */
public interface ModelBinder<ModelType>
{
    ModelType bindModel(HashMap<String, Object> data) throws NoSuchFieldException, IllegalAccessException;
}
