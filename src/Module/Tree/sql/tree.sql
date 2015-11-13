CREATE TABLE tree
	(
	id         VARCHAR(16) NOT NULL,
	showName   VARCHAR(200) NULL,
	parentId   INT NOT NULL,
	states     INT NULL DEFAULT 1,
	module     VARCHAR(50) NOT NULL
	)