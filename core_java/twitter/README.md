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
                                                   
![image](./asset/TwitterDao.png)\
                                                         UML Diagram

## Setup Intellij Environment Variables
There are four environment varialbes to be set up in Intellij, and they are:
+ consumerKey
+ consumerSecret
+ accessToken
+ tokenSecret

You can find this information in logging in twitter developer account, and select `Apps\twitterPipeline\Keys and tokens`,
`consumerKey` value is`API key`, `consumerSecret` is `API secret key`, `accessToken` is `Access token`, `tokenSecret` is `Access token secret` correspondingly.
## Quick Start Instruction
+ To post Twitter: `post "tweet_text" "latitude:longitude", e.g. post "Hello Twitter!" "20.0:20.0"`
+ To show Twitter: `show "tweet_id" "field1, field2", e.g. show "1251952635629928448" "text,coordinates"`
+ To delete Twitter: `delete "id", e.g. delete "1251952635629928448"`

## Improvements
+ Retweet funcion can be added to this app.
+ Images can be posted/shown/deleted by this app.
+ This app can be modified to use on cell phone, or integrate into a mobile app.

