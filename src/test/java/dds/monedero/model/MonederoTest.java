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
    cuenta.depositar(1500);
    Assert.assertEquals(1500.0 , cuenta.getSaldo());
  }

  @Test(expected = MontoNegativoException.class)
  public void NoSeDeberiaPoderPonerMontoNegativo() {
    cuenta.depositar(-1500);
  }

  @Test
  public void LaCantidadDeDepositosDelDiaDeberiaSerIgualA3() {
    cuenta.depositar(1500);
    cuenta.depositar(456);
    cuenta.depositar(1900);
    Assert.assertEquals(3 , cuenta.cantidadDeDepositosDiarios());
  }

  @Test(expected = MaximaCantidadDepositosException.class)
  public void NoSeDeberiaPoderMasDeTresDepositos() {
    cuenta.depositar(1500);
    cuenta.depositar(456);
    cuenta.depositar(1900);
    cuenta.depositar(245);
  }

  @Test(expected = SaldoMenorException.class)
  public void NoSeDeberiaPoderExtraerMasQueElSaldo() {
    cuenta.depositar(90);
    cuenta.extraer(1001);
  }

  @Test(expected = MaximoExtraccionDiarioException.class)
  public void NoSeDeberiaPoderExtraerMasDe1000() {
    cuenta.depositar(5000);
    cuenta.extraer(1001);
  }

  @Test(expected = MontoNegativoException.class)
  public void NoSeDeberiaPoderExtraerMontoNegativo() {
    cuenta.extraer(-500);
  }

}