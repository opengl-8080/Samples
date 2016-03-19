import vending.after.Change;
import vending.after.Coin;
import vending.after.Drink;
import vending.after.DrinkType;
import vending.after.VendingMachine;

public class Main {
    
    public static void main(String[] args) {
        VendingMachine vm = new VendingMachine();
        
        Drink drink = vm.buy(Coin.FIVE_HUNDRED, DrinkType.COKE);
        Change charge = vm.refund();
        
        if (drink.getKind() == DrinkType.COKE) {
            System.out.println("コーラを購入しました。");
            System.out.println("おつりは " + charge.getAmount() + " 円です。");
        } else {
            throw new RuntimeException("コーラ買えんかった(´ﾟдﾟ｀)");
        }
        
        drink = vm.buy(Coin.ONE_HUNDRED, DrinkType.DIET_COKE);
        charge = vm.refund();

        if (drink.getKind() == DrinkType.DIET_COKE) {
            System.out.println("ダイエットコーラを購入しました。");
            System.out.println("おつりは " + charge.getAmount() + " 円です。");
        } else {
            throw new RuntimeException("ダイエットコーラ買えんかった(´ﾟдﾟ｀)");
        }
        
        vm.buy(Coin.ONE_HUNDRED, DrinkType.DIET_COKE);
        vm.buy(Coin.ONE_HUNDRED, DrinkType.DIET_COKE);
        vm.buy(Coin.ONE_HUNDRED, DrinkType.DIET_COKE);
        vm.buy(Coin.ONE_HUNDRED, DrinkType.DIET_COKE);
        
        drink = vm.buy(Coin.ONE_HUNDRED, DrinkType.DIET_COKE);
        
        if (drink == null) {
            System.out.println("ダイエットコーラは売り切れ");
        } else {
            System.out.println(drink.getKind());
            throw new RuntimeException("なんか変なん出てきた。。。");
        }
    }
}
