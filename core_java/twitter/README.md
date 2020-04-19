# TwitterCLI App 

## Introduction
TwitterCLI App aims to post, search, and delete Twitter posts by Twitter REST API. By completing this app, I get familiar with REST API, Twitter API, HTTP Client, JSON serializer/deserializer, JUnit & Mockito(testing) libraries, DTO, DAO, CRUD, MVC design patterns, and Spring Ioc, etc.

## MVC architecture
This TwitterCLI app is based on MVC architecture, and it consists of the following components:

+ Models:\
Models are implemented with POJOs and encapsulates Tweet Objects. Tweet Object consists of 5 classes:
Coordinates, Entities, Hashtag, Tweet, UserMention. For example,
```{
   "created_at":"Sun April 19 21:24:09 +0000 2020",
   "id":1097607853342564556,
   "id_str":"1097607853342564556",
   "text":"Hello Twitter!",
   "entities":{
      "hashtags":[],      
      "user_mentions":[]  
   },
   "coordinates":null,    
   "retweet_count":0,
   "favorite_count":0,
   "favorited":false,
   "retweeted":false
}
```
+ Controller Layer: It interacts and parases user input (CLI args in this APP). It also calls service layer and return tweet.

+ Service Layer: It handles business logic and calls DAO layer and return tweet. If the tweet text is longer than 140 characters, and if longitude or latitude is out of range, errors will show up.

+ Data Access Layer(DAL/DA)): It handles models, and post, show and delete tweets. This layer calls HttpHelper class and return results. HttpHelper is responsible for executing HTTP with URI, and authorizes the HTTP request using Twitter secrets.

![image](./asset/diagram.png)
Class Dependency Diagram
![image](./asset/TwitterDao.png)
UML Diagram

## Instruction
+ To post Twitter: `post "tweet_text" "latitude:longitude", e.g. post "Hello Twitter!" "20:20"`
+ To show Twitter: `show "tweet_id" "field1, field2", e.g. show "1251952635629928448" "text,coordinates"`
+ To delete Twitter: `delete "id", e.g. delete "1251952635629928448"`

## Improvements
Grep App can add new feature such as counting the frequency of the regex in given directory.\
Grep App can add line numbers to each line to make it more user-friendly.

