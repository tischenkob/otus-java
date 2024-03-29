# otus-java

Домашки для курса https://otus.ru/lessons/java-professional/


## hw01
Научиться писать многомодульные build-скрипты с зависимостями на Gradle
```
./gradlew hw01:build

java -jar ./hw01/build/libs/fatOtus-0.1.jar hello brave new world
```
## hw02

Подобрать нужную коллекцию, исправить ошибки в классе Customer. Добиться прохождения всех тестов
```
./gradlew hw02:test
```
## hw03

Написать свой тестовый фреймворк с поддержкой аннотаций `@Test`, `@Before`, `@After`.
```java
import bjunit.BJUnit;

var testEngine = new BJUnit(SomeTest.class);

testEngine.execute();
```
## hw04

Проанализировать работу разных сборщиков мусора.

Я взял G1, ZGC, ParallelGC. Выводы >> CONSLUSIONS.md

## hw05

Попробовать аспектно-ориентированное программирование: создать аннотацию @Log для методов, при вызове которых в консоль будут выводиться название метода и его параметры
```java
@Log
void execute(int a, String b) { ... } >> "Called method `execute` with [$a, $b]"
```
## hw06

Цель: Применить на практике принципы SOLID.

Написать эмулятор АТМ (банкомата). Объект класса АТМ должен уметь:
- принимать банкноты разных номиналов
- выдавать запрошенную сумму минимальным количеством банкнот или ошибку если сумму нельзя выдать
- выдавать сумму остатка денежных средств

## hw07

Цель: Применить на практике шаблоны проектирования.

Builder, Memento и проч.

## hw08

Цель: научиться обрабатывать json, научиться работать с файлами

Некая система:  
    1. принимает входящий json файл;  
    2. обрабатывает данные из файла;  
    3. формирует ответный файл.  

## hw09

Самодельный ORM с помощью JDBC. Маппинг класса на таблицу по именам полей и @Id.

## hw10

На практике освоить основы Hibernate. Понять как аннотации влияют на формирование sql-запросов. Работа должна использовать базу данных в docker-контейнере.

Разметить классы таким образом, чтобы при сохранении/чтении объека каскадно сохранялись/читались вложенные объекты.

ВАЖНО: Hibernate должен создать только три таблицы, при сохранении нового объекта не должно быть update-ов. Посмотреть в логи и проверить, что эти два требования выполняются.

## hw11

Научиться использовать WeakMap как кэш, познакомиться с WeakReference.  
Добавить кэширование в свой ORM.

## hw12

На примере Jetty:
1. Научиться создавать серверный и пользовательский http-интерфейсы.
2. Научиться встраивать web-сервер в приложение.

## hw13

Реализовать свой IoC-контейнер