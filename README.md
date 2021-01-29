# HTW MVC Design
<br>

> This project is further implemented as full MVC design that can perform Hunt The Wumpus game. TextView or GUI can be generated respectively to play the game.

<br>

## Assumptions
* The Game Mode can only handle 1 Player or 2 Player. Additional Player will require further modification of MVC structure.
* In 2 Player Mode, each player proceed this game as taking turns. If one player is not able to input the available command, the controller throws hints and continue wait for right instruction.
* Starting position and Starting number of Arrows of both players are set at same. Since each player takes turns to move or shoot, game status will change depends on how each player's strategy.
* Game ends with either Hunter is eaten by Wumpus, falling into pit, or out of arrows to hunt.
* Game wins with either Hunter targets at Wumpus with his own arrow.

<br>

## Features

### Model
* Build the rooms using Kruskal algorithm and distinguish between caves and tunnels.
* Be able to set 1 cave as the Wumpus. 
* Be able to set adjacent caves to smell the Wumpus as hunter(Player) move to this cave.
* Be able to configure number of bottomless pits that randomly placed into a cave at game map.
* Be able to set adjacent caves to felt the draft as hunter(Player) move to this cave.
* Be able to configure number of super bats that randomly placed into a cave at game map.
* Be able to set super bats that has 50% chance to transport the hunter to a random cave.
* Be able to set the Game Mode as 1 Player or 2 Player.
* Be able to remain the Game Setting same at 2 Player mode while each Player takes turns to navigate through the maze.
* Be able to indicate which Player is taking turn updating the controller.
* Be able to end the game if either hunter reaches the end game condition above.

### View
* Be able to generate GUI or TextView according to the first command line arguments from the user.
* For TextView, catches the String out from the controller and output to the screen.
* For TextView, catches the command line arguments as input and stream into controller using BufferedReader.
* For TextView, indicates the Player 2 takes turn if game is in 2 Player Mode.
* For GUI, applies different Hunter Icon and Arrow Icon to distinguish two Hunters and takes turns to change position from the update of Controller.
* For GUI, enables Game Mode Setting Menu(1 Player or 2 Player).
* For GUI, enables Game Difficulty Setting Menu(Easy/ Medium/ Hard/ Hell) each level of difficulty is configured with different maze settings, number of superbats, number of pits, and number of arrows.
* For GUI, enables Game Restart Option with Same Setup or Random Setup. Keep same Random Seed for Same Setup, reset Random Seed for Random Setup option, render the Graphic Panel.
* For GUI, enables Resizable Panel with adjustable frame size.
* For GUI, enables Button clicks and Keyboard commands to navigate the maze with Move or Shoot Option.
* For GUI, enables God Mode with Keyboard command G, which reveal the entire Maze Map and playing. Can also use for testing.
* For GUI, enables Merry Xmas Mode with Keyboard command X. In this Mode the Icons are changed and Ending Game dialogue is changed. With same game logic applied.


### Controller
* Build the controller that takes Appendable inputs, and generates String outputs.
* Update the state of the game to the screen.
* Be able to navigate the player through the maze.
* Be able to shoot an arrow in a given direction and in a given distance of caves passing.
* Be able to update nearby caves and other relevant aspects of current game state.
* Be able to configure number of arrows for the hunter at Game Start.
* Be able to end the game if hunter moves to Wumpus position.
* Be able to end the game if hunter uses arrow to shoot the Wumpus.
* Be able to end the game if hunter moves to any bottomless pits position.
* Be able to end the game if hunter used up his arrows.

<br>

## Instructions to run program

```bash
# run the driver function as user and run the gui version of the game.
java -jar HTW.jar --gui

# enjoy your game!
```

```bash
# run the driver function as user and run the text-based version of the game.
java -jar HTW.jar --text

# Indicate Game Mode:
# One Player -- 1, Two Players -- 2
2

# Map property configuration:
# total rows to set up the maze, e.g. 10
10

# total columns to set up the maze, e.g. 10
10

# number of remaining walls for this maze to be built
# expected range [0, (rows - 1)*(columns - 1)], otherwise exception will be thrown. e.g. 5 is [0, 81]
5

# starting position, e.g. cave 59 the starting position will be same if in Two Players Mode
59

# Difficulty configuration: 
# configure total number of bottomless pits for this game e.g. 5
5

# configure total number of super bats for this game e.g. 5
5

# configure total number of arrows to start this game e.g. 3
3

# After Starting Position updated at consol, interact with input
# e.g. 
# You are in Cave 59
# Tunnel Leads to: [Cave 49, Cave 69, Cave 60, Cave 58]
# Shoot or Move (S-M)?
M

# Where to?
# Then input the Cave index where there is a Tunnel, e.g. 58
58

# Wait for update to the console and follow anther instruction
# e.g.
# You are in Cave 58
# Tunnel Leads to: [Cave 48, Cave 57, Cave 59, Cave 68]
# Shoot or Move (S-M)?

# Key in S for shoot, other invalid input will be requested to enter the input again.
S

# In Two Player Mode, once Player One finish the full command of Move To or Shoot To, Player Two will be instructed to take command line argument.
# e.g.

# ** Switch to Player 2 **

# You are in Cave 59
# Tunnel Leads to: [Cave 49, Cave 69, Cave 60, Cave 58]
# Shoot or Move (S-M)?

# Enjoy your play!
```
<br>


