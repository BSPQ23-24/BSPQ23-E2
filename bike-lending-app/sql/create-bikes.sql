/* DELETE 'bikesDB' database*/
DROP SCHEMA IF EXISTS bikesdb;
/* DELETE USER 'spq' AT LOCAL SERVER*/
DROP USER IF EXISTS 'spq'@'mysql';

/* CREATE 'messagesDB' DATABASE */
CREATE SCHEMA bikesdb;
/* CREATE THE USER 'spq' AT LOCAL SERVER WITH PASSWORD 'spq' */
CREATE USER IF NOT EXISTS 'spq'@'mysql' IDENTIFIED BY 'spq';

GRANT ALL ON bikesdb.* TO 'spq'@'mysql';
