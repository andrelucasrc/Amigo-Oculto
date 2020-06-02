package Interfaces;

import java.util.Scanner;
import Props.Grupo;
import Props.Convite;
import Utils.Utils;

/*	Interface principal do crud
 */
public class Interface{
	private Sugestoes iSugestoes;
	private Grupos iGrupos;
	private Convites iConvites;
    private Usuarios iUsuarios;
	private String email;
	private String dataFormato;

    public Interface(String nomeArquivo){
        try{
			iSugestoes = new Sugestoes(nomeArquivo);
			iGrupos = new Grupos(nomeArquivo);
			iConvites = new Convites(nomeArquivo);
            iUsuarios = new Usuarios(nomeArquivo);
			dataFormato = "dd/MM/yyyy HH:mm";
			email = "";
        }catch(Exception e){
            System.out.print("Erro ao criar interface: ");
            e.printStackTrace();
        }//end of catch
    }//end of Sistema constructor
	
	private void atualizarToken(int token,String e){
		email = e;
		iGrupos.changeLogged(token);
		iSugestoes.changeLogged(token);
	}//end of atualizar Token

	//Menu de login:
	public void menu(){
		try{
			boolean end = false;
			Scanner reader = new Scanner(System.in);
			int opcao;
			while(!end){
				Utils.limparTela();
				System.out.printf("Amigo Oculto 1.0\n=============================\n\nACESSO:\n\n");
				System.out.println("1)Acesso ao sistema");
				System.out.println("2)Novo usuario (primeiro acesso)");
				System.out.printf("\n\n0)Sair");
				System.out.printf("\n\nOpção: ");
				opcao = reader.nextInt();
				reader.nextLine();
				switch(opcao){
					case(1):
						int token = iUsuarios.acessar(reader);
						String email = iUsuarios.getEmail(token);
						if(token >= 0){
							atualizarToken(token,email);
							telaPrincipal(reader);
						}//end of valid user
						break;
					case(2):
						iUsuarios.criar(reader);
						break;
					case(0):
						end = true;
						break;
					default:
						System.out.println("Opção inválida, tente novamente.");
				}//end of switch
			}//end of while
			reader.close();
		}catch(Exception e){
			System.out.print("Erro no menu: ");
			e.printStackTrace();
		}//end of catch
	}//end of menu

	//Menu principal:
    private void telaPrincipal(Scanner entrada){
		boolean end = false;
		int opcao;
		int quantidade = iConvites.quantConvites(email);
		while(!end){
			Utils.limparTela();
			System.out.printf("Amigo Oculto 1.0\n=============================\n\nINÍCIO:\n\n");
			System.out.println("1)Sugestão de presentes.");
			System.out.println("2)Grupos.");
			System.out.println("3)Novos convites: " + quantidade);
			System.out.printf("\n0)Sair\n\n");
			System.out.print("Opção: ");
			opcao = entrada.nextInt();
			entrada.nextLine();
			switch(opcao){
				case 0:
					end = true;
					break;
				case 1:	
					iSugestoes.tela(entrada);
					break;
				case 2:
					telaDeGrupos(entrada);
					break;
				case 3:
					telaConvitesAlteracao(entrada);
					break;
				default:
					System.out.print("Entrada inválida, tente novamente: ");
					entrada.nextLine();
			}//end of menu choices
		}//end of main screen loop
		atualizarToken(-1, "");
	}//end of telaPrincipal

	//Menu de grupos:
	public void telaDeGrupos(Scanner entrada){
		int opcao;
		boolean end = false;
		while(!end){
			Utils.limparTela();
			System.out.println("Amigo Oculto 1.0\n================================================");
			System.out.println("INÍCIO > GRUPOS: \n");
			System.out.println("1) Criação e gerenciamento grupos.");
			System.out.println("2) Participação nos grupos.\n");
			System.out.println("0) Retornar ao início\n");
			System.out.print("Digite sua opção: ");
			opcao = entrada.nextInt();
			entrada.nextLine();
			switch(opcao){
				case 0:
					end = true;
					break;
				case 1:
					grupos(entrada);
					break;
				case 2:
					//Participação nos grupos
					break;
				default:
					System.out.println("Opção inválida, tente novamente");
					Utils.travarTela(entrada);
			}//end of switch
		}//end of while
	}//end of telaGrupos

