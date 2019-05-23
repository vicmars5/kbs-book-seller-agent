#! /bin/bash
export CLASSPATH=src/:lib/postgresql-42.2.5.jar:lib/json-20180813.jar:bin/

# javac src/BookSellerPostgres.java bin/*
# rm -rf bin/*
javac -d bin/ src/*.java
java bookseller.Main
