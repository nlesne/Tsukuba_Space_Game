# Tsukuba_Space_Game
### Explore the universe right now !

-- Put Graphics here --

## SETUP

To start the game on Windows you just have to double click on the .jar file or right click -> open.
On Linux you will need to open a terminal in the current repertory where the .jar file is and type "java -jar [jar_name]". Here, the jar_name is only Space_Game.jar but it might change later !

## CONTROLS

In this game three things will be the core of the gameplay : Moving, Shooting, and Enter the planet. But we added some features during the development that we thought could improve the experience of the player.

#### Gameplay :
##### Move : Arrow Keys
##### Shoot : F
##### Enter a planet : E
#### Camera :
##### Move the camera : WASD (on QWERTY keyboard) and ZQSD (on AZERTY keyboard)
##### Zoom & Dezoom : X and C
#### HUD :
##### Debug Mode : F2

--------------------------------------------------------------------------------------------------------------------------------

## CONTEXT 

This is a game that we (Nathan Lesne and me, Thomas Barré-Pitous) are currently developping with the help of Claus Aranha, teacher at the University of Tsukuba.
This project is a part of our internship abroad at the University of Tsukuba (筑波大学), Japan, proposed by the IUT Computer Science Department of Bordeaux.

The subject of this internship is "Game Development" and we are currently using LibGDX for our project. 

Our goal is to create a video game from scratch using Java or LUA and understand the process of creating a video game.

## CONTACT

Barré-Pitous Thomas : 
- barrepitousthomas@gmail.com

Nathan Lesne : 
- ?

Claus Aranha : 
- ?

Thank you for reading.

--------------------------------------------------------------------------------------------------------------------------------

## UPDATES

#### 14/06/2018 - v.1.0.0 : 

Here we are, we didn't update that much since the game had no goals and we felt that it was a necessary part of the experience to finish the last boss and give a clear update.

Added :
- The boss is completed and it is now the goal of the game.
- The sprites now change correctly in the HUD.
- Added shooting ennemies (with their AI).

Changes :
- The background no longer keep the same size in the Hangar menu when resized (Try to avoid resizing tho).
- The bullets are now faster, for you and your ennemies.
- Upgrades have been balanced and now include hitbox upgrade when changing the core.
- Changed the keyboard controls in order to be QWERTY and AZERTY friendly.


#### 06/06/2018 - v.0.0.3 : 


We are approaching the end and a lot of things need to be still be implemented. We have a piece of a game right now but the Day-To-Day work is really efficient and by speeding things up a little bit we should be on time.

Added :
- Starfield Parallax.
- Collision.
- Hitboxes.
- Upgrade System.
- Money & Quests.

Changes :
- Changed the HUD and now let you see your life points, your upgrade level and your money.
- Made our own sprites for the spaceship and ennemies but we are too short on time for making more of them.

#### 29/05/2018 - v.0.0.2 : 
We finished the implementation of Axeran's prototype into LibGDX and we are now creating components and systems for our ECS.

Added :
- The movements of the spaceship work now as intended.
- AI is now implemented for the ennemies.
- The entity indicator system is implemented.
- Implemented the planet entity but still need more to it.
- Shooting and bullets are now implemented by pressing the W button.

Changes :
- Changed ennemies and spaceship sprites.


#### 23/05/2018 - v.0.0.1 :

We are currently implementing Axeran's prototype from LUA to LibGDX. It should take a week and we are very short on time because we still don't have any graphics/sfx.

What we have : 
- Our ECS (Ashley) is implemented.
- Handling Inputs > Player and Camera.
- The camera follow the player. There is also a system of locked and unlocked camera.
