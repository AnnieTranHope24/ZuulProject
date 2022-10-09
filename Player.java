package ZuulProject;

import java.util.HashMap;
/**
 * Player class of the game.
 * Handle the player's action.
 * Contains infor about the player's
 * name, currentRoom, previousRoom, the maximum weight
 * the player can carry, list of items player is carrying.
 */
public class Player
{
    private String name;
    private Room currentRoom;
    private Room previousRoom;
    private HashMap<String, Item> pickedItems;
    private int maxWeight;
    private int currentWeight;

    /**
     * Constructor for objects of class Player
     */
    public Player(String name, int maxWeight)
    {
        this.name = name;
        this.maxWeight = maxWeight;
        currentWeight = 0;
        pickedItems = new HashMap<>();
        currentRoom = null;
        previousRoom =null;

    }

    /**
     * Get the player's current room.
     * @return the current room the player is in.
     */
    public Room getCurrentRoom(){
        return currentRoom;
    }

    /**
     * Set the current room of the player.
     */
    public void setCurrentRoom(Room room){
        currentRoom = room;
    }

    /**
     * Get the player's previous room.
     * @return the pervious room the player was in.
     */
    public Room getPreviousRoom(){
        return previousRoom;
    }    

    /**
     * Get the description of the player's current room.
     * @return the information of the current room the player is in.
     */
    public String getCurrentRoomInfor(){
        return currentRoom.getLongDescription();
    }

    /**
     * Get the player's name and weight information.
     * @ return personal information of the character.
     */
    public String getPersonalInfor(){
        String str = "You are " + name + ". " +  getWeightInfor();
        return str;
    }

    /**
     * Get the information about the weight the player can carry.
     * @ return information about the weight the player can carry.
     */
    public String getWeightInfor(){
        String str = "You can carry " + maxWeight + "lb(s) in total." + "\n";
        str += "You've already carried " + currentWeight + "lb(s).\n";
        str += "You can carry " + poundsCanPick() + " more pound(s).\n";       
        return str;
    }

    /**
     * Get the information of all items the player is carrying.
     * @return the information of all items the player is carrying.
     */
    public String getItemsInfor(){
        String str = "Item(s) carried: \n";
        for(String name : pickedItems.keySet()){
            str += pickedItems.get(name).getItemString() + "\n";
        }
        if(str.equals("")){
            str = "You don't carry any items.";
        }
        return str;
    }   

    /**
     * Get the total of weight the player is carrying.
     * @return the total of weight the player is carrying.
     */
    public String getCurrentWeight(){
        return "Total weight carried: " + currentWeight;
    }

    /**
     * Get the maximum weight the player can carry.
     * @return the maximum weight the player can carry.
     */
    public int getMaxWeight(){
        return maxWeight;
    }

    /**
     * Leave the current room,
     * enter another room.
     * Keep track of the room just left and the room just entered
     * using fields previousRoom and currentRoom.
     * Print out the information of the current room.
     * @param the room the player wants to enter.
     */
    private String enterRoom(Room otherRoom){
        previousRoom = currentRoom;
        currentRoom = otherRoom;
        return getCurrentRoomInfor();
    }    

    /**
     * Take an item from the current room.
     * @ return a notification telling whether or not the player can take the item.
     */
    public String takeItem(String name){
        String str = "";
        if(currentRoom.containsItem(name)){
            Item item = currentRoom.getItem(name);
            int tempWeight = currentWeight + item.getWeight();
            if(tempWeight <= maxWeight){
                pickedItems.put(name, item);
                currentWeight = tempWeight; 
                currentRoom.removeItem(name);
                str += "You picked up " + item.getDescription() + ".\n";
                str += "You can carry " + poundsCanPick() + " more pound(s).";
                return str;
            }else{
                int excess = tempWeight - maxWeight;
                str += "Urggg... That's too heavy. " +
                "Drop at least " + excess + " lb(s) to pick up this item.";
                return str;
            }            
        }
        else{
            str += "There's no such item to take.";
            return str;
        }

    }

    /**
     * Drop an item in the current room.
     * @ return a notification telling whether or not the player can drop the item. 
     */
    public String dropItem(String name){
        String str = "";
        if(pickedItems.get(name) != null){        
            Item item = pickedItems.get(name);
            removeItem(name);
            currentRoom.putItem(item);
            str += "You dropped " + item.getDescription() + ".\n";
            str += "You can carry " + poundsCanPick() + " more pound(s).";
            return str;
        }
        else{
            str += "There's no such item to drop.";
            return str;
        }
    }

    /**
     * Get the number of pounds the player still can pick up.
     * @return the number of pounds the player still can pick up. 
     */
    private int poundsCanPick(){
        return maxWeight - currentWeight;
    }

    /**
     * Increase the maximum weight the player can carry .
     * @param the number of pounds added to the maximum weight the player can carry.
     * @return a String telling the number of pounds added to the maximum weight.
     */
    private String increaseStrength(int addedPounds){
        maxWeight += addedPounds;
        return "The maximum weight you can carry increases by " + addedPounds + "lb(s) to " + maxWeight + "lb(s).";

    }

