#! /bin/bash
export CLASSPATH=src/:lib/postgresql-42.2.5.jar:lib/json-20180813.jar:classes/

# javac src/BookSellerPostgres.java bin/*
rm -rf classes/*
javac src/Main.java src/BookSellerPostgres.java src/BookSellerJson.java src/BookSellerCsv.java src/Book.java -d classes/

java bookseller.Main
