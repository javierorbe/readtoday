# ReadToday

[![Build](https://github.com/javierorbe/readtoday/actions/workflows/build.yml/badge.svg)](https://github.com/javierorbe/readtoday/actions/workflows/build.yml)
[![codecov](https://codecov.io/gh/javierorbe/readtoday/branch/master/graph/badge.svg?token=W4O6MOXZZO)](https://codecov.io/gh/javierorbe/readtoday)

## Building

Building the server module requires the MySQL database to be active,
to generate the jOOQ sources.

Build and execute all tests:

```
mvn test
```

Build and exclude integration tests (considering that integration tests are
those that interact with infrastructure):

```
mvn test -P exclude-integration-tests
```

## Use *.gitmessage* template

```shell
git config commit.template .gitmessage
```
