package edu.iis.mto.testreactor.atm;

import edu.iis.mto.testreactor.atm.bank.AuthorizationException;
import edu.iis.mto.testreactor.atm.bank.AuthorizationToken;
import edu.iis.mto.testreactor.atm.bank.Bank;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
    public void proper_deposit_and_proper_currency_return_success() throws ATMOperationException, AuthorizationException {
        Card card = Card.create("2300");
        PinCode pin = PinCode.createPIN(1,2,3,4);
        Money money = new Money(0);

        when(bank.autorize("1234","2300")).thenReturn(AuthorizationToken.create("2300"));
        BanknotesPack banknotesPack= BanknotesPack.create(50,Banknote.PL_10);
        List<BanknotesPack> deposit_pack = new ArrayList<>();
        deposit_pack.add(banknotesPack);
        deposit = MoneyDeposit.create(currency,deposit_pack);

        Withdrawal result = atMachine.withdraw(pin,card,money);

        BanknotesPack banknotesPackExcepted= BanknotesPack.create(0,Banknote.PL_10);
        List<BanknotesPack> deposit_pack_expected = new ArrayList<>();
        deposit_pack_expected .add(banknotesPackExcepted);
        Withdrawal expected = Withdrawal.create(deposit_pack_expected);
        assertEquals(expected,result);
    }

    @Test
    public void proper_pin_and_card_return_autorization() throws ATMOperationException, AuthorizationException {
        Card card = Card.create("2300");
        PinCode pin = PinCode.createPIN(1,2,3,4);
        Money money = new Money(0);

        when(bank.autorize("1234","2300")).thenReturn(AuthorizationToken.create("2300"));
        BanknotesPack banknotesPack= BanknotesPack.create(50,Banknote.PL_10);
        List<BanknotesPack> deposit_pack = new ArrayList<>();
        deposit_pack.add(banknotesPack);
        deposit = MoneyDeposit.create(currency,deposit_pack);

        Withdrawal result = atMachine.withdraw(pin,card,money);

        verify(bank).autorize("1234","2300");

    }

    @Test
    public void itCompiles() {
        assertThat(true, Matchers.equalTo(true));
    }

}
