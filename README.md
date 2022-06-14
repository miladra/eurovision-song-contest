# Vote Api
Java-based backend application that implements eurovision song contest voting system.

## API spec
- POST /api/v1/votes/2022 (create a vote for specific user)
- GET  /api/v1/votes/2022 (get 1st, 2nd and the 3rd place for each year)
- GET /api/v1/votes/2022/Netherlands (get the top three favorite songs for each country)


## Stack
* Spring Boot 2.6.6
* Java 11
* Spring Framework
* Gradle 7.4.2
* postgres:14.2
* Swagger
* Docker and docker-compose
* Lombok
* AspectJ

## Testing
* Junit, Spring Boot, H2database, Mockito

# Thought of Process

- In order to prevent fraud, I add userId and each userId only can vote once time in each year.
- I considered two entities VoteHistory and Vote.
- VoteHistory records each user's vote and prevents double voting of the user for each year.
- Vote entity was used for recording voting results.

- I considered multi songs are able to get the same position, for example, France and Germany get 2nd place at the same time. in this case, the report Is shown as follows.
     ```
        {
          "first": "Netherlands",
          "second": "France,Germany",
          "third": "Estonia"
        }
     ```

Solution
- The first solution that came to my mind was to record each vote in the database. then by getting a request I must get computation the result of the voting every time. but I thought when we have millions of users, it is not efficient,
- so in order to increase performance, I computed the vote for each country and recorded it in the Vote entity during voting time.
- In this case, I needed to read Votes and increase them and write them again, hence In order to increase performance,
- I used Cache (In this case I used caffeine cache because it is simple, in production other tools like Redies will be better because it provides features like clustering and persistence).
- First If data exist in the cache I read it from the cache and update it then write it into the database.
- if data does not exist in the cache I would read it from the database and then update it.
- so in this way, I reduce hit the database when data is read once.
- The application supports 2 language (nl, en) as a query param. ?lang=en OR lang=nl

### initial data

- Liquebase will create the initial list of vote in the dev profile

### Build and run the application standalone

- In order to run tha application you can use local database or you can run postgres by below command

    ```
    docker-compose -f src/main/docker/postgresql.yml up
    
    ```

- Clone the repository

     ```
     git clone https://github.com/miladra/eurovision-song-contest.git
     
     ```

- Clean and Build by Gradle, run:
     ```
     ./gradlew clean build
     
     ```

## Testing

To launch application's tests, run:

```
./gradlew test integrationTest jacocoTestReport
```

## Using Docker to development (optional)

- In root directory

   ```
    ./gradlew clean build

     docker-compose -f app.yml up
   ```

## Postman:

- You can find Postman file in postman folder in root of project, For each API call

## Code quality

- Code quality can be analyzed using SonarQube
- I specific Docker Compose configuration for Sonar

   ```
   docker-compose -f src/main/docker/sonar.yml up -d
    
   ```
- SonarQube default login and password is "admin"
- You need to get token from: in right top of sonarQube panel -> User(Administrator) > My Account > Security > Generate Tokens
- Then you can run below command by maven

   ```
   ./gradlew -Pprod clean check sonarqube -Dsonar.host.url=http://localhost:9001 -Dsonar.login='284677e4f6bf3f0017a209915fb5b6d5a0d84db1'
    
   ```

## API documentation

http://localhost:8080/swagger-ui/index.html

## Development Strategy

- I have followed the Git Flow that is called Release branches , I borrowed it from [link](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow#Release%20branches)

![Git Flow](https://wac-cdn.atlassian.com/dam/jcr:8f00f1a4-ef2d-498a-a2c6-8020bb97902f/03%20Release%20branches.svg)

- Using a dedicated branch to prepare releases makes it possible for one team to polish the current release while another team continues working on features for the next release
- **I have left open the Pull Requests from branches for simplicity**
### Future improvements
* Upgrade to Java 16
* Add Integration tests with continerized database, Architecture tests, End to End tests
* Add a security layer such as JWT
* Consider concurrency topics. adding versioning to handle optimistic locking
* Create deployment pipelines to build and deploy the artifact into different environments

###### By: Milad Ranjbari
