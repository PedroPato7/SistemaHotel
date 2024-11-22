package trabalhoHotel;

import java.util.ArrayList;
import java.util.List;

public class ArvoreRubroNegra {

    // Enum para definir cores dos nós
    private enum Cor {
        VERMELHO, PRETO
    }

    // Classe interna para os nós da árvore
    class Nodo {
        Reservas reserva; // Dados da reserva
        Nodo esquerdo, direito, pai;
        Cor cor;

        public Nodo(Reservas reserva) {
            this.reserva = reserva;
            this.esquerdo = null;
            this.direito = null;
            this.pai = null;
            this.cor = Cor.VERMELHO; // Todo novo nó começa como vermelho
        }
    }

    private Nodo raiz;

    public ArvoreRubroNegra() {
        this.raiz = null;
    }

    // Método público para inserir uma nova reserva
    public void inserir(Reservas reserva) {
        Nodo novoNodo = new Nodo(reserva);

        if (raiz == null) {
            raiz = novoNodo;
            raiz.cor = Cor.PRETO;
            System.out.println("Inserido na raiz: " + reserva.getClienteId());
            return;
        }

        Nodo atual = raiz;
        Nodo pai = null;

        while (atual != null) {
            pai = atual;
            if (reserva.getClienteId().compareTo(atual.reserva.getClienteId()) < 0) {
                atual = atual.esquerdo;
            } else if (reserva.getClienteId().compareTo(atual.reserva.getClienteId()) > 0) {
                atual = atual.direito;
            } else {
                System.out.println("Erro: Reserva com cliente ID já existente: " + reserva.getClienteId());
                return; // Não permite duplicados
            }
        }

        novoNodo.pai = pai;

        if (reserva.getClienteId().compareTo(pai.reserva.getClienteId()) < 0) {
            pai.esquerdo = novoNodo;
        } else {
            pai.direito = novoNodo;
        }

        System.out.println("Inserido cliente ID: " + reserva.getClienteId());
        balancearInsercao(novoNodo);
    }

    // Método para balancear a árvore após inserção
    private void balancearInsercao(Nodo nodo) {
        while (nodo != raiz && nodo.pai.cor == Cor.VERMELHO) {
            Nodo pai = nodo.pai;
            Nodo avo = pai.pai;

            if (avo == null) { // Verifica se o avô é nulo
                break;
            }

            if (pai == avo.esquerdo) { // Caso 1: pai é filho esquerdo do avô
                Nodo tio = avo.direito;

                if (tio != null && tio.cor == Cor.VERMELHO) { // Caso 1.1: Tio vermelho
                    pai.cor = Cor.PRETO;
                    tio.cor = Cor.PRETO;
                    avo.cor = Cor.VERMELHO;
                    nodo = avo;
                } else {
                    if (nodo == pai.direito) { // Caso 1.2.1: Nó é filho direito
                        nodo = pai;
                        rotacaoEsquerda(nodo);
                    }
                    pai.cor = Cor.PRETO;
                    avo.cor = Cor.VERMELHO;
                    rotacaoDireita(avo);
                }
            } else { // Caso 2: pai é filho direito do avô
                Nodo tio = avo.esquerdo;

                if (tio != null && tio.cor == Cor.VERMELHO) { // Caso 2.1: Tio vermelho
                    pai.cor = Cor.PRETO;
                    tio.cor = Cor.PRETO;
                    avo.cor = Cor.VERMELHO;
                    nodo = avo;
                } else {
                    if (nodo == pai.esquerdo) { // Caso 2.2.1: Nó é filho esquerdo
                        nodo = pai;
                        rotacaoDireita(nodo);
                    }
                    pai.cor = Cor.PRETO;
                    avo.cor = Cor.VERMELHO;
                    rotacaoEsquerda(avo);
                }
            }
        }
        raiz.cor = Cor.PRETO;
    }

    // Rotação à esquerda
    private void rotacaoEsquerda(Nodo nodo) {
        Nodo direito = nodo.direito;
        nodo.direito = direito.esquerdo;

        if (direito.esquerdo != null) {
            direito.esquerdo.pai = nodo;
        }

        direito.pai = nodo.pai;

        if (nodo.pai == null) {
            raiz = direito;
        } else if (nodo == nodo.pai.esquerdo) {
            nodo.pai.esquerdo = direito;
        } else {
            nodo.pai.direito = direito;
        }

        direito.esquerdo = nodo;
        nodo.pai = direito;
    }

