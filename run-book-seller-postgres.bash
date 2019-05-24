#! /bin/bash
export CLASSPATH=src/:lib/postgresql-42.2.5.jar:lib/json-20180813.jar:classes/

# javac src/BookSellerPostgres.java bin/*
# rm -rf bin/*
javac src/*.java -d classes/
java bookseller.Main
