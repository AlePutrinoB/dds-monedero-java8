package dds.monedero.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

public class Cuenta {

  private double saldo = 0;
  private List<Movimiento> movimientos = new ArrayList<>();

  public Cuenta() {
    
  }

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
  }

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  public void poner(double cuanto) {
	ValidadorDeMovimientos.validarMontoPositivo(cuanto);
    ValidadorDeMovimientos.validarCantidadDeDepositos(this);

    movimientos.add(new Deposito(LocalDate.now(), cuanto));
  }


  public long cantidadDeDepositos() {
	return getMovimientos().stream().filter(movimiento -> movimiento.isDeposito()).count();
  }

  public void sacar(double cuanto) {
    ValidadorDeMovimientos.validarMontoPositivo(cuanto);
    ValidadorDeMovimientos.validarSaldoSuficiente(cuanto, this);
    ValidadorDeMovimientos.validarCantidadDeExtraccionesDelDia(cuanto, this);
    movimientos.add(new Extraccion(LocalDate.now(), cuanto));
  }


  public void agregarMovimiento(Movimiento movimiento) {
    movimientos.add(movimiento);
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return extraccionesDelDia(fecha).mapToDouble(Movimiento::getMonto).sum();
  }

private Stream<Movimiento> extraccionesDelDia(LocalDate fecha) {
	return this.getMovimientos().stream()
        .filter(movimiento -> !movimiento.isDeposito() && movimiento.getFecha().equals(fecha));
}

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }


}
