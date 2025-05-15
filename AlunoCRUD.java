import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class AlunoCRUD {

    private static final String ARQUIVO = "alunos.txt";


    public static List<Aluno> ObterTodosAlunos() {
        List<Aluno> alunos = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                alunos.add(Aluno.fromString(linha)); // método que converte linha em objeto Aluno
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    
        return alunos;
    }
    public static void Gravar(Aluno aluno){ 

        try{
            
            if (!Files.exists(Paths.get(ARQUIVO))) {
                Files.createFile(Paths.get(ARQUIVO));
            }
            
            FileWriter fw = new FileWriter(ARQUIVO, true);
            fw.write(aluno.toString() + "\n");
            fw.close();
        }
        catch (Exception exception){
            System.err.println(exception.getMessage());
        }
    }    

    public static void ExcluirAluno(int Matricula){

        List<Aluno> alunos = ObterTodosAlunos();

    }
}
