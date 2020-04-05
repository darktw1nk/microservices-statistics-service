CREATE TABLE companyStatistic (
    id serial PRIMARY KEY,
    companyId INTEGER NOT NULL,
    companyName VARCHAR(255) NOT NULL,
    workers INTEGER NOT NULL,
    money INTEGER NOT NULL
);

CREATE TABLE projectStatistic (
    id serial PRIMARY KEY,
    projectId INTEGER NOT NULL,
    companyName VARCHAR(255) NOT NULL,
    projectName VARCHAR(255) NOT NULL,
    revenue INTEGER NOT NULL
)