#! /bin/bash
export CLASSPATH=/home/victor/projects/jade/lib/jade.jar:/home/victor/projects/jade/classes/

# Now it will run the hello world agent
java jade.Boot -gui holaAgent:examples.hello.HelloWorldAgent
