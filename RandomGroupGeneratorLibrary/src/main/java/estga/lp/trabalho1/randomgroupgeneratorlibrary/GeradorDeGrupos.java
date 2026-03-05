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

    public Collection<Grupo> gerarGrupos() {

        //copiar estudantes para poder baralhar sem alterar a lista original
        ArrayList<Estudante> disponiveis = new ArrayList<>(estudantes);
        //baralhar aleatóriamente
        Collections.shuffle(disponiveis, random);

        Collection<Grupo> grupos = new ArrayList<>();
        //reset antes de gerar
        estudanteSemGrupo = null;

        while (disponiveis.size() >= 2) { //enquanto existir 2 estudantes disponíveis, continua a tentar formar grupo
            Estudante e1 = disponiveis.remove(0); // escolhe o primeiro estudante da lista e remove-o

            //re baralha os restantes estudantes
            Collections.shuffle(disponiveis, random);

            int idxParceiro = -1; // variavel para guardar o parceiro encontrado

            for (int i = 0; i < disponiveis.size(); i++) {
                Estudante e2 = disponiveis.get(i);
                Grupo candidato = new Grupo(e1, e2);

                if (!historicoDeGrupos.contains(candidato)) {
                    idxParceiro = i;
                    break;
                }
            }

            if (idxParceiro == -1) {
                //não encontrou parceiro válido para e1
                estudanteSemGrupo = e1;
                break;
            }

            Estudante e2 = disponiveis.remove(idxParceiro);
            Grupo novoGrupo = new Grupo(e1, e2);

            grupos.add(novoGrupo);
            historicoDeGrupos.add(novoGrupo);
        }

        if (!disponiveis.isEmpty() && estudanteSemGrupo == null) {
            //sobrou 1 estudante (impar)
            estudanteSemGrupo = disponiveis.get(0);
        }

        return grupos;

    }

    public Estudante getEstudanteSemGrupo() {
        return estudanteSemGrupo;
    }
}


