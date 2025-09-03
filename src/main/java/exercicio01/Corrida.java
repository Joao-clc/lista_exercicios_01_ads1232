
package exercicio01;

public class Corrida {
    private final long id;
    private String enderecoPartida;
    private String enderecoDestino;
    private Passageiro passageiro;

    public Corrida() { 
        this.id = System.currentTimeMillis(); 
    }

    public Corrida(String partida, String destino, Passageiro pass) {
        this.id = System.currentTimeMillis(); 
        this.enderecoPartida = partida;
        this.enderecoDestino = destino; 
        this.passageiro = pass;
    }

    public long getId() { return id; }
    public String getEnderecoPartida() { 
        return enderecoPartida; 
    }
    
    public void setEnderecoPartida(String p) { 
        this.enderecoPartida = p; 
    }
    
    public String getEnderecoDestino() { 
        return enderecoDestino; 
    }
    
    public void setEnderecoDestino(String d) { 
        this.enderecoDestino = d; 
    }
    
    public Passageiro getPassageiro() { 
        return passageiro; 
    }
    
    public void setPassageiro(Passageiro p) { 
        this.passageiro = p; 
    }

    @Override 
    public String toString() {
        String primeiroNome = passageiro.getNome().split(" ")[0];
        String pronome = passageiro.getSexo() == Sexo.FEMININO ? "pegá-la" : "pegá-lo";
        return String.format("%s pede para %s em %s", primeiroNome, pronome, this.enderecoPartida);
    }
}
