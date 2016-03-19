package vending.after;

public class VendingMachine {

    Storage storage = new Storage();
    CoinMech coinMech = new CoinMech();
    
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
            coinMech.addChange(payment);
            return null;
        }
        
        if (storage.isEmpty(kindOfDrink)) {
            coinMech.addChange(payment);
            return null;
        }

        if (payment == Coin.FIVE_HUNDRED && coinMech.doesNotHaveChange()) {
            coinMech.addChange(payment);
            return null;
        }

        if (payment == Coin.ONE_HUNDRED) {
            // 100円玉を釣り銭に使える
            coinMech.addStockOf100Yen(payment);
        } else if (payment == Coin.FIVE_HUNDRED) {
            // 400円のお釣り
            coinMech.addChange(coinMech.takeOutChange());
        }

        storage.decrement(kindOfDrink);

        return new Drink(kindOfDrink);
    }

    /**
     * お釣りを取り出す.
     *
     * @return お釣りの金額
     */
    public Change refund() {
        return coinMech.refund();
    }
}