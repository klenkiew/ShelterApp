package core.repositories;

import core.Database;
import core.binders.ModelBinder;
import entities.Disease;

/**
 * Created by Kamil on 08.01.2017.
 */
public class DiseaseRepository extends ModelRepository<Disease>
{
    public DiseaseRepository(Database database, ModelBinder<Disease> binder)
    {
        super(database, binder);
    }
}
