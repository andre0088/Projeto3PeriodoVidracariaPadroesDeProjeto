package DAO;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import DTO.ClienteDTO;
import DTO.ServicoDTO;
import View.Exceptions.ElementoNaoEncontradoException;
import View.Exceptions.FaltaDeDinheiroException;
import View.Exceptions.NaoHaClientesDevendoException;
import View.Exceptions.NaoHaServicosAtrasadosException;
import View.Exceptions.NenhumServicoException;

public class RelatoriosDAO implements InterfaceRelatoriosDAO{

	private InterfaceServicoDAO servicoDao = new ServicoDAO_Postegres();
	private InterfaceClienteDAO clienteDao = new ClienteDAO_Postgres();

	public void quantidadesServicosMensais(int mes,int ano) throws NenhumServicoException{
		Format format= new SimpleDateFormat("MM");
		Format formato= new SimpleDateFormat("yyyy");
		int servicosConcluidos = 0;
		int servicosAndamento = 0;
		
		if(servicoDao.listarServicos().getListaServicos().isEmpty()) {
			throw new NenhumServicoException();
		}
		
		for(ServicoDTO servico: servicoDao.listarServicos().getListaServicos()) {
			GregorianCalendar mesCalPedido= new GregorianCalendar();
			mesCalPedido.setTime(servico.getDataPedido());
			int mesPedido = Integer.parseInt(format.format(mesCalPedido.getTime()));
			int anoPedido = Integer.parseInt(formato.format(mesCalPedido.getTime()));
			if(mesPedido==mes && anoPedido==ano && servico.getStatus().equals("Concluído")) {
				servicosConcluidos+=1;
			}else if(mesPedido==mes && anoPedido==ano && (servico.getStatus().equals("Concluído")==false)) {
				servicosAndamento+=1;
			}
		}
		Document doc = new Document(PageSize.A4, 72, 72, 72, 72);
		
		Timestamp data = new Timestamp(new Date().getTime()); //data pra setar no nome do documento
		String dt = data.toString();
		
		try{
			Date dataEmissao = new Date();
			SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat f = new SimpleDateFormat("MM/yyyy");
			String m = f.format(dataEmissao);
			String dataE = form.format(dataEmissao);
			PdfWriter.getInstance(doc, new FileOutputStream("Quantidade de serviços mensais "+dt.replace("-",".").replace(":","-")+".pdf"));
			doc.open();
			int total = servicosConcluidos + servicosAndamento;
			doc.add(new Paragraph("Data de emissão:  "+dataE));
			doc.add(new Paragraph("Quantidade de serviços do mês "+m+": "));
			doc.add(new Paragraph(" "));
			doc.add(new Paragraph(" "));
			doc.add(new Paragraph("Foi/Foram realizado(s) nesse mês: "+total+" serviço(s) no total."));
			doc.add(new Paragraph("Desse(s) "+total+" serviço(s) : "+servicosConcluidos+" Serviço(s) foi/foram concluído(s) e "+servicosAndamento+" Serviço(s) esta(ão) em andamento."));
			doc.add(new Paragraph("--------------------------------------------------------------------"));
			doc.add(new Paragraph("--------------------------------------------------------------------"));
			JOptionPane.showMessageDialog(null, "PDF gerado com sucesso !");
			doc.close();
			try {
				Desktop.getDesktop().open(new File("Quantidade de serviços mensais "+dt.replace("-",".").replace(":","-")+".pdf"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch(FileNotFoundException | DocumentException e) {}
		
	}
	
	
	public void ganhoDeDinheiroMensal(int mes, int ano) throws NenhumServicoException,FaltaDeDinheiroException{
		Format format= new SimpleDateFormat("MM");
		Format formato= new SimpleDateFormat("yyyy");
		int dinheiroTotal = 0;
		int restante = 0;
		int aux = 0;
		
		if(servicoDao.listarServicos().getListaServicos().isEmpty()) {
			throw new NenhumServicoException();
		}
		
		for(ServicoDTO servico: servicoDao.listarServicos().getListaServicos()) {
			GregorianCalendar mesCalPedido= new GregorianCalendar();
			mesCalPedido.setTime(servico.getDataPedido());
			int mesPedido = Integer.parseInt(format.format(mesCalPedido.getTime()));
			int anoPedido = Integer.parseInt(formato.format(mesCalPedido.getTime()));
			if(mesPedido==mes && anoPedido==ano) {
				dinheiroTotal+=servico.getQtdPago();
				aux+=servico.getPrecoCompleto();
			}
		}
		
		restante = aux-dinheiroTotal;
		
		if(dinheiroTotal==0) {
			throw new FaltaDeDinheiroException();
		}
		
		Timestamp data = new Timestamp(new Date().getTime()); //data pra setar no nome do documento
		String dt = data.toString();
		
		Document doc = new Document(PageSize.A4, 72, 72, 72, 72);
		
		try{
			Date dataEmissao = new Date();
			SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
			String dataE = form.format(dataEmissao);
			SimpleDateFormat f = new SimpleDateFormat("MM/yyyy");
			String m = f.format(dataEmissao);
			PdfWriter.getInstance(doc, new FileOutputStream("Lucro mensal "+dt.replace("-",".").replace(":","-")+".pdf"));
			doc.open();
			doc.add(new Paragraph("Data de emissão:  "+dataE));
			doc.add(new Paragraph("Lucro bruto mensal do mês "+m+": "));
			doc.add(new Paragraph(" "));
			doc.add(new Paragraph(" "));
			doc.add(new Paragraph("Foi ganho nesse mês o dinheiro equivalente à: "+dinheiroTotal+" R$."));
			doc.add(new Paragraph("Resta receber uma quantidade equivalente à: "+restante+" R$."));
			doc.add(new Paragraph("--------------------------------------------------------------------"));
			doc.add(new Paragraph("--------------------------------------------------------------------"));
			JOptionPane.showMessageDialog(null, "PDF gerado com sucesso !");
			doc.close();
			try {
				Desktop.getDesktop().open(new File("Lucro mensal "+dt.replace("-",".").replace(":","-")+".pdf"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch(FileNotFoundException | DocumentException e) {}
		
	}
	
	public void relatorioClientesDevendo() throws NaoHaClientesDevendoException{
		boolean bandeira = false;
		
		for(ServicoDTO servicoDto: servicoDao.listarServicos().getListaServicos()) {
			if(servicoDto.getStatus().equals("Concluído") && servicoDto.getQtdPago()<servicoDto.getPrecoCompleto()) {
				bandeira=true;
			}
		}
		
		if(bandeira==false) {
			throw new NaoHaClientesDevendoException();
		}
		
		Timestamp data = new Timestamp(new Date().getTime()); //data pra setar no nome do documento
		String dt = data.toString();
		
		Date dataEmissao = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		String dataE = formato.format(dataEmissao);
		
		Document doc = new Document(PageSize.A4, 72, 72, 72, 72);
		
		try{
			PdfWriter.getInstance(doc, new FileOutputStream("Clientes com Dívidas Atrasadas "+dt.replace("-",".").replace(":","-")+".pdf"));
			doc.open();
			doc.add(new Paragraph("Data de emissão:  "+dataE));
			doc.add(new Paragraph("Clientes com pagamentos atrasados:"));
			doc.add(new Paragraph(" "));
			doc.add(new Paragraph(" "));
			for(ServicoDTO servicoDto: servicoDao.listarServicos().getListaServicos()) {
				if(servicoDto.getStatus().equals("Concluído") && servicoDto.getQtdPago()<servicoDto.getPrecoCompleto()) {
					ClienteDTO cli = new ClienteDTO();
					cli.setCpf(servicoDto.getCpf());
					ClienteDTO dados = new ClienteDTO();
					try {
						dados = clienteDao.recuperarCliente(cli);
					} catch (ElementoNaoEncontradoException e) {
						e.printStackTrace();
					}
					doc.add(new Paragraph("Dados do Cliente: "));
					doc.add(new Paragraph("Nome - "+dados.getNome()+". Cpf - "+dados.getCpf()+". Endereço - "+dados.getEndereco()+".Telefone - "+dados.getTelefone()+"."));
					doc.add(new Paragraph("Dados do Serviço: "));
					doc.add(new Paragraph("ID do serviço: "+servicoDto.getId()+". Descrição do serviço: "+servicoDto.getDescricao()+". Endereço do serviço: "+servicoDto.getEndereco()));
					Format format= new SimpleDateFormat("dd/MM/yyyy");
					String dataPedido =format.format(servicoDto.getDataPedido());
					String dataEntrega=format.format(servicoDto.getDataEntrega());
					doc.add(new Paragraph("Data de solicitação do serviço: "+dataPedido+". Data de conclusão do serviço: "+dataEntrega));
					doc.add(new Paragraph("Preço completo do serviço: "+servicoDto.getPrecoCompleto()+" R$. Quantidade paga pelo cliente: "+servicoDto.getQtdPago()+" R$. Dinheiro que resta ser pago: "+(servicoDto.getPrecoCompleto()-servicoDto.getQtdPago())+" R$."));
					doc.add(new Paragraph("--------------------------------------------------------------------"));
					doc.add(new Paragraph("--------------------------------------------------------------------"));
				}

			}
			JOptionPane.showMessageDialog(null, "PDF gerado com sucesso !");
			doc.close();
			try {
				Desktop.getDesktop().open(new File("Clientes com Dívidas Atrasadas "+dt.replace("-",".").replace(":","-")+".pdf"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch(FileNotFoundException | DocumentException e) {}
	}

	public void relatorioServicosAtrasados() throws NaoHaServicosAtrasadosException{
		boolean bandeira = false;
		
		for(ServicoDTO servicoDto: servicoDao.listarServicos().getListaServicos()) {
			if((new Date().compareTo(servicoDto.getDataEntrega())==1) && servicoDto.getStatus().equals("Concluído")==false) {
				bandeira=true;
			}
		}
		
		if(bandeira==false) {
			throw new NaoHaServicosAtrasadosException();
		}
		
		Timestamp data = new Timestamp(new Date().getTime()); //data pra setar no nome do documento
		String dt = data.toString();
		
		Document doc = new Document(PageSize.A4, 72, 72, 72, 72);
		
		try{
			Date dataEmissao = new Date();
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			String dataE = formato.format(dataEmissao);
			PdfWriter.getInstance(doc, new FileOutputStream("Serviços Atrasados "+dt.replace("-",".").replace(":","-")+".pdf"));
			doc.open();
			doc.add(new Paragraph("Data de emissão:  "+dataE));
			doc.add(new Paragraph("Servicos atrasados: "));
			doc.add(new Paragraph(""));
			doc.add(new Paragraph(""));
			for(ServicoDTO servicoDto: servicoDao.listarServicos().getListaServicos()) {
				if((new Date().compareTo(servicoDto.getDataEntrega())==1) && servicoDto.getStatus().equals("Concluído")==false) {
					String nome = clienteDao.nomeCliente(servicoDto).getNome();
					doc.add(new Paragraph("Dados do Serviço: "));
					doc.add(new Paragraph("Nome do cliente - "+nome+" .ID do serviço: "+servicoDto.getId()+". Descrição do serviço: "+servicoDto.getDescricao()+". Endereço do serviço: "+servicoDto.getEndereco()));
					Format format= new SimpleDateFormat("dd/MM/yyyy");
					String dataPedido =format.format(servicoDto.getDataPedido());
					String dataEntrega=format.format(servicoDto.getDataEntrega());
					doc.add(new Paragraph("Data de solicitação do serviço: "+dataPedido+". Data que foi estimada para a conclusão do serviço: "+dataEntrega));
					doc.add(new Paragraph("Preço completo do serviço: "+servicoDto.getPrecoCompleto()+" R$. Quantidade paga pelo cliente: "+servicoDto.getQtdPago()+" R$. Dinheiro que resta ser pago: "+(servicoDto.getPrecoCompleto()-servicoDto.getQtdPago())+" R$."));
					doc.add(new Paragraph("--------------------------------------------------------------------"));
					doc.add(new Paragraph("--------------------------------------------------------------------"));
				}
			}
			JOptionPane.showMessageDialog(null, "PDF gerado com sucesso !");
			doc.close();
			try {
				Desktop.getDesktop().open(new File("Serviços Atrasados "+dt.replace("-",".").replace(":","-")+".pdf"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch(FileNotFoundException | DocumentException e) {}
	}
	
	public void gerarNota(ServicoDTO compra){
		
		Timestamp data = new Timestamp(new Date().getTime()); //data pra setar no nome do documento
		String dt = data.toString();
		
		
		String barra="584884 587588 599125 8 118885555";
		
		Document boleto=new Document();
    	String arquivoPdf="Nota"+dt.replace("-",".").replace(":","-")+".pdf";
    	
    	try {
    	     PdfWriter.getInstance(boleto, new FileOutputStream(arquivoPdf));
    	     boleto.open();
    	     Paragraph p  =new Paragraph("Nota.pdf");
    	     p.setAlignment(1);
    	     boleto.add(p);
    	     p=new Paragraph("\n");
    	     boleto.add(p);
    	     
    	     
    	     Date d = new Date();
    	     SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    	     
   	         String hoje = formato.format(d);
   	         
   	         double valor = compra.getPrecoCompleto();
   	         
    	     PdfPTable titulo=new PdfPTable(1);
    	     PdfPCell nome1=new PdfPCell(new Paragraph("NOTA DE PAGAMENTO"));
    	     nome1.setBackgroundColor(new BaseColor(139, 137, 137));
    	     nome1.setBorder(5);
    	     nome1.setHorizontalAlignment(1);
    	     titulo.addCell(nome1);
    	     
    	     PdfPTable titulo2=new PdfPTable(1);
    	     PdfPCell nome11=new PdfPCell(new Paragraph("BB - 001  ||  "+barra+valor));
    	     nome11.setBorder(5);
    	     nome11.setHorizontalAlignment(0);
    	     titulo2.addCell(nome11);
    	    
    	     
    	     
    	     
    	      
    	     PdfPTable cobrador=new PdfPTable(2);
    	     PdfPCell celula=new PdfPCell(new Paragraph("Banco do Brasil"+"\n---------------------------------------------------"+"\nData emissao: "+hoje+"\n---------------------------------------------------"
    	     +"\nAgencia: 0229-1 "+"\n---------------------------------------------------"+"\nConta: 3.669-9"+"\n   "));
    	     celula.setBackgroundColor(new BaseColor(139, 137, 137));
    	     celula.setBorder(5);
    	     celula.setHorizontalAlignment(0);
    	     PdfPCell celula1=new PdfPCell(new Paragraph("COBRADOR:\n"+"\nEmpresa: Lima Vidros\n" +"\nEndereço: Rua da Benção, nº 35"
    	     +"\n" +"\nCNPJ: 32.111.324/0001-52\n     "));
    	     cobrador.addCell(celula1);
    	     cobrador.addCell(celula);
    	     
    	     
    	     PdfPTable tabela2=new PdfPTable(2);
    	     PdfPCell celula3=new PdfPCell(new Paragraph("PAGADOR:\n"+"\nNome: "+clienteDao.nomeCliente(compra).getNome()+"\n" + "\nEndereço: "+compra.getEndereco()
    	     +"\n" +"\nCPF: "+compra.getCpf()+"\n   "));
    	     Date dataVencimento = compra.getDataEntrega();
    	     String vencimento = formato.format(dataVencimento);
    	     PdfPCell celula4=new PdfPCell(new Paragraph("Vencimento: "+vencimento+""+"\n---------------------------------------------------"
    	     +"\nValor: R$ "+valor+",00"+"\n---------------------------------------------------"+"\n   "));
    	     tabela2.addCell(celula3);
    	     tabela2.addCell(celula4);
    	   
    	    
    	     PdfPTable barras=new PdfPTable(1);
    	     PdfPCell br=new PdfPCell(new Paragraph("\nCodigo de Barras" +"\n"+barra+valor));
    	     br.setBackgroundColor(new BaseColor(139, 137, 137));
    	     br.setBorder(5);
    	     br.setHorizontalAlignment(0);
    	     barras.addCell(br);
    	     
    	     
    	     Paragraph p1  =new Paragraph("_____________________");
    	     p1.setAlignment(1);
    	     boleto.add(p1);
    	     p1=new Paragraph("\n");
    	     boleto.add(p1);
    	     
    	     
             
    	    
             boleto.add(titulo);
             boleto.add(titulo2);
   	         boleto.add(cobrador);
   	         boleto.add(tabela2);
   	         boleto.add(barras);
   	         boleto.close();
   	         Desktop.getDesktop().open(new File("Nota"+dt.replace("-",".").replace(":","-")+".pdf"));
    	     
    	}catch(Exception e) {
    		JOptionPane.showMessageDialog(null, "Erro não foi possivel geral a Nota !");
    		
    	}
	}

}
