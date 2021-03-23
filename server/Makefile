.PHONY: all build test run lint

all: build

build:
	@mvn verify

test:
	@mvn test

run:
	@mvn exec:java

lint:
	@mvn checkstyle:check
