package estga.lp.trabalho1.randomgroupgeneratorlibrary;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Collection;

public class TesteGerador {

    public static void main(String[] args) {

        Collection<Estudante> estudantes = new ArrayList<>();

        estudantes.add(new Estudante("Ana", 131415));
        estudantes.add(new Estudante("Manel", 151413));
        estudantes.add(new Estudante("Joaquim", 111416));
        estudantes.add(new Estudante("Ana", 121211));
        estudantes.add(new Estudante("Maria", 107817));
        estudantes.add(new Estudante("Zé", 131418));
        estudantes.add(new Estudante("Rita", 191418));


        GeradorDeGrupos gerador = new GeradorDeGrupos(estudantes);

        Collection<Grupo> grupos = gerador.gerarGrupos();

        int numero = 1;
        for (Grupo g : grupos) {
            System.out.println(
                    "Grupo " + numero++ + ": "
                            + g.getE1().getNome() + " [" + g.getE1().getNumero() + "]" + " / " +
                            g.getE2().getNome() + " [" + g.getE2().getNumero() + "]"
            );
        }
        System.out.println("Sem grupo: " + gerador.getEstudanteSemGrupo().getNome() + " [" + gerador.getEstudanteSemGrupo().getNumero() + "]" );
    }
}