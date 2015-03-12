CS3524 Assignment
=================

The documentation for this assignment has been generated using the `javadoc` tool. To access it, open `doc/index.html` in your browser. What follows is a short demonstration on how to use the whole system.

Tutorial
--------

This short tutorial will show you how to get around in the MUD. It will also highlight some of the features of the game.

### Building
First, make sure you are in the project's root directory.
If you are on Linux, type
```
make build
```

If you are using Windows, type
```
.\build
```

### Starting the server
To start the server, `cd` into the `class` directory and type:
```
java cs3524.mud.Server <rmi_port> <server_port> <max_number_of_MUD>
```
You will be presented with a prompt, which allows you to create a new world on the server. The commands must have a format like this
```
<world_name> <map_file> <items_file>
```
Please note, that example files are in the `worlds` directory. If you copy them to `class`, you can try this
```
Vikings vikings.map vikings.items
```
followed by ENTER. Then,
```
Elves elves.map elves.items
```
This will create two worlds in the MUD. If there is still a prompt, you can type
```
end
```
to finish creating, and register the server with what you have.

#### Warning!
Please make sure, that the map and item files are in the same directory from which you run the server, because otherwise, it might be impossible to access those files due to file permissions.

### Starting the client
To start the client, `cd` into the `class` directory and type:
```
java cs3524.mud.GameClient <hostname> <rmi_port>
```
This will start the game.

### First Steps
When you run the game, first thing you have to do is to choose which world you want to join. Simply type one of the names from the list.

Then choose a name for your character. It will be used to uniquely identify you in your journey through the MUD, so sometimes you might have to choose a different name, if there is someone playing with this name already.

With these formalities out of the way, you can start playing. There is a number of commands you can type in, but first, try typing
```
look
```
It will give you some useful information about your character, your location and players around you.
Once you have an idea about your whereabouts, try typing
```
where
```
This will tell you where you can go from here. Choose any destination from the ones listed and type `go <direction>`, for example
```
go north
```
Now, type `look` again, to see what items are at this location. Choose one, then type
```
take <name_of_that_item>
```
To see if you are now carrying it, type
```
status
```
If everything went well, you should see that item listed in your equipment.

That's the end of this tutorial. Feel free to explore. Below is a list of all commands available in the game.

Available commands:
-------------------
- `exit` or `quit`
	Removes the player from the MUD and closes the client application.

- `help`
	Displays a short message with the most useful instructions.

- `?`
	Displays a tip on how to start.

- `where`
	Displays player's current location and lists destinations accessible from that place.

- `look`
	Prints a description of the current user location, list other players and the items at this location.

- `status`
	Prints the player's name and lists the items carried in the user's backpack.

- `go <direction>`
	Moves the character in any of the four basic directions. Then, prints the name of the user's new location.
	If there is nothing on the map in that direction, prints a warning message.

- `take <item>`
	Adds the specified item to the character's equipment, provided that item can be found at the current location.
