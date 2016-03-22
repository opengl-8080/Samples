package vending.after.money;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import org.junit.Test;

public class ChangeTest {

    @Test
    public void デフォルトコンストラクタで作ったら０円() {
        // setup
        Change change = new Change();
        
        // exercise
        Money money = change.getAmount();
        
        // verify
        assertThat(money.toString()).isEqualTo(new Money(0).toString());
    }
    
    @Test
    public void Coinを渡して作成したら_その額と同じになる() {
        // setup
        Change change = new Change(Coin.FIVE_HUNDRED);
        
        // exercise
        Money money = change.getAmount();
        
        // verify
        assertThat(money.toString()).isEqualTo(Coin.FIVE_HUNDRED.toMoney().toString());
    }
    
    @Test
    public void CoinのListを渡して作成したら_その合計額と同じになる() {
        // setup
        Change change = new Change(Arrays.asList(Coin.ONE_HUNDRED, Coin.ONE_HUNDRED, Coin.ONE_HUNDRED));
        
        // exercise
        Money money = change.getAmount();
        
        // verify
        assertThat(money.toString()).isEqualTo(new Money(300).toString());
    }
}
