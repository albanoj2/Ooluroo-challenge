CREATE TABLE Albums (
	id INT NOT NULL auto_increment,
	artist VARCHAR(255) default NULL,
	title VARCHAR(255) default NULL,
	PRIMARY KEY (id)
);

CREATE TABLE Songs (
	id INT NOT NULL auto_increment,
	title VARCHAR(255) default NULL,
	lengthInSeconds INT default NULL,
	albumId INT NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (albumId) REFERENCES Albums(id)
);