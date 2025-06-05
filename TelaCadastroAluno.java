import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List; 
import java.time.format.DateTimeFormatter; 

public class TelaCadastroAluno extends JFrame {
    private JTextField campoNome, campoMatricula, campoCurso;
    private JFormattedTextField campoDataNascimento, campoCpf; 
    private JButton botaoCadastrar, botaoLimpar, botaoExcluir, botaoAtualizar; 
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
            MaskFormatter mascaraCpf = new MaskFormatter("###.###.###-##");
            mascaraCpf.setPlaceholderCharacter('_');
            campoCpf = new JFormattedTextField(mascaraCpf);
            campoCpf.setColumns(11);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        

        // Botões
        botaoCadastrar = new JButton("Cadastrar");
        botaoLimpar = new JButton("Limpar");
        botaoExcluir = new JButton("Excluir Selecionado");
        botaoAtualizar = new JButton("Atualizar Selecionado"); 

        
        modeloTabela = new DefaultTableModel(
                new Object[] { "Nome", "Matrícula", "Curso", "Data Nasc.", "CPF" }, 0);
        tabelaAlunos = new JTable(modeloTabela);
        tabelaAlunos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

       
        tabelaAlunos.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tabelaAlunos.getSelectedRow() != -1) {
                preencherCamposComAlunoSelecionado();
            }
        });

        // Painel de formulário
        JPanel painelForm = new JPanel(new GridLayout(7, 2, 10, 5));
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
        painelForm.add(botaoAtualizar); 

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
        botaoAtualizar.addActionListener(e -> atualizarAluno()); 

        carregarAlunosNaTabela();
    }

    private void carregarAlunosNaTabela() {
        modeloTabela.setRowCount(0); 
        List<Aluno> alunos = AlunoService.obterTodosAlunos();
        for (Aluno a : alunos) {
            modeloTabela.addRow(new Object[] { a.getName(), a.getMatricula(), a.getCurso(), a.getDataNascimento(), a.getCPF() });
        }
    }

    private void cadastrarAluno() {
        try {
            String nome = campoNome.getText().trim();
            int matricula = Integer.parseInt(campoMatricula.getText().trim());
            String curso = campoCurso.getText().trim();
            String dataNascimento = campoDataNascimento.getText().trim();
            String cpf = campoCpf.getText().trim();

            Aluno aluno = new Aluno(matricula, nome, dataNascimento, curso, cpf);
            AlunoService.cadastrarAluno(aluno);                
            
            JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarAlunosNaTabela(); 
            limparCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Matrícula inválida. Digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro de Validação", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar aluno: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarAluno() {
        int linhaSelecionada = tabelaAlunos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        try {
            String nome = campoNome.getText().trim();
            int matricula = Integer.parseInt(campoMatricula.getText().trim());
            String curso = campoCurso.getText().trim();
            String dataNascimento = campoDataNascimento.getText().trim();
            String cpf = campoCpf.getText().trim();
    
            Aluno alunoAtualizado = new Aluno(matricula, nome, dataNascimento, curso, cpf);
            AlunoService.atualizarAluno(alunoAtualizado);
    
            JOptionPane.showMessageDialog(this, "Aluno atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarAlunosNaTabela(); 
            limparCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Matrícula inválida. Digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro de Validação", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar aluno: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirAluno() {
        int linhaSelecionada = tabelaAlunos.getSelectedRow();

        if (linhaSelecionada != -1) {
            int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este aluno?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (confirmacao == JOptionPane.YES_OPTION) {
                try {
                    int matricula = (Integer) modeloTabela.getValueAt(linhaSelecionada, 1);
                    AlunoService.excluirAluno(matricula);
                    JOptionPane.showMessageDialog(this, "Aluno excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarAlunosNaTabela(); 
                    limparCampos();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir aluno: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um aluno para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void preencherCamposComAlunoSelecionado() {
        int linhaSelecionada = tabelaAlunos.getSelectedRow();
        if (linhaSelecionada != -1) {
            campoNome.setText(modeloTabela.getValueAt(linhaSelecionada, 0).toString());
            campoMatricula.setText(modeloTabela.getValueAt(linhaSelecionada, 1).toString());
            campoCurso.setText(modeloTabela.getValueAt(linhaSelecionada, 2).toString());
            campoDataNascimento.setText(modeloTabela.getValueAt(linhaSelecionada, 3).toString());
            campoCpf.setText(modeloTabela.getValueAt(linhaSelecionada, 4).toString());
        }
    }

    private void limparCampos() {
        campoNome.setText("");
        campoMatricula.setText("");
        campoCurso.setText("");
        campoDataNascimento.setText("");
        campoCpf.setText("");
        tabelaAlunos.clearSelection(); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCadastroAluno().setVisible(true));
    }
}