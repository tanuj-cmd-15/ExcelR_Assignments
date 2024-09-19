// Class and Object
class Animal {
    // Encapsulation: Private fields and public access getter/setter methods
    private String name;
    private int age;

    // Constructor
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getter and Setter method
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for age
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Method to display details ----Abstraction
    public void displayDetails() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}

//Polymorphism
interface Behavior {
    void makeSound(); // Abstract method
}

class Dog extends Animal implements Behavior {
    public Dog(String name, int age) {
        super(name, age);
    }

    //Method overriding
    @Override
    public void displayDetails() {
        System.out.println("Dog Name: " + getName() + ", Age: " + getAge());
    }

    //Method implementation from interface
    @Override
    public void makeSound() {
        System.out.println("Woof Woof!");
    }
}

class Cat extends Animal implements Behavior {
    public Cat(String name, int age) {
        super(name, age);
    }

    // Method overriding
    @Override
    public void displayDetails() {
        System.out.println("Cat Name: " + getName() + ", Age: " + getAge());
    }

    // Concept: Method implementation from interface
    @Override
    public void makeSound() {
        System.out.println("Meow Meow!");
    }
}

//Abstract Class
abstract class Vehicle {
    abstract void startEngine(); //Abstract method

    public void displayType() {
        System.out.println("This is a vehicle.");
    }
}

class Car extends Vehicle {
    @Override
    void startEngine() {
        System.out.println("Car engine started.");
    }
}

// Composition and Aggregation
class Garage {
    private Car car; // Aggregation

    public Garage(Car car) {
        this.car = car;
    }

    public void parkCar() {
        System.out.println("Parking the car in the garage.");
        car.startEngine();
    }
}

// Generalization and Specialization
class SportsCar extends Car { // Specialization
    @Override
    void startEngine() {
        System.out.println("Sports car engine roars to life!");
    }
}


public class Main {
    public static void main(String[] args) {
        // Creating objects of Animal class
        Dog dog = new Dog("Buddy", 3);
        Cat cat = new Cat("Whiskers", 2);

        //polymorphism and method overriding
        dog.displayDetails();
        cat.displayDetails();

        //polymorphism through interface
        Behavior[] animals = {dog, cat};
        for (Behavior animal : animals) {
            animal.makeSound();
        }

        //abstract class
        Vehicle myCar = new Car();
        myCar.startEngine();
        myCar.displayType();

        //composition and aggregation
        Garage garage = new Garage(new SportsCar());
        garage.parkCar();

        //specialization
        SportsCar sportsCar = new SportsCar();
        sportsCar.startEngine();
    }
}
