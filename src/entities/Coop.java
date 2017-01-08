package entities;

/**
 * Created by Kamil on 08.01.2017.
 */
public class Coop
{
    private int id;
    private int areaM2;
    private int capacity;
    private int numberOfDogs;
    private int roomIdl;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getAreaM2()
    {
        return areaM2;
    }

    public void setAreaM2(int areaM2)
    {
        this.areaM2 = areaM2;
    }

    public int getCapacity()
    {
        return capacity;
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }

    public int getNumberOfDogs()
    {
        return numberOfDogs;
    }

    public void setNumberOfDogs(int numberOfDogs)
    {
        this.numberOfDogs = numberOfDogs;
    }

    public int getRoomIdl()
    {
        return roomIdl;
    }

    public void setRoomIdl(int roomIdl)
    {
        this.roomIdl = roomIdl;
    }
}
