English/(Portugues)[https://gitlab.com/gabepk.ape/kalaha/edit/master/README.pt.md]

# Kalaha

This is a simple [game](https://gabepk-kalaha.herokuapp.com/) that I've developed in Java to a company's hiring process.

# Flow diagram of the game

![Flow Diagram](https://raw.githubusercontent.com/gabepk/kalaha/master/kalaha/WebContent/resources/img/flow-diagram.png?raw=true "Flow Diagram")

# Why I didn't use Spring?

It's a simple project that doesn't need to be build upon a huge framework. Frameworks are a big chunk of pre-built code which you add 
to your own code to solve problems. A few years ago (~1999), when programing in Java for the web was complex, we needed frameworks to 
make our apps cleaner, faster and easy to maintain.

Examples of frameworks:

* JSF (v. 1.0 - 2004): A Java specification for building **component-based UI for webapps**. [Some people criticise JSF](https://dzone.com/articles/why-you-should-avoid-jsf) due to the fact that the interface (HTML) is intrincictly connected to the back-end (Bean), and, therefore, the front-end attributes can change the server side life cycle, violating the [separation of concerns](https://en.wikipedia.org/wiki/Separation_of_concerns). Some people defend the framework saying that it is already updated to be statefull (version 2.0+) and to scale well.

* Spring (v. 1.0 - 2003): An application framework that helps you to **connect and combine different components together**. It makes heavy use of annotations to minimize the amount of configuration in XML files. But this can make the code [harder to debug and maintain](https://www.quora.com/What-are-some-criticisms-of-the-Spring-Framework), pointed out by some programmers that this framework is pure [magic](http://samatkinson.com/why-i-hate-spring/) (BTW, the author doesn't like magic and neither do I). Overall, Spring work well with mocking libs due to the dependency injection concept, so testing is relatively simple.

# Statefull vs Stateless

This is the architecture difference between a stateful and a stateless application:

* A StateFUL protocol is a communication protocol in which the session information of every client is maintained on the server side; example: JSF
* StateLESS is the opposite: the session can be stored, but on the client side. 

This webapp is stateful since the state of the game is stored on the session of the user.

# What is REST

REST (Representational State Transfer) is an **architectural design** with [6 contrains](https://restfulapi.net/rest-architectural-constraints/#uniform-interface) for creating a web service:
1. Client-Server architecture: separation of concerns to increase scalability (server doesn't keep clients contexts) and portability (flexible front-end).
2. Stateless: No client context is stored on the server between requests. Session state is held in the client (usually in the cookies)
3. Cacheability: Response should be cacheable for performance improvement on the client side, like elimination of some client-server interactions
4. Layered system: You can deploy the API on server A, store data on server B. authenticate on server C, etc.
5. Uniform interface: It simplifies and decouples the architecture, which enables each part to evolve independently
6. Code on demand (optional): Servers can temporarily extend or customize the functionality of a client by transferring executable code.

A RESTFull is a web server that implements the REST Architecture.
Altough this game uses a JSON-based Web Service, it is not REST, since it's not stateless.
After this project, I realized I've never used REST architecture. In fact, my client-server app always looked 
[like this](https://fernandofranzini.wordpress.com/2015/10/20/gerenciando-http-session-em-rest-com-jax-rs/)


# What problem REST solves?

Mainly scalability and portability.

# JAX-RS (Jersey) vs Spring

* Jersey is just a library to map endpoints and Spring is a full framework (BTW, [they can be used together](https://dzone.com/articles/lets-compare-jax-rs-vs-spring-for-rest-endpoints)).
* Jersey Tests run a separate container and unit tests make calls to the running instance of your REST resource. Spring MVC tests do not require any containers - and are more well integrated with its controllers. Dependency Injection can be used to inject mock services / DAOs to have better unit tests. [Source](https://stackoverflow.com/questions/26824423/what-is-the-difference-among-spring-rest-service-and-jersey-rest-service-and-spr)
* Documentation on Spring projects are more mature when compared to Jersey

In my conclusion, if you plan to integrate any other Spring libraries into your application, then go with Spring. 
If it's just a simple RESTFul API, go with jersey.

# What's the difference between REST and Queue?

REST is "async" (actually it is just a non-blocking-io-multithread environment; the async behavior depends on the client) and the queue is sync (first in, first out).

# Why the Utils class?

To separate the game algorithm from the request/response manegment. This architecture is better for applying unit tests.

# Javascript framework vs JSF?

JSF App is good for teams with lack of front-end designers because it has a lot of pre-build UI components, 
but it doesn't scale as much as RESTFul APIs that uses only JS + some framework.

In my case, I know enough about both front-end and back-end to build an app in JS + Vue.js and Java.

# What could I improve?

* Add instructions on the interface
* Build 100% REST architecture (remove /getGameInSession, etc)
* Interface testing
* ~~Publish online~~