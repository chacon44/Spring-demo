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
    * Use IA scratches or https://www.postman.com/product/rest-client/ to test your new endpoint. (as browser can not
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

6. Add exceptions and exception handling to your project:

    * Create exception class in your project, that extends RuntimeException. Give it two fields: String description (you
      can use parent class fields for that) and ErrorCode code, where ErrorCode is new enum.
    * Create ErrorCode enum with (for now) single value: OUT_OF_IDS. You can add you own error codes for different
      situations, if you have any ideas for them
    * In IdManagementService add a condition, that check if current id reached maximum value (for actual logic it
      should Long.MAX_VALUE, but to be able to test it, let's set it for 5 or 10 for now). If maximum value is reached
      instead of returning the id, method should throw your new exception with error code OUT_OF_IDS.
    * Try it in practice and see, how Spring would convert exception into response (probably with HTTP 500), when it
      happens
    * Look into https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
      and https://reflectoring.io/spring-boot-exception-handling/
    ##* You would need to create @ControllerAdvice class, that would work with RestController and would handle your
      exception instead of default Spring handler
    ##* Create new DTO ErrorResponse. It should contains same two fields as your exception, but both of type string:

      ```json
      {
      "code": "OUT_OF_IDS",
      "description": "Unable to generate new ID, maximum value: 10 was reached"
      }
      ```

    * Create @ExceptionHandler method in your @ControllerAdvice class, that would convert your exception into 
    ErrorResponse and return ResponseEntity with HTTP 500 and ErrorResponse as a body.
    //* Next step is to expand your handling capabilities by using ResponseEntityExceptionHandler as a base for your
      @ConrtollerAdvice class. Remember, that ResponseEntityExceptionHandler already declares handlers, so instead
      of redeclaring them you override its base methods.
    * Create new ErrorCode in enum: "WRONG_HTTP_METHOD".
    * Override ResponseEntityExceptionHandler method to return ErrorResponse with this new code as a body with HTTP
      405, when user tries to send wrong type of HTTP request method (i. e. do POST /demo/{id} for example)

7. Add logging to your application:

   * Go through: **https://www.baeldung.com/slf4j-with-log4j2-logback** and setup logging with SL4J and logback in your
     application
   * Test it with some logs to console.
   * You can look into Lombok and https://projectlombok.org/features/log to use @SL4J annotation to autogenerate
     logger in your classes.
   * Look into https://www.baeldung.com/spring-yaml and https://reflectoring.io/spring-boot-profiles/ to see
     different ways to configure Spring profiles via .yml (in single file or in multiple .yml files). You would need two
     profile going forward: "dev" and "prod" (development and production. Try to configure IDEA run configuration to start
     your app in either of them (active profile should be listed in Spring logs during start).
   * Look into: https://howtodoinjava.com/spring-boot/configure-logging-application-yml/ on how to configure
     logging via .yml files. This article refers to Spring Boot logging, which relies on logback. That's why we. set our
     logging through it at the beggining.
   * Setup logging of INFO and higher logs for prod profile and DEBUG and higher logs for dev profile. You can add
     other ways to differentiate them (or set logs for different packages, if Spring start to spam too much logs in
     full debug).
   * Add your own logs over the project. You need to log each incoming request with INFO, each error with ERROR (
     errors are those, that result in response with HTTP code different from 2XX) and also add DEBUG logs everywhere they
     make sense (like id generation, saved questions search, etc).
   * Test your application via Postman in each of profiles and confirm, that logs are working correctly and you
     only see debug logs, when dev profile is enabled

* Subtask 8: Unit testing and mocking:
  * Purview: https://www.baeldung.com/junit-5, https://www.baeldung.com/mockito-series and https://www.baeldung.com/mockito-junit-5-extension and ask questions
  * Create test classes ( You can use Ctrl-Shift-T to ask IDEA to create a test class for currently opened class: https://www.jetbrains.com/idea/guide/tips/generate-new-test/) for following classes: ControllerAdviceClass, IdManagementService, QuestionManagementService, RandomAnswerService
  * Not all tests would require Mockito and mocks. For example IdManagementService do not take any arguments or dependencies and can be tested on pure Junit. For it you would need to test both successful id increment and reach of IDs limit and confirm it behavior in both cases.
  * For RandomAnswerService you may either think of a strategy to test random responses OR make Random random be passed in via @Autowire. (For that to work you would need to provide it in @Configuration via @Bean). Than in test you could use @Mock and @InjectMocks to create RandomAnswerService depending on mocked Random and use it for tests.
  * For ControllerAdviceClass you would use mock to mock arguments as creating WebRequest is not a trivial task, that takes a lot of code. Instead just use @Mock of WebRequest when calling your methods in test. You would need to test both of your handler methods and confirm, that they return correct responses.
  * Finally, for QuestionManagementService you would need a more complex tests, that cover various scenarios of use: save and get saved, get nonsaved, save, get, remove and get again, remove nonsaved, save and search for question, search for nonsaved question, save same question multiple times.

* Subtask 9: Integration tests with Spring.
  * Did you notice, how we skipped RestController during previous task? It is because HTTP interactions are too complex for unit tests (since they go through dispatcher serlvet and jackson mappers, etc). We can just write unit tests for controller as well, but we would miss any error related to HTTP part, like wrong HTTP methods, wrong DTO types, etc.
  * Look into https://www.baeldung.com/integration-testing-in-spring. You could also consult ChatGPT on specific of Spring Web testing with pure Spring (as most sources on the web would guide you to Spring Boot approach)
  * To write actual tests, you would need to use both Mockito and Spring Test together. You would mock ALL the services in your controller and than write test for each scenario. You would need to call endpoints in different tests with different mock configurations to test every pathway. For example, for postQuestion you would need four tests: question is null, question is empty, matchedQuestion found (configure questionManagementService mock to return full Optional for that), matchedQuestion not found (configure mock to return empty Optional for that).
  * Cover all scenarios to complete the task. You may want to create separate document, that lists all the scenarios in plain english/spanish to not miss anything.