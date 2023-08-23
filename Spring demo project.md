# Subtasks

1. Setup Spring startup
    * For Spring look into https://zetcode.com/spring/jetty/?utm_content=cmp-true and use of Maven jetty plugin for
      deployment
    * For Spring Boot look into Maven Spring Boot plugin
    * Use https://www.baeldung.com/running-setup-logic-on-startup-in-spring to setup displaying "Hello, world!" on
      application startup (i. e. when Spring context is successfully initialized)
    * Completion criteria: app starts and after Spring context is initialized "Hello, world!" is displayed
2. Setup simple Spring controller
    * It should be a rest controller, which processes GET requests for the  "/demo" path.
    * It should return JSON object, that looks like:
      ```json
      {
          "greeting": "Hello, world"
      }
   ```
   * JSON object should be represented by separate DTO(Data Transfer Object) class in code
   * Completion criteria: after starting the app, navigating in browser to "http://localhost:8080/demo" displays the JSON response
3. Setup Spring service
    * Create new DTO class, that should render into JSON, like:
   ```json
   {
         "answer": true
   }
   ```
    * Create interface with single method "getAnswer", which should have no arguments and return the new DTO.
    * Instead of returning fixing DTO in controller, make controller rely on this interface, which should be autowired
      into controller
    * Create interface implementation and make it Spring service. This service should implement the method by randomly (
      50/50) generating true or false value and returning it in DTO.
    * Completion criteria: after starting the app, navigating in browser to "http://localhost:8080/demo" displays the
      JSON response, which has randomly "answer" value of true or false ( should randomly change on page refresh)
4. Add processing of request body
    * Add new method in controller, that processes POST requests to the same "/demo" path
    * Add new two new DTOs, one for request, that has JSON format of:
   ```json
   {
         "question": "Would i complete this course?"
   }
   ```
    * And one for response, that has JSON format of:
   ```json
   {
         "question": "Would i complete this course?",
         "answer": true
   }
   ```
    * Add new method for your service (both interface and implementation), that takes request DTO as argument and
      generates response DTO (by copying question from request and generating random answer)
    * Create new method in controller, that accepts POST requests to the same "/demo" endpoint. Make it accept request
      DTO as POST request body, and use new service method to generate response
    * Make it return HTTP code 400, if request DTO is missing, or question in it is empty string
    * Use IDEA scratches or https://www.postman.com/product/rest-client/ to test your new endpoint. (as browser can not
      easily send POST requests)
    * Completion criteria: after starting the app, sending POST request without body results in 400 and with body
      containing question, results in 200 with answer.

5. Expand your API and make it RESTful.
    * Add to your response DTO additional field ID, which is either long value (1, 2,.... 123456,.....1000000001, etc)
      or string GUID (https://stackoverflow.com/questions/2982748/create-a-guid-uuid-in-java). It should look like:
   ```json 
   { 
   "id": 42,
   "question": "Would i complete this course?", 
   "answer": true 
   }
   ```
    * Make your service save questions and their responses inside itself (Using HashMap or similar data structure, no DB
      needed on this stage).
    * Create GET /demo/{id} endpoint (use https://www.baeldung.com/spring-requestparam-vs-pathvariable to learn about
      URL path parameters). This endpoint should return response with that ID it exists or HTTP 404, if it doesn't. Make
      sure that it is idempotent endpoint (if you call it multiple time result is always the same)
    * Create DELETE /demo/{id} endpoint. This endpoint should remove saved question+response for specific id.
      Update POST /demo endpoint. If question send to it is new it returns new response with HTTP 201 (created) and
      response is saved in service. If on the other hand question was already asked you can either a) Return existing
      response with 302 (HTTP Found) or b) Error with 400 (HTTP Bad Request). In either way it should NOT return 201 as
      POST endpoints are NOT idempotent.
    * Finally to complete CRUD (Create, Read, Update, Delete) set of operation, we only need update one. Create PUT
      /demo/{id} endpoint, that accepts request body in form of: json { "answer": false }
      This endpoint would return 404 if question with such id is not saved. If question exists, it would replaced saved
      answer with new one, that was passed in request body and return 200.
    * Use IDEA scratches or https://www.postman.com/product/rest-client/ to test your new API (all 4 endpoints in
      order).
    * Completion criteria: after starting the app, you could create new question via POST, read it via GET (and
      answer would be still same), change answer via PUT and read again (answer should be as per PUT body) and finally
      remove it via DELETE and new GET would return 404.