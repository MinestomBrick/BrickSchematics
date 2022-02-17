# BrickSchematics

A library for [Minestom](https://github.com/Minestom/Minestom) to load and use schematics.

## API

```
repositories {
    maven { url "https://repo.jorisg.com/snapshots" }
}

dependencies {
    implementation 'com.gufli.bricksidebar:api:1.0-SNAPSHOT'
}
```

The API works with layering, multiple extensions can push a sidebar, the latest one will be shown to the player.
The top sidebar can later be removed and the player will see the underlying sidebar.
```java
Sidebar sidebar = new Sidebar(Component.text("title"));
sidebar.appendLines(Component.text("multi"), Component.text("line"), Component.text("text!"));

SidebarAPI.push(player, sidebar);
SidebarAPI.pop(player);
SidebarAPI.remove(player, sidebar);
```

Sidebars wil automatically evaluate placeholders in it's contents every x milliseconds (configurable).

