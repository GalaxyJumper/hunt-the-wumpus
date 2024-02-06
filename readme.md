Object Descriptions

Object purpose summaries:

•	High Score object: Manages the high scores (including saving high scores and displaying a high score scoreboard).
•	Trivia object: Manages the trivia questions for the game (including asking questions and loading questions from a file).
•	GameLocations object: Keeps track of the locations of the player, the Wumpus, and the hazards. Also handles Wumpus movements unless there’s a separate Wumpus object.
•	Player object: Keeps track of the player’s inventory and score. (This could be done by the same person doing the GameLocations object).
•	Cave object: Keeps track of which rooms in the cave are connected to which other rooms.
•	Graphical Interface object: Displays the state of the current game (the current room, connected rooms, inventory, etc.).
•	Game Control object: Handles events reported by the Graphical Interface, coordinates all the other parts of the game.

Additional objects (these are only added when there are more than six students)
•	Sound object: Plays sounds for the game, from a selection of different themes.
•	Lazy Wumpus object: Controls the behavior of the Wumpus, providing it with more complicated behavior.
•	Active Wumpus object: Controls the behavior of the Wumpus, but in a different manner than the Lazy Wumpus object.

-------------------------------------------------------------------------------
The Game Control Object

The Game Control Object coordinates all of the other pieces of the game:
•	The Game Control Object keeps track of the game state (Currently playing a game, displaying the splash screen, displaying the high scores).
•	The Game Control Object accepts and validates user input (that is, player commands and game control commands). The trivia and high score objects will manage their own user input.
•	The Game Control Object interacts with the GameLocations Object, the Player Object, the Graphical Interface Object, the Trivia Management System, and the High Score Management System.
•	Some aspects of the Game Control Object should be exposed to the user through a “main menu” that allows the user to launch the game, display high scores, or exit.
The Graphical Interface Object

------------------------------------------------------------------------------------
The Graphical Interface Object

The Graphical Interface Object is the object which does the actual drawing to the screen during the game. The tasks the Graphical Interface Object performs are as follows:
•	Display on the game screen a representation of the room, including the hexagonal form of the room, with each edge illustrated as either a tunnel to an adjacent room or a wall blocking access to an adjacent room.  Include an illustration of the player, any present hazards, the wumpus if present, and any additional graphics that add to the realism of the cave.
•	Display on the game screen the player’s score.
•	Display on the game screen the player’s inventory.
•	Display on the game screen any hints based on the player’s room. (For example: I smell a wumpus).
•	Display on the game screen all actions the player can take on the current turn.
o	Move
o	Shoot an arrow
o	Purchase arrows
o	Purchase a secret

---------------------------------------------------------------------------------
The GameLocations Object

The GameLocations Object tracks the locations of all of the objects in the current game. The tasks it performs are as follows:

•	Store and interact with the cave used for this game
•	Keep track of where the hazards are
•	Keep track of where the Wumpus is. This includes controlling Wumpus behavior (that is, asleep, awake, moving).
•	Keep track of where the player is
•	Control arrow shooting.
•	Give any necessary warnings
•	Obtain hints to help the player

-------------------------------------------------------------------------------
The Cave Object

The Cave Object keeps track of the cave, including data that describes the “connectivity information” for adjacent rooms.  Adjacent rooms may either be connected by way of a tunnel, or not connected at all (separated by a wall). The tasks it performs are as follows:

•	Read and parse cave data from a file
•	Keeps an internal data representation of the cave sufficient for representing connections for each room in the cave
•	Exposes appropriate methods and/or attributes for other objects and the main program of the Hunt The Wumpus game.

-------------------------------------------------------------------------------
The Player Object

The Player Object keeps track of the player and all information associated with the player. The tasks it performs are as follows:

•	Keep track of player inventory
o	Arrows
o	Gold coins
•	Keep track of how many turns the player has taken
•	Compute ending score of player

-------------------------------------------------------------------------------
The Trivia Management Object

This component of Hunt the Wumpus is used to resolve conflicts and purchases during gameplay, and provide secrets or hints that help the user to progress through the game.  The user will interact with it, when it is called by other game objects for the following:

•	Purchasing additional arrows: 2 out of 3 trivia questions must be answered correctly.
•	Purchasing a secret: 2 of 3 correct answers.
•	Saving from a bottomless pit: 2 of 3 correct answers.
•	Escaping the Wumpus: 3 out of 5 correct answers.

The Trivia Management System will handle asking questions and getting the user response itself.
Judges at Finals will accept projects substituting a different mini-game instead of trivia.  However, substitution is allowed ONLY after a full trivia implementation is in place.  You must build trivia first!  If a different mini-game of similar scope is developed instead of trivia, the mini-game object must still be responsible for displaying the mini-game state and handling user input.  Also, as defined in the spec there are cases where the user will receive trivia answers.  If a different mini-game is developed, the mini-game object is still responsible for providing hints or other advantages in playing the mini-game to replace trivia answers.

-------------------------------------------------------------------------------
The High Score Management Object

This component of Hunt the Wumpus manages scores achieved by players that have won the game.  The user will interact with this component in two ways:

•	Storing a new high score
•	Viewing existing high scores

The default high score data for the game should be the names of all of the participants in this project, with zero scores and arbitrary cave names.

The high score data will be exposed in a couple of ways: the user will be able to bring up the high score table through the game’s menu system, and the high score table will automatically be displayed after a player finishes a game. 

At no time will there be more than 10 scores tracked by the High Score Management System.  If a new score is submitted, that is good enough to be included in an already-full high score table, the new score will cause the lowest other score to be removed automatically.

-------------------------------------------------------------------------------
The Sound Object

The Sound object is responsible for playing sounds upon requests from the Game Control object. The tasks it performs are as follows:
•	Reads list of sound files from its configuration file.
•	Sounds files are grouped into sound themes (alternative sets of sounds).
•	The Game Control object gets a list of available sound themes from the Sound object and selects (randomly or based on user’s input) an active scheme.
•	The Game Control object calls the Sound object to play a particular game sound (player moves, player shoots an arrow, wumpus moves, trivia pops up, win, lose, etc.) from the selected theme.
•	The Sound object opens and plays the sound file that contains the requested sound.
•	There should be at least two sound themes.

-------------------------------------------------------------------------------
The Lazy Wumpus Object

The Lazy Wumpus object handles the movement of the Wumpus in the current game. The tasks it performs are as follows:
•	Keep track of the current state the Wumpus is in (that is, asleep, awake, moving).
•	If the player shoots an arrow and misses while the Wumpus is sleeping, the Wumpus wakes up and runs up to two rooms away from current position.
•	If the Wumpus is defeated in trivia, it will run up to three rooms away.
•	The Wumpus is slow and can only move one room per turn.
•	If the Wumpus does not move for two turns, it falls asleep.

-------------------------------------------------------------------------------
The Active Wumpus Object

The Active Wumpus object handles a different set of rules of movement for the Wumpus in the current game. The tasks it performs are as follows:
•	Keep track of the current state the Wumpus is in (*that is, asleep and awake).
•	Keep track of the number of turns.
•	Every 5 to 10 turns the Wumpus will wake up and move 1 room per turn for up to three turns before going back to sleep.
•	Every turn, there is a 5% chance the Wumpus will immediately teleport to a new, random location.
•	If the Wumpus is defeated in trivia, it will run up to two rooms away per turn for up to three turns. 
