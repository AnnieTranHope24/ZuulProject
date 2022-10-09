package ZuulProject;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Player player;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createGame();
        parser = new Parser();

    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createGame()
    {
        Room mainHall, pianoRoom, diningHall, yard, gate;
        //create player
        player = new Player("Meowzart", 50);
        // create the rooms
        mainHall = new Room("at the middle of the main hall");
        pianoRoom = new Room("in the piano room");
        diningHall = new Room("in the dining hall");
        yard = new Room("in the big front yard");
        gate = new Room("at the gate");

        //initialise the non-player characters in each room
        mainHall.setNPCharacter("Birdthoven");
        diningHall.setNPCharacter("Poodle");

        // initialise room exits
        setExitPair("east", pianoRoom, "west", mainHall, false);
        setExitPair("south", yard, "north", mainHall, true);
        setExitPair("west", diningHall, "east", mainHall, true);
        setExitPair("south", gate, "north", yard, true);

        // initialise items of each room
        gate.addItem("cookie", "a magic fish cookie - eating this cookie makes you stronger and be able to carry more pounds", 2);
        mainHall.addItem("bench", "a black piano bench with padded cushion", 40);
        mainHall.addItem("mouse", "a plastic mouse", 10);
        mainHall.addItem("key", "a key helping you to open the exit", 1);
        pianoRoom.addItem("bear", "a teddy bear", 5);
        diningHall.addItem("ball", "a big yellow wool ball", 10);
        yard.addItem("shelf", "a wood wall shelf", 30);
        yard.addItem("cookie", "a magic fish cookie - eating this cookie makes you stronger and be able to carry more pounds", 2);
        gate.addItem("box", "a carton box, comfortable for cats in all sizes to sleep in", 5);

        player.setCurrentRoom(mainHall);  // start game in the main hall.
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Meowzart's Castle!");
        System.out.println("Type \"" + CommandWord.HELP + "\" if you need help.");
        System.out.println();
        System.out.println(player.getPersonalInfor());
        System.out.println(player.getCurrentRoomInfor());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        CommandWord commandWord = command.getCommandWord();
        switch(commandWord){
            case UNKNOWN:
            System.out.println("I don't know what you mean...");
            break;
            case HELP:
            printHelp();
            break;
            case GO:
            goRoom(command);
            break;
            case QUIT:
            wantToQuit = quit(command);
            break;
            case LOOK:
            look();
            break;
            case EAT:
            eat(command);
            break;
            case BACK:
            back(command);
            break;
            case TAKE:
            take(command);
            break;            
            case DROP:
            drop(command);
            break;
            case ITEMS:
            items(command);
            break;  
            case GIVE:
            give(command);
            break;

        }
        return wantToQuit;
    }

    // implementations of user commands:
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println(player.getPersonalInfor());
        System.out.println("You are a genius musician.");
        System.out.println("You've just moved in a magnificent but messy castle.");
        System.out.println("Your mission is to find and decorate the piano room.");
        System.out.println("Pick up items and place them at the piano room to complete your mission.");
        System.out.println("You can eat a cookie to increase your strength.");
        System.out.println("Good luck.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommands());
        System.out.println();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        // Try to leave current room. If the exit's lock, use a key to unlock it.
        System.out.println(player.unlockEnterRoom(direction, "key"));
        
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * Look around the room, 
     * print out the description of the room
     * and the exits again.
     */
    private void look(){
        System.out.println(player.getCurrentRoomInfor());
    }

    /**
     *  Take the player into the previous room he/she was in.
     */
    private void back(Command command){
        if(command.hasSecondWord()){
            System.out.println("Back what?");
            return;
        }
        System.out.println(player.back());
    }


    /**
     * The player picks up an item.
     * Add the item to the combination of items the player is carrying
     * Remove the item from the combination of items in the current room.
     */
    private void take(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Take what?");
            return;
        }
        String itemToPickName = command.getSecondWord();
        System.out.println(player.takeItem(itemToPickName));

    }

    /**
     * Give the non-player character an item to receive a help.
     */
    private void give(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to give...
            System.out.println("Give what?");
            return;
        }  
        String itemToGiveName = command.getSecondWord();
        System.out.println(player.wantToUnlockExits(itemToGiveName)); //unlock all exits of the current room.
    }

    /**
     * The player drops an item.
     * Remove the item from the combination of items the player is carrying
     * Add the item to the combination of items in the current room. 
     */
    private void drop(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop..
            System.out.println("Drop what?");
            return;
        }
        String itemToDropName = command.getSecondWord();
        System.out.println(player.dropItem(itemToDropName));

    }        

    /**
     * Prints out all items currently carried and their total weight.
     */
    private void items(Command command){
        if(command.hasSecondWord()){
            System.out.println("Items what?");
        }else{
            System.out.println(player.getItemsInfor());
            System.out.println(player.getCurrentWeight());
        }

    }

    /**
     * Let the player eat,
     * print out a notification.
     * If the player eats a magic cookie,
     * the maximum weight the player can 
     * carry increases by a specified number.
     */
    private void eat(Command command){
        if(!command.hasSecondWord()){     //if there is no second word, we don't know what to eat..
            System.out.println("Eat what?");
            return;
        }
        String food = command.getSecondWord();

        if(food.equals("cookie")){  // an item which can be eaten.
            System.out.println(player.eatToBeStronger(food, 20));  //food that can increase the player's strength
        }else{              //the player's trying to eat an inedible item.
            System.out.println("You cannot eat that!");
        }
    }

    /**
     * Creates a pair of exits between two rooms.
     */
    private void setExitPair(String direction1, Room room1, String direction2, Room room2, boolean isLocked){
        room1.setExit(direction2, room2, isLocked);
        room2.setExit(direction1, room1, isLocked);
    }
}

