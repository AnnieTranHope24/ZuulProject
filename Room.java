package ZuulProject;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * It can have a non-player character who can talk and give the player proper help.
 * There are items placed in a room.
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{ 
    private String description;
    private HashMap<String, HashMap<Room, Boolean>> exits;
    private HashMap<String, Item> items;
    private Person character;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new HashMap<>();
        character = null;

    }

    /**
     * Set the non-player character of the room.
     */
    public void setNPCharacter(String name){
        character = new Person(name);
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * Each exit can be locked or not locked.
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     * @param true if the exit is locked, false is not.
     */
    public void setExit(String direction, Room neighbor, Boolean isLocked) 
    {
        HashMap<Room, Boolean> neighborAndLock = new HashMap<>();
        neighborAndLock.put(neighbor, isLocked);
        exits.put(direction, neighborAndLock);
    }

    /**
     * Return the description of the room.
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Get an exit room.
     * @return the room that the exit leads to.
     */
    public Room getExit(String direction){
        if(exits.get(direction) != null){
            for(Room room : exits.get(direction).keySet()){
                return room;

            }
        }
        return null;

    }

    /**
     * Return true if the exit is locked, false if it is not locked or there's no exit.
     * @return true if the exit is locked, false if it is not locked or there's no exit.
     */

    public boolean isExitLocked(String direction){

        if(exits.get(direction) != null){
            Room r;
            for(Room room : exits.get(direction).keySet()){
                r = room;
                return exits.get(direction).get(r);
            }

        }
        return false; 
    }

    /**
     * Unlock the exit only one way.
     * @param the exit.
     */
    private void unlock(String direction){
        if(exits.get(direction) != null){
            Room r;
            for(Room room : exits.get(direction).keySet()){
                r = room;
                HashMap<Room, Boolean> exitAndLock = new HashMap<>();
                exitAndLock.put(r, false);
                exits.put(direction, exitAndLock);
                return;
            } 
        }
    }

    /**
     * Get the direction needed to go to a specified room.
     * @return the String direction.
     */
    public String directionToRoom(Room room){
        for(String direction : exits.keySet()){
            if(exits.get(direction).containsKey(room)){
                return direction;
            }
        }
        return null;
    }

    /**
     * Return a description of the room's exits,
     * for example, "Exits: north west".
     * @return A description of the available exits.
     */
    public String getExitString(){
        String exitString = "# Exits:";
        for(String direction : exits.keySet()){
            if(exits.get(direction) != null){
                exitString += " " + direction;
            }
        }
        return exitString;
    }

    /**
     * Return a long description of this room, of the form:
     *      You are in the kitchen.
     *      Exits: north west
     * @return A description of the room, including exits.
     */
    public String getLongDescription(){
        String str = "You are " + description + ".\n" ;
        str += "# Room's item(s): \n" + getItemString() + greetingfromNPC() +"\n";
        str+= getExitString();
        return str;
    }

    /**
     * Non-player character says hi.
     * @return a string.
     */
    private String greetingfromNPC(){
        if(character != null){
            String str = "# Non-player character: \n";
            str += character.sayHi();
            return str;
        }
        return "# There's no non-player character in the room.";
    }

    /**
     * Return true if the room has a non-player character, false if not.
     * @return true if the room has a non-player character, false if not.
     */
    public boolean hasNPCharacter(){
        if(character!=null){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Get the information of the items in the room.
     * @return the string contains information of the items in the room.
     */
    public String getItemString(){
        String str = "";
        for(String name : items.keySet()){
            if(items.get(name) != null){
                str += items.get(name).getItemString();
                str += "\n";
            }
        }
        if(str.equals("")){
            str = "There's no item in the room. \n";
        }
        return str;
    }

    /**
     * Add an item to the items list.
     */
    public void addItem(String name, String itemDescription, int weight){
        Item item = new Item(name, itemDescription, weight);
        items.put(name, item);

    }

    /**
     * Return true if the room contains an item, false if doesn't.
     * @param the name of the item.
     * @return true if the room contains this item.
     */
    public boolean containsItem(String itemName){
        if(items.get(itemName) != null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Get a specified item of the room.
     * @return an item in the room or null if there's no such item.
     */
    public Item getItem(String itemName){
        if(items.get(itemName) != null){
            return items.get(itemName);
        }else{
            return null;
        }

    }

    /**
     * Remove an item from the room
     */
    public void removeItem(String itemName){
        items.remove(itemName);
    }

    /**
     * Put an item in the room.
     */
    public void putItem(Item item){
        items.put(item.getName(), item);
    }

    /**
     * Get the non-player character of the room.
     * @return the non-player character of the room.
     */
    public Person getNPC(){
        return character;
    }

    /**
     * Unlock all exits of the room
     */
    public void unlockAllExits(){
        for(String direction: exits.keySet()){
            unlock2ways(direction);
        }
    }

    /**
     * Unlock the exit from two ends.
     */
    public void unlock2ways(String direction){
        if(getExit(direction)!=null){
            Room r = getExit(direction);
            unlock(direction);
            String d2 = r.directionToRoom(this);
            if(d2!= null) 
            {
                r.unlock(d2);
            }

        }

    }
}
