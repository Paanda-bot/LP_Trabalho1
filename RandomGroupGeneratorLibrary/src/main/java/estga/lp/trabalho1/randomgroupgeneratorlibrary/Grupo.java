package estga.lp.trabalho1.randomgroupgeneratorlibrary;

public class Grupo {

    private final Estudante e1;
    private final Estudante e2;

    public Grupo(Estudante e1, Estudante e2) {
        //condição para controlar estudante null
        if (e1 == null || e2 == null) {
            throw new IllegalArgumentException("Grupo inválido: Estudante null.");
        }

        //condição para controlo de estudantes repetidos
        if (e1.getNumero() == e2.getNumero()) {
            throw new IllegalArgumentException("Grupo inválido: Estudantes repetidos");
        }

        this.e1 = e1;
        this.e2 = e2;

    }

}


