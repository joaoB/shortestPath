# Curve - ShortestPath Challenge!

A REST API which finds shortest contribution path between two users based on which repositories they contributed.

## Getting Started

To run this demo using sbt:

 * `git clone` this repository
 * Update the MySQL server url, username and password in `conf/application.conf` (default is root:root)
 * Create a `curve` database on your MySQL server.

```mysql
    CREATE DATABASE curve;
```

 * Launch the demo using `activator run`
 * Open the Play web server at <http://localhost:9000>
 * You should be prompted to apply the evolution script. Apply the script.
 * You should now see the app running.
 
## Populate Data

The following script generates some dummie
```mysql
    INSERT INTO contributor VALUES (1, 'user1');
	INSERT INTO contributor VALUES (2, 'user2');
	INSERT INTO contributor VALUES (3, 'user3');
	INSERT INTO contributor VALUES (4, 'user4');
	  
	INSERT INTO repo VALUES (1, 'repo1');
	INSERT INTO repo VALUES (2, 'repo2');
	INSERT INTO repo VALUES (3, 'repo3');
	INSERT INTO repo VALUES (4, 'repo4');
	
	
	INSERT INTO contributor_has_repo VALUES ('user1', 'repo1');
	INSERT INTO contributor_has_repo VALUES ('user1', 'repo2');
	
	INSERT INTO contributor_has_repo VALUES ('user2', 'repo1');
	INSERT INTO contributor_has_repo VALUES ('user2', 'repo3');
	
	INSERT INTO contributor_has_repo VALUES ('user3', 'repo1');
	INSERT INTO contributor_has_repo VALUES ('user3', 'repo2');
	INSERT INTO contributor_has_repo VALUES ('user3', 'repo3');
	INSERT INTO contributor_has_repo VALUES ('user3', 'repo4');
	
	INSERT INTO contributor_has_repo VALUES ('user4', 'repo4');
``` 

 * Make a GET request to <http://localhost:9000/shortestPath/user1/user4> to see results