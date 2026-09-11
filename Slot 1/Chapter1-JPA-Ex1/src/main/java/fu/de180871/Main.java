package fu.de180871;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("hsf302FU")) {
            System.out.println("EntityManagerFactory created successfully!");
        }
    }
}
