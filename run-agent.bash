
#! /bin/bash
export CLASSPATH=src/:lib/postgresql-42.2.5.jar:lib/json-20180813.jar:lib/jade.jar:classes/;

javac src/*.java -d classes;

java jade.Boot -gui holaAgent:bookseller.HelloWorldAgent;
