package gui;


import core.repositories.ModelRepository;
import entities.Dog;

/**
 * Created by Kamil on 27.12.2016.
 */
public class DogModel extends MainModel<Dog>
{
    @Override
    protected String[] getColumnTitles()
    {
        String[] columnTitles = { "Id",
                                  "Name",
                                  "Age",
                                  "A",
                                  "O",
                                  "V"
        };

        return columnTitles;
    }

    @Override
    protected Object[] getRowData(Dog obj)
    {
        Object[] rowData = new Object[]{ obj.getId(),
                                         obj.getName(),
                                         obj.getAge(),
                                         obj.isAggressive(),
                                         obj.isOpen(),
                                         obj.isVulnerable()
        };

        return rowData;
    }

    public DogModel(ModelRepository<Dog> repository)
    {
        super(repository);
    }
}
