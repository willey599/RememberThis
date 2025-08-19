# Remember This
This is my first full-stack application, made as a study tool to help me remember terms during my CS degree.
The project is still very much in development and needs tidying up, but it is currently usable as a "flashcard" app. The concept was to incorporate spatial elements into a note-taking app, almost like a memory palace. I am planning to add more customizable elements and the ability to store BLOBS into the database.

## Technologies Used: 
Languages: Java, Typescript, HTML/CSS, SQL
Backend: Spring Boot 3. Spring Security 6 w/ OAuth2.0, JPA Persistence
Frontend: Angular 20 (Utillizing Signals, CdkDrag, Dialog, Fetch)
DB: MySQL80

## How It Was Made/Features
The front end is a standalone component-based SPA with dynamic elements. Its standalone architecture allows for relatively simple but effective user interactions like cloud population, deletion, editing, and dialog box interactions/form control. 
The main feature of note is Signals, which allows for dynamic listening across all components. Signals allows for seamless database integration, allowing GET, POST, and DELETE requests to alter and respond to the database. The app also keeps track of users, populating their data based on their OIDCUserID, ensuring that users only have access to their own data. The app also keeps track of absolute page location, which allows spatial configurations to persist even after logging out. 

The backend follows a typical MVC architecture with RESTful functionality to control HTTP requests. JPA is used to interact with the database, allowing all database queries to be managed by the Spring framework.

OAuth2.0 via Spring Security allows users to securely access their data. The flow is an Authorization Code Flow, and the user sends an XSRF token as well as a JSESSIONID cookie through the browser for fetch requests.

## Optimizations/Future Features
There are not many optimizations at the moment, other than the baked-in features of Angular 20. The only special technology at the moment is Signals, which completely replaces the need for RxJS observables. In doing this, the code base is far more intuitive, easy to understand, and reactive to changes across the app.

I hope to implement the following features in future updates: 
1) Color and font customizations for texts and clouds
2) BLOB/image uploading functionality
3) Customizable sound effects
4) Game mode functionality for self-testing purposes
5) Asset caching

# I intend to deploy this onto a cloud soon, but for now, here are some screenshots:

![alt text](https://github.com/willey599/RememberThis/tree/main/readme_images/RememberThisScreenshot.png) "Screenshot")
