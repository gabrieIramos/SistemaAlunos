import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import java.awt.*;
import java.text.ParseException;
import java.time.LocalDate;

public class TelaCadastroAluno extends JFrame {
    private JTextField campoNome, campoMatricula, campoCurso, campoDataNascimento, campoCpf;
    private JButton botaoCadastrar, botaoLimpar, botaoExcluir;
    private JTable tabelaAlunos;
    private DefaultTableModel modeloTabela;

    public TelaCadastroAluno() {
        setTitle("Cadastro de Aluno");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Campos de entrada
        campoNome = new JTextField(15);
        campoMatricula = new JTextField(10);
        campoCurso = new JTextField(10);

        try {
            MaskFormatter mascaraData = new MaskFormatter("##/##/####");
            mascaraData.setPlaceholderCharacter('_');
            campoDataNascimento = new JFormattedTextField(mascaraData);
            campoDataNascimento.setColumns(10);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            MaskFormatter mascaraData = new MaskFormatter("###.###.###-##");
            mascaraData.setPlaceholderCharacter('_');
            campoCpf = new JFormattedTextField(mascaraData);
            campoCpf.setColumns(11);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        

        // Botões
        botaoCadastrar = new JButton("Cadastrar");
        botaoLimpar = new JButton("Limpar");
        botaoExcluir = new JButton("Excluir Selecionado");

        // Tabela e modelo
        modeloTabela = new DefaultTableModel(
                new Object[] { "Nome", "Matrícula", "Curso", "Data Nasc.", "CPF" }, 0);
        tabelaAlunos = new JTable(modeloTabela);
        tabelaAlunos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Painel de formulário
        JPanel painelForm = new JPanel(new GridLayout(6, 2, 10, 5));
        painelForm.setBorder(BorderFactory.createTitledBorder("Cadastro de Aluno"));
        painelForm.add(new JLabel("Nome:"));
        painelForm.add(campoNome);
        painelForm.add(new JLabel("Matrícula:"));
        painelForm.add(campoMatricula);
        painelForm.add(new JLabel("Curso:"));
        painelForm.add(campoCurso);
        painelForm.add(new JLabel("Data Nascimento:"));
        painelForm.add(campoDataNascimento);
        painelForm.add(new JLabel("CPF:"));
        painelForm.add(campoCpf);
        painelForm.add(botaoCadastrar);
        painelForm.add(botaoLimpar);

        JPanel painelInferior = new JPanel();
        painelInferior.add(botaoExcluir);

        setLayout(new BorderLayout(10, 10));
        add(painelForm, BorderLayout.NORTH);
        add(new JScrollPane(tabelaAlunos), BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);

        // Ações
        botaoCadastrar.addActionListener(e -> cadastrarAluno());
        botaoLimpar.addActionListener(e -> limparCampos());
        botaoExcluir.addActionListener(e -> excluirAluno());

          AlunoCRUD.ObterTodosAlunos().forEach(a -> {
            modeloTabela.addRow(new Object[] { a.getName(), a.getMatricula(), a.getCurso(), a.getDataNascimento(), a.getCPF() });
        });
    }

    private void cadastrarAluno() {
        String nome = campoNome.getText().trim();
        int matricula = Integer.parseInt(campoMatricula.getText().trim());
        String curso = campoCurso.getText().trim();
        String dataNascimento = campoDataNascimento.getText().trim();
        String cpf = campoCpf.getText().trim();

        if (nome.isEmpty() || curso.isEmpty() || matricula <= 0 ||
                dataNascimento.isEmpty() || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (cpf.length() != 11) {
            JOptionPane.showMessageDialog(this, "CPF deve ter 11 dígitos.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Aluno aluno = new Aluno(matricula, nome, dataNascimento, curso, cpf);
        AlunoCRUD.Gravar(aluno);                

        modeloTabela.addRow(new Object[] { aluno.getName(), aluno.getMatricula(), aluno.getCurso(), aluno.getDataNascimento(), aluno.getCPF() });
        
        
        limparCampos();
    }

    private void limparCampos() {
        campoNome.setText("");
        campoMatricula.setText("");
        campoCurso.setText("");
        campoDataNascimento.setText("");
        campoCpf.setText("");
    }

    private void excluirAluno() {
        int linhaSelecionada = tabelaAlunos.getSelectedRow();

        if (linhaSelecionada != -1) {
            int matricula = (Integer) modeloTabela.getValueAt(linhaSelecionada, 1);
            AlunoCRUD.ExcluirAluno(matricula);
        }

        if (linhaSelecionada != -1) {
            modeloTabela.removeRow(linhaSelecionada);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um aluno para excluir.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCadastroAluno().setVisible(true));
    }
}
