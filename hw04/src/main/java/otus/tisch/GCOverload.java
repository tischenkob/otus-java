/*
Написать приложение, которое пишет в лог
- Количество сборок каждого типа
- Время, ушедшее на сборки в минуту

Добиться OutOfMemory примерно через пять минут после старта

Собрать статистику по разным GC

Какой GC лучше и почему? >> hw04/CONCLUSIONS.md
Результаты измерений представить в таблице

Размеры хипа: 128Мб и 12Гб
*/

package otus.tisch;

import java.util.*;

public class GCOverload {
    public static void main(String[] args) {
        Set<Object> set = new HashSet<>();
        Map<Object> map = new HashMap();

        List<Person> people = new ArrayList<>();

        while (true) {
            for (int i = 0; i < 1000; i++) {
                people.add(new Person());
            }
            for (int i = 0; i < 500; i++) {
                people.remove(i);
            }
        }
    }

    static class Person {
        private final String name;
        private final Integer age;

        Person() {
            this.age = new Random().nextInt();
            name = randomName();
        }

        public Integer getAge() {
            return age;
        }

        private static String randomName() {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                var letter = (char) (new Random()).nextInt();
                result.append(letter);
            }
            return result.toString();
        }

        public String getName() {
            return name;
        }
    }
}
