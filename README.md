CS3524 Assignment
=================

- `Item` class
	Every item has its name, description and weight. `name` cannot be empty.
- `Location`
	Every location has a description and a list of stored items. An `Item` can be stored at a location by calling the `store()`	method on a location. Every location can be explored. By calling `explore()` on a location, you get a list of possible actions. `name` cannot be empty.
- `Path`
	A path is an edge connecting two `Location` objects (i.e. vertices).
- `Player`
	Every player has a name, maximum total weight of the carried items and a list of items carried at the moment. `name` cannot be empty.

- `MUD`
	+ `join(Player p)` adds a player to the MUD
	+ `leave(Player p)` removes a player from the MUD
	+ `getStartingLocation()`
	+ `manual()`
	+ `intro()`
	+ `Map<Direction, Location> listAdjacentTo(Location l)`
	+ `from(Location l, Direction d)`
		Returns a location accessible from the location `l`, when heading in the direction `d`. If there is nothing in that direction, returns null.
	+ `public List<Player> listPlayersAt(Location l)`
	+ `movePlayer(Player p, Location destination)` moves player to a given location

Available commands:
-------------------
- `help`

- `exit` or `quit`
	Removes the player from the MUD and closes the client application.

- `help`
	Displays a short message with the most useful instructions.

- `commands`
	Displays the list of commands.

- `where`
	Displays player's current location and lists destinations accessible from that place.

- `look`
	Prints a description of the current user location and list the items at this location.

- `status`
	Prints the player's name and lists the items carried in the user's backpack.

- `go <direction>` 
	Moves the character in the specified direction, which can be any of:
	- north,
	- south,
	- east,
	- west.

	If there is nothing on the map in that direction, prints an error message.

- `go back`
	Moves the character to the previous location.

- `take <item>`
	Adds the specified item to the character's equipment. If an item is not found at the current location, prints an error message.

- `drop <item>`
	Removes the specified item from the character's equipment and stores it in the character's current location.
	