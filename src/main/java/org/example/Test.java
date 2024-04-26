package org.example;

public class Test {
    public static void main(String[] args) {
        String regex = "/join+\\s+\\d+$";
        System.out.println("/join 99.1".matches(regex));
    }
}
