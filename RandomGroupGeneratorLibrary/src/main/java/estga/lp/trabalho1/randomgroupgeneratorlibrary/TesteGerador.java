package estga.lp.trabalho1.randomgroupgeneratorlibrary;


import java.util.ArrayList;
import java.util.Collection;

public class TesteGerador {

    public static void main(String[] args) {
        
        Collection<Estudante> estudantes = new ArrayList<>();
        
        estudantes.add(new Estudante("Ana", 131415));
        estudantes.add(new Estudante("Manel", 151413));
        estudantes.add(new Estudante("Joaquim", 111416));
        estudantes.add(new Estudante("Maria", 121211));
        estudantes.add(new Estudante("José", 107817));
        estudantes.add(new Estudante("Rita", 131418));
        
        System.out.println("=== TESTE DA BIBLIOTECA RandomGroupGenerator ===\n");
        
        GeradorDeGrupos gerador = new GeradorDeGrupos(estudantes);
        
        // Mostra informação inicial
        System.out.println("Número de estudantes: " + estudantes.size());
        System.out.println("Grupos possíveis de formar: " + gerador.gruposRestantes());
        System.out.println("Pode gerar novos grupos? " + gerador.podeGerarNovosGrupos());
        System.out.println();
        
        // Geração 1
        System.out.println("--- GERAÇÃO 1 ---");
        Collection<Grupo> grupos1 = gerador.gerarGrupos();
        imprimirGrupos(grupos1, gerador);
        System.out.println("Grupos restantes possíveis: " + gerador.gruposRestantes());
        System.out.println();
        
        // Geração 2
        System.out.println("--- GERAÇÃO 2 ---");
        Collection<Grupo> grupos2 = gerador.gerarGrupos();
        imprimirGrupos(grupos2, gerador);
        System.out.println("Grupos restantes possíveis: " + gerador.gruposRestantes());
        System.out.println();
        
        // Geração 3
        System.out.println("--- GERAÇÃO 3 ---");
        Collection<Grupo> grupos3 = gerador.gerarGrupos();
        imprimirGrupos(grupos3, gerador);
        System.out.println("Grupos restantes possíveis: " + gerador.gruposRestantes());
        System.out.println();
        
        // Teste de inserção manual
        System.out.println("--- TESTE INSERÇÃO MANUAL ---");
        Estudante ana = new Estudante("Ana", 131415);
        Estudante jose = new Estudante("José", 107817);
        Grupo grupoManual = new Grupo(ana, jose);
        
        boolean inserido = gerador.inserirGrupoManual(grupoManual);
        System.out.println("Grupo Ana-José inserido manualmente: " + inserido);
        System.out.println("Grupos restantes possíveis: " + gerador.gruposRestantes());
        System.out.println();
        
        // Tentar continuar gerando até não ser mais possível
        int geracao = 4;
        while (gerador.podeGerarNovosGrupos()) {
            System.out.println("--- GERAÇÃO " + geracao + " ---");
            try {
                Collection<Grupo> grupos = gerador.gerarGrupos();
                imprimirGrupos(grupos, gerador);
                System.out.println("Grupos restantes: " + gerador.gruposRestantes());
                System.out.println();
                geracao++;
            } catch (IllegalStateException e) {
                System.out.println("ERRO: " + e.getMessage());
                break;
            }
        }
        
        System.out.println("=== FIM: Não é mais possível gerar novos grupos ===");
    }
    
    private static void imprimirGrupos(Collection<Grupo> grupos, GeradorDeGrupos gerador) {
        int numero = 1;
        for (Grupo g : grupos) {
            System.out.println(
                "Grupo " + numero++ + ": "
                + g.getE1().getNome() + " [" + g.getE1().getNumero() + "]" + " / " +
                g.getE2().getNome() + " [" + g.getE2().getNumero() + "]"
            );
        }
        
        Estudante semGrupo = gerador.getEstudanteSemGrupo();
        if (semGrupo != null) {
            System.out.println("Sem grupo: " + semGrupo.getNome() + " [" + semGrupo.getNumero() + "]");
        }
    }
}