	//Menu de gerenciamento de grupos, convites, participantes e sorteio:
	private void grupos(Scanner entrada){
		int opcao;
		boolean end = false;
		while(!end){
			Utils.limparTela();
			System.out.println("Amigo Oculto 1.0\n================================================");
			System.out.println("INÍCIO > GRUPOS > MENU GRUPOS: \n");
			System.out.println("1) Grupos");
			System.out.println("2) Convites");
			System.out.println("3) Participantes");
			System.out.println("4) Sorteio\n");
			System.out.println("0) Retornar ao menu de Grupos\n");
			System.out.print("Digite sua opção: ");
			opcao = entrada.nextInt();
			entrada.nextLine();
			switch(opcao){
				case 0:
					end = true;
					break;
				case 1:
					iGrupos.gerenciamento(entrada);
					break;
				case 2:
					telaDeConvites(entrada);
					break;
				case 3:
					//participantes
					break;
				case 4:
					//sorteio
					break;
				default:
					System.out.println("Opção inválida.");
					Utils.travarTela(entrada);
			}//end of switch
		}//end of while
	}//end of grupos
	
	//Menu de convites:
	private void telaDeConvites(Scanner entrada){
		int opcao;
		boolean end = false;
		while(!end){
			Utils.limparTela();
			System.out.println("Amigo Oculto 1.0\n================================================");
			System.out.println("INÍCIO > GRUPOS > MENU GRUPOS > GERENCIAMENTO DE CONVITES: \n");
			System.out.println("1) Listagem dos convites");
			System.out.println("2) Emissão dos convites");
			System.out.println("3) Cancelamento dos convites\n");
			System.out.println("0) Retornar ao menu anterior\n");
			System.out.print("Digite sua opção: ");
			opcao = entrada.nextInt();
			entrada.nextLine();
			switch(opcao){
				case 1:
					telaDeConvitesListagem(entrada);
					break;
				case 2:
					telaDeConvitesEmissao(entrada);
					break;
				case 3:
					telaDeConvitesCancelamento(entrada);
					break;
				case 0:
					end = true;
					break;
				default:
					System.out.println("Opção inválida, tente novamente.");
					Utils.travarTela(entrada);
			}//end of switch
		}//end of while
	}//end of telaDeConvites

	//Menu anterior ao de listagem de convites, um auxiliar:
	private void telaDeConvitesListagem(Scanner entrada){
		try{
			Utils.limparTela();
			//Pegar todos os grupos:
			Grupo[] g = iGrupos.getGrupos();
			System.out.println("INÍCIO > GRUPOS > MENU GRUPOS > GERENCIAMENTO DE CONVITES > LISTAGEM: \n");
			System.out.println("Meus grupos: \n ");
			//Listar todos os grupos:
			for(int i = 0; i < g.length; i++ ){
				System.out.println( (i + 1) + ". " + g[i]);
			}//end of for
			//Pegar qual o grupo desejado do usuario:
			System.out.print("Digite a opção desejada para a listagem de convites: ");
			int opcao = entrada.nextInt();
			entrada.nextLine();
			opcao -= 1;
			//Verificar se a opção digitada é válida:
			if( opcao < 0 || opcao > g.length -1)
				throw new Exception("Opção inválida.");

			if( !g[opcao].estaAtivo())
				throw new Exception("Grupo não está ativo.");

			if( g[opcao].sorteado())
				throw new Exception("Grupo já foi sorteado.");
			
			//Chamar o método de convite listagem de convite:
			iConvites.listar(g[opcao].getID(), entrada, g[opcao].getNome());
		} catch ( Exception e ) {
			System.out.println("Erro ao listar convites.");
			e.printStackTrace();
			Utils.travarTela(entrada);
		}//end of try
	}//end of listagem

