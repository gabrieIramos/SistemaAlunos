import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class AlunoRepository {

    private static final String ARQUIVO = "alunos.txt";


    public static List<Aluno> ObterTodosAlunos() {
        List<Aluno> alunos = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                alunos.add(Aluno.fromString(linha)); 
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
        File arquivoTemp = new File("alunos_temp.txt");
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoTemp))) {
            for (Aluno aluno : alunos) {
                if (aluno.getMatricula() != Matricula) {
                    bw.write(aluno.toString());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao excluir aluno: " + e.getMessage());
            return;
        }
        
        try {
            Files.delete(Paths.get(ARQUIVO)); 
            Files.move(Paths.get(arquivoTemp.getPath()), Paths.get(ARQUIVO)); 
        } catch (IOException e) {
            System.err.println("Erro ao finalizar a exclusão do aluno: " + e.getMessage());
        }
    }

    public static void Atualizar(Aluno alunoAtualizado) {
        List<Aluno> alunos = ObterTodosAlunos();
        File arquivoTemp = new File("alunos_temp.txt");
    
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoTemp))) {
            boolean alunoEncontrado = false;
            for (Aluno aluno : alunos) {
                if (aluno.getMatricula().equals(alunoAtualizado.getMatricula())) {
                    bw.write(alunoAtualizado.toString());
                    alunoEncontrado = true;
                } else {
                    bw.write(aluno.toString());
                }
                bw.newLine();
            }
            if (!alunoEncontrado) {
                System.err.println("Aluno com matrícula " + alunoAtualizado.getMatricula() + " não encontrado para atualização.");
            }
        } catch (IOException e) {
            System.err.println("Erro ao atualizar aluno: " + e.getMessage());
            return;
        }
    
        try {
            Files.delete(Paths.get(ARQUIVO));
            Files.move(Paths.get(arquivoTemp.getPath()), Paths.get(ARQUIVO));
        } catch (IOException e) {
            System.err.println("Erro ao finalizar a atualização do aluno: " + e.getMessage());
        }
    }
}