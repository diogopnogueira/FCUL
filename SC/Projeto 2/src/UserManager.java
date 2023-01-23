import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

class UserManager {

	private static String pathFicheiroUtilizadores = "utilizadoresRegistados.txt";
	private static String pathMACFile = "MACPassword.txt";
	private static String patternSHA256Algorithm = "HmacSHA256";
	private static Random saltingHash = new Random();
	private static BufferedReader brInput = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {


		System.out.println("Introduza a MAC password: ");
		String InsertedMACPassword = brInput.readLine();
		if (isValidMAC(InsertedMACPassword)) {
			boolean alive = true;

			while(alive) {

				System.out.println("Lista de comandos disponiveis:\n"
						+ "addUser\n"
						+ "removeUser\n"
						+ "changePassword\n"
						+ "quit\n\n");

				System.out.println("Introduza a operacao a realizar: ");
				String operacao = brInput.readLine();
				
				if(operacao.equals("quit")){
					alive = false;
					System.out.println("A fechar...");
				}
				else {
					if ((!operacao.equals("addUser")) && (!operacao.equals("removeUser")) && (!operacao.equals("changePassword"))) {
						System.out.println("Comando invalido!\n");
					}
					else {
						System.out.println("Introduza o nome do utilizador ao qual "
								+ "quer efetuar a operacao: ");
						String username = brInput.readLine();
						System.out.println("Introduza a password desse utilizador "
								+ "por motivos de segurança: ");
						String password = brInput.readLine();

						if (operacao.equals("addUser")) {
							if (!userExists(username)) {
								addUserToFile(username, password);
							}
							else {
								System.out.println("Utilizador jah existe, se deseja alterar a password utilize"
										+ "o comando changePassword");
							}
						}
						else if(operacao.equals("removeUser")) {
							if (userExists(username)) {
								removeUserFromFile(username,password);
							}
							else {
								System.out.println("Utilizador nao existe, logo nao pode ser removido,"
										+ "se desejar criar utilize o comando addUser");
							}
						}
						else if(operacao.equals("changePassword")) {
							if (userExists(username)) {
								System.out.println("Insira a nova palavra passe: ");
								String newPassword = brInput.readLine();
								changePasswordFromFile(username,password,newPassword);
							}
							else {
								System.out.println("Este utilizador nao existe.");
							}

						}
					}
				}
			}
		}
		else {
			System.out.println("MAC password invalida, a fechar...");
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////                                          ////////////////////////////
	/////////////////////////METODOS DOS ALGORITMOS MAC E SALTED HASH//////////////////////////////
	////////////////////////                                          /////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	public static boolean isValidMAC(String InsertedMACPassword) {

		try {
			File MACFile = new File(pathMACFile);
			if (!MACFile.exists()) {
				MACFile.createNewFile();
			}

			FileReader fs = new FileReader(MACFile);
			BufferedReader br = new BufferedReader(fs);
			String storedMACPassword;

			if ((storedMACPassword = br.readLine()) != null) {
				byte [] pass = InsertedMACPassword.getBytes(); 
				SecretKey key = new SecretKeySpec(pass, patternSHA256Algorithm); 
				Mac m; 
				byte[] mac=null; 
				m = Mac.getInstance(patternSHA256Algorithm); 
				m.init(key); 
				mac = m.doFinal();
				String macPass = turnBytesToString(mac);
				if (storedMACPassword.equals(macPass)) {
					br.close();
					System.out.println("Autenticacao efetuada com sucesso!");
					return true;
				} else {
					System.out.println("PASSWORD ERRADA! A FECHAR.");
					br.close();
					return false;
				}
			} 
			else {
				System.out.println("Este ficheiro ainda nao estah protegido por um MAC, a gerar chave...");
				byte [] pass = InsertedMACPassword.getBytes(); 
				SecretKey key = new SecretKeySpec(pass, patternSHA256Algorithm); 
				try {
					Mac m; 
					byte[] mac=null; 
					m = Mac.getInstance(patternSHA256Algorithm); 
					m.init(key); 
					mac = m.doFinal();
					FileWriter fos = new FileWriter(pathMACFile);
					String macToString = turnBytesToString(mac);
					fos.write(macToString);
					fos.close();
					return true;
				} catch (IOException e) {
					e.printStackTrace();
					br.close();
					return false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (InvalidKeyException e1) {
			e1.printStackTrace();
			return false;
		} catch (NoSuchAlgorithmException e2) {
			e2.printStackTrace();
			return false;
		}   
	}

	private static void updateFileMACPassword() throws IOException {
		System.out.println("Introduza a nova MAC password: ");
		String InsertedMACPassword = brInput.readLine();
		byte [] pass = InsertedMACPassword.getBytes(); 
		SecretKey key = new SecretKeySpec(pass, patternSHA256Algorithm); 
		Mac m; 
		byte[] mac=null; 
		try {
			m = Mac.getInstance(patternSHA256Algorithm);
			m.init(key); 
			mac = m.doFinal();
			FileWriter fos = new FileWriter(pathMACFile,false);
			String macPass = turnBytesToString(mac);
			fos.write(macPass);
			fos.close();
		}
		catch (NoSuchAlgorithmException | InvalidKeyException | IOException e) {
			e.printStackTrace();
		} 
	}

	private static String createSaltedHash(String saltingHashToString, String password) {
		String hashResult = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			String newSaltedHash = (password+saltingHashToString);

			byte [] newSaltedBuffer = newSaltedHash.getBytes();
			byte [] newHash = md.digest(newSaltedBuffer);
			return turnBytesToString(newHash);

		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hashResult;
	}
	
	private static String turnBytesToString(byte[] byteArray) {

		//converting byte array to Hexadecimal String
		StringBuilder sb = new StringBuilder(2*byteArray.length);
		for(byte b : byteArray){
			sb.append(String.format("%02x", b&0xff));
		}

		return sb.toString();
	}



	///////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////                                          ////////////////////////////
	/////////////////////////     METODOS PEDIDOS PELO ADMINISTRADOR  /////////////////////////////
	////////////////////////                                          /////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * Muda a palavra passe do utilizador para uma nova palavra-passe
	 */
	private static void changePasswordFromFile(String username, String password, String newPassword) {

		File inputFile = new File(pathFicheiroUtilizadores);
		File tempFile = new File("myTempFile.txt");
		boolean encontrou = false;
		try {
			tempFile.createNewFile();

			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			String currentLine;

			while((currentLine = reader.readLine()) != null) {
				if(currentLine.startsWith(username)) {
					String [] userInfo = currentLine.split(":");
					String toVerifyPass = createSaltedHash(userInfo[1], password);
					if (toVerifyPass.equals(userInfo[2])) {
						String newPasswordHash = createSaltedHash(userInfo[1], newPassword);
						writer.write(userInfo[0] + ":" + userInfo[1] + ":" + newPasswordHash + "\n");
						encontrou = true;
						System.out.println("A password foi atualizada com sucesso!");
					}
					else {
						System.out.println("A password estah incorreta");
						writer.write(currentLine + "\n");
					}
				}
				else {
					writer.write(currentLine + "\n");
				}
			}
			writer.close(); 
			reader.close();
			inputFile.delete();
			tempFile.renameTo(inputFile);
			if (encontrou) {
				updateFileMACPassword();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}


	/*
	 *  Metodo que remove um utilizador do ficheiro de utilizadores
	 */
	private static void removeUserFromFile(String nomeUtilizadorARemover, String password) {
		File inputFile = new File(pathFicheiroUtilizadores);
		File tempFile = new File("myTempFile.txt");
		boolean removidoComSucesso = false;
		try {
			tempFile.createNewFile();

			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			String currentLine;

			while((currentLine = reader.readLine()) != null) {
				if(currentLine.startsWith(nomeUtilizadorARemover)) {
					String [] userInfo = currentLine.split(":");
					String toVerifyPass = createSaltedHash(userInfo[1], password);
					if (toVerifyPass.equals(userInfo[2])) {
						System.out.println("Utilizador Removido com Sucesso!");
						removidoComSucesso = true;
					}
					else {
						System.out.println("A password estah incorreta, nao eh possivel remover"
								+ "o utilizador");
					}
				}
				else {
					writer.write(currentLine + "\n");
				}
			}
			writer.close(); 
			reader.close();
			inputFile.delete();
			tempFile.renameTo(inputFile);
			if (removidoComSucesso) {
				updateFileMACPassword();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Metodo que adiciona um utilizador ao ficheiro de utilizadores
	 */
	public static void addUserToFile(String user, String password) {
		try {
			String saltingHashToString = Integer.toString(saltingHash.nextInt());
			String hash = createSaltedHash(saltingHashToString, password);
			BufferedWriter output = new BufferedWriter(new FileWriter(new File(pathFicheiroUtilizadores), true));
			output.append(user + ":" + saltingHashToString + ":" + hash + "\n");
			output.close();
			updateFileMACPassword();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////                                          ////////////////////////////
	/////////////////////////           METODOS AUXILIARES            /////////////////////////////
	////////////////////////                                          /////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////

	private static boolean userExists(String user) {
		try {
			FileReader fs = new FileReader(new File(pathFicheiroUtilizadores));
			BufferedReader br = new BufferedReader(fs);
			String utilizador;
			while ((utilizador = br.readLine()) != null) {
				String [] userInfo = utilizador.split(":");
				if (userInfo[0].equals(user)) {
					br.close();
					return true;
				}
			}
			br.close();
		}
		catch(IOException e) {
			e.getStackTrace();
		}
		return false;
	}
}



