package vending.after.money;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

public class CoinTest {

    @Test
    public void _100円硬貨をお金に変換したら100円になる() {
        // exercise
        Money money = Coin.ONE_HUNDRED.toMoney();
        
        // verify
        assertThat(money.toString()).isEqualTo(new Money(100).toString());
    }

    @Test
    public void _500円硬貨をお金に変換したら500円になる() {
        // exercise
        Money money = Coin.FIVE_HUNDRED.toMoney();
        
        // verify
        assertThat(money.toString()).isEqualTo(new Money(500).toString());
    }
}
