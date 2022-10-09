package ZuulProject;

/**
 * Item class.
 * An Item is something the player can carry around.
 * It has a name, a description and its weight.
 */
public class Item
{
    private String description;
    private int weight;
    private String name;

    /**
     * Constructor for objects of class Item
     */
    public Item(String name, String description, int weight)
    {
        this.description = description;
        this.weight = weight;
        this.name = name;
    }
    /**
     * Get all informations about the item.
     * @return: a string contain information about the item.
     */
    public String getItemString(){
        return name + ": " + description + ", weight: " + weight;
    }
    /**
     * Get the name of the item.
     * @return name of the item.
     */
    public String getName(){
        return name;
    }
    /**
     * Get the description of the item.
     * @return the description of the item.
     */
    public String getDescription(){
        return description;
    }
    /**
     * Get the weight of the item.
     * @return the weight of the item.
     */
    public int getWeight(){
        return weight;
    }

}
