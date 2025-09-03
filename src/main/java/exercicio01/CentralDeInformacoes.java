
package exercicio01;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CentralDeInformacoes {
    private ArrayList<Passageiro> todosOsPassageiros = new ArrayList<>();
    private ArrayList<Corrida> todasAsCorridas = new ArrayList<>();

    public ArrayList<Passageiro> getTodosOsPassageiros() { 
        return todosOsPassageiros; 
    }
    
    public void setTodosOsPassageiros(ArrayList<Passageiro> p) { 
        this.todosOsPassageiros = p; 
    }
    
    public ArrayList<Corrida> getTodasAsCorridas() { 
        if(this.todasAsCorridas == null) 
            this.todasAsCorridas = new ArrayList<>(); 
        return todasAsCorridas; 
    }
    
    public void setTodasAsCorridas(ArrayList<Corrida> c) { 
        this.todasAsCorridas = c; 
    }

    public Passageiro recuperarPassageiroPeloEmail(String email) {
        for (Passageiro p : todosOsPassageiros) 
            if (p.getEmail().equalsIgnoreCase(email)) return p;
        return null;
    }
    public boolean adicionarPassageiro(Passageiro passageiro) {
        if (recuperarPassageiroPeloEmail(passageiro.getEmail()) != null) {
            System.err.println("ERRO DE VALIDAÇÃO: E-mail já cadastrado."); 
            return false;
        }
        Calendar dataNasc = Calendar.getInstance();
        dataNasc.setTime(passageiro.getDataNascimento());
        Calendar hoje = Calendar.getInstance();
        int idade = hoje.get(Calendar.YEAR) - dataNasc.get(Calendar.YEAR);
        if (hoje.get(Calendar.MONTH) < dataNasc.get(Calendar.MONTH) ||
           (hoje.get(Calendar.MONTH) == dataNasc.get(Calendar.MONTH) && hoje.get(Calendar.DAY_OF_MONTH) < dataNasc.get(Calendar.DAY_OF_MONTH))) {
            idade--;
        }
        if (idade < 18) {
            System.err.println("ERRO DE VALIDAÇÃO: Passageiro é menor de idade ("+idade+" anos)."); return false;
        }
        this.todosOsPassageiros.add(passageiro);
        return true;
    }
    public boolean adicionarCorrida(Corrida corrida) {
        if (getTodasAsCorridas().stream().anyMatch(c -> c.getId() == corrida.getId())) return false;
        this.todasAsCorridas.add(corrida);
        return true;
    }
    public Corrida recuperarCorridaPeloId(long id) {
        for (Corrida c : getTodasAsCorridas()) if (c.getId() == id) return c;
        return null;
    }
    public ArrayList<Corrida> recuperarCorridasDeUmPassageiro(String email) {
        if (recuperarPassageiroPeloEmail(email) == null) return null;
        ArrayList<Corrida> corridasDoPassageiro = new ArrayList<>();
        for (Corrida c : getTodasAsCorridas()) {
            if (c.getPassageiro().getEmail().equalsIgnoreCase(email)) {
                corridasDoPassageiro.add(c);
            }
        }
        return corridasDoPassageiro;
    }
}

