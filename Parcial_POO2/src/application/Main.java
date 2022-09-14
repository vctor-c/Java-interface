package application;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import db.DB;
import db.DbException;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textID;
	private JTextField textDescricao;
	private JTextField textCAD;
	private JTable tabela_DB;
	private JButton btnEditar;
	private JButton btnConfirmarEdicao;
	private JButton btnCancelar;
	private JButton btnGravar;
	private JButton btnExcluir;
	private JButton btnNovo;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() { 
		this.setTitle("Parcial POO2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 734, 437);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblId.setBounds(20, 29, 17, 13);
		contentPane.add(lblId);

		JLabel lblDataCadastro = new JLabel("Data Cadastro");
		lblDataCadastro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDataCadastro.setBounds(171, 29, 102, 13);
		contentPane.add(lblDataCadastro);

		JLabel lblDescricao = new JLabel("Descrição");
		lblDescricao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDescricao.setBounds(20, 85, 89, 13);
		contentPane.add(lblDescricao);

		textID = new JTextField();
		textID.setEditable(false);
		textID.setBounds(47, 28, 96, 19);
		contentPane.add(textID);
		textID.setColumns(10);

		textDescricao = new JTextField();
		textDescricao.setEditable(false);
		textDescricao.setBounds(89, 84, 315, 19);
		contentPane.add(textDescricao);
		textDescricao.setColumns(10);

		textCAD = new JTextField();
		textCAD.setEditable(false);
		textCAD.setBounds(268, 28, 136, 19);
		contentPane.add(textCAD);
		textCAD.setColumns(10);

		btnNovo = new JButton("Novo");
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEditar.setVisible(false);
				btnExcluir.setVisible(false);
				btnNovo.setVisible(false);
				btnCancelar.setBounds(433,83,85,21);
				btnCancelar.setVisible(true);
				textDescricao.setEditable(true);
				btnGravar.setVisible(true);
				limparCampos();
				textDescricao.requestFocus();
			}
		});
		btnNovo.setBounds(433, 83, 85, 21);
		contentPane.add(btnNovo);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancelar.setVisible(false);
				btnConfirmarEdicao.setVisible(false);
				btnEditar.setVisible(true);
				btnExcluir.setVisible(true);
				btnGravar.setVisible(false);
				btnNovo.setVisible(true);
				linhaSelecionada();
				
			}
		});
		contentPane.add(btnCancelar);
		btnCancelar.setVisible(false);
		
		btnConfirmarEdicao = new JButton("OK");
		btnConfirmarEdicao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancelar.setVisible(false);
				btnConfirmarEdicao.setVisible(false);
				btnNovo.setVisible(true);
				editarProduto();
			}
		});
		btnConfirmarEdicao.setBounds(433, 27, 85, 21);
		contentPane.add(btnConfirmarEdicao);
		btnConfirmarEdicao.setVisible(false);
		

		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnExcluir.setVisible(false);
				btnNovo.setVisible(false);
				textDescricao.setEditable(true);
				btnEditar.setVisible(false);
				btnConfirmarEdicao.setVisible(true);
				btnCancelar.setBounds(433, 55, 85, 21);
				btnCancelar.setVisible(true);
			}
		});
		
		btnEditar.setBounds(433, 27, 85, 21);
		contentPane.add(btnEditar);
		btnEditar.setVisible(false);
		btnGravar = new JButton("Gravar");
		btnGravar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textDescricao.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Digite uma Descrição");
				} else {
					btnCancelar.setVisible(false);
					btnGravar.setVisible(false);
					btnNovo.setVisible(true);
					gravarProduto();
				}

			}
		});
		btnGravar.setBounds(528, 83, 85, 21);
		contentPane.add(btnGravar);
		btnGravar.setVisible(false);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnExcluir.setVisible(false);
				btnNovo.setVisible(true);
				btnConfirmarEdicao.setVisible(false);
				btnCancelar.setVisible(false);
				excluirProduto();
			}
		});
		btnExcluir.setBounds(528, 83, 85, 21);
		contentPane.add(btnExcluir);
		btnExcluir.setVisible(false);

		tabela_DB = new JTable(){

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
	    };
		tabela_DB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnGravar.setVisible(false);
				btnExcluir.setVisible(true);
				btnConfirmarEdicao.setVisible(false);
				btnCancelar.setVisible(false);
				btnEditar.setVisible(true);
				linhaSelecionada();
			}
		});
		tabela_DB.setBounds(10, 144, 700, 246);
		contentPane.add(tabela_DB);


		listarProduto();
	}

	private void listarProduto() { // consulta tabela no banco de dados e exibe no Jtable
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = DB.getConnection();
			if (conn == null) {
				JOptionPane.showMessageDialog(null, "Falha em Conectar o Banco de Dados");
			} else {
				st = conn.createStatement();
				rs = st.executeQuery("select * from produto");
				String[] colunasTabela = new String[] { "ID", "DESCRICAO", "POSICAO" };
				DefaultTableModel modeloTabela = new DefaultTableModel(null, colunasTabela);
				modeloTabela.addRow(new String[] { "ID", "DESCRICAO", "CADASTRO" });
				if (rs != null) {
					while (rs.next()) {

						modeloTabela.addRow(new String[] { String.valueOf(rs.getInt("id")), rs.getString("descricao"),
								rs.getString("data_cadastro") });
					}
				}
				tabela_DB.setModel(modeloTabela);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DbException(e.getMessage());
		} finally {
			if (st != null) {
				DB.closeStatement(st);
			}
			if (rs != null) {
				DB.closeResultSet(rs);
			}
			if (conn != null) {
				DB.closeConnection();
			}
		}

	}

	private void gravarProduto() {// grava novo produto com base na descricao. id e data de cadastro são auto
									// incremento e gerenciadas pelo banco de dados
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			if (conn == null) {
				JOptionPane.showMessageDialog(null, "Falha em Conectar o Banco de Dados");
			} else {
				st = conn.prepareStatement("INSERT INTO produto" + "(descricao, data_cadastro)" + "VALUES " + "(?, ?)");
				st.setString(1, textDescricao.getText());
				st.setString(2, null);
				st.executeUpdate();
				limparCampos();
				desabilitarCampos();
				listarProduto();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DbException(e.getMessage());
		} finally {
			if (st != null) {
				DB.closeStatement(st);
			}
			if (conn != null) {
				DB.closeConnection();
			}

		}

	}
	private void editarProduto() { // exclui produto selecionado com base no ID
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			if (conn == null) {
				JOptionPane.showMessageDialog(null, "Falha em Conectar o Banco de Dados");
			} else {
				st = conn.prepareStatement("UPDATE produto " 
						+ "SET descricao = ? "
						+"WHERE " + "id = ?");
				st.setString(1, textDescricao.getText());
				st.setInt(2, Integer.parseInt(textID.getText()));
				st.executeUpdate();
				limparCampos();
				desabilitarCampos();
				listarProduto();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DbException(e.getMessage());
		} finally {
			if (st != null) {
				DB.closeStatement(st);
			}
			if (conn != null) {
				DB.closeConnection();
			}

		}

	}
	private void excluirProduto() { // exclui produto selecionado com base no ID
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			if (conn == null) {
				JOptionPane.showMessageDialog(null, "Falha em Conectar o Banco de Dados");
			} else {
				st = conn.prepareStatement("DELETE FROM produto " + "WHERE " + "id = ?");
				st.setInt(1, Integer.parseInt(textID.getText()));
				st.executeUpdate();
				limparCampos();
				desabilitarCampos();
				listarProduto();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DbException(e.getMessage());
		} finally {
			if (st != null) {
				DB.closeStatement(st);
			}
			if (conn != null) {
				DB.closeConnection();
			}

		}

	}

	private void desabilitarCampos() { // impede que os campos sejam editados
		textDescricao.setEditable(false);
		textID.setEditable(false);
		textCAD.setEditable(false);

	}

	private void linhaSelecionada() { // Preenche os campos de descricao, id e data de cadastro com os dados da linha
										// selecionada na tabela
		desabilitarCampos();
		DefaultTableModel tableModel = (DefaultTableModel) tabela_DB.getModel();
		int row = tabela_DB.getSelectedRow();
		if (row < 0) {
			row = 0;
			btnExcluir.setVisible(false);
			btnEditar.setVisible(false);
		}
		if (tableModel.getValueAt(row, 0).toString() != "ID") {
			textID.setText(tableModel.getValueAt(row, 0).toString());
			textDescricao.setText(tableModel.getValueAt(row, 1).toString());
			textCAD.setText(tableModel.getValueAt(row, 2).toString());
		}
	}

	private void limparCampos() { // Limpa campos de descricao, id, e data de cadastro
		textDescricao.setText(null);
		textID.setText(null);
		textCAD.setText(null);
		btnEditar.setVisible(false);
	}
}
