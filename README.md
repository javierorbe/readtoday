# ReadToday

[![Build](https://github.com/javierorbe/readtoday/actions/workflows/build.yml/badge.svg)](https://github.com/javierorbe/readtoday/actions/workflows/build.yml)
[![Codecov](https://img.shields.io/codecov/c/github/javierorbe/readtoday?color=373737&flag=server&label=Coverage%20%7C%20server)](https://app.codecov.io/gh/javierorbe/readtoday)
[![Codecov](https://img.shields.io/codecov/c/github/javierorbe/readtoday?color=373737&flag=client&label=Coverage%20%7C%20client)](https://app.codecov.io/gh/javierorbe/readtoday)

## Configuration

### Server configuration

The configuration variables are loaded from three different sources, in this order
of preference:

1. Environment variables
2. ```.env.local``` file
3. ```.env``` file

The [.env.local](/server/.env.local) and [.env](/server/.env) files contain the
configuration variables needed.

The required Google OAuth credentials can be generated here: [Google Cloud Platform](https://console.cloud.google.com/apis/dashboard).

### Client configuration

Fill the [config.toml](/client/src/main/resources/config.toml) file.

## Building

The first step to compiling is generating the jOOQ sources. It requires the
database to be running, and the schema to be created. The database schema can
be created by running the [schema.sql](/server/src/main/sql/schema.sql) script.
The jOOQ sources are  generated at compile time, but they can also be generated
with ```mvn jooq-codegen:generate```.

Build and execute unit tests:

```
mvn package
```

Build and execute all types of tests:

```
mvn verify
```

## Running the server

### With Docker Compose

```
docker-compose up
```

### With Maven plugin

```
mvn exec:java
```

## Running the client

```
mvn javafx:run
```

## Working with docs

* To generate doxygen reports: `mvn doxygen:report`
* To copy generated html directory into docs folder: `mvn validate`
* To remove generated target files including dir docs with html code: `mvn clean`

## Style Guides

### Udacity Commit Message Style Guide

Commit messages should follow the
[Udacity Git Commit Message Style Guide](https://udacity.github.io/git-styleguide/). Commit message
style is checked (but not enforced) in development branches with a GitHub Action.

Use *.gitmessage* template:

```shell
git config commit.template .gitmessage
```

### Google Java Style Guide

Java source code should follow the
[Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).
Java source code style is checked (but not enforced) in development
branches with a GitHub Action.

## Software design concepts

Software design concepts that have been considered when developing:

- [SOLID principles](https://en.wikipedia.org/wiki/SOLID)
- [Hexagonal architecture](https://en.wikipedia.org/wiki/Hexagonal_architecture_%28software%29)
- [Domain-driven design](https://en.wikipedia.org/wiki/Domain-driven_design)
- [Code smell](https://refactoring.guru/refactoring/smells)
  [refactorings](https://refactoring.guru/refactoring/techniques)

## Technologies used

- [Eclipse Jersey](https://eclipse-ee4j.github.io/jersey/) as REST framework.
- [jOOQ](https://www.jooq.org/) for database access.
- [JavaFX](https://openjfx.io/) for the client GUI.
- [OAuth 2.0](https://oauth.net/2/) for user Sign Up & Sign In.
- [JWT (JSON Web Tokens)](https://jwt.io/) for Token-Based Authentication.
- [JUnit 5](https://junit.org/junit5/), [Mockito](https://site.mockito.org/)
  & [Cucumber](https://cucumber.io/) for testing.
- [Doxygen](https://www.doxygen.nl/) for documentation.
