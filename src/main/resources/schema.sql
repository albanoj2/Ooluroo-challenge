create table Albums (
	id INT NOT NULL auto_increment,
	artist VARCHAR(20) default NULL,
	title VARCHAR(20) default NULL,
	PRIMARY KEY (id)
);

create table Songs (
	id INT NOT NULL auto_increment,
	title VARCHAR(20) default NULL,
	lengthInSeconds INT default NULL,
	idx INT NOT NULL,
	albumId INT NOT NULL,
	PRIMARY KEY (id)
);