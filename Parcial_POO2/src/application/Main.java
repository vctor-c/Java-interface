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
	private JLabel lblQuant;
	private JTextField textQuant;
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
				textQuant.setEditable(true);
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
				try {
					editarProduto();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnConfirmarEdicao.setBounds(625, 27, 85, 21);
		contentPane.add(btnConfirmarEdicao);
		btnConfirmarEdicao.setVisible(false);
		

		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnExcluir.setVisible(false);
				btnNovo.setVisible(false);
				textDescricao.setEditable(true);
				textQuant.setEditable(true);
				btnEditar.setVisible(false);
				btnConfirmarEdicao.setVisible(true);
				btnCancelar.setBounds(625, 55, 85, 21);
				btnCancelar.setVisible(true);
			}
		});
		
		btnEditar.setBounds(625, 27, 85, 21);
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
					try {
						gravarProduto();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
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
				try {
					excluirProduto();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
		
		lblQuant = new JLabel("Quantidade:");
		lblQuant.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQuant.setBounds(414, 21, 89, 29);
		contentPane.add(lblQuant);
		
		textQuant = new JTextField();
		textQuant.setEditable(false);
		textQuant.setBounds(492, 28, 96, 19);
		contentPane.add(textQuant);
		textQuant.setColumns(10);


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
				rs = st.executeQuery("select * from produto p inner join estoque e on p.id = e.id");
				String[] colunasTabela = new String[] { "ID", "DESCRICAO", "POSICAO", "QUANTIDADE" };
				DefaultTableModel modeloTabela = new DefaultTableModel(null, colunasTabela);
				modeloTabela.addRow(new String[] { "ID", "DESCRICAO", "CADASTRO", "QUANTIDADE" });
				if (rs != null) {
					while (rs.next()) {

						modeloTabela.addRow(new String[] { String.valueOf(rs.getInt("id")), rs.getString("descricao"),
								rs.getString("data_cadastro"), String.valueOf(rs.getInt("quantidade")) });
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

	private void gravarProduto() throws SQLException {// grava novo produto com base na descricao. id e data de cadastro são auto
									// incremento e gerenciadas pelo banco de dados
		Connection conn = null;
		PreparedStatement ps = null;
		Statement st = null;
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			if (conn == null) {
				JOptionPane.showMessageDialog(null, "Falha em Conectar o Banco de Dados");
			} else {
				ps = conn.prepareStatement("INSERT INTO produto" + "(descricao, data_cadastro)" + "VALUES " + "(?, ?)", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, textDescricao.getText());
				ps.setString(2, null);
				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				int idUltimo = 0;
				while (rs.next()) {
					 idUltimo = rs.getInt(1);
				}
				Statement stmt = conn.createStatement();
		        String query="insert into estoque(id,quantidade) values("
		               +idUltimo+","+textQuant.getText()+")";
		        stmt.executeUpdate(query);
				conn.commit();
				limparCampos();
				desabilitarCampos();
				listarProduto();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			conn.rollback();
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
	private void editarProduto() throws SQLException { // exclui produto selecionado com base no ID
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			if (conn == null) {
				JOptionPane.showMessageDialog(null, "Falha em Conectar o Banco de Dados");
			} else {
				st = conn.prepareStatement("UPDATE produto " 
						+ "SET descricao = ? "
						+"WHERE " + "id = ?");
				st.setString(1, textDescricao.getText());
				st.setInt(2, Integer.parseInt(textID.getText()));
				st.executeUpdate();
				Statement stmt = conn.createStatement();
		        String query="UPDATE estoque " 
						+ "SET quantidade = " + textQuant.getText()
						+" WHERE " + "id = " + textID.getText();
		        stmt.executeUpdate(query);
				conn.commit();
				limparCampos();
				desabilitarCampos();
				listarProduto();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			conn.rollback();
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
	private void excluirProduto() throws SQLException { // exclui produto selecionado com base no ID
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			if (conn == null) {
				JOptionPane.showMessageDialog(null, "Falha em Conectar o Banco de Dados");
			} else {
				st = conn.prepareStatement("DELETE FROM produto " + "WHERE " + "id = ?");
				st.setInt(1, Integer.parseInt(textID.getText()));
				st.executeUpdate();
				Statement stmt = conn.createStatement();
		        String query="DELETE FROM estoque "
						+"WHERE " + "id = " + textID.getText();
		        stmt.executeUpdate(query);
				conn.commit();
				limparCampos();
				desabilitarCampos();
				listarProduto();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			conn.rollback();
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
		textQuant.setEditable(false);

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
			textQuant.setText(tableModel.getValueAt(row, 3).toString());
		}
	}

	private void limparCampos() { // Limpa campos de descricao, id, e data de cadastro
		textDescricao.setText(null);
		textID.setText(null);
		textCAD.setText(null);
		textQuant.setText(null);
		btnEditar.setVisible(false);
	}
}
