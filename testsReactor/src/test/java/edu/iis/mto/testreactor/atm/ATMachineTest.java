package edu.iis.mto.testreactor.atm;

import edu.iis.mto.testreactor.atm.bank.Bank;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ATMachineTest {

    @Mock
    private Bank bank;

    public MoneyDeposit deposit;

    public ATMachine atMachine;
    public Currency currency = Currency.getInstance("PLN") ;



    @BeforeEach
    public void setUp() throws Exception {
        atMachine = new ATMachine(bank,currency);
    }

    @Test
    public void proper_deposit_and_proper_currency_return_success() throws ATMOperationException {
        Card card = Card.create("2300");
        PinCode pin = PinCode.createPIN(1,2,3,4);
        Money money = new Money(20);
        BanknotesPack banknotesPack= BanknotesPack.create(50,Banknote.PL_10);
        List<BanknotesPack> deposit_pack = new ArrayList<>();
        deposit_pack.add(banknotesPack);
        deposit = MoneyDeposit.create(currency,deposit_pack);

        Withdrawal result = atMachine.withdraw(pin,card,money);

        BanknotesPack banknotesPackExcepted= BanknotesPack.create(2,Banknote.PL_10);
        List<BanknotesPack> deposit_pack_expected = new ArrayList<>();
        deposit_pack_expected .add(banknotesPackExcepted);
        Withdrawal expected = Withdrawal.create(deposit_pack_expected);
        assertEquals(expected,result);
    }

    @Test
    public void itCompiles() {
        assertThat(true, Matchers.equalTo(true));
    }

}
