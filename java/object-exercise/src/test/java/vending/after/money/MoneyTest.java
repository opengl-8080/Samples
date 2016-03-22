package vending.after.money;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Test;

public class MoneyTest {

    @Test
    public void toStringしたら_コンストラクタで渡した金額がそのまま文字列になって返される() {
        // setup
        Money money = new Money(100);
        
        // exercise
        String actual = money.toString();
        
        // verify
        assertThat(actual).isEqualTo("100");
    }

    @Test
    public void 足し算ができる() {
        // setup
        Money one = new Money(100);
        Money other = new Money(300);
        
        // exercise
        Money actual = one.add(other);
        
        // verify
        assertThat(one.toString()).as("元のオブジェクトは変化しない（100円）").isEqualTo("100");
        assertThat(other.toString()).as("元のオブジェクトは変化しない（300円）").isEqualTo("300");
        assertThat(actual.toString()).as("合計金額").isEqualTo("400");
    }

}