	//Método anterior ao método de emissão de convites:
	private void telaDeConvitesEmissao(Scanner entrada){
		try{
			Utils.limparTela();
			//Pegar todos os grupos:
			Grupo[] g = iGrupos.getGrupos();
			System.out.println("INÍCIO > GRUPOS > MENU GRUPOS > GERENCIAMENTO DE CONVITES > EMISSÃO: \n");
			System.out.println("Meus grupos: \n ");
			//Listar grupos:
			for(int i = 0; i < g.length; i++ ){
				System.out.println( (i + 1) + ". " + g[i]);
			}//end of for
			//Pegar qual o grupo desejado do usuario:
			System.out.print("Digite a opção desejada para a emissão de convites: ");
			int opcao = entrada.nextInt();
			entrada.nextLine();
			opcao -= 1;
			//Verificar se a opção digitada é válida:
			if( opcao < 0 || opcao > g.length -1)
				throw new Exception("Opção inválida.");

			if( !g[opcao].estaAtivo())
				throw new Exception("Grupo não está ativo.");

			if( g[opcao].sorteado())
				throw new Exception("Grupo já foi sorteado.");
			
			//Chamar o método de convite emissão de convite:
			iConvites.emitir(g[opcao].getID(), entrada, g[opcao].getNome());
		} catch ( Exception e ) {
			System.out.println("Erro ao emissão convites.");
			e.printStackTrace();
			Utils.travarTela(entrada);
		}//end of try
	}//end of emissao

	private void telaDeConvitesCancelamento(Scanner entrada){
		try{
			Utils.limparTela();
			//Pegar todos os grupos:
			Grupo[] g = iGrupos.getGrupos();
			System.out.println("INÍCIO > GRUPOS > MENU GRUPOS > GERENCIAMENTO DE CONVITES > CANCELAMENTO: \n");
			System.out.println("Meus grupos: \n ");
			//Listar grupos:
			for(int i = 0; i < g.length; i++ ){
				System.out.println( (i + 1) + ". " + g[i]);
			}//end of for
			//Pegar qual o grupo desejado do usuario:
			System.out.print("Digite a opção desejada para o cancelamento de convites: ");
			int opcao = entrada.nextInt();
			entrada.nextLine();
			opcao -= 1;
			if( opcao < 0 || opcao > g.length -1)
				throw new Exception("Opção inválida.");
			
			if( !g[opcao].estaAtivo())
				throw new Exception("Grupo não está ativo.");

			if( g[opcao].sorteado())
				throw new Exception("Grupo já foi sorteado.");
			
			//Chamar o método de convite cancelar de convite:
			iConvites.cancelar(g[opcao].getID(), entrada, g[opcao].getNome());
		} catch ( Exception e ) {
			System.out.println("Erro ao cancelamento convites.");
			e.printStackTrace();
			Utils.travarTela(entrada);
		}//end of try
	}//end of cancelamento

	//Tela principal para aceitação ou recusa de convites:
	private void telaConvitesAlteracao(Scanner entrada){
		try{
			Utils.limparTela();
			System.out.println("VOCÊ FOI CONVIDADO PARA PARTICIPAR DOS GRUPOS ABAIXO.");
			System.out.println("ESCOLHA QUAL CONVITE DESEJA ACEITAR OU RECUSAR:\n");
			//Pegar todos os convites relacionados ao email logado:
			Convite[] c = iConvites.getConvites(email);
			Grupo g = null;
			String nome = "";
			String data = "";
			//Listar todos os convites e suas informações necessárias:
			for(int i = 0; i < c.length; i++){
				g = iGrupos.getGrupo(c[i].getIDGrupo());
				nome = iUsuarios.getNome(g.getIDUsuario());
				data = Utils.dateToString(c[i].getMomentoConvite(), dataFormato);
				System.out.println( (i + 1) + ". " + g.getNome());
				System.out.println( "   Convidado em " + data );
				System.out.println( "   por " + nome);
			}//end of for
			//Pegar a opção do usuário
			System.out.print("Convite: ");
			int opcao = entrada.nextInt();
			entrada.nextLine();
			opcao -= 1;
			//Verificar a opção do usuario
			if( opcao < 0 || opcao > c.length -1)
				throw new Exception("Opcao inválida");
			
			//Chamar o método de alteração dos convites:
			iConvites.telaSecundaria(c[opcao].getID(), entrada);
		} catch ( Exception e ) {
			System.out.println("Erro ao alterar convite.");
			e.printStackTrace();
			Utils.travarTela(entrada);
		}//end of try
	}//end of convitesAlteracao
}//end of Interface