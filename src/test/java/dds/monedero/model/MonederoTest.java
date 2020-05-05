package dds.monedero.model;

import org.junit.Before;
import org.junit.Test;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import junit.framework.Assert;

public class MonederoTest {
  private Cuenta cuenta;

  @Before
  public void init() {
    cuenta = new Cuenta();
  }

  @Test
  public void ElSaldoDeberiaSer1500LuegoDePonerDichaCantidad() {
    cuenta.poner(1500);
    Assert.assertEquals(1500.0 , cuenta.getSaldo());
  }

  @Test(expected = MontoNegativoException.class)
  public void NoSeDeberiaPoderPonerMontoNegativo() {
    cuenta.poner(-1500);
  }

  @Test
  public void ElSaldoDeberiaSer3856LuegoDeTresDepositosDe1500_456Y1900() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
    Assert.assertEquals(3856.0 , cuenta.getSaldo());
  }

  @Test(expected = MaximaCantidadDepositosException.class)
  public void NoSeDeberiaPoderMasDeTresDepositos() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
    cuenta.poner(245);
  }

  @Test(expected = SaldoMenorException.class)
  public void NoSeDeberiaPoderExtraerMasQueElSaldo() {
    cuenta.setSaldo(90);
    cuenta.sacar(1001);
  }

  @Test(expected = MaximoExtraccionDiarioException.class)
  public void NoSeDeberiaPoderExtraerMasDe1000() {
    cuenta.setSaldo(5000);
    cuenta.sacar(1001);
  }

  @Test(expected = MontoNegativoException.class)
  public void NoSeDeberiaPoderExtraerMontoNegativo() {
    cuenta.sacar(-500);
  }

}