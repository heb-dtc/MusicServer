# SoLi project

This project is an attempt at developing a sound library ecosystem providing:
- a backend for media library management 
- front applications for accessing the media library and media playback
- a player with running locally and in the cloud:
 - a full [MPD](https://www.musicpd.org) server implementation, therefore compatible with existing MPD clients
 - an HTTP API allowing music playback controls

The player is using the [gstreamer](https://gstreamer.freedesktop.org) framework as the underlying media pipeline.

This is also an experiment on the KotlinMultiplatform and JetpackCompose dev env.

## [LibSoli]

The lib soli is the central piece of this project. Its is loaded and used by all the different
actors of the ecosystem and provide them with the code that they all have to share (data models, repositories, ...)

#### TODOS
[] add a multiplatform logger
[] add a Player interface that clients should implement
[] get podcast duration
[] combine the fetch of radios and podcasts in MediaRepository

## [Apps]
### TODOS Android
[] call to /api/history
[] react to system event for pausing / restarting playback
[] implement History screen
[] add some loading state when fetching

### iOS
#### TODOS iOS
[] load libsoli and fetch radios

## [Backend]
### Prerequisite
A working java env. is needed.

### Building and Running

Build the application using shadowJar task to package a fat JAR
```bash
./gradlew :backend:shadowJar
java -jar backend/build/libs/backend-all.jar
```

Build the application using installShadowDist task to package an executable
```bash
./gradlew :backend:installShadowDist
./backend/build/install/backend-shadow/bin/backend
```

#### TODOS
[] endpoint serving history media playback
[] player api - register current player and current playback info
[] player api - control current playback from any client
[] cloud player?

## [Desktop]
### Prerequisite

The gstreamer library needs to be installed locally if you want to run the local player.
A working java env. is needed as well.

### Building and Running

Build the application using the installDist task
```bash
./gradlew :desktop:installDist
./desktop/build/install/desktop/bin/desktop
```

#### TODOS
[] load podcast episodes
[] properly expose playback event on the system

## Useful links

[what is gstreamer](https://gstreamer.freedesktop.org/documentation/application-development/introduction/gstreamer.html?gi-language=c)  
[gstreamer command line interface](https://gstreamer.freedesktop.org/documentation/frequently-asked-questions/using.html?gi-language=c)  
[mpd protocol](https://www.musicpd.org/doc/html/protocol.html)  

**thoughts on what to do**

- implement base wrapper on top of gstreamer to load, start/stop a playlist
- implement a simple http server to load, start/stop a playlist
- implement a simple MPD server exposing current playback status

## Built With

* [kotlin](https://kotlinlang.org) - For the codez
* [gstreamer](https://gstreamer.freedesktop.org) - Media pipeline
* [Gradle](https://gradle.org/) - Dependency Management
