# MusicServer Project

This project is an attempt at developing a music server providing:
- a backend for media library management 
- front applications for accessing the media library and media playback
- a player with:
 - a full [MPD](https://www.musicpd.org) server implementation, therefore compatible with existing MPD clients
 - an HTTP API allowing music playback controls

The server is using the [gstreamer](https://gstreamer.freedesktop.org) framework as the underlying media pipeline.

## [AndroidApp] Getting Started
 TODO

## [Backend] Getting Started
### Prerequisite

The gstreamer library needs to be installed locally if you want to run the local player.
A working java env. is needed as well.

### Building

Build the application with gradle
```bash
./gradlew clean build                                          
```

### Installing

Package the application with gradle
```bash
./gradlew clean installShadowDist                                          
```

## Architecture?

### useful links

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
