import domain.Customer;
import domain.Destination;
import domain.DueDate;
import domain.InquireInventory;
import domain.Item;
import domain.ItemPackage;
import domain.Order;
import domain.Quantity;
import domain.Variety;

public class Main {

    public static void main(String[] args) {
        Variety variety = new Variety("ABC");
        ItemPackage itemPackage = new ItemPackage("16K");
        Item item = new Item(variety, itemPackage);

        InquireInventory inquireInventory = new InquireInventory();
        inquireInventory.initialzie(item, new Quantity(12));

        Quantity quantity = new Quantity(10);
        DueDate dueDate = new DueDate("2016-10-20");
        Destination destination = new Destination("大阪府◯◯");
        Customer customer = new Customer("株式会社ほげ");
        Order order = new Order(customer, item, quantity, dueDate, destination);

        if (inquireInventory.existsStock(item)) {
            inquireInventory.reserve(item, quantity);
            order.toPlan();
        }


    }
}
