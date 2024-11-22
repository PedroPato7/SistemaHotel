package trabalhoHotel;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
    	 // Instanciando componentes principais
        ArvoreRubroNegra arvoreReservas = new ArvoreRubroNegra();
        HistoricoReservas historicoReservas = new HistoricoReservas();
        Hotel hotel = new Hotel("Hotel Java", arvoreReservas, historicoReservas);
        SistemaHotel sistema = new SistemaHotel(hotel);

        // Teste 1: Cadastro de reservas sem conflito
        System.out.println("Teste 1: Cadastro de reservas sem conflito");
        Reservas reserva1 = new Reservas("11983597902", 101, "Pedro", "Luxo", LocalDate.of(2024, 11, 25), LocalDate.of(2024, 11, 30));
        Reservas reserva2 = new Reservas("11988899902", 102, "Gian", "Econômico", LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 5));
        Reservas reserva3 = new Reservas("32165497802", 101, "Igor", "Luxo", LocalDate.of(2024, 11, 28), LocalDate.of(2024, 12, 2));

        // Consulta
        sistema.consultarDisponibilidade(101, LocalDate.of(2024, 11, 28), LocalDate.of(2024, 11, 29), "Luxo");

        hotel.cadastrarReserva(reserva1);
        hotel.cadastrarReserva(reserva2);
        hotel.cadastrarReserva(reserva3);

        // Teste 2: Tentativa de criar reservas com conflito
        System.out.println("\nTeste 2: Cadastro de reserva com conflito");
        Reservas reservaConflito = new Reservas("32165497802", 101, "Igor", "Luxo", LocalDate.of(2024, 11, 28), LocalDate.of(2024, 12, 2));
        hotel.cadastrarReserva(reservaConflito);

        // Teste 3: Cancelamento de reserva existente
        System.out.println("\nTeste 3: Cancelamento de reserva");
        hotel.cancelarReserva("11983597902");

        // Teste 4: Listagem de reservas ativas e histórico de cancelamentos
        System.out.println("\nTeste 4: Listagem de reservas");
        System.out.println("Reservas ativas:");
        hotel.listarReservasCheckIn();

        System.out.println("\nHistórico de cancelamentos:");
        historicoReservas.listarHistoricoCancelamento();

        // Teste 5: Cadastro e cancelamento em sequência
        System.out.println("\nTeste 5: Cadastro e cancelamento em sequência");
        Reservas reserva4 = new Reservas("12345678901", 103, "Carlin", "Luxo", LocalDate.of(2024, 11, 27), LocalDate.of(2024, 11, 29));
        hotel.cadastrarReserva(reserva4);
        hotel.cancelarReserva("12345678901");

        // Teste final: Listagem atualizada
        System.out.println("\nReservas finais:");
        hotel.listarReservasCheckIn();
        System.out.println("\nHistórico final de cancelamentos:");
        historicoReservas.listarHistoricoCancelamento();
    }
}