    /**
     * The player eats an item.
     * Remove the item from the current room or from the map of items the player is carrying.
     * @return a String notifying that the player just ate or null if the player failed to eat the item.
     * @param the name of the item the player eats.
     */
    public String eat(String name){
        if(currentRoom.containsItem(name)){ //if the player eats an item placed in the current room.
            Item item = currentRoom.getItem(name);
            currentRoom.removeItem(name);
            return "You've just eaten. ";
        }
        else if(pickedItems.get(name) != null){ //if the player eats an item he/she is carrying.
            Item item = pickedItems.get(name);
            removeItem(name); 
            return "You've just eaten. ";
        }
        else{
            return null;
        }

    }

    /**
     * Eat and increase strength.
     * @param the name of the item the player eats.
     * @param the number of pounds added to the player's maximum weight.
     * @return a String notifying whether or not the player successfully eats the item.
     */
    public String eatToBeStronger(String food, int addedPounds){
        String result = eat(food);
        if(result!= null){
            return result + increaseStrength(addedPounds);
        }
        return "There's no " + food + " to eat. ";
    }

    /**
     * The player uses an item that he/she is carrying.
     * Remove that item from the pickedItems.
     * @param the name of the item.
     * @return the name of the item if the player uses it successfully, null if the player doesn't.
     */
    public String useItem(String name){
        if(pickedItems.get(name) != null){
            removeItem(name);  
            return name;
        }
        return null;
    }

    /**
     * Check if the player is carrying an item.
     * @return true if the player is carrying the item
     * @param the name of the item.
     */
    public boolean containsItem(String name){
        return pickedItems.containsKey(name);
    }

    /**
     * Remove an item from pickedItems and reduce the current weight the player
     * is carrying by the weight of that item.
     */
    private void removeItem(String name){
        Item item = pickedItems.get(name);
        if(item != null){
            pickedItems.remove(name);
            currentWeight -= item.getWeight();
        }

    }

    /**
     * Unlock the locked exit.
     */
    private void unlockExit(String direction){
        currentRoom.unlock2ways(direction);
    }

    /**
     * Come back to the previous room.
     * @return: the information of the room the player just entered
     */
    public String back(){
        if(previousRoom != null){
            Room temp = previousRoom;
            return enterRoom(temp);

        }else{
            return "There's no room to come back!";
        }

    }

    /**
     * Give the non-player character in the room an item.
     * @return a notification if the player successfully gives the item to the NPC, null if doesn't.
     */
    private String giveItemForHelp(String itemName){
        if(currentRoom.hasNPCharacter() && !currentRoom.getNPC().gotItem()){ //if the current room has a NPC who hasn't received an item.
            String str = useItem(itemName);
            if(str != null){                                        //if the item the player wants to give is valid
                return currentRoom.getNPC().giveHelp();             
            }
            else{                                                   //if the item the player wants to give is invalid
                return null;
            }
        }else{
            return null;            ///there's no NPC in the room or the room's NPC already reveived an item.
        }
    }

    /**
     * Give an item to a NPC to unlock all the exits of the current room.
     * @return a notification whether or not exchanging item for help is successful.
     */
    public String wantToUnlockExits(String itemName){
        if(giveItemForHelp(itemName)!=null){
            unlockAllExits();
            return "Item received! All exits in this room are unlocked. ";
        }

        if (!currentRoom.hasNPCharacter()){
            return "There's no NPC in the room to give items to.";
        }else{
            if(useItem(itemName) == null){
                return "You cannot give that to me.";
            }          
            return "Keep your item. NPC doesn't help you twice!";
        }

    }

    /**
     * Unlock all the exits of the current room.
     */
    private void unlockAllExits(){
        currentRoom.unlockAllExits();
    }

    /**
     * Get an exit of the current room the player is in.
     * @return the room that the exit leads to
     */
    public Room getCurrentRoomExit(String direction){
        return currentRoom.getExit(direction);
    }

    /**
     * Unlock if an exit is locked using a specified item and enter the room.
     * @param the exit.
     * @param the name of the item used to unlock the exit.
     * @return notification whether unlocking and entering room is successful.
     */
    public String unlockEnterRoom(String direction, String key){
        Room nextRoom = getCurrentRoomExit(direction);
        if(currentRoom.isExitLocked(direction)){  //if the exit is locked
            if(!containsItem(key)){             //player doesn't have key
                return "This exit is locked. Obtain a key to open it.";
            }   
            else{
                useItem(key);
                unlockExit(direction);
                return "Key's used! The door is opened! " + enterRoom(nextRoom); 

            }          
        }
        
        else{  //if the exit is not locked or there's no exit
            if(nextRoom!= null){  //if there is an exit
                return enterRoom(nextRoom);
            }
            else{                 //if there is no exit
                return "There is no door!"; 
            }
        }        
    }
}
