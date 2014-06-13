#!/bin/sh
rm -f *.class *.jar
javac -sourcepath src -d bin src/ChatRoomGUI.java
javac -sourcepath src -d bin src/ChatGUI.java
jar -cfm BBChat.jar MainClass resources -C bin/ .