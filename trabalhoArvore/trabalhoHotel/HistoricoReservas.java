package trabalhoHotel;

public class HistoricoReservas {

    private ArvoreRubroNegra historicoReservas;

    public HistoricoReservas() {
        this.historicoReservas = new ArvoreRubroNegra();
    }

    public void adicionarReservaCancelada(Reservas reserva) {
        historicoReservas.inserir(reserva);
    }

    public void listarHistoricoCancelamento() {
        historicoReservas.mostrarArvore();
    }
}