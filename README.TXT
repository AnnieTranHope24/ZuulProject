Project: zuul-bad
Authors: Michael Kölling and David J. Barnes

This project is part of the material for the book

   Objects First with Java - A Practical Introduction using BlueJ
   Sixth edition
   David J. Barnes and Michael Kölling
   Pearson Education, 2016

This project is a simple framework for an adventure game. In this version,
it has a few rooms and the ability for a player to walk between these rooms.
That's all.

To start this application, create an instance of class "Game" and call its
"play" method.

This version of the game contains some very bad class design. It should NOT
be used as a basis for extending the project without fixing these design
problems. It serves as an example to discuss good and bad design (chapter 8
of the book).

Chapter 8 of the book contains a detailed description of the problems in this
project, and how to fix them.

The project 'zuul-better' contains a version of this project with better
designed class structure. It includes the fixes discussed in the book.

Project: zuul
Authors: Annie Ngoc Tran

Explain changes in the project:
+ Game class: only responsible for setting a specific game. For the most part, 
the class is a combination of available methods from other classes to design
a game. This class doesn't need to know how each small task is actually solved.  

+ Player class: in charge of all tasks the player encounters. 
It uses methods of Room to solve problems related to the player's 
currentRoom, previousRoom and how the player move from one room to another. 
It makes use of methods of Person to decide how the player interact with the NPC, and
methods of Item to make the player eat, use, take or drop an Item.

+ Room class: a room has items, NPC, and exits.
HashMap<String, HashMap<Room, Boolean>> exits holds the names of the exits,
the rooms, and whether or not those exits are locked. Use the exit name to get
the exit room and whether or not it's locked.
HashMap<String, Item> items holds the items in the room. Use the item name to
get the item.

In conclusion, the most obvious change I made to the project is to make each class solve
its own problems based on responsibility-driven design rules.


Test Script:
##Test part 1: in createGame(), set all exits to be unlocked
1. back test
back/ Expect: There's no room to back.
go east
back/Expect: come back to the previous room
back hai/ Expect: Back what?
2 back in a row/ Expect: come back to the previous room.

2. take and drop test
take/ Expect: Take what?
take ab/Expect: No such item
take bench/ Expect: successful to take, can carry 10 more lbs
take mouse/ Expect: successful to take, can carry 0 more lb
go south
go south
take box/ Expect: fail to take, drop at least 5lbs to take this item.
drop/Expect: Drop what?
drop ab/Expect: No such item
drop mouse/ Expect: successful to drop, now can carry 10 more lbs.
drop mouse/ Expect: fail to drop, there's no such item to drop.
take box/ Expect: successful to take, now can carry 5 more lbs.
look/ Expect: mouse, cookie in the room

3. items test
items/ Expect: bench, box, total weight: 45

4. eat cookie test
eat/ Expect: eat what?
eat you/ Expect: you cannot eat that!
eat cookie/ Expect: you've just eaten a magic cookie, max weight you can carry: 70, still can carry: 25
look/ Expect: mouse in the room
go north
take cookie/ Expect: successful to take, now can carry 23 more lbs
items/ Expect: bench, box, cookie, total weight: 47
eat cookie/ Expect: successful to eat, max weight can carry: 90, still can carry: 45
items/ Expect: bench, box, total weight: 45
eat cookie/ Expect: there's no cookie to eat.
quit

##Test part 2: in createGame(), set:
        setExitPair("east", pianoRoom, "west", mainHall, false);
        setExitPair("south", yard, "north", mainHall, true);
        setExitPair("west", diningHall, "east", mainHall, true);
        setExitPair("south", gate, "north", yard, true);

+ Locked exits and NPC test

go east/successful
back/back to the main hall
go west/locked
go south/locked
give bench/failed
take bench/successful
take mouse/successful
give bench/successful, NPC helps to open all doors.
go west/successful
go east/successful back to the main hall
go south/successful
give mouse/failed, no NPC in the room
go south/locked
back/back to the main hall
take key/successful
go south
go south/key's used
quit










