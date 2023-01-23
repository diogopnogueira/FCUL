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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import javax.net.ssl.*;

/**
 * Class MsgFile do cliente
 * @author Diogo Nogueira 49435 Filipe Silveira 49506 Filipe Capela 50296
 *
 */
public class MsgFile {

	/**
	 * Funcao que verifica se os argumentos sao validos
	 * @param args - argumentos
	 * @return true se forem validos
	 */
	public static boolean notargs(String[] args){
		String[] s = args[0].split(":", 2);
		try{
			Integer.parseInt(s[1]);
		}catch(NumberFormatException e){
			return true;
		}
		if(args.length < 2 || args.length > 3) {
			return true;
		}
		return false;
	}

	/**
	 * Funcao main do cliente
	 * @param args - argumentos
	 */
	public static void main(String[] args) {
		String pw = null;
		if(notargs(args)){
			System.out.println("Argumentos invalidos");
			System.out.println("Deve ser: MsgFile <serverAddress> <localUserID> <password>");
			System.out.println("Exemplo: MsgFile 127.1.1.1:12345 utilizador palavrapass");
		}
		else {
			Scanner reader = new Scanner(System.in);
			Boolean faltaPass = args.length == 2;
			if(!faltaPass) {
				pw = args[2];
			}
			while (faltaPass) {
				System.out.println("Por favor introduza a palavra-passe");
				pw = reader.nextLine();
				if (pw != null) {
					faltaPass = false;
				}
			}
			System.setProperty("javax.net.ssl.trustStore", "src/myClient.keystore");
			SSLSocket soc = null;
			try {

				String[] s = args[0].split(":", 2);
				SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
				soc = (SSLSocket) socketFactory.createSocket(s[0],Integer.parseInt(s[1]));
				//soc = new Socket(s[0],Integer.parseInt(s[1]));
				
				//SSLSession session = ((SSLSocket) soc).getSession();
				//Certificate[] cchain = session.getPeerCertificates();
								
				ObjectOutputStream outStream = new ObjectOutputStream(soc.getOutputStream());
				ObjectInputStream inStream = new ObjectInputStream(soc.getInputStream());
				outStream.writeObject(args[1]);
				outStream.writeObject(pw);

				Boolean active = (boolean) inStream.readObject();
				String username = args[1];
				if(!active){
					String erro = (String)inStream.readObject();
					System.out.println(erro);
					System.out.println("Sessao Terminada");
				}
				else {
					String sucesso = (String)inStream.readObject();

					File pastaCliente = new File("clientes/area-" + username);
					if (!pastaCliente.isDirectory()) {
						pastaCliente.mkdir();
					}
					System.out.println(sucesso);
				}

				while(active){
					System.out.println("Por favor introduza um dos seguintes comandos.");
					System.out.println("Comandos disponiveis:");
					System.out.println("store <files>\nlist\nremove <files>\nusers\ntrusted <trustedUserIDs>");
					System.out.println("untrusted <untrustedUserIDs>\ndownload <userID> <file>\nmsg <userID> <msg>\ncollect\nquit\n");
					String leitor = reader.nextLine();
					String[] parametros = leitor.split(" ");
					String comando = parametros[0];
					switch(comando) {

					case "store":
						if(parametros.length < 2){
							System.out.println("Argumentos invalidos.");
						}
						else{
							outStream.writeObject(comando);
							outStream.writeObject(parametros.length-1);
							for (int i = 1; i < parametros.length; i++) {
								String nomeDoFicheiro = parametros[i];
								File fileToStore = new File("clientes/area-" + username + "/" + nomeDoFicheiro);
								if(!fileToStore.exists()) { 
									System.out.println("Erro: Ficheiro " + "\"" + nomeDoFicheiro
											+ "\"" + " nao existe\nNao se esqueca de indicar a extensão do ficheiro!\n");
									outStream.writeObject(false);
								}else {
									outStream.writeObject(true);
									outStream.writeObject(nomeDoFicheiro);
									if ((boolean) inStream.readObject()) {
										int fileLength = (int) fileToStore.length();
										outStream.writeObject(fileLength);
										byte[] mybytearray = new byte[fileLength];
										FileInputStream fis = new FileInputStream(fileToStore);
										BufferedInputStream bis = new BufferedInputStream(fis);
										bis.read(mybytearray,0,mybytearray.length);
										outStream.write(mybytearray, 0, mybytearray.length);
										outStream.flush();
										bis.close();
										if ((boolean)inStream.readObject()) {
											System.out.println("Ficheiro: " + parametros[i] + " guardado com sucesso!\n");
										}else {
											System.out.println("Erro ao guardar o ficheiro " + parametros[i] + "\n");
										}

									}
									else {
										System.out.println("Ficheiro " + parametros[i] + " jah estah guardado no servidor \n");
									}
								}

							}
						}
						break;

					case "list":
						outStream.writeObject(comando);
						String nomeFicheiro;
						int contador = 1;
						while(((boolean) inStream.readObject())) {
							nomeFicheiro = (String) inStream.readObject();
							System.out.println("Ficheiro" + contador + " : " + nomeFicheiro + "\n");
							contador++;
						}
						System.out.println((String)inStream.readObject());
						break;

					case "remove":
						if(parametros.length < 2){
							System.out.println("Argumentos invalidos.");
						}
						else{
							outStream.writeObject(comando);
							outStream.writeObject(parametros.length-1);
							for (int i = 1; i < parametros.length; i++) {
								outStream.writeObject(parametros[i]);			
								if ((boolean)inStream.readObject()) {
									System.out.println("Ficheiro: " + parametros[i] + "\nRemovido com sucesso!"); 
								}else {
									System.out.println("Nao existe nenhum ficheiro com o nome " + parametros[i] + " no servidor");
								}
							}
						}
						break;

					case "users":
						outStream.writeObject(comando);
						String nomeUtilizadores;
						int count = 1;
						while(((boolean) inStream.readObject())) {
							nomeUtilizadores = (String) inStream.readObject();
							System.out.println("Utilizador " + count + ": " + nomeUtilizadores);
							count++;
						}

						System.out.print("\n");
						break;

					case "trusted":
						if(parametros.length < 2){
							System.out.println("Argumentos invalidos.");
						}
						else{
							outStream.writeObject(comando);
							outStream.writeObject(parametros.length-1);
							for (int i = 1; i < parametros.length; i++) {
								outStream.writeObject(parametros[i]);
								if ((boolean)inStream.readObject()) {
									System.out.println("Utilizador: " + parametros[i] + 
											"\nAdicionado ah trusted list com sucesso!"); 
								}else {
									System.out.println((String) inStream.readObject());
								}
							}
						}
						break;

					case "untrusted":
						if(parametros.length < 2){
							System.out.println("Argumentos invalidos.");
						}
						else{
							outStream.writeObject(comando);
							outStream.writeObject(parametros.length-1);
							for (int i = 1; i < parametros.length; i++) {
								outStream.writeObject(parametros[i]);

								if ((boolean)inStream.readObject()) {
									System.out.println("Utilizador: " + parametros[i] + 
											"\nremovido da trusted list com sucesso!"); 
								}else {
									System.out.println((String) inStream.readObject());
								}
							}
						}
						break;

					case "download":
						if(parametros.length != 3){
							System.out.println("Argumentos invalidos.");
						}
						else{
							outStream.writeObject(comando);
							outStream.writeObject(parametros[1]);
							String nomeFicheiroDownloaded = parametros[2];
							outStream.writeObject(nomeFicheiroDownloaded);

							if ((boolean)inStream.readObject()) {
								@SuppressWarnings("unused")
								int fileLength = (int) inStream.readObject();
								byte[] mybytearray = new byte[8192];
								File pastaDownloads = new File("clientes/area-" + username + 
										"/ficheirosTransferidos");
								if (!pastaDownloads.isDirectory()) {
									pastaDownloads.mkdir();
								}
								FileOutputStream fos = new FileOutputStream("clientes/area-" + username + 
										"/ficheirosTransferidos/" + nomeFicheiroDownloaded);
								BufferedOutputStream bos = new BufferedOutputStream(fos);
								int bytesRead = inStream.read(mybytearray, 0, mybytearray.length);
								bos.write(mybytearray, 0, bytesRead);
								bos.flush();
								System.out.println("Ficheiro " + "\"" + nomeFicheiroDownloaded + "\"" +
										" transferido com sucesso! " ); 
								bos.close();
							}else {
								System.out.println((String)inStream.readObject());
							}
						}
						break;

					case "msg":
						if(parametros.length < 3){
							System.out.println("Argumentos invalidos.");
						}
						else{
							outStream.writeObject(comando);
							outStream.writeObject(parametros[1]);
							if ((boolean)inStream.readObject()) {
								StringBuilder sb = new StringBuilder();
								for (int i = 2; i < parametros.length-1; i++) {
									sb.append(parametros[i] + " ");
								}
								sb.append(parametros[parametros.length-1]);

								outStream.writeObject(sb.toString());
								System.out.println("Mensagem enviada com sucesso"); 
							}else {
								System.out.println((String)inStream.readObject());
							}
						}					
						break;
					case "collect":
						if(parametros.length != 1){
							System.out.println("Argumentos invalidos.");
						}
						else{
							outStream.writeObject(comando);				
							while ((boolean)inStream.readObject()) {
								String mensagem = (String) inStream.readObject();
								System.out.println(mensagem);
							}
							System.out.println((String)inStream.readObject());
						}
						break;

					case "quit":
						outStream.writeObject(comando);
						active = false;
						if ((boolean) inStream.readObject()) {
							System.out.println("Sessão terminada");
						}
						break;

					default:
						System.out.println("Comando introduzido estah errado!\n");
						break;
					}
				}
				soc.close();

			} catch (SocketException e) {
				System.out.println("Ligação perdida");
				System.out.println("A desconectar");
			}
			catch (IOException e) {
				System.err.println(e.getMessage());
				System.exit(-1);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

		}
	}
}
