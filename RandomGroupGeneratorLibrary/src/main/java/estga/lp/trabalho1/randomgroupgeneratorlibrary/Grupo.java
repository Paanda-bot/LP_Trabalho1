package estga.lp.trabalho1.randomgroupgeneratorlibrary;

public class Grupo {

    private final Estudante e1;
    private final Estudante e2;

    public Grupo(Estudante e1, Estudante e2) {
        //condição para controlo de estudante null
        if (e1 == null || e2 == null) {
            throw new IllegalArgumentException("Grupo inválido: Estudante null.");
        }

        //condição para controlo de estudantes repetidos
        if (e1.getNumero() == e2.getNumero()) {
            throw new IllegalArgumentException("Grupo inválido: Estudantes repetidos.");
        }

        //condição para que a ordem dos grupos não interfira a sua criação: e1/e2 = e2/e1

        if (e2.getNumero() < e1.getNumero()) {
           Estudante temp = e1;
           e1 = e2;
           e2 = temp;
        }

        this.e1 = e1;
        this.e2 = e2;

    }

    public Estudante getE1() {
        return e1;
    }

    public Estudante getE2() {
        return e2;
    }

}


