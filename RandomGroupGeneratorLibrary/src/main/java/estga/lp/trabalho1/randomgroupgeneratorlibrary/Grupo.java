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

    //método para 2 grupos com os mesmo estudantes, sejam considerados iguais.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Grupo)) return false;

        Grupo other = (Grupo) obj;

        return e1.equals(other.e1) && e2.equals(other.e2);
    }

    // se dois objetos são iguais segundo equals(), então têm de ter obrigatoriamente o mesmo hashCode
    @Override
    public int hashCode() {
        return 31 * e1.hashCode() + e2.hashCode();
    }

    public Estudante getE1() {
        return e1;
    }

    public Estudante getE2() {
        return e2;
    }
}


