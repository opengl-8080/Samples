import vending.after.VendingMachine;
import vending.after.drink.Drink;
import vending.after.drink.DrinkType;
import vending.after.money.Change;
import vending.after.money.Coin;

public class Main {
    
    public static void main(String[] args) {
        VendingMachine vm = new VendingMachine();
        
        Drink drink = vm.buy(Coin.FIVE_HUNDRED, DrinkType.COKE);
        Change charge = vm.refund();
        
        if (drink.isCoke()) {
            System.out.println("コーラを購入しました。");
            System.out.println("おつりは " + charge.getAmount() + " 円です。");
        } else {
            throw new RuntimeException("コーラ買えんかった(´ﾟдﾟ｀)");
        }
        
        drink = vm.buy(Coin.ONE_HUNDRED, DrinkType.DIET_COKE);
        charge = vm.refund();

        if (drink.isDietCoke()) {
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
            throw new RuntimeException("なんか変なん出てきた。。。");
        }
    }
}
