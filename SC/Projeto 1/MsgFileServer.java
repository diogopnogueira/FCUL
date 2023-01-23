/***************************************************************************
 *
 *	Seguranca e Confiabilidade 2018/19
 *  Grupo SegC-017
 *  Diogo Nogueira 49435
 *  Filipe Silveira 49506
 *  Filipe Capela 50296
 *
 ***************************************************************************/

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Utilizador
 * @author Diogo Nogueira 49435 Filipe Silveira 49506 Filipe Capela 50296
 *
 */
class Utilizador{

	private String id;
	private List<String> files;
	private List<String> trustedIDs;

	/**
	 * Construtor que cria um utilizador
	 * @param id - id do cliente
	 * @param password - password do cliente
	 */
	Utilizador(String id, String password){
		this.id = id;
		this.files = new ArrayList<>();
		this.trustedIDs = new ArrayList<String>();

		File areaUtilizador = new File("servidor/area-"+id);
		if(areaUtilizador.isDirectory()) {
			try {
				Scanner allFilesScanner = new Scanner(new File("servidor/area-" + id +
						"/listaDeFicheiros.txt"));
				while (allFilesScanner.hasNext()){
					files.add(allFilesScanner.next());
				}
				allFilesScanner.close();

				Scanner trustedScanner;
				trustedScanner = new Scanner(new File("servidor/area-" + id + "/trustedUsers.txt"));
				while (trustedScanner.hasNext()){
					trustedIDs.add(trustedScanner.next());
				}
				trustedScanner.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else{
			try {
				areaUtilizador.mkdir();
				File ficheiros = new File("servidor/area-" + id + "/listaDeFicheiros.txt");
				ficheiros.createNewFile();
				File trusted = new File("servidor/area-" + id + "/trustedUsers.txt");
				trusted.createNewFile();
				File caixaMensagens = new File("servidor/area-" + id + "/caixaMensagens.txt");
				caixaMensagens.createNewFile();
				File pastaFicheirosRecebidos = new File("servidor/area-" + id + "/ficheirosArmazenados");
				pastaFicheirosRecebidos.mkdir();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return o id do utilizador
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * @return os ficheiros do utilizador
	 */
	public List<String> getFiles() {
		return this.files;
	}

	/**
	 * @return a lista de amigos do utilizador
	 */
	public List<String> getTrustedIDs() {
		return this.trustedIDs;
	}

	/**
	 * Funcao que verifica se um ficheiro esta na lista de ficheiros do utilizador
	 * @param nomeDoFicheiro - o nome do ficheiro a verificar
	 * @return true se o ficheiro estiver na lista de ficheiros do utilizador
	 */
	public boolean containsFile(String nomeDoFicheiro) {
		if(files.contains(nomeDoFicheiro)){
			return true;
		}
		return false;
	}

	/**
	 * Funcao que adiciona um ficheiro na lista de ficheiros do utilizador
	 * @param nomeDoFicheiro - o nome do ficheiro a adicionar
	 * @return true se adicionar o ficheiro com sucesso
	 */
	public boolean addFile(String nomeDoFicheiro){
		try {
			Writer output;
			output = new BufferedWriter(new FileWriter("servidor/area-" + id + 
					"/listaDeFicheiros.txt", true));
			output.append(nomeDoFicheiro + "\n");
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		files.add(nomeDoFicheiro);
		return true;
	}

	/**
	 * Funcao que adiciona um utilizador ah lista de amigos do utilizador
	 * @param nome - o nome do utilizador a adicionar ah lista
	 * @return true se adiciona o utilizador ah lista de amigos
	 */
	public boolean addTrust(String nome){
		if(trustedIDs.contains(nome)){
			return false;
		}
		else{
			trustedIDs.add(nome);
			return true;
		}
	}

	/**
	 * Funcao que remove um utilizador da lista de amigos do utilizador
	 * @param nome - o nome do utilizador a remover ah lista 
	 * @return true se o utilizador foi removido com sucesso da lista de amigos
	 */
	public boolean removeTrust(String nome){
		if(trustedIDs.contains(nome)){
			trustedIDs.remove(nome);
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Funcao que adiciona uma mensagem ah caixa de mensagens de um utilizador
	 * @param msg - a mensagem
	 * @param id - o id do utilizador
	 */
	public void addMsg(String msg, String id) {

		Writer output;
		try {
			output = new BufferedWriter(new FileWriter("servidor/area-"+ this.id + 
					"/caixaMensagens.txt", true));
			output.append(id + " disse: " + msg + "\n");
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Altera o id do utilizador
	 * @param id - o id do utilizador
	 */
	public void setId(String id) {
		this.id = id;
	}
}


/**
 * Class MsgFileServer
 * @author Diogo Nogueira 49435 Filipe Silveira 49506 Filipe Capela 50296
 *
 */
public class MsgFileServer{

	static Semaphore mutex = new Semaphore(1);

	public ArrayList<Utilizador> users = new ArrayList<>();

	/**
	 * Funcao main do MsgFileServer
	 * @param args - os argumentos
	 */
	public static void main(String[] args) {

		try{
			int soc = Integer.parseInt(args[0]);
			System.out.println("servidor: main");
			MsgFileServer server = new MsgFileServer();
			server.startServer(soc);
		}catch(IOException e){
			System.out.println("Argumentos invalidos");
			System.out.println("Deve ser: MsgFileServer <port>");
			System.out.println("Exemplo: MsgFileServer 12345");
		}
	}

	/**
	 * Funcao que inicia o servidor
	 * @param soc - o numero do socket
	 * @throws FileNotFoundException - ficheiro nao encontrado
	 */
	public void startServer (int soc) throws FileNotFoundException{

		new File("servidor").mkdir();
		new File("clientes").mkdir();
		File utilizadoresRegistados = new File("servidor/utilizadoresRegistados.txt");
		try {
			utilizadoresRegistados.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			FileReader fs = new FileReader(new File("servidor/utilizadoresRegistados.txt"));
			BufferedReader br = new BufferedReader(fs);
			String line;
			while ((line = br.readLine()) != null) {
				String[] up = line.split(":");
				users.add(new Utilizador(up[0], up[1]));
			}
			br.close();
			fs.close();
		}
		catch(IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}

		ServerSocket sSoc = null;

		try {
			sSoc = new ServerSocket(soc);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		System.out.println("A aceitar ligacoes");
		Boolean b = true;
		while(b){
			try {
				Socket inSoc = sSoc.accept();
				ServerThread newServerThread = new ServerThread(inSoc);
				newServerThread.start();
			}
			catch (IOException e) {
				e.printStackTrace();
				b = false;
			}
		}
		try {
			sSoc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Threads utilizadas para comunicacao com os clientes
	 * @author Diogo Nogueira 49435 Filipe Silveira 49506 Filipe Capela 50296
	 *
	 */
	class ServerThread extends Thread {

		private Socket socket = null;
		public Utilizador utilizador;

		/**
		 * @param inSoc - o socket
		 */
		public ServerThread(Socket inSoc) {
			socket = inSoc;
		}


		/* 
		 * Funcao run da thread
		 */
		public void run(){
			try {
				ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());

				String user = (String)inStream.readObject();
				String passwd = (String)inStream.readObject();


				FileReader fs = new FileReader(new File("servidor/utilizadoresRegistados.txt"));
				BufferedReader br = new BufferedReader(fs);
				String line;
				boolean autenticacao = true;
				boolean alive = true;
				try {
					mutex.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				while ((line = br.readLine()) != null && autenticacao) {
					String[] userPass = line.split(":");
					//User pertence ao sistema
					if (userPass[0].equals(user)) {
						//password coincide
						if(userPass[1].equals(passwd)) {
							outStream.writeObject(true);
							outStream.writeObject("Autenticacao bem sucedida! Bem vindo "
									+ "\"" + user + "\"" + "!\n");
							System.out.println("Utilizador " + "\"" + user + "\"" + " autenticou-se!");

							for (Utilizador utilizadorCorrente : users) {
								if (utilizadorCorrente.getId().equals(user)) {
									utilizador = utilizadorCorrente;
								}
							}
							autenticacao = false;

						}
						//password nao coincide
						else {
							//enquanto a palavra passe estiver errada
							outStream.writeObject(false);
							outStream.writeObject("Password errada!");
							//fim da autenticacao
							alive = false;
							autenticacao = false;
						}
					}
				}
				mutex.release();
				br.close();
				//registo de novo utilizador no sistema
				if (autenticacao) {
					utilizador = new Utilizador(user,passwd);
					users.add(utilizador);
					Writer output;
					output = new BufferedWriter(new FileWriter("servidor/utilizadoresRegistados.txt"
							, true));
					output.append(user + ":" + passwd + "\n");
					output.close();
					outStream.writeObject(true);
					outStream.writeObject("Registo bem sucedido! Bem vindo "
							+ "\"" + user + "\"" + "!\n");
					System.out.println("\nUtilizador " + "\"" + user + "\"" + " registou-se e autenticou-se!");
				}

				while(alive){ //para manter vivo o servidor
					System.out.println("Aguardando instrucoes...");
					String comando = (String)inStream.readObject();
					System.out.println("Utilizador " + "\"" + user + "\"" + " fez um pedido do tipo \"" 
							+ comando + "\" \n");
				
					switch (comando) {
					case "store":
						int numElemToStore = (int) inStream.readObject();
						for (int i = 0; i < numElemToStore; i++) {
							if ((boolean) (inStream.readObject())) {
								String nomeDoFicheiro = (String) inStream.readObject();
								if (!this.utilizador.containsFile(nomeDoFicheiro)){
									outStream.writeObject(true);
									@SuppressWarnings("unused")
									int fileLength = (int) inStream.readObject();
									byte[] mybytearray = new byte[8192];
									FileOutputStream fos = new FileOutputStream(
											"servidor/area-" + this.utilizador.getId() +
											"/ficheirosArmazenados/" + nomeDoFicheiro);
									BufferedOutputStream bos = new BufferedOutputStream(fos);
									int bytesRead = inStream.read(mybytearray,0,mybytearray.length);
									int current = bytesRead;

									bos.write(mybytearray, 0 , current);
									bos.flush();


									outStream.writeObject(this.utilizador.addFile(nomeDoFicheiro));
									bos.close();
								}
								else {
									outStream.writeObject(false);						
								}
							}
						}
						break;
					case "list":
						int count = 0;
						for (String ficheiro : this.utilizador.getFiles()) {
							outStream.writeObject(true);
							outStream.writeObject(ficheiro);
							count++;
						}
						if (count == 0) {
							outStream.writeObject(false);
							outStream.writeObject("Nao tem ficheiros no servidor!\n");
						}
						else {
							outStream.writeObject(false);
							outStream.writeObject("List terminado\n");
						}
						break;
					case "remove":
						int numElemToRemove = (int) inStream.readObject();
						for (int i = 0; i < numElemToRemove; i++) {
							String nomeFicheiroARemover = (String) inStream.readObject();
							if (utilizador.containsFile(nomeFicheiroARemover)){
								File ficheiroARemover = new File("servidor/area-"+this.utilizador.getId()+
										"/ficheirosArmazenados/" + nomeFicheiroARemover);		
								if(ficheiroARemover.delete()){ 
									this.utilizador.getFiles().remove(nomeFicheiroARemover);

									File inputFile = new File("servidor/area-" + this.utilizador.getId() 
									+ "/listaDeFicheiros.txt");
									File tempFile = new File("servidor/area-" + this.utilizador.getId() 
									+ "/myTempFile.txt");
									tempFile.createNewFile();

									BufferedReader reader = new BufferedReader(new FileReader(inputFile));
									BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

									String lineToRemove = nomeFicheiroARemover;
									String currentLine;

									while((currentLine = reader.readLine()) != null) {
										String trimmedLine = currentLine.trim();
										if(trimmedLine.equals(lineToRemove)) continue;
										writer.write(currentLine + System.getProperty("line.separator"));
									}
									writer.close(); 
									reader.close();
									inputFile.delete();
									tempFile.renameTo(inputFile);
									outStream.writeObject(true);
								} 
								else{ 
									outStream.writeObject(false); 
								} 
							}
							else {
								outStream.writeObject(false);
							}
						}
						break;
					case "users":
						for (Utilizador utilizador : users) {
							outStream.writeObject(true);
							outStream.writeObject(utilizador.getId());
						}
						outStream.writeObject(false);
						break;
					case "trusted":
						int numElemToTrust = (int) inStream.readObject();
						boolean done = false;
						for (int i = 0; i < numElemToTrust; i++) {
							done = false;
							String nomeDaPessoa = (String) inStream.readObject();
							if (nomeDaPessoa.equals(this.utilizador.getId())) {
								outStream.writeObject(false);
								outStream.writeObject("O utilizador eh igual ao seu!");
							}
							else {
								for (Utilizador utilizador : users) {
									if (utilizador.getId().equals(nomeDaPessoa)){
										boolean resultado = this.utilizador.addTrust(nomeDaPessoa);
										if (resultado){
											Writer output;
											output = new BufferedWriter(new FileWriter("servidor/area-" + 
													this.utilizador.getId()+"/trustedUsers.txt", true));
											output.append(nomeDaPessoa + "\n");
											output.close();
											outStream.writeObject(true);
											done = true;
											break;
										}
										else {
											outStream.writeObject(resultado);
											outStream.writeObject("Utilizador jah eh seu amigo!");
											done = true;
											break;
										}
									}
								}
								if (!done) {
									outStream.writeObject(false);
									outStream.writeObject("Utilizador nao estah registado no sistema!");
								}
							}


						}
						break;
					case "untrusted":
						int numElemToUntrust = (int) inStream.readObject();
						for (int i = 0; i < numElemToUntrust; i++) {
							String nomeDaPessoa = (String) inStream.readObject();

							boolean resultado = utilizador.removeTrust(nomeDaPessoa);

							if (resultado){
								File inputFile = new File("servidor/area-" + this.utilizador.getId() 
								+ "/trustedUsers.txt");
								File tempFile = new File("servidor/area-" + this.utilizador.getId() 
								+ "/myTempFile.txt");
								tempFile.createNewFile();

								BufferedReader reader = new BufferedReader(new FileReader(inputFile));
								BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

								String lineToRemove = nomeDaPessoa;
								String currentLine;

								while((currentLine = reader.readLine()) != null) {
									String trimmedLine = currentLine.trim();
									if(trimmedLine.equals(lineToRemove)) continue;
									writer.write(currentLine + System.getProperty("line.separator"));
								}
								writer.close(); 
								reader.close(); 
								inputFile.delete();
								tempFile.renameTo(inputFile);
							}
							outStream.writeObject(resultado);
							if (!resultado) {
								if (this.utilizador.getId().equals(nomeDaPessoa)) {
									outStream.writeObject("Impossivel adicionar o nome " + nomeDaPessoa +
											", é igual ao seu!");
								}
								outStream.writeObject("O utilizador " + nomeDaPessoa + " nao existe na sua lista de trustedUsers");
							}
						}
						break;
					case "download":
						String userIDDownload = (String) inStream.readObject();
						List<String> trustedUserIDDownload = null;

						if (userIDDownload.equals(this.utilizador.getId())) {
							outStream.writeObject(false);
							outStream.writeObject("O utilizador eh igual ao seu!");
						}else {
							for (Utilizador utilizador : users) {
								if (utilizador.getId().equals(userIDDownload)) {
									trustedUserIDDownload = utilizador.getTrustedIDs();
								}							
							}
							if (trustedUserIDDownload == null) {
								outStream.writeObject(false);
								outStream.writeObject("O utilizador nao existe!");
							}else {
								if (trustedUserIDDownload.contains(this.utilizador.getId())) {
									String nomeFicheiroDownload = (String) inStream.readObject();
									File fileToDownload = new File("servidor/area-" + userIDDownload
											+ "/ficheirosArmazenados/" + nomeFicheiroDownload);
									if(!fileToDownload.exists()) { 
										outStream.writeObject(false);
										outStream.writeObject("O ficheiro nao existe!");
									}else {
										outStream.writeObject(true);
										FileInputStream fis = new FileInputStream(fileToDownload);
										BufferedInputStream bis = new BufferedInputStream(fis);
										int tamanhoFicheiro = (int) fileToDownload.length();
										outStream.writeObject(tamanhoFicheiro);
										byte[] mybytearray = new byte[tamanhoFicheiro];
										bis.read(mybytearray, 0, tamanhoFicheiro);
										outStream.write(mybytearray, 0, tamanhoFicheiro);
										outStream.flush();
										bis.close();		
									}
								}
								else {
									outStream.writeObject(false);
									outStream.writeObject("Nao eh amigo deste utilizador!");
								}
							}
						}
						break;
					case "msg":
						String userIDMsg = (String) inStream.readObject();
						List<String> trustedUserIDMsg = null;
						Utilizador amigo = null;
						if (userIDMsg.equals(this.utilizador.getId())) {
							outStream.writeObject(false);
							outStream.writeObject("O utilizador eh igual ao seu!");
						}else {
							for (Utilizador utilizador : users) {
								if (utilizador.getId().equals(userIDMsg)) {
									trustedUserIDMsg = utilizador.getTrustedIDs();
									amigo = utilizador;
								}							
							}
							if (trustedUserIDMsg == null) {
								outStream.writeObject(false);
								outStream.writeObject("O utilizador nao existe!");
							}else {
								if (trustedUserIDMsg.contains(this.utilizador.getId())) {
									outStream.writeObject(true);
									String mensagem = (String) inStream.readObject();
									amigo.addMsg(mensagem, this.utilizador.getId());
								}
								else {
									outStream.writeObject(false);
									outStream.writeObject("Nao eh amigo deste utilizador!");
								}
							}
						}
						break;

					case "collect":
						BufferedReader reader;
						try {
							reader = new BufferedReader(
									new FileReader("servidor/area-"+ 
											this.utilizador.getId() + "/caixaMensagens.txt"));
							String msg = reader.readLine();
							if(msg == null) {
								outStream.writeObject(false);
								outStream.writeObject("\nA caixa de mensagens esta vazia!\n");				
							}
							else {
								outStream.writeObject(true);
								outStream.writeObject(msg);
								while ((msg = reader.readLine()) != null) {
									outStream.writeObject(true);
									outStream.writeObject(msg);
								}
								outStream.writeObject(false);
								outStream.writeObject("\nNão tem mais mensagens para ler!\n");
							}
							reader.close();

							File inputFile = new File("servidor/area-" + this.utilizador.getId() 
							+ "/caixaMensagens.txt");
							File tempFile = new File("servidor/area-" + this.utilizador.getId() 
							+ "/myTempFile.txt");
							tempFile.createNewFile();
							inputFile.delete();
							tempFile.renameTo(inputFile);

						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					case "quit":
						alive = false;
						outStream.writeObject(true);
						System.out.println("Utilizador " + "\"" + this.utilizador.getId() + "\"" + " terminou a sessão");
						break;
					}
				}
				outStream.close();
				inStream.close();
				socket.close();
			}   catch (SocketException e) {
				System.out.println("Cliente " + this.utilizador.getId() + " desconectado abruptamente");
				System.out.println("A aceitar ligações");
			}
			catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
