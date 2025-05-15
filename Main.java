import java.time.LocalDate;

public class Main {
    
    public static void main(String[] args) {


        LocalDate dtHoje = LocalDate.now();
        Aluno aluno = new Aluno(1, "Gabriel", dtHoje, "CC", "02528359238");

        AlunoCRUD.Gravar(aluno);



    }


}
