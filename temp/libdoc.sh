#!/bin/sh
FULLPATH=`dirname "$0"`/`basename "$0"`
SHPATH=`readlink -f "$FULLPATH"`
BASEPATH=`dirname "$SHPATH"`

TOOLSPATH=$BASEPATH/tools.jar
RFPATH=$BASEPATH/robotframework.jar

CLASSPATH=$TOOLSPATH:$RFPATH
java -cp "$CLASSPATH" org.robotframework.RobotFramework libdoc "$@"
