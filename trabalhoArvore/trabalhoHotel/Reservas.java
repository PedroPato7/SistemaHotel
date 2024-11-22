package trabalhoHotel;

import java.time.LocalDate;

public class Reservas {

    private String clienteId;
    private int numQuarto;
    private String clienteNome;
    private String categoriaQuarto;
    private LocalDate dataCheckIn;
    private LocalDate dataCheckOut;

    public Reservas(String clienteId, int numQuarto, String clienteNome, String categoriaQuarto, LocalDate dataCheckIn, LocalDate dataCheckOut) {
        this.clienteId = clienteId;
        this.numQuarto = numQuarto;
        this.clienteNome = clienteNome;
        this.categoriaQuarto = categoriaQuarto;
        this.dataCheckIn = dataCheckIn;
        this.dataCheckOut = dataCheckOut;
    }

	public String getClienteId() {
		return clienteId;
	}

	public void setClienteId(String clienteId) {
		this.clienteId = clienteId;
	}

	public int getNumQuarto() {
		return numQuarto;
	}

	public void setNumQuarto(int numQuarto) {
		this.numQuarto = numQuarto;
	}

	public String getClienteNome() {
		return clienteNome;
	}

	public void setClienteNome(String clienteNome) {
		this.clienteNome = clienteNome;
	}

	public String getCategoriaQuarto() {
		return categoriaQuarto;
	}

	public void setCategoriaQuarto(String categoriaQuarto) {
		this.categoriaQuarto = categoriaQuarto;
	}

	public LocalDate getDataCheckIn() {
		return dataCheckIn;
	}

	public void setDataCheckIn(LocalDate dataCheckIn) {
		this.dataCheckIn = dataCheckIn;
	}

	public LocalDate getDataCheckOut() {
		return dataCheckOut;
	}

	public void setDataCheckOut(LocalDate dataCheckOut) {
		this.dataCheckOut = dataCheckOut;
	}

	@Override
	public String toString() {
	    return String.format("Reserva[Cliente: %s, Quarto: %d, Check-in: %s, Check-out: %s, Categoria: %s]",
	            clienteNome, numQuarto, dataCheckIn, dataCheckOut, categoriaQuarto);
	}
}
