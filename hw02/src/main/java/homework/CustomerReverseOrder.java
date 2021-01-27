package homework;

import java.util.ArrayDeque;
import java.util.Deque;

public class CustomerReverseOrder {

    //TODO: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    private Deque<Customer> dequeOfCustomers = new ArrayDeque<>();

    public void add(Customer customer) {
       dequeOfCustomers.addLast(customer);
    }

    public Customer take() {
        return dequeOfCustomers.removeLast();
    }
}