    // Rotação à direita
    private void rotacaoDireita(Nodo nodo) {
        Nodo esquerdo = nodo.esquerdo;
        nodo.esquerdo = esquerdo.direito;

        if (esquerdo.direito != null) {
            esquerdo.direito.pai = nodo;
        }

        esquerdo.pai = nodo.pai;

        if (nodo.pai == null) {
            raiz = esquerdo;
        } else if (nodo == nodo.pai.direito) {
            nodo.pai.direito = esquerdo;
        } else {
            nodo.pai.esquerdo = esquerdo;
        }

        esquerdo.direito = nodo;
        nodo.pai = esquerdo;
    }

    // Método público para exibir a árvore
    public void mostrarArvore() {
        if (raiz == null) {
            System.out.println("Árvore vazia.");
        } else {
            mostrarArvoreRecursiva(raiz, "", true);
        }
    }

    private void mostrarArvoreRecursiva(Nodo nodo, String prefixo, boolean isDireito) {
        if (nodo != null) {
            System.out.println(prefixo + (isDireito ? "|--- " : "|--- ") + nodo.reserva.getClienteId() + " (" + nodo.cor + ")");
            mostrarArvoreRecursiva(nodo.direito, prefixo + (isDireito ? "    " : "|   "), true);
            mostrarArvoreRecursiva(nodo.esquerdo, prefixo + (isDireito ? "    " : "|   "), false);
        }
    }
    
    public Nodo buscar(String clienteId) {
        Nodo atual = raiz;

        while (atual != null) {
            if (clienteId.compareTo(atual.reserva.getClienteId()) < 0) {
                atual = atual.esquerdo;
            } else if (clienteId.compareTo(atual.reserva.getClienteId()) > 0) {
                atual = atual.direito;
            } else {
                return atual; // Encontrado
            }
        }
        return null; // Não encontrado
    }

    public Reservas remover(String clienteId) {
        Nodo nodoRemover = buscarNodo(raiz, clienteId);
        if (nodoRemover == null) return null;

        Reservas reservaRemovida = nodoRemover.reserva;

        Nodo substituto = removerNodo(nodoRemover);

        if (substituto != null && substituto.cor == Cor.PRETO) {
            balancearRemocao(substituto);
        }

        return reservaRemovida;
    }

    // Métodos auxiliares
    private Nodo buscarNodo(Nodo raiz, String clienteId) {
        while (raiz != null) {
            int comparacao = clienteId.compareTo(raiz.reserva.getClienteId());
            if (comparacao < 0) {
                raiz = raiz.esquerdo;
            } else if (comparacao > 0) {
                raiz = raiz.direito;
            } else {
                return raiz; // Encontrado
            }
        }
        return null; // Não encontrado
    }
    private Nodo buscarNodo(Nodo raiz, int chave) {
        while (raiz != null) {
            if (chave == raiz.reserva.getClienteId().hashCode()) {
                return raiz;
            }
            raiz = chave < raiz.reserva.getClienteId().hashCode() ? raiz.esquerdo : raiz.direito;
        }
        return null;
    }

    private Nodo removerNodo(Nodo nodo) {
        if (nodo.esquerdo == null || nodo.direito == null) {
            // Nodo tem no máximo um filho
            Nodo substituto = (nodo.esquerdo != null) ? nodo.esquerdo : nodo.direito;

            if (substituto != null) {
                substituirNodo(nodo, substituto);
            } else if (nodo == raiz) {
                raiz = null; // Caso especial: removendo a raiz sem filhos
            } else {
                // Nodo é folha
                if (nodo.cor == Cor.PRETO) {
                    balancearRemocao(nodo); // Balancear porque removemos um nó preto
                }
                substituirNodo(nodo, null);
            }

            return substituto;
        } else {
            // Nodo tem dois filhos: encontre o sucessor
            Nodo sucessor = encontrarMinimo(nodo.direito);
            nodo.reserva = sucessor.reserva; // Substituir os dados
            return removerNodo(sucessor); // Remover sucessor recursivamente
        }
    }

    private void substituirNodo(Nodo antigo, Nodo novoNodo) {
        if (antigo.pai == null) {
            raiz = novoNodo;
        } else if (antigo == antigo.pai.esquerdo) {
            antigo.pai.esquerdo = novoNodo;
        } else {
            antigo.pai.direito = novoNodo;
        }

        if (novoNodo != null) {
            novoNodo.pai = antigo.pai;
        }
    }

