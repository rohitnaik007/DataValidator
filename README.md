# Data Validator

Project parses a csv file and finds potential duplicates and non duplicates

The project is divided into two parts :

  - Server Application (Spring Boot back-end)
  - Client Application (React front-end)
 
Please install both the applications using below instructions:
# Server Application

  - Clone repository
  - Import the server project in an IDE like eclipse
  - Run the application on port 8080
  
You can run a get request as follows :

> http://localhost:8080/api/dataAdvanced 
> (To get validated data from advanced.csv)

> http://localhost:8080/api/data 
> (To get validated data from normal.csv)


# Client Application
##### Note : make sure you run the server first
 - In the client project directory, you can run:

```sh
npm install
```

 - Then you can run:

```sh
npm start
```
 - Runs the app in the development mode
- Open http://localhost:3000 to view it in the browser.

### About the project 

Logic for Advanced parsing of CSV File (Works well with both normal.csv and advanced.csv) :

| Steps | Details |
| ------ | ------ |
| Data Load | Loads data from advanced.csv |
| Data Cleaner | Cleans data like removing extra spaces, removing special characters etc. |
| Scoring By help of Algorithms | Uses Metaphone and Levenshtein algorithm to score based on the string matching |
| Valid if score is above acceptance | Two Strings match if they are above acceptance limit |
| Add the data to List | If matching rate between two records is above limit ,e.g. Score is 0.9 and limit is 0.8, then data matches, then add both records in duplicate data list |
| Return | Returning as separate lists of duplicate and non-duplicate data |

Logic for Normal parsing of CSV File (Works with only normal.csv, partially with advanced.csv if data is sorted) :

| Steps | Details |
| ------ | ------ |
| Data Load | Loads data from normal.csv |
| Levenshtein Score | Each record consists of many sub-records which are matched with another records sub-records, Levenshtein matching gives a value |
| Checking Limit | If the value is above a predefined score, skip entire record match else add to duplicates |
| Return | Returning as separate lists of duplicate and non-duplicate data |

This method needs to have each score value below limit to work. Also it only compares current record with next record. So if duplicates are not continuous, it fails.

Final validated output in result folder
