clean:
	rm class/cs3524/mud/*.class

build:
	javac -cp class -d class java/cs3524/mud/Direction.java
	javac -cp class -d class java/cs3524/mud/Item.java
	javac -cp class -d class java/cs3524/mud/Location.java
	javac -cp class -d class java/cs3524/mud/Path.java
	javac -cp class -d class java/cs3524/mud/Player.java
	javac -cp class -d class java/cs3524/mud/MUD.java
	javac -cp class -d class java/cs3524/mud/World.java
	javac -cp class -d class java/cs3524/mud/GameSrvrIntfc.java
	javac -cp class -d class java/cs3524/mud/GameServer.java
	javac -cp class -d class java/cs3524/mud/GameClient.java
	javac -cp class -d class java/cs3524/mud/Server.java
	
