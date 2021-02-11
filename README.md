# otus-java

Домашки для курса https://otus.ru/lessons/java-professional/


## hw01
Научиться писать многомодульные build-скрипты с зависимостями на Gradle

./gradlew hw01:build

java -jar ./hw01/build/libs/fatOtus-0.1.jar hello brave new world

## hw02

Подобрать нужную коллекцию, исправить ошибки в классе Customer. Добиться прохождения всех тестов

./gradlew hw02:test

## hw03

Написать свой тестовый фреймворк с поддержкой аннотаций @Test, @Before, @After.

import bjunit.BJUnit;

var testEngine = new BJUnit(SomeTest.class);

testEngine.execute();