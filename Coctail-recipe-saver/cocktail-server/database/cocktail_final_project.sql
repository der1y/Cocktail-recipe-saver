BEGIN TRANSACTION;

DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS cocktails CASCADE;
DROP TABLE IF EXISTS cocktail_book CASCADE;
DROP TABLE IF EXISTS ingredients CASCADE;
DROP TABLE IF EXISTS cocktail_ingredient CASCADE;
DROP TABLE IF EXISTS glass CASCADE;
DROP TABLE IF EXISTS glass_cocktail CASCADE;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	name varchar(50) NOT NULL,
	city varchar(50) NULL,
	state_code char(2) NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE cocktails (
    cocktail_id SERIAL,
    name varchar(50) NOT NULL,
    instructions varchar(500) NOT NULL,
    CONSTRAINT PK_cocktail PRIMARY KEY (cocktail_id)
);

CREATE TABLE cocktail_book (
    cocktail_book_id SERIAL,
    cocktail_id int NOT NULL,
    user_id int NOT NULL,
    is_public boolean,
    CONSTRAINT PK_cocktail_book PRIMARY KEY (cocktail_book_id),
    CONSTRAINT FK_cocktail_book_cocktail FOREIGN KEY (cocktail_id) REFERENCES cocktails(cocktail_id),
    CONSTRAINT FK_cocktail_book_user FOREIGN KEY (user_id) REFERENCES users (user_id)

);

CREATE TABLE ingredients (
    ingredient_id SERIAL,
    name varchar(50) NOT NULL UNIQUE,
    CONSTRAINT PK_ingredient_id PRIMARY KEY (ingredient_id)
);

CREATE TABLE cocktail_ingredient (
    cocktail_ingredient_id SERIAL,
    cocktail_id int NOT NULL,
    ingredient_id int NOT NULL,
    measurement float NOT NULL,
    unit varchar(10) NOT NULL,
    CONSTRAINT PK_cocktail_ingredient PRIMARY KEY (cocktail_ingredient_id),
    CONSTRAINT FK_cocktail_ingredient_cocktail FOREIGN KEY (cocktail_id) REFERENCES cocktails (cocktail_id),
    CONSTRAINT FK_cocktail_ingredient_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredients (ingredient_id)
);

CREATE TABLE glass (
    glass_id SERIAL,
    name varchar(50) NOT NULL UNIQUE,
    CONSTRAINT PK_glass PRIMARY KEY (glass_id)
);

CREATE TABLE glass_cocktail (
    glass_cocktail_id SERIAL,
    glass_id int NOT NULL,
    cocktail_id int NOT NULL,
    CONSTRAINT PK_glass_cocktail_id PRIMARY KEY (glass_cocktail_id),
    CONSTRAINT FK_glass_cocktail_glass FOREIGN KEY (glass_id) REFERENCES glass (glass_id),
    CONSTRAINT FK_glass_cocktail_cocktail FOREIGN KEY (cocktail_id) REFERENCES cocktails (cocktail_id)
);

INSERT INTO users (username,password_hash,role, name, city, state_code) VALUES
    ('user', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER',  'Jack O''Lantern', 'Cleveland', 'OH'), --1
    ('admin', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_ADMIN', 'Jill O''Lantern', 'Beverly Hills', 'CA'), --2
    ('kelly73', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER', 'Kelly Jordan Lopez', 'Indianapolis', 'IN'); --3

INSERT INTO cocktails (name, instructions) VALUES
    ('Old Fashioned', 'Add ingredients to a mixing glass and stir down with ice for 10 seconds and then pour into glass with a big cube'),--1
    ('Gin and Tonic', 'Fill glass with ice and add the gin first ant then top with tonic.'), --2
    ('Paper Plane', 'Add ingredients to a shaker and shake with ice. Double strain into the glass.'), --3
    ('Gimlet', 'Add ingredients to a shaker and shake with ice. Double strain into the glass.'), --4
    ('Margarita', 'Rim the glass with salt. Add ingredients to a shaker and shake with ice. Dirty dump into the glass.'), --5
    ('Daiquiri', 'Add ingredients to a shaker and shake with ice. Double strain into the glass.'); --6

INSERT INTO cocktail_book (cocktail_id, user_id, is_public) VALUES
    (1, 1, true),
    (2, 1, true),
    (3, 1, false),
    (4, 2, true),
    (5, 2, false),
    (6, 3, true);

INSERT INTO ingredients (name) VALUES
    ('Lemon juice'), --1
    ('Lime juice'), --2
    ('Angostura Bitters'), --3
    ('Orange Bitters'), --4
    ('Whiskey'), --5
    ('Gin'), --6
    ('2:1 Demarara sugar'), --7
    ('Tonic'), --8
    ('Amaro Nonino'), --9
    ('Aperol'), --10
    ('Rye'), --11
    ('Tequila'), --12
    ('Agave syrup'), --13
    ('Cointreau'), --14
    ('Simple syrup'), --15
    ('Rum'); --16

INSERT INTO cocktail_ingredient (cocktail_id, ingredient_id, measurement, unit) VALUES
    (1, 3, 4, 'dash(s)'),
    (1, 4, 2, 'dash(s)'),
    (1, 5, 2, 'oz'),
    (1, 7, 0.25, 'oz'),
    (2, 6, 2, 'oz'),
    (2, 8, 7, 'oz'),
    (3, 1, 0.75, 'oz'),
    (3, 9, 0.75, 'oz'),
    (3, 10, 0.75, 'oz'),
    (3, 11, 0.75, 'oz'),
    (4, 2, 0.75, 'oz'),
    (4, 6, 2, 'oz'),
    (4, 15, 0.75, 'oz'),
    (5, 2, 1, 'oz'),
    (5, 12, 2, 'oz'),
    (5, 13, 0.5, 'oz'),
    (5, 14, 0.5, 'oz'),
    (6, 2, 0.75, 'oz'),
    (6, 15, 0.75, 'oz'),
    (6, 16, 2, 'oz');

INSERT INTO glass (name) VALUES
    ('Rocks glass'), --1
    ('Nick and Nora'), --2
    ('Coupe'), --3
    ('Collins'), --4
    ('Hurricane Glass'), --5
    ('Wine glass'), --6
    ('Martini'); --7

INSERT INTO glass_cocktail (glass_id, cocktail_id) VALUES
    (1, 1),
    (4, 2),
    (2, 3),
    (3, 4),
    (1, 5),
    (3, 6);

COMMIT TRANSACTION;