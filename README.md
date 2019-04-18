Products Repository

Run command:

$ java -jar target/truboapp-0.0.1-SNAPSHOT.jar

CRUD Methods:

base url: http://localhost:8080

++ Get Product By Id ++ 
$ GET: /products/{id}

++ Create Product ++ 
$ POST: /products
request body: 
{
	"id": String
	"displayName": String,
	"description": String
}

++ Edit Product ++ 
$ PUT: /products
request body: 
{
	"id": String
	"displayName": String,
	"description": String
}

++ Delete Product ++ 
$ DELETE: /products
request body: 
{
	"id": String
	"displayName": String,
	"description": String
}