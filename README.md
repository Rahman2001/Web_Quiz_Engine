# Web_Quiz_Engine

This is Java project built on Spring Boot. 

1.A user have to register his/her account via "localhost:8889/api/register" (POST) sending raw JSON format "email" and "password".
\n2. After registration, a user has to pass through basic authentication in order to create, solve, view, delete quizzes.
\n3. To delete particular quiz, a user has to request "/api/quizzes/id" where id is quizID (DELETE) and be author of that quiz otherwise "FORBIDDEN" error will be thrown. and.
\n4. To view all existing quizzes, a user should request "/api/quizzes?page=__" (GET) where page indicates a portion of sent quizzes by the server.
\n5. To create a quiz, a user sends request "/api/quizzes" (POST) in below JSON format: 
\n{ 
  "title":"____",
  "text":"____",
  "options": [" __", "___", "   "],
  "answer": [ , ] (optional)
}


