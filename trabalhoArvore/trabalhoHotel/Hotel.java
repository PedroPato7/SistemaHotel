package trabalhoHotel;

import java.time.LocalDate;
import java.util.List;

public class Hotel {

	private String nomeHotel;
    private ArvoreRubroNegra reserva; // Árvores Rubro-Negra por quarto
    private HistoricoReservas historico;

    public Hotel(String nomeH, ArvoreRubroNegra reserva, HistoricoReservas historico) {
        this.nomeHotel = nomeH;
        this.reserva = reserva;
        this.historico = historico;
    }

    public boolean cadastrarReserva(Reservas reservas) {
        if (!verificarDisponibilidade(reservas.getNumQuarto(), reservas.getDataCheckIn(), reservas.getDataCheckOut(), reservas.getCategoriaQuarto())) {
            System.out.printf(
                "Reserva não cadastrada: Conflito no quarto %d para as datas %s a %s.\n",
                reservas.getNumQuarto(),
                reservas.getDataCheckIn(),
                reservas.getDataCheckOut()
            );
            return false;
        }

        // Insere a reserva na árvore
        reserva.inserir(reservas);
        System.out.printf(
            "Reserva cadastrada com sucesso! Cliente: %s | Quarto: %d | Período: %s a %s\n",
            reservas.getClienteNome(),
            reservas.getNumQuarto(),
            reservas.getDataCheckIn(),
            reservas.getDataCheckOut()
        );
        return true;
    }

    public void cancelarReserva(String clienteId) {
        if (reserva == null || historico == null) {
            System.out.println("Erro: Não é possível cancelar a reserva, pois o sistema está vazio ou incompleto.");
            return;
        }

        Reservas reservaRemovida = reserva.remover(clienteId);

        if (reservaRemovida != null) {
            historico.adicionarReservaCancelada(reservaRemovida);
            System.out.printf(
                "Reserva cancelada com sucesso!\nCliente: %s | Quarto: %d | Período: %s a %s\n",
                reservaRemovida.getClienteNome(),
                reservaRemovida.getNumQuarto(),
                reservaRemovida.getDataCheckIn(),
                reservaRemovida.getDataCheckOut()
            );
        } else {
            System.out.printf("Erro: Reserva para o cliente ID '%s' não encontrada.\n", clienteId);
        }
    }

    public boolean verificarDisponibilidade(int numQuarto, LocalDate checkIn, LocalDate checkOut, String categoria) {
        // Listar todas as reservas no quarto solicitado
        List<Reservas> reservasDoQuarto = reserva.listarReservasPorQuarto(numQuarto);

        for (Reservas reserva : reservasDoQuarto) {
            // Verificar conflito de datas e categoria
            if (categoria.equals(reserva.getCategoriaQuarto()) && 
                !(checkOut.isBefore(reserva.getDataCheckIn()) || checkIn.isAfter(reserva.getDataCheckOut()))) {
                return false; // Conflito encontrado
            }
        }
        return true; // Disponível
    }


    public void listarReservasCheckIn() {
        reserva.mostrarArvore();
    }
    
    public List<Reservas> getReservas() {
        return reserva.listarTodasReservas();
    }

}