## Design/Model Changes
Please refer to HW6_design.png file for updated UML design, the previous design can be found as HW6_design_before.png.


Modifications:
* Game class
    * adding Random Seed and generate setter for GUI random game menu.
    * adding Game status flag isGodMode and isSpecialEdition
    * extends Observable at Game Class so GameView can observe change made in the Game controller
    * Update isEnd(), adding specific termination function isEaten(), isFallen(), isOutOfArrows(),isGameWon()
    * Modify the status update method in Game. As the controller now takes boolean isTwoPlayers to switch player turns
    * Adding the moveToIndex() which takes direction string and generated moveToIndex to be handled by controller
    * Adding the shootToIndex() which takes direction string and generated shootToIndex to be handled by controller

* Driver class
    * adding args[0] indicator to generate game with  command line argument --gui or --text
    * adding GUI instantiate method and set GUI to visible

* RoomMaze class
    * adding Kruskal seed as parameter, and update in Game class. So if Game is set randomly generated, the RoomMaze will also be randomly built. Otherwise stays same build.
    * adding revealGameMap function, when game ends then Game View will notify the controller and call this function. Then update to Game View to show all the elements and caves.

All other modification is the implementations of entire view package for this Final Project.

<br>

## Description of Example Run
### Run 1, refer to file named Run_1_TextView_2Players.txt
Two Player Mode, Game end because Player One falling into pit 
* Execute JAR file to run the program
    * **line 1**
* Game configuration: 
    * **line 4-24**
* First Step for Player One, Move to Cave 3
    * **line 27-32**
* Switch Turn for Player Two, Move to Cave 7
    * **line 35-42**
* Print Current Status for Player One, Move to Cave 4
    * **line 44-50**
* Print Current Status for Player Two, Dodge the Bat, Move to Cave 12
    * **line 44-50**
* Print Current Status for Player One, Cave 4 is Pit, Game Ends
    * **line 64-66**
* Option to restart the Game with Play Again Option - Y.
     * **line 67**

### Run 2, refer to file named,refer to Run_2_TextView_1Player_ShootWumpus.txt
One Player Mode, Game wins because Player One shoot at the Wumpus
* Execute JAR file to run the program
    * **line 1**
* Game configuration: 
    * **line 4-24**
* Show Hunter current Location as Navigate through Maze from Cave 50 to Cave 45:
    * **line 4-24**
* Try to shoot the Wupus towards Cave 4, catches invalid Cave index: 
    * **line 144-156**

* Try to shoot the Wupus towards Cave 44, missed: 
    * **line 157-164**

* Try to shoot the Wupus towards Cave 33, target the Wumpus: 
    * **line 163-173**
* Option to restart the Game with Play Again Option - Y.
     * **line 175**

### Run 3, refer to file named:
* Run_3_GUI_2Player_ShootWumpus_1.png 
* Run_3_GUI_2Player_ShootWumpus_2.png

Two Players Mode, Game wins because Player Two shoot at the Wumpus

### Run 4, refer to file named:
* Run_4_GUI_1Player_Hard_isEaten_1.png
* Run_4_GUI_1Player_Hard_isEaten_2.png

One Player, Hard Mode, Game ends because Hunter is eaten by Wumpus

<br>




## Appendix
### UML design
<p align="center">
  <img height="2000" src="https://github.com/0zz10/HuntTheWumpus_MVC/blob/master/res/HW6_design.png?raw=true">
</p>

### Demo
<p align="center">
  <img height="500" src="https://github.com/0zz10/HuntTheWumpus_MVC/blob/master/Final_Demo_HTW.gif?raw=true">
</p>

### References
* [Course Materials in Modules](https://northeastern.instructure.com/courses/25755/modules)
* [Java GUI video](https://www.youtube.com/watch?v=Kmgo00avvEw)
* [Icons Copyright]
    * [arrow icon](https://www.google.ca/url?sa=i&url=https%3A%2F%2Ficons-for-free.com%2Farrow%2Bleft%2Bhunting%2Bhunting%2Barrow%2Bleft%2Blove%2Blove%2Barrow%2Bicon-1320185741715358574%2F&psig=AOvVaw3hN1pNew2dNmtxTXNcrprA&ust=1608012747865000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJCepLvozO0CFQAAAAAdAAAAABAJ)
    * [move icon](https://www.google.ca/url?sa=i&url=https%3A%2F%2Ficons-for-free.com%2Fbt%2Bdirection%2Bmove%2Bnavigation%2Bright%2Bicon-1320085922748375218%2F&psig=AOvVaw2r8M-VmpcCdarptV7x175m&ust=1608014382815000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCMitl8ruzO0CFQAAAAAdAAAAABAH)
    * [elements icons](https://northeastern.instructure.com/courses/25755/modules)
    * [reindeer1 icon](https://www.iconninja.com/files/499/330/470/reindeer-icon.png)
    * [reindeer2 icon](https://cdn0.iconfinder.com/data/icons/christmas-icons-rounded/110/Reindeer-512.png)
    * [santa icon](https://img.favpng.com/17/0/13/santa-claus-christmas-scalable-vector-graphics-icon-png-favpng-2MY22ZaZPjfq0tcQfGrhjqz2B_t.jpg) 
    * [sock icon](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ-sgsA6cU-J4_ohJbqFiwMtJP2PhECkAJrvg&usqp=CAU)








