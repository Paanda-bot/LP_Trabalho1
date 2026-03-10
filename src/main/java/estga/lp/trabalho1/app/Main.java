package estga.lp.trabalho1.app;

import estga.lp.trabalho1.randomgroupgeneratorlibrary.Estudante;
import estga.lp.trabalho1.randomgroupgeneratorlibrary.GeradorDeGrupos;
import estga.lp.trabalho1.randomgroupgeneratorlibrary.Grupo;
import lp.trabalho1.GroupInfo;
import lp.trabalho1.IODataClass;
import lp.trabalho1.StudentInfo;
//import java.util.Scanner;

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

                    /*    // --- DEMONSTRAÇÃO--- inserção manual de grupo pelo utilizador 
                        Scanner scanner = new Scanner(System.in);

                        System.out.println("Pretende inserir um grupo manualmente? (s/n)");
                        String resposta = scanner.nextLine().trim().toLowerCase();

                        if (resposta.equals("s")) {

                            System.out.println("Introduza o número do primeiro estudante:");
                            int num1 = Integer.parseInt(scanner.nextLine().trim());

                            System.out.println("Introduza o número do segundo estudante:");
                            int num2 = Integer.parseInt(scanner.nextLine().trim());

                            Estudante estudante1 = null;
                            Estudante estudante2 = null;

                            // Procurar os estudantes na coleção carregada do ficheiro
                            for (Estudante e : estudantes) {
                                if (e.getNumero() == num1) {
                                    estudante1 = e;
                                } else if (e.getNumero() == num2) {
                                    estudante2 = e;
                                }
                            }

                            if (estudante1 == null || estudante2 == null) {
                                System.out.println("Erro: nao foi possivel encontrar um dos estudantes especificados.");
                                System.out.println("Nenhum grupo manual foi inserido.\n");
                            } else {
                                Grupo grupoManual = new Grupo(estudante1, estudante2);
                                boolean inserido = gerador.inserirGrupoManual(grupoManual);

                                System.out.println("Grupo manual inserido ("
                                        + estudante1.getNumero() + " - " + estudante1.getNome() + " / "
                                        + estudante2.getNumero() + " - " + estudante2.getNome() + "): "
                                        + (inserido ? "SIM" : "NAO (já existia no histórico)"));
                                System.out.println();
                            }
                        }
                        */
                      

            Collection<Grupo> gruposGerados = gerador.gerarGrupos();

            mostrarGrupos(gruposGerados, gerador);

            // 1) grava só os novos grupos num ficheiro separado (como antes)
            gravarGrupos(io, gruposGerados, "novos_grupos.txt");

            // 2) acumula no histórico.txt  histórico antigo + novos grupos
            //Collection<Grupo> historicoAntigo = lerHistorico(io, "historico.txt");

            // junta os novos grupos ao histórico antigo
            //historicoAntigo.addAll(gruposGerados);

            // grava tudo de volta para o historico.txt (agora com tudo acumulado)
            //gravarGrupos(io, historicoAntigo, "historico.txt");

            System.out.println("\nProcesso concluido.");



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

        System.out.println("Grupos restantes possiveis: " +
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