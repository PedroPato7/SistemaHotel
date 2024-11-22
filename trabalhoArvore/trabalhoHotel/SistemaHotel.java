package trabalhoHotel;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class SistemaHotel {

    private Hotel hotel;

    public SistemaHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void cadastrarNovaReserva(String clienteId, int numQ, String nome, LocalDate checkIn, LocalDate checkOut, String categoria) {
        Reservas novaReserva = new Reservas(clienteId, numQ, nome, categoria, checkIn, checkOut);

        if (hotel.cadastrarReserva(novaReserva)) {
            System.out.println("Reserva cadastrada com sucesso!");
        } else {
            System.out.println("Conflito de datas, o quarto já está ocupado.");
        }
    }

    public void cancelarReserva(String clienteId) {
        hotel.cancelarReserva(clienteId);
        System.out.println("Reserva cancelada.");
    }

    public void consultarDisponibilidade(int numQuarto, LocalDate checkIn, LocalDate checkOut, String categoria) {
        boolean disponivel = hotel.verificarDisponibilidade(numQuarto, checkIn, checkOut, categoria);
        System.out.println("Disponibilidade para o quarto " + numQuarto + " (" + categoria + "): " + 
                            (disponivel ? "Disponível" : "Indisponível"));
    }
    
    public void listarReservasPorCheckIn() {
        hotel.listarReservasCheckIn();
    }
    
    public double calcularTaxaOcupacao(LocalDate inicio, LocalDate fim) {
        List<Reservas> todasReservas = hotel.getReservas(); // Método deve listar todas as reservas
        long diasPeriodo = ChronoUnit.DAYS.between(inicio, fim);
        long reservasPeriodo = todasReservas.stream()
            .filter(r -> !(r.getDataCheckOut().isBefore(inicio) || r.getDataCheckIn().isAfter(fim)))
            .count();

        return (double) reservasPeriodo / diasPeriodo * 100;
    }
}
