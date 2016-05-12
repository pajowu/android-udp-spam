# UDP Spammer

A simple android-app for testing udp-server.

## Install

To build and install simply run
```
./gradlew installDebug
```

### Compile only

To compile simply run
```
./gradlew build
```

## Usage

Input your data in the fields. The order is:
- IP-Address/Hostname
- Port
- Packet size (Values over 1024 don't work reliably)
- Packets per second

Then just press "Start spamming" and the packets start to flow. The button should look pressed all the time. If the button goes back into "unpressed" mode, something failed when sending the packets. All exceptions are logged. To stop the packages from being send, just close the app.

## Authors

* **Karl Engelhardr** - *Inital version* - [pajowu](https://github.com/pajowu)