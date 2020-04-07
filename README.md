# DatabaseAPI

   This API keeps connected to a database. Statements of each connected database can be created by a static method

## Usage


### Create a connection

```java
DBConnection.addConnection("Database url", "username", "password", "alias");
```

### Get a statement

```java
Statement statement = DBConnection.getStatement("alias");
```


### Get a connection

```java
Connection connection = DBConnection.getConnection("alias");
```

### Close a connection
* To close a connection get the connection, close it and don't use it again
