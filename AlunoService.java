import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AlunoService {

    public static void cadastrarAluno(Aluno aluno) throws IllegalArgumentException {
        validarAluno(aluno);
        AlunoRepository.Gravar(aluno);
    }

    public static void atualizarAluno(Aluno aluno) throws IllegalArgumentException {
        validarAluno(aluno);
        AlunoRepository.Atualizar(aluno);
    }

    public static void excluirAluno(int matricula) {
        AlunoRepository.ExcluirAluno(matricula);
    }

    public static List<Aluno> obterTodosAlunos() {
        return AlunoRepository.ObterTodosAlunos();
    }

    private static void validarAluno(Aluno aluno) throws IllegalArgumentException {
        if (aluno.getName() == null || aluno.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do aluno não pode ser vazio.");
        }
        if (aluno.getMatricula() == null || aluno.getMatricula() <= 0) {
            throw new IllegalArgumentException("A matrícula do aluno deve ser um número positivo.");
        }
        if (aluno.getCurso() == null || aluno.getCurso().trim().isEmpty()) {
            throw new IllegalArgumentException("O curso do aluno não pode ser vazio.");
        }

        
        if (aluno.getDataNascimento() == null || aluno.getDataNascimento().trim().isEmpty()) {
            throw new IllegalArgumentException("A data de nascimento não pode ser vazia.");
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataNascimento = LocalDate.parse(aluno.getDataNascimento(), formatter);
            if (dataNascimento.getYear() > LocalDate.now().getYear()) {
                throw new IllegalArgumentException("A data de nascimento não pode ser no futuro.");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de data de nascimento inválido. Use dd/MM/yyyy.");
        }

        
        if (aluno.getCPF() == null || aluno.getCPF().trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF do aluno não pode ser vazio.");
        }
        String cpfLimpo = aluno.getCPF().replaceAll("[^0-9]", ""); 
        if (cpfLimpo.length() != 11) {
            throw new IllegalArgumentException("O CPF deve conter 11 dígitos.");
        }        
    }
}