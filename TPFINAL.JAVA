package Tp;

import com.google.gson.Gson;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Cadastro {
    private JButton btnIncluir;
    private JButton btnLimpar;
    private JButton btnMostrar;
    private JButton btnSair;
    private JTextField txtNome;
    private JTextField txtIdade;
    private JTextField txtPeso;
    private JTextField txtAltura;
    private JTextField txtObjetivo;
    private JLabel nomeLabel;
    private JLabel idadeLabel;
    private JLabel pesoLabel;
    private JLabel alturaLabel;
    private JLabel objetivoLabel;
    private JPanel mainPanel;
    Connection con;
    Statement stmt;
    ResultSet rs;

    public Cadastro() {
        this.buildComponents();
        this.connectDatabase();
        this.btnIncluir.addActionListener((e) -> {
            this.incluirAluno();
        });
        this.btnLimpar.addActionListener((e) -> {
            this.limpar();
        });
        this.btnMostrar.addActionListener((e) -> {
            this.mostrar();
        });
        this.btnSair.addActionListener((e) -> {
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("app");
        mainFrame.setContentPane((new Cadastro()).mainPanel);
        mainFrame.setLocationRelativeTo((Component)null);
        mainFrame.setSize(500, 300);
        mainFrame.setDefaultCloseOperation(3);
        mainFrame.setVisible(true);
    }

    private void connectDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/aulajava", "root", "root");
            this.stmt = this.con.createStatement();
            System.out.println("Database connected.");
        } catch (Exception var2) {
            System.out.println(var2.getMessage());
        }

    }

    private void incluirAluno() {
        try {
            String nome = this.txtNome.getText();
            int idade = Integer.parseInt(this.txtIdade.getText());
            float peso = Float.parseFloat(this.txtPeso.getText());
            float altura = Float.parseFloat(this.txtAltura.getText());
            String objetivo = this.txtObjetivo.getText();
            String queryString = "INSERT INTO aluno (nome, idade, peso, altura, objetivo) VALUES ('" + nome + "', " + idade + ", " + peso + ", " + altura + ", '" + objetivo + "')";
            this.stmt.executeUpdate(queryString);
        } catch (Exception var7) {
            JOptionPane.showMessageDialog(this.mainPanel, "Não foi possível inserir o aluno no banco. Tenha certeza de ter inserido todos os dados corretamente");
        }

    }

    private void limpar() {
        this.txtAltura.setText("");
        this.txtNome.setText("");
        this.txtIdade.setText("");
        this.txtPeso.setText("");
        this.txtObjetivo.setText("");
    }

    private void mostrar() {
        ArrayList alunos = new ArrayList();

        try {
            this.rs = this.stmt.executeQuery("SELECT * FROM aluno");

            while(this.rs.next()) {
                Cadastro.Aluno aluno = new Cadastro.Aluno();
                aluno.id = this.rs.getInt("id");
                aluno.nome = this.rs.getString("nome");
                aluno.idade = this.rs.getInt("idade");
                aluno.peso = this.rs.getFloat("peso");
                aluno.altura = this.rs.getFloat("altura");
                aluno.objetivo = this.rs.getString("objetivo");
                alunos.add(aluno);
            }

            Gson gson = new Gson();
            String stringedGson = gson.toJson(alunos);
            JOptionPane.showMessageDialog(this.mainPanel, stringedGson);
        } catch (Exception var4) {
            JOptionPane.showMessageDialog(this.mainPanel, "Não foi possível retornar os alunos registrados");
        }

    }

    private void buildComponents() {
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new GridLayoutManager(6, 4, new Insets(0, 0, 0, 0), -1, -1, true, false));
        this.nomeLabel = new JLabel();
        this.nomeLabel.setText("Nome:");
        this.mainPanel.add(this.nomeLabel, new GridConstraints(0, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.idadeLabel = new JLabel();
        this.idadeLabel.setText("Idade:");
        this.mainPanel.add(this.idadeLabel, new GridConstraints(1, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.pesoLabel = new JLabel();
        this.pesoLabel.setText("Peso:");
        this.mainPanel.add(this.pesoLabel, new GridConstraints(2, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.alturaLabel = new JLabel();
        this.alturaLabel.setText("Altura:");
        this.mainPanel.add(this.alturaLabel, new GridConstraints(3, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.objetivoLabel = new JLabel();
        this.objetivoLabel.setText("Objetivo:");
        this.mainPanel.add(this.objetivoLabel, new GridConstraints(4, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.btnLimpar = new JButton();
        this.btnLimpar.setText("LIMPAR");
        this.mainPanel.add(this.btnLimpar, new GridConstraints(5, 1, 1, 1, 0, 1, 3, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.btnMostrar = new JButton();
        this.btnMostrar.setText("MOSTRAR");
        this.mainPanel.add(this.btnMostrar, new GridConstraints(5, 2, 1, 1, 0, 1, 3, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.btnSair = new JButton();
        this.btnSair.setText("SAIR");
        this.mainPanel.add(this.btnSair, new GridConstraints(5, 3, 1, 1, 0, 1, 3, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.txtNome = new JTextField();
        this.mainPanel.add(this.txtNome, new GridConstraints(0, 1, 1, 3, 8, 1, 4, 0, (Dimension)null, new Dimension(150, -1), (Dimension)null, 0, false));
        this.txtIdade = new JTextField();
        this.mainPanel.add(this.txtIdade, new GridConstraints(1, 1, 1, 3, 8, 1, 4, 0, (Dimension)null, new Dimension(150, -1), (Dimension)null, 0, false));
        this.txtPeso = new JTextField();
        this.mainPanel.add(this.txtPeso, new GridConstraints(2, 1, 1, 3, 8, 1, 4, 0, (Dimension)null, new Dimension(150, -1), (Dimension)null, 0, false));
        this.txtAltura = new JTextField();
        this.mainPanel.add(this.txtAltura, new GridConstraints(3, 1, 1, 3, 8, 1, 4, 0, (Dimension)null, new Dimension(150, -1), (Dimension)null, 0, false));
        this.txtObjetivo = new JTextField();
        this.mainPanel.add(this.txtObjetivo, new GridConstraints(4, 1, 1, 3, 8, 1, 4, 0, (Dimension)null, new Dimension(150, -1), (Dimension)null, 0, false));
        this.btnIncluir = new JButton();
        this.btnIncluir.setText("INCLUIR");
        this.mainPanel.add(this.btnIncluir, new GridConstraints(5, 0, 1, 1, 0, 1, 3, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
    }

    public JComponent $$$getRootComponent$$$() {
        return this.mainPanel;
    }

    static class Aluno {
        public int id;
        public String nome;
        public int idade;
        public float peso;
        public float altura;
        public String objetivo;

        Aluno() {
        }
    }
}
