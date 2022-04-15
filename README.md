# Web_Quiz_Engine

This is Java project built in Spring Boot. 

! All the tests described below can be completed using Postman!  This project all about backend (disclaimer for frontend devs).

1. A user have to register his/her account via "localhost:8889/api/register" (POST) sending raw JSON format "email" and "password".

2. After registration, a user has to pass through basic authentication in order to create, solve, view, delete quizzes.

3. To delete particular quiz, a user has to request "/api/quizzes/id" (DELETE) where id is quizID and be author of that quiz otherwise "FORBIDDEN" error will be thrown.

4. To view all existing quizzes, a user should request "/api/quizzes?page=__" (GET) where page indicates a portion of quizzes sent by the server.

5. To create a quiz, a user sends request "/api/quizzes" (POST) in below JSON format: 
{ 
  "title":"____",
  "text":"____",
  "options": [" __", "___", "   "],
  "answer": [ , ] (optional)
}


