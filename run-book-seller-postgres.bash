#! /bin/bash
export CLASSPATH=src/:lib/postgresql-42.2.5.jar

javac src/BookSellerPostgres.java bin/*
java src/BookSellerPostgres.java
