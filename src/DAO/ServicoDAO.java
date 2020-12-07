package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import DTO.ServicoDTO;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoDuplicadoException;

public class ServicoDAO implements InterfaceServicoDAO{
	
	private XStream xstream = new XStream(new DomDriver("ISO-8859-1"));
	private File arquivo = new File("servicos.xml");
	
	private ArrayList<ServicoDTO> listaServicos = new ArrayList<ServicoDTO>();
	
	public ServicoDAO() {
		this.listaServicos=recuperarServicos();
	}
	
	
	public void salvarServicos(ArrayList<ServicoDTO> listaServicos){
		String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n";
		xml += xstream.toXML(listaServicos);
		try {
			if(!arquivo.exists()) {
				arquivo.createNewFile();
			}
			PrintWriter gravar = new PrintWriter(arquivo);
			gravar.print(xml);
			gravar.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ServicoDTO> recuperarServicos() {
		try {
			if(arquivo.exists()) {
				FileInputStream fis = new FileInputStream(arquivo);
				return (ArrayList<ServicoDTO>)xstream.fromXML(fis);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return new ArrayList<ServicoDTO>();
	}
	
	@Override
	public boolean cadastrarServico(ServicoDTO servicoDto) throws DadosNaoPreenchidosException, ElementoDuplicadoException{
		boolean bandeira = false;
		
		if(servicoDto.getCpf().equals("") || servicoDto.getDataEntrega()==null || servicoDto.getDescricao().equals("") || servicoDto.getEndereco().equals("") || 
				servicoDto.getId()==0 || servicoDto.getPrecoCompleto()==0 || servicoDto.getStatus().equals("")) {
			throw new DadosNaoPreenchidosException();
		}
		
		
		if(listaServicos.isEmpty()==false) {
			for(ServicoDTO servico :listaServicos) {
				if(servico.getId()==servicoDto.getId()) {
					bandeira=true;
				}
			}
	
		}
		
		if(bandeira == true) {
			throw new ElementoDuplicadoException();
		}
		
		listaServicos.add(servicoDto);
		salvarServicos(listaServicos);
		
		return bandeira;
		
	}
	
	public boolean contains(ServicoDTO servico) {
		boolean bandeira = false;
		
		for(ServicoDTO servicoDto :listaServicos) {
			if(servico.getId()==servicoDto.getId()) {
				bandeira=true;
			}
		}
		
		return bandeira;
	}

	@Override
	public void deletarServico(ServicoDTO servicoDto) {
		for(int i =0;i<listaServicos.size();i++) {
			if(listaServicos.get(i).getId() == servicoDto.getId()) {
				listaServicos.remove(i);
			}
		}
		salvarServicos(listaServicos);
		
	}

	@Override
	public void editarServico(ServicoDTO servicoDto) throws DadosNaoPreenchidosException{
		
		if(servicoDto.getCpf().equals("") || servicoDto.getDataEntrega()==null || servicoDto.getDescricao().equals("") || servicoDto.getEndereco().equals("") || 
				servicoDto.getId()==0 || servicoDto.getPrecoCompleto()==0 || servicoDto.getStatus().contentEquals("")) {
			throw new DadosNaoPreenchidosException();
		}
		
		for(ServicoDTO servico :listaServicos) {
			if(servico.getId()==(servicoDto.getId())) {
				servico.setCpf(servicoDto.getCpf());
				servico.setDataEntrega(servicoDto.getDataEntrega());
				servico.setDataPedido(servicoDto.getDataPedido());
				servico.setDescricao(servicoDto.getDescricao());
				servico.setEndereco(servicoDto.getEndereco());
				servico.setPrecoCompleto(servicoDto.getPrecoCompleto());
				servico.setStatus(servicoDto.getStatus());
				salvarServicos(listaServicos);
			}
		}
		
	}

	@Override
	public ServicoDTO listarServicos() {
		ServicoDTO servicoDto = new ServicoDTO();
		for(ServicoDTO servico:listaServicos){
			servicoDto.getListaServicos().add(servico);
		}
		return servicoDto;
	}

	
}
