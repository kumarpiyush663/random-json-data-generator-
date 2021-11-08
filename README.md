# random-json-data-generator 

random-json-data-generator helps you build json data that you need for test data purposes.   It has a lot of nice features that you can use to build as much test data as you need. Also the generated json is finally inserted into MongoDB instance.

## Dependency

Add below dependencies to `extLib` and configure these jars to build path. 

```
java-json.jar
mongodb-driver-core-3.11.2.jar
mongo-java-driver-3.11.2.jar
```

## Requirements
- JDK 1.8 or higher

## Running it from Java

```

```

## Running it as a standalone jar

```

```
### Properties
Add properties to `Resource/Configuration/Properties.config` file.

Example

```
MONGO_CLIENT_URI=mongodb://admin:changeme@slcxxxxx.us.xyz.com:27019
DATABASE_NAME=MYCOLL_new
COLLECTION_NAME=employee
INSERT_OPERATION_COUNT=200000
DELETE_OPERATION_COUNT=1000
UPDATE_OPERATION_COUNT=100000
UPSERT_OPERATION_COUNT=500
IS_DROP_DB=false
```


### Sample Data Example

```
{
	"_id" : ObjectId("6188ec52d7e465644169f285"),
	"Name" : "HMLKN",
	"EmployeeId" : 656899,
	"Designation" : "KRXJOUH",
	"Level" : "MGR4",
	"Age" : 25,
	"Qualification" : "Phd",
	"Address" : {
		"Street" : "Street_831",
		"City" : "City_231",
		"Nationality" : "Australia"
	},
	"myid" : 19
}
```
## Operations Supported for MongoDB

 - insert
 - delete
 - update
 - upsert

## Available functions

You can do a lot of cool functions in your puesdo json that that help you randomize your test data.

**random alphabetic strings**

```
public String randomNameIdentifier() 
```

**random integer in range**

```
{{int(min,max)}}
```


**objectId (16 byte hex string)**

```
{{objectId()}}
```

**random Document of address**

```
Document randomAddressGenerator(ArrayList<String> nationalityList)
```

**random phone number**

```
{{phone()}}
```

**random nationality**

```
{{nationality()}}
```




# Change Log

## [1.2]

**Improvements:**



## [1.1]First Commit (2021-11-06)

**Improvements:**

- Repeats can be randomized between a range of two integers
- Multiple names can be used
- Created objectId (16 byte hex string) function

**Bugs:**

- Floating point numbers can be parsed and used in fuctions