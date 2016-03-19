package vending.after;

import vending.after.drink.Drink;
import vending.after.drink.DrinkType;
import vending.after.money.Change;
import vending.after.money.Coin;
import vending.after.money.CoinMech;
import vending.after.stock.Storage;

public class VendingMachine {

    private Storage storage = new Storage();
    private CoinMech coinMech = new CoinMech();
    
    /**
     * ジュースを購入する.
     *
     * @param payment           投入金額. 100円と500円のみ受け付ける.
     * @param drinkType ジュースの種類.
     *                    コーラ({@code Juice.COKE}),ダイエットコーラ({@code Juice.DIET_COKE},お茶({@code Juice.TEA})が指定できる.
     * @return 指定したジュース. 在庫不足や釣り銭不足で買えなかった場合は {@code null} が返される.
     */
    public Drink buy(Coin payment, DrinkType drinkType) {
        
        this.coinMech.put(payment);
        
        if (this.coinMech.doesNotHaveChange()) {
            return null;
        }
        
        if (this.storage.doesNotHaveStock(drinkType)) {
            return null;
        }
        
        this.coinMech.commit();
        
        return this.storage.takeOut(drinkType);
    }

    /**
     * お釣りを取り出す.
     *
     * @return お釣りの金額
     */
    public Change refund() {
        return this.coinMech.refund();
    }
}