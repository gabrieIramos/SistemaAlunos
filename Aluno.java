
public class Aluno {

    private Integer MATRICULA;
    private String NOME;
    private String DATANASCIMENTO;
    private String CURSO;
    private String CPF;

    public Aluno(int Matricula, String nome, String dtNascimento, String curso, String cpf) {
        this.MATRICULA = Matricula;
        this.NOME = nome;
        this.DATANASCIMENTO = dtNascimento;
        this.CURSO = curso;
        this.CPF = cpf;
    }

    public String getName() {
        return this.NOME;
    }

    public Integer getMatricula() {
        return this.MATRICULA;
    }

    public String getDataNascimento() {
        return this.DATANASCIMENTO;
    }

    public String getCurso() {
        return this.CURSO;
    }

    public String getCPF() {
        return this.CPF;
    }

    @Override
    public String toString() {
        return MATRICULA + ";" + NOME + ";" + DATANASCIMENTO + ";" + CURSO + ";" + CPF;
    }

    public static Aluno fromString(String linha) {
        String[] partes = linha.split(";");

        Integer matricula = Integer.parseInt(partes[0]);
        String nome = partes[1];
        String dataNascimento = partes[2];
        String curso = partes[3];
        String cpf = partes[4];

        return new Aluno(matricula, nome, dataNascimento, curso, cpf);
    }

}