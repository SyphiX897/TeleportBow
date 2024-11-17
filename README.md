# About
Teleport Bow is a simple and useful plugin to use in your minecraft servers. You can use this plugin for your hubs/lobbies and entertain the players

Minecraft Version: 1.14+ - 1.20+

* Hangar: [TeleportBow](https://hangar.papermc.io/SyphiX/TeleportBow)
* Modrinth: [TeleportBow](https://modrinth.com/plugin/teleportbow)


# Compiling
1. Compilation requires JDK 17.
2. To compile the plugin, run ./gradlew build from the terminal.
3. Once the plugin compiles, grab the jar from /bin folder.

# Permissions
* teleportbow.use
*  teleportbow.drop
* teleportbow.changeslot
* teleportbow.commands

# Commands
* /teleportbow give - Give the TeleportBow and arrow
* /teleportbow give <player_name> - Gives the TeleportBow and arrow to a player.
* /teleportbow reload - Reload the plugin

# Integration
### **Gradle Kotlin**

Repository
```gradle.kts
maven { url = uri("https://repo.sayandev.org/snapshots" }
```
Dependency
```gradle.kts
implementation("ir.syphix:TeleportBow:tag")
```
* Replace Tag with the desired version of TeleportBow.

### **Gradle Groovy**

Repository
```gradle
maven { url = "https://repo.sayandev.org/snapshots" }
```
Dependency
```gradle
implementation "ir.syphix:TeleportBow:tag"
```
* Replace Tag with the desired version of TeleportBow.

### **Maven**

Repository
```xml
<repository>
  <url>https://repo.sayandev.org/snapshots</url>
</repository>
```
Dependency
```xml
<dependency>
  <groupId>ir.syphix</groupId>
  <artifactId>TeleportBow</artifactId>
  <version>tag</version>
</dependency>
```
* Replace Tag with the desired version of TeleportBow.

# Found any problems? contact to me
Discord ID: .syphix
