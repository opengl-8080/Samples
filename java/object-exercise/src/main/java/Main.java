import vending.Drink;
import vending.VendingMachine;

public class Main {
    
    public static void main(String[] args) {
        VendingMachine vm = new VendingMachine();
        
        Drink drink = vm.buy(500, Drink.COKE);
        int charge = vm.refund();
        
        if (drink.getKind() == Drink.COKE) {
            System.out.println("コーラを購入しました。");
            System.out.println("おつりは " + charge + " 円です。");
        } else {
            throw new RuntimeException("コーラ買えんかった(´ﾟдﾟ｀)");
        }
    }
}
