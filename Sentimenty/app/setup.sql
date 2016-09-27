DROP TABLE Users IF EXISTS;
CREATE TABLE Users (
    id int(11) unsigned NOT NULL AUTO_INCREMENT,
    username varchar(30) NOT NULL,
    default_view varchar(40) NOT NULL DEFAULT 'employee',
    PRIMARY KEY ('id')
);

DROP TABLE Views IF EXISTS;
CREATE TABLE Views (
    id int(11) unsigned NOT NULL AUTO_INCREMENT,
    userID int(11) unsigned NOT NULL,
    name varchar (30) NOT NULL,
    column_one varchar(40),
    column_two varchar(40),
    column_three varchar(40),
    PRIMARY KEY (id),
    FOREIGN KEY (userid) REFERENCES views(id)
);

DROP TABLE Modules IF EXISTS;
CREATE TABLE Modules (
    id int(11) unsigned NOT NULL AUTO_INCREMENT,
    title varchar (30) NOT NULL,
    api_endpoint varchar(30) NOT NULL
);

--DROP TABLE StockHistory IF EXISTS;
--CREATE TABLE StockHistory (
--    dateStock varchar(10) NOT NULL,
--    closePrice decimal(5,2) NOT NULL
--);


INSERT INTO users (username, default_view) VALUES ('potato', 'employee');
INSERT INTO users (username, default_view) VALUES ('bob', 'employee');
INSERT INTO users (username, default_view) VALUES ('dick', 'employee');

INSERT INTO views (name, userid, column_one, column_two, column_three) VALUES ('brotato', 1, '','','');
INSERT INTO views (name, userid, column_one, column_two, column_three) VALUES ('broato', 1, '','','');
INSERT INTO views (name, userid, column_one, column_two, column_three) VALUES ('brtato', 1, '','','');
INSERT INTO views (name, userid, column_one, column_two, column_three) VALUES ('brotao', 1, '','','');
INSERT INTO views (name, userid, column_one, column_two, column_three) VALUES ('broto', 2, '','','');
INSERT INTO views (name, userid, column_one, column_two, column_three) VALUES ('broto', 2, '','','');

--BULK INSERT StockHistory FROM './dbHistoricalParsed.csv' WITH ( FIRSTROW=0, FIELDTERMINATOR = ',', ROWTERMINATOR = '\n', TABLOCK);
--LOAD DATA LOCAL INFILE 'dbHistoricalParsed.csv' INTO TABLE 'StockHistory' FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' ()