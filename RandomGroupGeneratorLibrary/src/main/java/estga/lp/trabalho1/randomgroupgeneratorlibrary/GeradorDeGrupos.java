package estga.lp.trabalho1.randomgroupgeneratorlibrary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GeradorDeGrupos {

    //guarda todos os estudantes disponiveis para criar grupos
    private final Collection<Estudante> estudantes = new ArrayList<>();
    //gerador de números aleatórios usado para selecionar estudantes
    private final Random random = new Random();
    //pode mudar a cada geração, logo não é final
    private Estudante estudanteSemGrupo;

    public GeradorDeGrupos(Collection<Estudante> estudantes) {
        if (estudantes == null || estudantes.size() < 2) {
            throw new IllegalArgumentException("Número insuficiente de estudantes.");
        }
        this.estudantes.addAll(estudantes);
    }

    public Collection<Grupo> gerarGrupos() {

        //copiar estudantes para poder baralhar sem alterar a lista original
        ArrayList<Estudante> lista = new ArrayList<>(estudantes);

        //baralhar aleatoriamente
        java.util.Collections.shuffle(lista, random);

        Collection<Grupo> grupos = new ArrayList<>();

        //reset antes de gerar
        estudanteSemGrupo = null;

        //criar grupos de 2 em 2
        for (int i = 0; i < lista.size() - 1; i += 2) {
            Estudante e1 = lista.get(i);
            Estudante e2 = lista.get(i + 1);

            grupos.add(new Grupo(e1, e2));
        }

        if (lista.size() % 2 != 0) {
            estudanteSemGrupo = lista.get(lista.size() - 1);
        }

        //se for impar o último estudante fica sem grupo
        if (lista.size() % 2 == 0) {
            estudanteSemGrupo = lista.get(lista.size() - 1);
        }

        return grupos;

    }

    public Estudante getEstudanteSemGrupo() {
        return estudanteSemGrupo;
    }

}


