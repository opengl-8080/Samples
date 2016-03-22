package vending.after.money;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

public class CashBoxTest {
    private CashBox cashBox = new CashBox();

    @Test
    public void _100円玉が3枚の場合は釣り銭切れ() {
        // setup
        add100Yen(cashBox, 3);
        
        // exercise
        boolean actual = cashBox.doesNotHaveChange();
        
        // verify
        assertThat(actual).isTrue();
    }
    
    @Test
    public void _100円玉が4枚の場合は釣り銭有り() {
        // setup
        add100Yen(cashBox, 4);
        
        // exercise
        boolean actual = cashBox.doesNotHaveChange();
        
        // verify
        assertThat(actual).isFalse();
    }
    
    @Test
    public void _１００円硬貨の枚数を取得できる() throws Exception {
        // setup
        add100Yen(cashBox, 5);
        
        // exercise
        int count = cashBox.count();
        
        // verify
        assertThat(count).isEqualTo(5);
    }
    
    @Test
    public void 釣り銭を取ったら１００円硬貨の数が４枚減る() {
        // setup
        add100Yen(cashBox, 10);
        
        // exercise
        cashBox.takeOutChange();
        
        // verify
        assertThat(cashBox.count()).isEqualTo(6);
    }
    
    @Test
    public void 釣り銭を取ったら４００円が取得できる() {
        // setup
        add100Yen(cashBox, 10);
        
        // exercise
        Change change = cashBox.takeOutChange();
        
        // verify
        assertThat(change.getAmount().toString()).isEqualTo(new Money(400).toString());
    }

    private static void add100Yen(CashBox cashBox, int number) {
        for (int i=0; i<number; i++) {
            cashBox.add(Coin.ONE_HUNDRED);
        }
    }
}
