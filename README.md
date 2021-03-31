# ReadToday

[![Build](https://github.com/javierorbe/readtoday/actions/workflows/build.yml/badge.svg)](https://github.com/javierorbe/readtoday/actions/workflows/build.yml)
[![codecov](https://codecov.io/gh/javierorbe/readtoday/branch/master/graph/badge.svg?token=W4O6MOXZZO)](https://codecov.io/gh/javierorbe/readtoday)

## Building

Building the server module requires MySQL to be active. The database must
contain the ReadToday schema
([server/src/main/sql/schema.sql](/server/src/main/sql/schema.sql)), in order
to generate the jOOQ sources. The jOOQ sources are generated at compile time,
but can also be generated with ```mvn jooq-codegen:generate```.

Build and execute all tests:

```
mvn test
```

Build and exclude integration tests (considering that integration tests are
those that interact with infrastructure):

```
mvn test -P exclude-integration-tests
```

## Style Guides

### Udacity Commit Message Style Guide

Commit messages should follow the
[Udacity Git Commit Message Style Guide](https://udacity.github.io/git-styleguide/).
Commit message style is checked (but not enforced) in development branches
with a GitHub Action.

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
- [JUnit 5](https://junit.org/junit5/) & [Mockito](https://site.mockito.org/) for testing.
