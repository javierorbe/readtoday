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

## Use *.gitmessage* template

```shell
git config commit.template .gitmessage
```
