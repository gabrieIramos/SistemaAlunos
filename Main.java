import java.time.LocalDate;

public class Main {
    
    public static void main(String[] args) {
        
        Aluno aluno = new Aluno(1, "Gabriel", "20/02/2008", "CC", "02528359238");

        AlunoCRUD.Gravar(aluno);
    }


}
