package DAO;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import Connection.Conexao;
import DTO.ServicoDTO;
import View.Exceptions.DadosNaoPreenchidosException;

public class ServicoDAO_Postegres implements InterfaceServicoDAO{

	@Override
	public boolean cadastrarServico(ServicoDTO servicoDto)	throws DadosNaoPreenchidosException{
		boolean bandeira = true;
		
		if(servicoDto.getCpf().equals("") || servicoDto.getDataEntrega()==null || servicoDto.getDescricao().equals("") || servicoDto.getEndereco().equals("") || 
				servicoDto.getId() == 0 || servicoDto.getPrecoCompleto()==0 || servicoDto.getStatus().contentEquals("")) {
			bandeira = false;
			throw new DadosNaoPreenchidosException();
		}
	
		Connection con = Conexao.getInstancia();
		PreparedStatement pst;
		try {
			pst = con.prepareStatement("INSERT INTO servico (cpf,descricao,endereco,preco_completo,"
					+ "qtd_paga,data_pedido,data_entrega,status) VALUES (?,?,?,?,?,?,?,?)");
			pst.setString(1, servicoDto.getCpf());
			pst.setString(2, servicoDto.getDescricao());
			pst.setString(3, servicoDto.getEndereco());
			pst.setFloat(4, servicoDto.getPrecoCompleto());
			pst.setFloat(5, servicoDto.getQtdPago());
			java.sql.Date dtPedido = new java.sql.Date(servicoDto.getDataPedido().getTime()); 
			java.sql.Date dtentrega = new java.sql.Date(servicoDto.getDataEntrega().getTime()); 
			pst.setDate(6, dtPedido);
			pst.setDate(7, dtentrega);
			pst.setString(8, servicoDto.getStatus());
			pst.execute();
			Conexao.fecharConexao();
			bandeira = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return bandeira;
	}

	@Override
	public void deletarServico(ServicoDTO servicoDto) {
		Connection con = Conexao.getInstancia();
		PreparedStatement pst;
		try {
			pst = con.prepareStatement("DELETE FROM servico where id=?");
			pst.setInt(1, servicoDto.getId());
			pst.execute();
			Conexao.fecharConexao();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void editarServico(ServicoDTO servicoDto) throws DadosNaoPreenchidosException {

		if(servicoDto.getCpf().equals("") || servicoDto.getDataEntrega()==null || servicoDto.getDescricao().equals("") || servicoDto.getEndereco().equals("") || 
				servicoDto.getId()==0 || servicoDto.getPrecoCompleto()==0 || servicoDto.getStatus().contentEquals("")) {
			throw new DadosNaoPreenchidosException();
		}
		Connection con = Conexao.getInstancia();
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(	"update servico set cpf=?, descricao=?, endereco=?, preco_completo=?, "+ 
									"qtd_paga=?, data_pedido=?, data_entrega=?, status=? where id ='"+servicoDto.getId()+"'");
			pst.setString(1, servicoDto.getCpf());
			pst.setString(2, servicoDto.getDescricao());
			pst.setString(3, servicoDto.getEndereco());
			pst.setFloat(4, servicoDto.getPrecoCompleto());
			pst.setFloat(5, servicoDto.getQtdPago());
			java.sql.Date dtPedido = new java.sql.Date(servicoDto.getDataPedido().getTime()); 
			java.sql.Date dtentrega = new java.sql.Date(servicoDto.getDataEntrega().getTime()); 
			pst.setDate(6, dtPedido);
			pst.setDate(7, dtentrega);
			pst.setString(8, servicoDto.getStatus());
			pst.execute();
			Conexao.fecharConexao();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public ServicoDTO listarServicos() {
		Connection con = Conexao.getInstancia();
		PreparedStatement pst;
		ServicoDTO serivico = new ServicoDTO();
		try {
			pst = con.prepareStatement("SELECT * FROM Servico");
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				ServicoDTO serv = new ServicoDTO();
				serv.setId(rs.getInt("id"));
				serv.setCpf(rs.getString("cpf"));
				serv.setDescricao(rs.getString("descricao"));
				serv.setEndereco(rs.getString("endereco"));
				serv.setPrecoCompleto(rs.getFloat("preco_completo"));
				serv.setQtdPago(rs.getFloat("qtd_paga"));
				serv.setDataPedido(rs.getDate("data_pedido"));
				serv.setDataEntrega(rs.getDate("data_entrega"));
				serv.setStatus(rs.getString("status"));
				serivico.getListaServicos().add(serv);
			}
			rs.close();
			Conexao.fecharConexao();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return serivico;
	}

	
	
}
