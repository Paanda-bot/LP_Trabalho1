package estga.lp.trabalho1.randomgroupgeneratorlibrary;

public class Estudante {

    private final String nome;
    private final int numero;

    public Estudante(String nome, int numero) {
        this.nome = nome;
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }
    public int getNumero() {
        return numero;
    }
    
    /**
     * Compara se dois estudantes são iguais com base no número.
     * 
     * @param obj o objeto a ser comparado
     * @return true se os estudantes têm o mesmo número, false caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        // 1. Se for o mesmo objeto na memória, são iguais
        if (this == obj) return true;
        
        // 2 Se for null ou classe diferente, não são iguais
        if (obj == null || getClass() != obj.getClass()) return false;
        
        // 3 Converte para Estudante e compara os atributos
        Estudante outro = (Estudante) obj;
        
        // Dois estudantes são iguais se têm o mesmo número
        // (assumindo que número é único por estudante)
        return this.numero == outro.numero;
    }
    /**
     * Gera o código hash do estudante baseado no número.
     * 
     * @return código hash calculado a partir do número do estudante
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(numero);
    }
    
    
}


