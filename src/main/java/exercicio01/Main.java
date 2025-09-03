/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package exercicio01;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import java.util.ArrayList;

public class Main {

    private static final String NOME_ARQUIVO = "uber_sistema.xml";
    private static CentralDeInformacoes central;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        central = Persistencia.recuperarCentral(NOME_ARQUIVO);
        System.out.println("Sistema de Corridas iniciado. " + central.getTodosOsPassageiros().size() + " passageiro(s) e "
                + (central.getTodasAsCorridas() != null ? central.getTodasAsCorridas().size() : 0) + " corrida(s) carregada(s).");

        String opcao = "";
        while (!opcao.equalsIgnoreCase("S")) {
            exibirMenu();
            opcao = scanner.nextLine();

            switch (opcao.toUpperCase()) {
                case "1": cadastrarNovoPassageiro(); break;
                case "2": listarTodosOsPassageiros(); break;
                case "3": exibirInformacoesPassageiro(); break;
                case "4": cadastrarNovaCorrida(); break;
                case "5": listarTodasAsCorridas(); break;
                case "6": listarCorridasDeUmPassageiro(); break;
                case "S": System.out.println("Salvando e encerrando o sistema..."); break;
                default: System.out.println("Opção inválida! Tente novamente.");
            }
        }
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1 - Novo passageiro");
        System.out.println("2 - Listar todos os passageiros");
        System.out.println("3 - Exibir informações de um passageiro");
        System.out.println("4 - Nova corrida");
        System.out.println("5 - Listar todas as corridas");
        System.out.println("6 - Listar corridas de um passageiro");
        System.out.println("S - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarNovoPassageiro() {
        System.out.println("\n--- Cadastro de Novo Passageiro ---");
        try {
            System.out.print("Nome: "); String nome = scanner.nextLine();
            System.out.print("E-mail: "); String email = scanner.nextLine();
            Sexo sexo = null;
            while (sexo == null) {
                System.out.print("Sexo (MASCULINO/FEMININO): ");
                try { sexo = Sexo.valueOf(scanner.nextLine().toUpperCase()); } 
                catch (IllegalArgumentException e) { System.err.println("Erro: Sexo inválido."); }
            }
            Date dataNascimento = null;
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            formatter.setLenient(false);
            while (dataNascimento == null) {
                System.out.print("Data de Nascimento (dd/MM/yyyy): ");
                try { dataNascimento = formatter.parse(scanner.nextLine()); } 
                catch (ParseException e) { System.err.println("Erro: Formato de data inválido."); }
            }
            Passageiro novoPassageiro = new Passageiro(nome, sexo, dataNascimento, email);
            if (central.adicionarPassageiro(novoPassageiro)) {
                System.out.println("Passageiro cadastrado com sucesso!");
                Persistencia.salvarCentral(central, NOME_ARQUIVO);
            }
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }
    
    private static void listarTodosOsPassageiros() {
        System.out.println("\n--- Lista de Todos os Passageiros ---");
        ArrayList<Passageiro> passageiros = central.getTodosOsPassageiros();
        if (passageiros.isEmpty()) {
            System.out.println("Nenhum passageiro cadastrado no sistema.");
        } else {
            for (int i = 0; i < passageiros.size(); i++) {
                System.out.println((i + 1) + ". " + passageiros.get(i).getNome() + " (Email: " + passageiros.get(i).getEmail() + ")");
            }
        }
    }

    private static void exibirInformacoesPassageiro() {
        System.out.println("\n--- Buscar Passageiro por E-mail ---");
        System.out.print("Digite o e-mail do passageiro: ");
        String email = scanner.nextLine();
        Passageiro passageiro = central.recuperarPassageiroPeloEmail(email);
        if (passageiro != null) {
            System.out.println("Nome: " + passageiro.getNome());
            System.out.println("E-mail: " + passageiro.getEmail());
            System.out.println("Sexo: " + passageiro.getSexo());
            System.out.println("Data de Nascimento: " + new SimpleDateFormat("dd/MM/yyyy").format(passageiro.getDataNascimento()));
        } else {
            System.out.println("Passageiro com o e-mail '" + email + "' não foi encontrado.");
        }
    }

    private static void cadastrarNovaCorrida() {
        System.out.println("\n--- Solicitar Nova Corrida ---");
        System.out.print("Informe o e-mail do passageiro solicitante: ");
        String email = scanner.nextLine();
        Passageiro passageiro = central.recuperarPassageiroPeloEmail(email);
        if (passageiro == null) {
            System.err.println("Erro: Passageiro não encontrado.");
            return;
        }
        System.out.print("Endereço de Partida: "); String partida = scanner.nextLine();
        System.out.print("Endereço de Destino: "); String destino = scanner.nextLine();
        Corrida novaCorrida = new Corrida(partida, destino, passageiro);
        if (central.adicionarCorrida(novaCorrida)) {
            System.out.println("Corrida solicitada com sucesso!");
            Persistencia.salvarCentral(central, NOME_ARQUIVO);
        } else {
            System.err.println("Erro: Não foi possível registrar a corrida.");
        }
    }

    private static void listarTodasAsCorridas() {
        System.out.println("\n--- Lista de Todas as Corridas ---");
        ArrayList<Corrida> corridas = central.getTodasAsCorridas();
        if (corridas == null || corridas.isEmpty()) {
            System.out.println("Nenhuma corrida registrada no sistema.");
        } else {
            for (Corrida c : corridas) {
                System.out.println("- ID " + c.getId() + ": " + c.toString());
            }
        }
    }

    private static void listarCorridasDeUmPassageiro() {
        System.out.println("\n--- Listar Corridas por Passageiro ---");
        System.out.print("Informe o e-mail do passageiro: ");
        String email = scanner.nextLine();
        ArrayList<Corrida> corridas = central.recuperarCorridasDeUmPassageiro(email);
        if (corridas == null) {
            System.err.println("Passageiro não encontrado.");
        } else if (corridas.isEmpty()) {
            System.out.println("Nenhuma corrida encontrada para este passageiro.");
        } else {
            System.out.println("Corridas solicitadas por " + corridas.get(0).getPassageiro().getNome() + ":");
            for (Corrida c : corridas) {
                System.out.println("- ID " + c.getId() + ": Partida em '" + c.getEnderecoPartida() + "' | Destino: '" + c.getEnderecoDestino() + "'");
            }
        }
    }
}