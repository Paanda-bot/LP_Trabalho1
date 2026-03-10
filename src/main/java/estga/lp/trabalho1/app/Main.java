package estga.lp.trabalho1.app;

import estga.lp.trabalho1.randomgroupgeneratorlibrary.Estudante;
import estga.lp.trabalho1.randomgroupgeneratorlibrary.GeradorDeGrupos;
import estga.lp.trabalho1.randomgroupgeneratorlibrary.Grupo;
import lp.trabalho1.GroupInfo;
import lp.trabalho1.IODataClass;
import lp.trabalho1.StudentInfo;

import java.util.ArrayList;
import java.util.Collection;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== Gerador de Grupos ===\n");

        IODataClass io = new IODataClass();

        try {

            Collection<Estudante> estudantes = lerEstudantes(io, "estudantes.txt");
            System.out.println("Estudantes carregados: " + estudantes.size());

            GeradorDeGrupos gerador = new GeradorDeGrupos(estudantes);

            Collection<Grupo> historico = lerHistorico(io, "historico.txt");
            gerador.carregarHistorico(historico);

            System.out.println("Grupos no histórico: " + historico.size());
            System.out.println();

            Collection<Grupo> gruposGerados = gerador.gerarGrupos();

            mostrarGrupos(gruposGerados, gerador);

            gravarGrupos(io, gruposGerados, "novos_grupos.txt");

            System.out.println("\nProcesso concluído.");

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static Collection<Estudante> lerEstudantes(IODataClass io, String ficheiro) {
        StudentInfo[] alunosLidos = io.loadStudentUC(ficheiro);
        Collection<Estudante> estudantes = new ArrayList<>();

        for (StudentInfo aluno : alunosLidos) {
            estudantes.add(new Estudante(
                    aluno.getStudentName(),
                    aluno.getStudentID()
            ));
        }

        return estudantes;
    }

    private static Collection<Grupo> lerHistorico(IODataClass io, String ficheiro) {
        GroupInfo[] gruposLidos = io.loadGroups(ficheiro);
        Collection<Grupo> historico = new ArrayList<>();

        for (GroupInfo g : gruposLidos) {

            Estudante e1 = new Estudante(
                    g.getSt1().getStudentName(),
                    g.getSt1().getStudentID()
            );

            Estudante e2 = new Estudante(
                    g.getSt2().getStudentName(),
                    g.getSt2().getStudentID()
            );

            historico.add(new Grupo(e1, e2));
        }

        return historico;
    }

    private static void mostrarGrupos(Collection<Grupo> grupos, GeradorDeGrupos gerador) {

        System.out.println("Novos grupos gerados:");

        int numeroGrupo = 1;

        for (Grupo g : grupos) {
            System.out.println(
                    "Grupo " + numeroGrupo + ": "
                            + g.getE1().getNumero() + " - " + g.getE1().getNome()
                            + " / "
                            + g.getE2().getNumero() + " - " + g.getE2().getNome()
            );
            numeroGrupo++;
        }

        Estudante semGrupo = gerador.getEstudanteSemGrupo();

        if (semGrupo != null) {
            System.out.println("Sem grupo: " +
                    semGrupo.getNumero() + " - " + semGrupo.getNome());
        }

        System.out.println("Grupos restantes possíveis: " +
                gerador.gruposRestantes());
    }

    private static void gravarGrupos(IODataClass io, Collection<Grupo> grupos, String ficheiro) {

        String[] linhas = new String[grupos.size()];

        int i = 0;
        int idGrupo = 1;

        for (Grupo g : grupos) {

            linhas[i] = idGrupo + ","
                    + g.getE1().getNumero() + "," + g.getE1().getNome() + ","
                    + g.getE2().getNumero() + "," + g.getE2().getNome();

            i++;
            idGrupo++;
        }

        io.outputGroups(ficheiro, linhas);

        System.out.println("\nFicheiro \"" + ficheiro + "\" criado com sucesso.");
    }
}