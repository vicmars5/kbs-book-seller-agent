
#! /bin/bash
# export CLASSPATH=src/:lib/postgresql-42.2.5.jar:lib/json-20180813.jar:lib/jade.jar:classes/;

PROJECTPATH=/home/victor/projects/kbs-book-seller-agent
export CLASSPATH="$PROJECTPATH/src/:$PROJECTPATH/lib/postgresql-42.2.5.jar:$PROJECTPATH/lib/json-20180813.jar:$PROJECTPATH/lib/jade.jar:$PROJECTPATH/classes/";
javac src/*.java -d classes;

# java jade.Boot -gui holaAgent:bookseller.HelloWorldAgent;
java jade.Boot -gui; # holaAgent:bookseller.BookSellerBarnesNobleCsvAgent;
