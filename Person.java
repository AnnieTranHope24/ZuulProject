package ZuulProject;

/**
 * A Person, placed in a Room, is a non-player character who can talk
 * when the player enters the room.
 * Person has two fields: name and gotItem.
 * name is the name of the NPC.
 * gotItem shows whether or not the NPC received an item 
 * from the player and gave the player help.
 * NPC will give the player help if the player gives him/her
 * an item. After the NPC receives an item and gives the player
 * help, NPC cannot continue to receive items and give help.
 */
public class Person
{

    private String name;
    private boolean gotItem;
    /**
     * Constructor for objects of class ItemKeeper.
     */
    public Person(String name)
    {
        this.name = name;
        gotItem = false;

    }
    /**
     * Greet when the player enters the room.
     * @return a String.
     */
    public String sayHi(){
        String str = "Ha, you're new here. I'm " + name + ". Give me an item, I can help you unlock all exits of this room.\n";
        str += "You can save keys for other rooms!\n";
        str += "Type \"" + CommandWord.GIVE + "\" and the item name to give it to me to reveive my help.";
        return str;
    }   
    
    /**
     * Agree to give the player help only if never gave help to the player before.
     * @return a notification if NPC successfully gives help, null if doesn't.
     */
    public String giveHelp(){
        if(!gotItem){
            gotItem = true;
            return "Item received";
        }
        return null;
    }
    /**
     * Check if the NPC received an item from the player.
     * @return true if the NPC received an item from the player, false if didn't.
     */
    public boolean gotItem(){
        return gotItem;
    }





}



