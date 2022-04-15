# Web_Quiz_Engine

This is Java project built in <strong>Spring Boot</strong>. 

! All the tests described below can be completed using <strong>Postman</strong>!  This project all about backend (disclaimer for frontend devs).

1. A user have to register his/her account via <i>"localhost:8889/api/register"</i> (POST) sending raw JSON format "email" and "password".

2. After registration, a user has to pass through basic authentication in order to create, solve, view, delete quizzes.

3. To delete particular quiz, a user has to request <i>"/api/quizzes/id"</i> (DELETE) where id is quizID and be author of that quiz otherwise "FORBIDDEN" error will be thrown.

4. To view all existing quizzes, a user should request <i>"/api/quizzes?page=__"</i> (GET) where page indicates a portion of quizzes sent by the server.

5. To create a quiz, a user sends request <i>"/api/quizzes"</i> (POST) in below JSON format: </br>
{ </br>
  "title":"____", </br>
  "text":"____", </br>
  "options": [" __", "___", "   "], </br>
  "answer": [ ,... ] (optional) </br>
}
