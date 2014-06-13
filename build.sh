#!/bin/sh
rm -f *.class *.jar
javac -sourcepath src -d bin src/BriBorChat.java
jar -cfm BriBorChat.jar MainClass resources -C bin/ .