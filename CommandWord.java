package ZuulProject;

/**
 * Enumeration class CommandWord 
 *
 */
public enum CommandWord
{
    //"go", "quit", "help", "look", "eat", "back", "take", "drop", "items"
    GO("go"), QUIT("quit"), HELP("help"), LOOK("look"), EAT("eat"), BACK("back"), TAKE("take"), DROP("drop"), ITEMS("items"),
    UNKNOWN("?"), GIVE("give");
    
    private String commandString;
    
    CommandWord(String commandString){
        this.commandString = commandString;
    }
    /**
     * Command word as a string.
     * @return The command word as a string.
     */
    public String toString(){
        return commandString;
    }
}

