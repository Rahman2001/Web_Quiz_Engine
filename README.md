# Web_Quiz_Engine

This is Java project built in **Spring Boot** framework. 

## Features of API 
All the tests described below can be completed by using **Postman**!  This project is all about backend (disclaimer for frontend devs).

1. A user have to register his/her account via <code>"localhost:8889/api/register"</code> **(POST)** by sending raw JSON data of "email" and "password".

2. After registration, a user has to pass through basic authentication in order to create, solve, view, delete quizzes.

3. To delete particular quiz, a user has to 
   1. request <code>"/api/quizzes/id"</code> **(DELETE)** where 'id' is <code>quizID</code> 
   2. be author of that quiz otherwise "FORBIDDEN" error will be thrown.

4. To view all existing quizzes, a user should request <code>"/api/quizzes?page=__"</code> **(GET)** where 'page' indicates a page number where some portion of quizzes is sent to that page by the server.

5. To create a quiz, a user sends a request <code>"/api/quizzes"</code> **(POST)** in below JSON format: </br>

### Examples of raw JSON for each API
1. To register as a user:
~~~json
{
  "email":"rahman@gmail.com"
  "password":"computerEngineering"
}
~~~
2. To create a quiz by calling <code>***/api/quizzes***</code>
~~~json
{ 
  "title":"The Java Logo",
  "text":"What is the logo of Java?",
  "options":["Robot", "Cup of tea", "Snake"],
  "answer":[2]  (optional)
}
~~~
3. To delete a quiz by calling <code>***/api/quizzes/id***</code> can be accomplished provided the 'id' is known, for example: <pre><code>**/api/quizzes/2</pre>**</code>

## Resources
A book I read for learning Spring Boot is
* ***Spring in Action (5<sup>th</sup> edition)***

![This is an image of the book taken from amazon.com.tr](https://images-na.ssl-images-amazon.com/images/I/51xkEqwHOxL._SX258_BO1,204,203,200_QL70_ML2_.jpg)
