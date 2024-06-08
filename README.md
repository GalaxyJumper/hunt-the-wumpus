Contributors:
- Oliver Davis: Captain 1 and GUI Class
- Aviral Mishra: Captain 2, GameControl Class, Cave Class, and Trivia Class
- Lincoln Nguyen: Trivia Class, Trivia Questions, and Story Elements
- Shivansh Bhatt: Trivia Class, Trivia Questions, and Story Elements
- Kavya Tayal: Player Class, HighScore Class, and Trivia Questions
- Josh Lennon: Player Class, HighScore Class, GameLocations Class, SecretsManager Class, Trivia Questions, Story Elements, Art, and SFX
- Nick Lennon: Player Class, HighScore Class, SoundManager Class, and Trivia Questions
- Toki Young: SoundManager Class and Trivia Questions

This is a java game titled "In the Deep" created by our team at Bothell High School for Microsoft's 'Hunt the Wumpus' challenge. You must navigate through a maze of underwater caverns, avoid deadly hazards, skirt death by the power of your wit, and ultimately strike the Wumpus dead the only way we know how: 1200 pounds of TNT and tetrodotoxin, delivered gracefully by our "reposessed" Mark 14 torpedoes.

"In the Deep" plays like the original "Hunt the Wumpus", with a few minor gameplay modifications:
Trivia Questions: when encountering any hazard, positive outcomes are determined by successfully answering randomly ordered, themed, trivia questions.

Pits and Bats have been changed to Abyss and Currents respectively while Wumpus remained the same. Players still receive a warning when adjacent to these hazards. Arrows have been changed to torpedoes.

The Spec
In terms of following the spec, many differences have been implented. Based on a read of the spec, it seems one could quite quickly find the Wumpus and just shoot it with one of their three starting arrows with little to no trouble. We changed it so the player starts with zero torpedoes (arrow equivalent), trivia only earns one torpedo at a time, and three out of five trivia questions must be answered correctly to earn one at all. This also meant the player shouldn't immediately lose for running torpedoes since they start with none. For simplicity, we then switched all trivia events to require three out of five trivia questions as opposed to some taking two out of three. In addition, it seems too easy to rack up a lot of points by simply moving back and forth between two rooms that need to be safe, so we completely reworked the coin system. The player now earns coins for every unique room explored. Since this would drastically increase the difficulty of the game due to having to pay the few number of coins the player has for every trivia question, we instead only required one coin to attempt to buy a torpedo or a secret. We also allowed the player to buy a hint to any trivia question for a coin due to the difficulty of the questions and the lack of providing them trivia when they move through tunnels. To fit with the theme, we also made it so Currents (Bat equivalent) do not move to a different room after sending the player somewhere random.

In-Game Navigation
All actions use left clicking EXCEPT shooting torpedoes (right click)
To purchase items, access the menu in the top right

Troubleshooting
Game Crashes on Startup:
Ensure you have the correct version of JDK installed.

Awkward Graphics:
Do not resize the game

Performance Issues:
Close other applications running in the background.

Compilation Errors:
Ensure all necessary files are in the correct directories.
Check for any missing dependencies.