CREATE TABLE IF NOT EXISTS PUBLIC.USERS (
	USER_ID INTEGER NOT NULL AUTO_INCREMENT,
	EMAIL varchar(100) NOT NULL,
	LOGIN varchar(100) NOT NULL,
	NAME varchar(100) NOT NULL,
	BIRTHDAY DATE NOT NULL,
	CONSTRAINT USERS_PK PRIMARY KEY (USER_ID)
);
CREATE TABLE IF NOT EXISTS PUBLIC.FRIENDS_LIST (
	USER_ID INTEGER NOT NULL,
	FRIEND_ID INTEGER NOT NULL,
	CONFIRMATION BOOLEAN NOT NULL,
	CONSTRAINT FRIENDS_LIST_PK PRIMARY KEY (USER_ID,FRIEND_ID),
	CONSTRAINT USER_ID_FK FOREIGN KEY (USER_ID) REFERENCES PUBLIC.USERS(USER_ID) ON DELETE CASCADE,
	CONSTRAINT FRIENDS_ID_FK FOREIGN KEY (FRIEND_ID) REFERENCES PUBLIC.USERS(USER_ID) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS PUBLIC.FILMS (
	FILM_ID INTEGER NOT NULL AUTO_INCREMENT,
	NAME varchar(100) NOT NULL,
	DESCRIPTION varchar(500),
	RELEASE_DATE DATE NOT NULL,
	DURATION INTEGER NOT NULL,
	MPA_ID INTEGER,
	CONSTRAINT FILMS_PK PRIMARY KEY (FILM_ID)
);
CREATE TABLE IF NOT EXISTS PUBLIC.LIKES_LIST (
	USER_ID INTEGER NOT NULL,
	FILM_ID INTEGER NOT NULL,
	CONSTRAINT LIKES_LIST_PK PRIMARY KEY (USER_ID,FILM_ID),
	CONSTRAINT LIKES_LIST_FK FOREIGN KEY (USER_ID) REFERENCES PUBLIC.USERS(USER_ID),
	CONSTRAINT LIKES_LIST_FK_1 FOREIGN KEY (FILM_ID) REFERENCES PUBLIC.FILMS(FILM_ID)
);
CREATE TABLE IF NOT EXISTS PUBLIC.GENRE (
	GENRE_ID INTEGER NOT NULL AUTO_INCREMENT,
	NAME varchar(50) NOT NULL,
	CONSTRAINT GENRE_PK PRIMARY KEY (GENRE_ID)
);
CREATE TABLE IF NOT EXISTS PUBLIC.FILMS_GENRE (
	FILM_ID INTEGER NOT NULL,
	GENRE_ID INTEGER NOT NULL,
	CONSTRAINT FILMS_GENRE_PK PRIMARY KEY (FILM_ID,GENRE_ID),
	CONSTRAINT FILMS_ID_FK FOREIGN KEY (FILM_ID) REFERENCES PUBLIC.FILMS(FILM_ID),
	CONSTRAINT GENRE_ID_FK FOREIGN KEY (GENRE_ID) REFERENCES PUBLIC.GENRE(GENRE_ID)
);
CREATE TABLE IF NOT EXISTS PUBLIC.MPA (
	MPA_ID INTEGER NOT NULL AUTO_INCREMENT,
	NAME varchar(50) NOT NULL,
	CONSTRAINT MPA_PK PRIMARY KEY (MPA_ID)
);
ALTER TABLE IF EXISTS PUBLIC.FILMS ADD CONSTRAINT IF NOT EXISTS FILMS_FK FOREIGN KEY (MPA_ID) REFERENCES PUBLIC.MPA(MPA_ID);