    private Nodo encontrarMinimo(Nodo nodo) {
        while (nodo.esquerdo != null) {
            nodo = nodo.esquerdo;
        }
        return nodo;
    }

    private void balancearRemocao(Nodo nodo) {
        while (nodo != raiz && (nodo == null || nodo.cor == Cor.PRETO)) {
            if (nodo == nodo.pai.esquerdo) {
                Nodo irmao = nodo.pai.direito;

                if (irmao.cor == Cor.VERMELHO) {
                    irmao.cor = Cor.PRETO;
                    nodo.pai.cor = Cor.VERMELHO;
                    rotacaoEsquerda(nodo.pai);
                    irmao = nodo.pai.direito;
                }

                if ((irmao.esquerdo == null || irmao.esquerdo.cor == Cor.PRETO) &&
                    (irmao.direito == null || irmao.direito.cor == Cor.PRETO)) {
                    irmao.cor = Cor.VERMELHO;
                    nodo = nodo.pai;
                } else {
                    if (irmao.direito == null || irmao.direito.cor == Cor.PRETO) {
                        if (irmao.esquerdo != null) {
                            irmao.esquerdo.cor = Cor.PRETO;
                        }
                        irmao.cor = Cor.VERMELHO;
                        rotacaoDireita(irmao);
                        irmao = nodo.pai.direito;
                    }

                    irmao.cor = nodo.pai.cor;
                    nodo.pai.cor = Cor.PRETO;
                    if (irmao.direito != null) {
                        irmao.direito.cor = Cor.PRETO;
                    }
                    rotacaoEsquerda(nodo.pai);
                    nodo = raiz;
                }
            } else {
                Nodo irmao = nodo.pai.esquerdo;

                if (irmao.cor == Cor.VERMELHO) {
                    irmao.cor = Cor.PRETO;
                    nodo.pai.cor = Cor.VERMELHO;
                    rotacaoDireita(nodo.pai);
                    irmao = nodo.pai.esquerdo;
                }

                if ((irmao.direito == null || irmao.direito.cor == Cor.PRETO) &&
                    (irmao.esquerdo == null || irmao.esquerdo.cor == Cor.PRETO)) {
                    irmao.cor = Cor.VERMELHO;
                    nodo = nodo.pai;
                } else {
                    if (irmao.esquerdo == null || irmao.esquerdo.cor == Cor.PRETO) {
                        if (irmao.direito != null) {
                            irmao.direito.cor = Cor.PRETO;
                        }
                        irmao.cor = Cor.VERMELHO;
                        rotacaoEsquerda(irmao);
                        irmao = nodo.pai.esquerdo;
                    }

                    irmao.cor = nodo.pai.cor;
                    nodo.pai.cor = Cor.PRETO;
                    if (irmao.esquerdo != null) {
                        irmao.esquerdo.cor = Cor.PRETO;
                    }
                    rotacaoDireita(nodo.pai);
                    nodo = raiz;
                }
            }
        }

        if (nodo != null) {
            nodo.cor = Cor.PRETO;
        }
    }

    public List<Reservas> listarTodasReservas() {
        List<Reservas> reservas = new ArrayList<>();
        listarTodasReservasRecursivo(raiz, reservas);
        return reservas;
    }

    private void listarTodasReservasRecursivo(Nodo nodo, List<Reservas> reservas) {
        if (nodo != null) {
            listarTodasReservasRecursivo(nodo.esquerdo, reservas); // Visitar subárvore esquerda
            reservas.add(nodo.reserva);                            // Adicionar reserva atual
            listarTodasReservasRecursivo(nodo.direito, reservas);  // Visitar subárvore direita
        }
    }
    
    public List<Reservas> listarReservasPorQuarto(int numQuarto) {
        List<Reservas> reservas = new ArrayList<>();
        listarReservasPorQuartoRecursivo(raiz, numQuarto, reservas);
        return reservas;
    }

    private void listarReservasPorQuartoRecursivo(Nodo nodo, int numQuarto, List<Reservas> reservas) {
        if (nodo == null) {
            return;
        }

        if (nodo.reserva.getNumQuarto() == numQuarto) {
            reservas.add(nodo.reserva);
        }

        listarReservasPorQuartoRecursivo(nodo.esquerdo, numQuarto, reservas);
        listarReservasPorQuartoRecursivo(nodo.direito, numQuarto, reservas);
    }
}
