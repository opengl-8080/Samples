package vending.after;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.stream.IntStream;

public class VendingMachine {

    Stock stockOfCoke = new Stock(5); // コーラの在庫数
    Stock stockOfDietCoke = new Stock(5); // ダイエットコーラの在庫数
    Stock stockOfTea = new Stock(5); // お茶の在庫数
    StockOf100Yen stockOf100Yen = new StockOf100Yen();
    Change change = new Change();

    public VendingMachine() {
        for (int i=0; i<10; i++) {
            this.stockOf100Yen.add(Coin.ONE_HUNDRED);
        }
    }
    
    /**
     * ジュースを購入する.
     *
     * @param payment           投入金額. 100円と500円のみ受け付ける.
     * @param kindOfDrink ジュースの種類.
     *                    コーラ({@code Juice.COKE}),ダイエットコーラ({@code Juice.DIET_COKE},お茶({@code Juice.TEA})が指定できる.
     * @return 指定したジュース. 在庫不足や釣り銭不足で買えなかった場合は {@code null} が返される.
     */
    public Drink buy(Coin payment, DrinkType kindOfDrink) {
        // 100円と500円だけ受け付ける
        if ((payment != Coin.ONE_HUNDRED) && (payment != Coin.FIVE_HUNDRED)) {
            change.add(payment);
            return null;
        }

        if ((kindOfDrink == DrinkType.COKE) && (stockOfCoke.getQuantity() == 0)) {
            change.add(payment);
            return null;
        } else if ((kindOfDrink == DrinkType.DIET_COKE) && (stockOfDietCoke.getQuantity() == 0)) {
            change.add(payment);
            return null;
        } else if ((kindOfDrink == DrinkType.TEA) && (stockOfTea.getQuantity() == 0)) {
            change.add(payment);
            return null;
        }

        // 釣り銭不足
        if (payment == Coin.FIVE_HUNDRED && stockOf100Yen.size() < 4) {
            change.add(payment);
            return null;
        }

        if (payment == Coin.ONE_HUNDRED) {
            // 100円玉を釣り銭に使える
            stockOf100Yen.add(payment);
        } else if (payment == Coin.FIVE_HUNDRED) {
            // 400円のお釣り
            change.add(this.calculateChange());
        }

        if (kindOfDrink == DrinkType.COKE) {
            stockOfCoke.decrement();
        } else if (kindOfDrink == DrinkType.DIET_COKE) {
            stockOfDietCoke.decrement();
        } else {
            stockOfTea.decrement();
        }

        return new Drink(kindOfDrink);
    }
    
    private Change calculateChange() {
        List<Coin> coins = IntStream.range(0, 4)
                            .mapToObj(i -> stockOf100Yen.pop())
                            .collect(toList());
        
        return new Change(coins);
    }

    /**
     * お釣りを取り出す.
     *
     * @return お釣りの金額
     */
    public Change refund() {
        Change result = change.clone();
        change.clear();
        return result;
    }
}