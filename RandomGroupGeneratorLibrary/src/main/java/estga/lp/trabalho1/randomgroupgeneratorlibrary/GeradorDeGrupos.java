package estga.lp.trabalho1.randomgroupgeneratorlibrary;

import java.util.*;

public class GeradorDeGrupos {

    //guarda todos os estudantes disponiveis para criar grupos
    private final Collection<Estudante> estudantes = new ArrayList<>();
    //gerador de números aleatórios usado para selecionar estudantes
    private final Random random = new Random();
    //histórico de todos os grupos já criados
    private final Set<Grupo> historicoDeGrupos = new HashSet<>();
    //pode mudar a cada geração, logo não é final
    private Estudante estudanteSemGrupo;

    public GeradorDeGrupos(Collection<Estudante> estudantes) {
        if (estudantes == null || estudantes.size() < 2) {
            throw new IllegalArgumentException("Número insuficiente de estudantes.");
        }
        this.estudantes.addAll(estudantes);
    }

    public void carregarHistorico(Collection<Grupo> gruposAnteriores) {
        if (gruposAnteriores == null) {
            throw new IllegalArgumentException("Histórico inválido: null");
        }
        historicoDeGrupos.addAll(gruposAnteriores);
    }

    /**
     * Gera grupos de 2 estudantes de forma aleatória, evitando repetições
     * do histórico. Tenta várias combinações antes de desistir.
     * 
     * @return coleção de grupos gerados
     * @throws IllegalStateException se não for possível gerar grupos
     */
    public Collection<Grupo> gerarGrupos() {
        // Verifica se é matematicamente possível
        if (!podeGerarNovosGrupos()) {
            throw new IllegalStateException(
                "Impossível gerar novos grupos: todas as combinações já foram usadas."
            );
        }
        
        // Tenta gerar até 100 vezes antes de desistir
        int tentativas = 0;
        int maxTentativas = 100;
        
        while (tentativas < maxTentativas) {
            Collection<Grupo> grupos = tentarGerarGrupos();
            
            // Se conseguiu gerar pelo menos 1 grupo, retorna
            if (!grupos.isEmpty()) {
                return grupos;
            }
            
            tentativas++;
        }
        
        // Se após 100 tentativas não conseguiu, lança erro
        throw new IllegalStateException(
            "Não foi possível gerar grupos após " + maxTentativas + " tentativas."
        );
    }

    /**
     * Método auxiliar que tenta gerar grupos uma vez.
     * 
     * @return coleção de grupos gerados (pode ser vazia se falhar)
     */
    private Collection<Grupo> tentarGerarGrupos() {
        ArrayList<Estudante> disponiveis = new ArrayList<>(estudantes);
        Collections.shuffle(disponiveis, random);
        
        Collection<Grupo> grupos = new ArrayList<>();
        estudanteSemGrupo = null;
        
        while (disponiveis.size() >= 2) {
            Estudante e1 = disponiveis.remove(0);
            Collections.shuffle(disponiveis, random);
            
            int idxParceiro = -1;
            
            for (int i = 0; i < disponiveis.size(); i++) {
                Estudante e2 = disponiveis.get(i);
                Grupo candidato = new Grupo(e1, e2);
                
                if (!historicoDeGrupos.contains(candidato)) {
                    idxParceiro = i;
                    break;
                }
            }
            
            if (idxParceiro == -1) {
                // Não encontrou parceiro, falhou esta tentativa
                return new ArrayList<>();  // Retorna lista vazia
            }
            
            Estudante e2 = disponiveis.remove(idxParceiro);
            Grupo novoGrupo = new Grupo(e1, e2);
            
            grupos.add(novoGrupo);
            historicoDeGrupos.add(novoGrupo);
        }
        
        if (!disponiveis.isEmpty() && estudanteSemGrupo == null) {
            estudanteSemGrupo = disponiveis.get(0);
        }
        
        return grupos;
    }
    
    /**
     * Insere um grupo manualmente no histórico, validando se já existe.
     * 
     * @param grupo o grupo a ser inserido
     * @return true se foi inserido com sucesso, false se já existia
     */
    public boolean inserirGrupoManual(Grupo grupo) {
        if (historicoDeGrupos.contains(grupo)) {
            return false;  // Já existe, não pode inserir
        }
        historicoDeGrupos.add(grupo);
        return true;  // Inserido com sucesso
    }

    /**
     * Calcula se ainda é matematicamente possível gerar novos grupos
     * sem repetir pares já formados.
     * 
     * @return true se ainda é possível gerar grupos, false caso contrário
     */
    public boolean podeGerarNovosGrupos() {
        int numEstudantes = estudantes.size();

        // Caso especial: menos de 2 estudantes
        if (numEstudantes < 2) {
            return false;
        }

        // Calcula o número máximo de combinações possíveis
        // Fórmula: n * (n-1) / 2
        int combinacoesPossiveis = (numEstudantes * (numEstudantes - 1)) / 2;

        // Número de grupos já formados
        int gruposJaFormados = historicoDeGrupos.size();

        // Se ainda há combinações disponíveis, pode gerar
        return gruposJaFormados < combinacoesPossiveis;
    }
   
    /**
     * Retorna quantos grupos ainda podem ser formados.
     * 
     * @return número de grupos únicos que ainda podem ser criados
     */
    public int gruposRestantes() {
        int numEstudantes = estudantes.size();

        if (numEstudantes < 2) {
            return 0;
        }

        int combinacoesPossiveis = (numEstudantes * (numEstudantes - 1)) / 2;
        int gruposJaFormados = historicoDeGrupos.size();

        return Math.max(0, combinacoesPossiveis - gruposJaFormados);
    }

    public Estudante getEstudanteSemGrupo() {
        return estudanteSemGrupo;
    }
}
