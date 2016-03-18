package vending.after;

public class VendingMachine {

    int quantityOfCoke = 5; // コーラの在庫数
    int quantityOfDietCoke = 5; // ダイエットコーラの在庫数
    int quantityOfTea = 5; // お茶の在庫数
    int numberOf100Yen = 10; // 100円玉の在庫
    int charge = 0; // お釣り

    /**
     * ジュースを購入する.
     *
     * @param payment           投入金額. 100円と500円のみ受け付ける.
     * @param kindOfDrink ジュースの種類.
     *                    コーラ({@code Juice.COKE}),ダイエットコーラ({@code Juice.DIET_COKE},お茶({@code Juice.TEA})が指定できる.
     * @return 指定したジュース. 在庫不足や釣り銭不足で買えなかった場合は {@code null} が返される.
     */
    public Drink buy(int payment, int kindOfDrink) {
        // 100円と500円だけ受け付ける
        if ((payment != 100) && (payment != 500)) {
            charge += payment;
            return null;
        }

        if ((kindOfDrink == Drink.COKE) && (quantityOfCoke == 0)) {
            charge += payment;
            return null;
        } else if ((kindOfDrink == Drink.DIET_COKE) && (quantityOfDietCoke == 0)) {
            charge += payment;
            return null;
        } else if ((kindOfDrink == Drink.TEA) && (quantityOfTea == 0)) {
            charge += payment;
            return null;
        }

        // 釣り銭不足
        if (payment == 500 && numberOf100Yen < 4) {
            charge += payment;
            return null;
        }

        if (payment == 100) {
            // 100円玉を釣り銭に使える
            numberOf100Yen++;
        } else if (payment == 500) {
            // 400円のお釣り
            charge += (payment - 100);
            // 100円玉を釣り銭に使える
            numberOf100Yen -= (payment - 100) / 100;
        }

        if (kindOfDrink == Drink.COKE) {
            quantityOfCoke--;
        } else if (kindOfDrink == Drink.DIET_COKE) {
            quantityOfDietCoke--;
        } else {
            quantityOfTea--;
        }

        return new Drink(kindOfDrink);
    }

    /**
     * お釣りを取り出す.
     *
     * @return お釣りの金額
     */
    public int refund() {
        int result = charge;
        charge = 0;
        return result;
    }
}