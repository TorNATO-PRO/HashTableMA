#
# Makefile (see GNU make) for a small Java program
#
# Contributors:
#  Aaron S. Crandall <acrandal@wsu.edu>, 2019
#
# Copyright:
#   For academic use only under the Creative Commons
#   Attribution-NonCommercial-NoDerivatives 4.0 International License
#   http://creativecommons.org/licenses/by-nc-nd/4.0
#
# These tools are normally replaced with Ant, Maven, and Gradle for Java
#  Crandall is just still learning these systems so we're using make as
#  a hack solution for now, but make is widely used in other languages
#  as a build and system control tool so it's worth seeing


# Use an environment variable to store the name of the main .java file to build
# This allows us to choose the file expected by the 'test' target here
MAIN_SRC_FILE=MA_HashTable_Add_Remove_main

# The first target (string:) in the file is the 'default'
# This default just does the 'build' target and returns
default: build

# Build builds all of the java files in the directory - brute force 
build:
	javac -cp ./lib/junit4.jar *.java

# Run first build, then run the main program
#  if it did anything other than test
run: build
	java $(MAIN_SRC_FILE)

# Test targets first run 'build', then run tests on the program as needed
test: build
	#java -cp ./lib/junit4.jar:. org.junit.runner.JUnitCore $(MAIN_SRC_FILE)
	java -cp ./lib/junit4.jar:. $(MAIN_SRC_FILE)

# Clean targets remove temporary files, such as .class files
clean:
	rm *.class

# If you install telnet (In Windows tools or apt install telnet) you can watch
#  StarWars in the terminal
starwars:
	telnet towel.blinkenlights.nl
