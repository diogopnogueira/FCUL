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
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
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

	private static String pathAreaUserNoServer = "servidor/area-";
	private static String pathListaFicheiros = "/listaDeFicheiros.txt";

	/**
	 * Construtor que cria um utilizador
	 * @param id - id do cliente
	 * @param password - password do cliente
	 * @throws Exception 
	 * @throws InvalidKeyException 
	 */	Utilizador(String id){
		 this.id = id;
		 this.files = new ArrayList<>();
		 this.trustedIDs = new ArrayList<>();

		 File areaUtilizador = new File(pathAreaUserNoServer+id);
		 if(areaUtilizador.isDirectory()) {
			 try {
				 File ficheiroListaEncriptado = new File(pathAreaUserNoServer + id +
						 pathListaFicheiros + ".cif");
				 AESSymmetricEncryption decryptFile = new AESSymmetricEncryption();
				 decryptFile.verifySign(ficheiroListaEncriptado.getAbsolutePath());

				 File ficheiroDecriptadoLista = new File(pathAreaUserNoServer + id +
						 pathListaFicheiros);
				 Scanner allFilesScanner = new Scanner(ficheiroDecriptadoLista);
				 while (allFilesScanner.hasNext()){
					 files.add(allFilesScanner.next());
				 }
				 allFilesScanner.close();

				 ficheiroDecriptadoLista.delete();

				 File ficheiroTrustedEncriptado = new File(pathAreaUserNoServer + id +
						 "/trustedUsers.txt" + ".cif");
				 AESSymmetricEncryption decryptTrusted = new AESSymmetricEncryption();
				 decryptTrusted.verifySign(ficheiroTrustedEncriptado.getAbsolutePath());

				 File ficheiroDecriptadoTrusted = new File(pathAreaUserNoServer + id +
						 "/trustedUsers.txt");

				 Scanner trustedScanner = new Scanner(ficheiroDecriptadoTrusted);
				 while (trustedScanner.hasNext()){
					 trustedIDs.add(trustedScanner.next());
				 }
				 trustedScanner.close();

				 ficheiroDecriptadoTrusted.delete();

			 } catch (IOException | GeneralSecurityException e) {
				 e.printStackTrace();
			 }
		 }
		 else{
			 try {
				 areaUtilizador.mkdir();
				 AESSymmetricEncryption encriptarListaFicheiros = new AESSymmetricEncryption();
				 String listaFicheiros = pathAreaUserNoServer + id + pathListaFicheiros;
				 File ficheiroListaFicheiros = new File(listaFicheiros);
				 ficheiroListaFicheiros.createNewFile();
				 encriptarListaFicheiros.encryptClientFile(listaFicheiros, 0, null);
				 ficheiroListaFicheiros.delete();
				 encriptarListaFicheiros.signFile(listaFicheiros + ".cif");

				 AESSymmetricEncryption encriptarTrustedUsers = new AESSymmetricEncryption();
				 String trustedUsers =pathAreaUserNoServer + id + "/trustedUsers.txt";
				 File ficheiroTrusted = new File(trustedUsers);
				 ficheiroTrusted.createNewFile();	
				 encriptarTrustedUsers.encryptClientFile(trustedUsers, 0, null);
				 ficheiroTrusted.delete();
				 encriptarTrustedUsers.signFile(trustedUsers + ".cif");	

				 AESSymmetricEncryption encriptarCaixaMensagens = new AESSymmetricEncryption();
				 String caixaMensagens = pathAreaUserNoServer + id + "/caixaMensagens.txt";
				 File ficheiroCaixaMensagens = new File(caixaMensagens);
				 ficheiroCaixaMensagens.createNewFile();
				 encriptarCaixaMensagens.encryptClientFile(caixaMensagens, 0, null);
				 ficheiroCaixaMensagens.delete();
				 encriptarCaixaMensagens.signFile(caixaMensagens + ".cif");	

				 File pastaFicheirosRecebidos = new File(pathAreaUserNoServer + id + "/ficheirosArmazenados");
				 pastaFicheirosRecebidos.mkdir();

			 } catch (IOException | GeneralSecurityException e) {
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
		 return files.contains(nomeDoFicheiro);
	 }

	 /**
	  * Funcao que adiciona um ficheiro na lista de ficheiros do utilizador
	  * @param nomeDoFicheiro - o nome do ficheiro a adicionar
	  * @return true se adicionar o ficheiro com sucesso
	  * @throws GeneralSecurityException 
	  * @throws Exception 
	  * @throws InvalidKeyException 
	  */
	 public boolean addFile(String nomeDoFicheiro) throws GeneralSecurityException{
		 try {

			 String listaFicheirosCifradoPath = pathAreaUserNoServer + id + pathListaFicheiros + ".cif";

			 AESSymmetricEncryption verify = new AESSymmetricEncryption(); 
			 byte[] ficheiroDecifrado = verify.decryptClientFile(listaFicheirosCifradoPath, 0, null);
			 String listaFicheirosDecriptadoPath = pathAreaUserNoServer + id + pathListaFicheiros;
			 File listaFicheirosDecriptado = new File(listaFicheirosDecriptadoPath);

			 Writer output;
			 output = new BufferedWriter(new FileWriter(listaFicheirosDecriptado, true));
			 output.append(nomeDoFicheiro + "\n");
			 output.close();

			 FileInputStream fis = new FileInputStream(listaFicheirosDecriptado);
			 byte [] myByteArray = Files.readAllBytes(listaFicheirosDecriptado.toPath());
			 int bytesRead = fis.read(myByteArray);

			 verify.encryptClientFile(listaFicheirosDecriptadoPath, bytesRead, myByteArray);
			 verify.signFile(listaFicheirosCifradoPath);
			 fis.close();
			 listaFicheirosDecriptado.delete();

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
	 public void addMsg(String msg) {

		 Writer output;
		 try {
			 output = new BufferedWriter(new FileWriter(pathAreaUserNoServer+ this.id + 
					 "/caixaMensagens.txt", true));
			 output.append(msg + "\n");
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

class AESSymmetricEncryption {

	private Key aesKKey;
	private Cipher aesCipher;
	private Cipher cWrap = Cipher.getInstance("RSA");
	private FileInputStream kfile = new FileInputStream(new File("src/myServer.keystore"));
	private BufferedInputStream bkfile = new BufferedInputStream(kfile);
	private KeyPair serverKeyPair;
	private static final String groupPass = "grupo17";



	public AESSymmetricEncryption () 
			throws NoSuchAlgorithmException, NoSuchPaddingException, KeyStoreException, CertificateException, IOException, UnrecoverableKeyException {

		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(128);
		this.aesKKey = generator.generateKey();
		this.aesCipher = Cipher.getInstance("AES");


		KeyStore kstore = KeyStore.getInstance("jceks");
		kstore.load(bkfile, groupPass.toCharArray());

		Key key = kstore.getKey("myServer", groupPass.toCharArray());
		if (key instanceof PrivateKey) {

			Certificate cert = kstore.getCertificate("myServer");

			PublicKey serverPubKey =  cert.getPublicKey();

			this.serverKeyPair = new KeyPair(serverPubKey, (PrivateKey) key);
		}
	}


	private byte[] cipherFile(int cipherMode, String inputFile, String outputFile, int bytesRead, byte[] myByteArray) 
			throws IOException, GeneralSecurityException {	


		FileInputStream fis = new FileInputStream(new File(inputFile));
		FileOutputStream fos = new FileOutputStream(new File(outputFile));

		if(bytesRead == 0 && myByteArray == null) {
			myByteArray = new byte[256];
			bytesRead = fis.read(myByteArray,0,myByteArray.length);
		}


		if (bytesRead == -1) {
			bytesRead = 0;
		}

		if (cipherMode == Cipher.ENCRYPT_MODE) {


			this.aesCipher.init(cipherMode, this.aesKKey);
			CipherOutputStream cos = new CipherOutputStream(fos, this.aesCipher);
			BufferedOutputStream bos = new BufferedOutputStream(cos);
			bos.write(myByteArray, 0 , bytesRead);     
			bos.close();
			fis.close();

		}
		else if (cipherMode == Cipher.DECRYPT_MODE) {

			this.aesCipher.init(cipherMode, this.aesKKey);
			CipherInputStream cis = new CipherInputStream(fis, this.aesCipher);
			BufferedInputStream bis = new BufferedInputStream(cis);
			bis.read(myByteArray, 0 , bytesRead);     
			bis.close();
			fos.close();
		}
		else if (cipherMode == Cipher.WRAP_MODE) {

			fos.write(myByteArray); 
			fos.close();
			fis.close();

		}
		else if (cipherMode == Cipher.UNWRAP_MODE) {


			fis.read(myByteArray, 0 , bytesRead);   
			fis.close();
			fos.close();
		}
		return myByteArray;
	}


	public byte[] encryptClientFile(String path, int bytesRead, byte[] myByteArray) 
			throws IOException, GeneralSecurityException {


		cipherFile(Cipher.ENCRYPT_MODE, path, path + ".cif", bytesRead, myByteArray);

		this.cWrap.init(Cipher.WRAP_MODE, this.serverKeyPair.getPublic());


		byte [] key = cWrap.wrap(this.aesKKey);
		String newFileName = path + ".cif.key";

		return cipherFile(Cipher.WRAP_MODE, path, newFileName, key.length, key);
	}


	public byte[] decryptClientFile(String path, int bytesRead, byte[] myByteArray) 
			throws IOException, GeneralSecurityException {

		String newFileName = path + ".key"; //PATH TO CHECK
		String newFileTemp = path + "lalalala.key";
		File newFileTempFile = new File(newFileTemp);

		this.cWrap.init(Cipher.UNWRAP_MODE, this.serverKeyPair.getPrivate());

		byte[] encKey = cipherFile(Cipher.UNWRAP_MODE, newFileName, newFileTemp, bytesRead, myByteArray);
		newFileTempFile.delete();
		this.aesKKey = cWrap.unwrap(encKey, "AES", Cipher.SECRET_KEY);

		String ficheiroDecifradoPath = path.replaceAll(".cif", "");

		return cipherFile(Cipher.DECRYPT_MODE, path, ficheiroDecifradoPath, 0, myByteArray);
	}

	public String encryptMsg(String msg) {
		try {
			aesCipher.init(Cipher.ENCRYPT_MODE, this.aesKKey);
			byte[] encryptedMsg = aesCipher.doFinal(msg.getBytes());
			return Base64.getEncoder().encodeToString(encryptedMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String decryptMsg(String encryptedMsg) {
		try {    
			this.aesCipher.init(Cipher.DECRYPT_MODE, this.aesKKey);
			byte[] msg = aesCipher.doFinal(Base64.getDecoder().decode(encryptedMsg));
			return new String(msg);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	public void signFile(String path) throws IOException, GeneralSecurityException{

		File ficheiroEncriptado = new File(path);
		File ficheiroAssinatura = new File(path +".sig");
		byte[] fileContent = Files.readAllBytes(ficheiroEncriptado.toPath());
		Signature rsa = Signature.getInstance("MD5withRSA"); 
		rsa.initSign(this.serverKeyPair.getPrivate());
		rsa.update(fileContent);

		FileOutputStream fos = new FileOutputStream(ficheiroAssinatura);
		byte [] oQueEleEscreve = rsa.sign();
		fos.write(oQueEleEscreve);
		fos.close();
	}


	public boolean verifySign(String ficheiroCifrado) throws IOException, GeneralSecurityException{

		byte[] ficheiroDecifrado = decryptClientFile(ficheiroCifrado, 0, null);
		String ficheiroDecifradoPath = ficheiroCifrado.replaceAll(".cif", "");
		File ficheiroDecifradoFile = new File(ficheiroDecifradoPath);
		try (FileOutputStream fos = new FileOutputStream(ficheiroDecifradoFile)) {
			fos.write(ficheiroDecifrado);
		}
		if (ficheiroDecifrado != null) {

			AESSymmetricEncryption encriptarDeNovo = new AESSymmetricEncryption();
			FileInputStream fisListaFicheiros = new FileInputStream(ficheiroDecifradoFile);
			byte [] bytesListaFicheiros = Files.readAllBytes(ficheiroDecifradoFile.toPath());
			int numBytesListaFicheiros = fisListaFicheiros.read(bytesListaFicheiros);
			fisListaFicheiros.close();
			byte [] ficheiroEncriptadoBytes = encriptarDeNovo.encryptClientFile(ficheiroDecifradoPath, numBytesListaFicheiros, bytesListaFicheiros);

			Signature sig = Signature.getInstance("MD5withRSA");
			sig.initVerify(this.serverKeyPair.getPublic());
			sig.update(ficheiroEncriptadoBytes);

			File ficheiroSignature = new File(ficheiroCifrado + ".sig");
			byte[] signatureLida = Files.readAllBytes(ficheiroSignature.toPath());

			return sig.verify(signatureLida);
		}
		return false;
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
	 * @throws Exception 
	 * @throws InvalidKeyException 
	 */
	public static void main(String[] args) {

		int soc = Integer.parseInt(args[0]);
		BufferedReader reader =  
				new BufferedReader(new InputStreamReader(System.in)); 

		try {
			File MACFile = new File("MACPassword.txt");
			if (!MACFile.exists()) {
				MACFile.createNewFile();
				System.out.println("Insira a MAC password:");
			}
			else{
				System.out.println("Insira a MAC password:");
			}

			final String InsertedMACPassword = reader.readLine();

			FileReader fs = new FileReader(MACFile);
			BufferedReader br = new BufferedReader(fs);
			String storedMACPassword;

			if ((storedMACPassword = br.readLine()) != null) {
				byte [] pass = InsertedMACPassword.getBytes(); 
				SecretKey key = new SecretKeySpec(pass, "HmacSHA256");
				Mac m; 
				byte[] mac=null; 

				m = Mac.getInstance("HmacSHA256");
				m.init(key); 
				mac = m.doFinal();

				StringBuilder sb = new StringBuilder(2*mac.length);
				for(byte b : mac){
					sb.append(String.format("%02x", b&0xff));
				}

				String macPass = sb.toString();

				if (storedMACPassword.equals(macPass)) {
					System.out.println("Ligado com sucesso");
					MsgFileServer server = new MsgFileServer();
					server.startServer(soc);
				}
				else {
					System.out.println("PASSWORD ERRADA! A FECHAR.");
					br.close();
					return;
				}
				br.close();
			}
			else {
				System.out.println("Este ficheiro ainda nao estah protegido por um MAC, a gerar chave...");

				byte [] pass = InsertedMACPassword.getBytes(); 
				SecretKey key = new SecretKeySpec(pass, "HmacSHA256"); 
				Mac m; 
				byte[] mac=null; 
				m = Mac.getInstance("HmacSHA256"); 
				m.init(key); 
				mac = m.doFinal();
				StringBuilder sb = new StringBuilder(2*mac.length);
				for(byte b : mac){
					sb.append(String.format("%02x", b&0xff));
				}

				String macPass = sb.toString();

				try {
					Writer output = new BufferedWriter(new FileWriter("MACPassword.txt"));
					output.write(macPass);
					output.close();


					System.out.println("Ligado com sucesso");
					MsgFileServer server = new MsgFileServer();
					server.startServer(soc);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}   
	}

	/**
	 * Funcao que inicia o servidor
	 * @param soc - o numero do socket
	 * @throws Exception 
	 * @throws InvalidKeyException 
	 */
	public void startServer (int soc){

		new File("servidor").mkdir();
		new File("clientes").mkdir();

		File utilizadoresRegistados = new File("utilizadoresRegistados.txt");

		try {
			if (!utilizadoresRegistados.exists()) {
				utilizadoresRegistados.createNewFile();
			}

			FileReader fs = new FileReader(utilizadoresRegistados);
			BufferedReader br = new BufferedReader(fs);
			String line;
			while ((line = br.readLine()) != null) {
				String[] up = line.split(":");
				users.add(new Utilizador(up[0]));
			}
			br.close();
			fs.close();
		}
		catch(IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}

		System.setProperty("javax.net.ssl.keyStore", "src/myServer.keystore");
		System.setProperty("javax.net.ssl.keyStorePassword", "grupo17");

		SSLServerSocket sSoc = null;

		try {
			SSLServerSocketFactory sslServerSocketFactory = 
					(SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
			sSoc = (SSLServerSocket) sslServerSocketFactory.createServerSocket(soc);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		System.out.println("A aceitar ligacoes");
		Boolean b = true;
		while(b){
			try {
				Socket inSoc = sSoc.accept();
				SSLSession session = ((SSLSocket) inSoc).getSession();
				Certificate[] cchain = session.getLocalCertificates();
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
		private Utilizador utilizador;
		private static final String pathAreaUserNoServer = "servidor/area-";
		private static final String pathNovoTempFile = "/myTempFile.txt";

		/**
		 * @param inSoc - o socket
		 */
		public ServerThread(Socket inSoc) {
			socket = inSoc;
		}

		/* 
		 * Funcao run da thread
		 */
		@Override
		public void run(){
			try {
				ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());

				String user = (String)inStream.readObject();
				String passwd = (String)inStream.readObject();

				FileReader fs = new FileReader(new File("utilizadoresRegistados.txt"));
				BufferedReader br = new BufferedReader(fs);
				String line;
				boolean autenticacao = true;
				boolean alive = false;
				boolean userExists = false;
				mutex.acquire();
				//autenticação
				while ((line = br.readLine()) != null && autenticacao) {
					String[] userPass = line.split(":");
					//User pertence ao sistema
					if (userPass[0].equals(user)) {
						userExists = true;
						//password coincide
						String salt = userPass[1];

						MessageDigest md = MessageDigest.getInstance("SHA");
						String saltedHash = (passwd+salt);

						byte [] saltedBuffer = saltedHash.getBytes();
						byte [] hash = md.digest(saltedBuffer);

						StringBuilder sb = new StringBuilder(2*hash.length);
						for(byte b : hash){
							sb.append(String.format("%02x", b&0xff));
						}

						String hashAVerificar = sb.toString();

						if(hashAVerificar.equals(userPass[2])) {
							outStream.writeObject(true);
							outStream.writeObject("Autenticacao bem sucedida! Bem vindo "
									+ "\"" + user + "\"" + "!\n");
							System.out.println("Utilizador " + "\"" + user + "\"" + " autenticou-se!");

							for (Utilizador utilizadorCorrente : users) {
								if (utilizadorCorrente.getId().equals(user)) {
									utilizador = utilizadorCorrente;
								}
							}
							alive = true;
							autenticacao = false;
						}
						else {
							//enquanto a palavra passe estiver errada
							outStream.writeObject(false);
							outStream.writeObject("Password errada!");
							//fim da autenticacao
							autenticacao = false;
						}
					}
					if(!userExists) {
						alive = false;
						System.out.println("Erro, utilizador nao existe!");
					}
				}
				mutex.release();
				br.close();

				while(alive){ //para manter vivo o servidor
					System.out.println("Aguardando instrucoes...");
					String comando = (String)inStream.readObject();
					System.out.println("Utilizador " + "\"" + user + "\"" + " fez um pedido do tipo \"" 
							+ comando + "\" \n");

					switch (comando) {
					//STORE
					case "store":
						int numElemToStore = (int) inStream.readObject();
						for (int i = 0; i < numElemToStore; i++) {
							if ((boolean) (inStream.readObject())) {
								AESSymmetricEncryption aesEncryption = new AESSymmetricEncryption();
								String nomeDoFicheiro = (String) inStream.readObject();
								String ficheiroPath = pathAreaUserNoServer + this.utilizador.getId() +
										"/ficheirosArmazenados/" + nomeDoFicheiro;
								if (!this.utilizador.containsFile(nomeDoFicheiro)){
									outStream.writeObject(true);

									int fileLength = (int) inStream.readObject();
									byte[] myByteArray = new byte[8192];		
									int bytesRead = inStream.read(myByteArray,0,myByteArray.length);

									File ficheiroNoServidorDesencriptado = new File(ficheiroPath);
									ficheiroNoServidorDesencriptado.createNewFile();
									//CRIAR A COPIA DO FICHEIRO PARA DEPOIS ENCRIPTAR
									FileOutputStream fosFicheiroNoServidorDecrypted = 
											new FileOutputStream(ficheiroNoServidorDesencriptado);
									BufferedOutputStream bosFicheiroNoServidorDecrypted = 
											new BufferedOutputStream(fosFicheiroNoServidorDecrypted);
									bosFicheiroNoServidorDecrypted.write(myByteArray,0,fileLength);
									bosFicheiroNoServidorDecrypted.close();

									aesEncryption.encryptClientFile(ficheiroPath, bytesRead, myByteArray);
									ficheiroNoServidorDesencriptado.delete();

									outStream.writeObject(this.utilizador.addFile(nomeDoFicheiro));		
								}
								else {
									outStream.writeObject(false);						
								}
							}
						}
						break;
						//LIST
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
								File ficheiroARemover = new File(pathAreaUserNoServer+this.utilizador.getId()+
										"/ficheirosArmazenados/" + nomeFicheiroARemover + ".cif");		
								if(ficheiroARemover.delete()){ 
									this.utilizador.getFiles().remove(nomeFicheiroARemover);

									AESSymmetricEncryption aesEncryption = new AESSymmetricEncryption();
									String listaFicheirosCifradoPath = pathAreaUserNoServer + this.utilizador.getId() 
									+ "/listaDeFicheiros.txt.cif";

									if(aesEncryption.verifySign(listaFicheirosCifradoPath)) {
										//NESTE MOMENTO TEMOS O FICHEIRO DESENCRIPTADO E ESTAH NA HORA DE REMOVER A LINHA
										File listaFicheirosDecifrado = new File(pathAreaUserNoServer + this.utilizador.getId() 
										+ "/listaDeFicheiros.txt");

										File newListaFicheirosSemALinha = new File(pathAreaUserNoServer + this.utilizador.getId() 
										+ pathNovoTempFile);
										newListaFicheirosSemALinha.createNewFile();

										BufferedReader reader = new BufferedReader(new FileReader(listaFicheirosDecifrado));
										BufferedWriter writer = new BufferedWriter(new FileWriter(newListaFicheirosSemALinha));

										String lineToRemove = nomeFicheiroARemover;
										String currentLine;

										while((currentLine = reader.readLine()) != null) {
											String trimmedLine = currentLine.trim();
											if(trimmedLine.equals(lineToRemove)) continue;
											writer.write(currentLine + System.getProperty("line.separator"));
										}
										writer.close(); 
										reader.close();
										newListaFicheirosSemALinha.renameTo(listaFicheirosDecifrado);

										//FICHEIRO JA NAO TEM A LINHA, TEMOS DE ENCRIPTAR O FICHEIRO DE NOVO

										AESSymmetricEncryption encriptarListaFicheiros = new AESSymmetricEncryption();
										String listaFicheirosPath = newListaFicheirosSemALinha.getAbsolutePath();

										FileInputStream fisListaFicheiros = new FileInputStream(newListaFicheirosSemALinha);
										byte [] bytesListaFicheiros = Files.readAllBytes(newListaFicheirosSemALinha.toPath());
										int numBytesListaFicheiros = fisListaFicheiros.read(bytesListaFicheiros);
										encriptarListaFicheiros.encryptClientFile(listaFicheirosPath, numBytesListaFicheiros, bytesListaFicheiros);
										encriptarListaFicheiros.signFile(listaFicheirosCifradoPath);
										listaFicheirosDecifrado.delete();
										fisListaFicheiros.close();

										//FICHEIRO JA ESTAH ENCRIPTADO, ACABOU O METODO
										outStream.writeObject(true);
									}
									else {
										outStream.writeObject(false); 
									}
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
						for (Utilizador utilizadorCerto : users) {
							outStream.writeObject(true);
							outStream.writeObject(utilizadorCerto.getId());
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
								AESSymmetricEncryption verifyTrusted = new AESSymmetricEncryption();
								for (Utilizador utilizadorCerto : users) {
									if (utilizadorCerto.getId().equals(nomeDaPessoa)){
										boolean resultado = this.utilizador.addTrust(nomeDaPessoa);
										String path = pathAreaUserNoServer + 
												this.utilizador.getId()+"/trustedUsers.txt.cif";
										if (resultado && verifyTrusted.verifySign(path)){				
											Writer output;

											output = new BufferedWriter(new FileWriter(pathAreaUserNoServer + 
													this.utilizador.getId()+"/trustedUsers.txt", true));
											output.append(nomeDaPessoa + "\n");
											output.close();
											outStream.writeObject(true);	
											done = true;

											//ENCRIPTACAO DO FICHEIRO DEPOIS DE SE METER A LINHA NOVA
											File trustedUsersFile = new File(pathAreaUserNoServer + 
													this.utilizador.getId()+"/trustedUsers.txt");
											AESSymmetricEncryption encriptarTrustedUsersFile = new AESSymmetricEncryption();
											String trustedUsersFilePath = trustedUsersFile.getAbsolutePath();

											FileInputStream fisTrustedUsersFile = new FileInputStream(trustedUsersFile);
											byte [] bytesTrustedFile = Files.readAllBytes(trustedUsersFile.toPath());
											int numBytesTrustedFile = fisTrustedUsersFile.read(bytesTrustedFile);
											encriptarTrustedUsersFile.encryptClientFile(trustedUsersFilePath, numBytesTrustedFile, bytesTrustedFile);
											encriptarTrustedUsersFile.signFile(path);
											trustedUsersFile.delete();
											fisTrustedUsersFile.close();
										}
										else {
											outStream.writeObject(resultado);
											outStream.writeObject("Utilizador jah eh seu amigo!");
											done = true;
										}
										break;
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
								String untrustedFileCifradoPath = pathAreaUserNoServer + this.utilizador.getId() 
								+ "/listaDeFicheiros.txt.cif";
								AESSymmetricEncryption aesEncryption = new AESSymmetricEncryption();
								if(aesEncryption.verifySign(untrustedFileCifradoPath)) {

									File untrustedFileDecifrado = new File(pathAreaUserNoServer + this.utilizador.getId() 
									+ "/trustedUsers.txt");

									File newUntrustedFileSemALinha = new File(pathAreaUserNoServer + this.utilizador.getId() 
									+ pathNovoTempFile);
									newUntrustedFileSemALinha.createNewFile();

									BufferedReader reader = new BufferedReader(new FileReader(untrustedFileDecifrado));
									BufferedWriter writer = new BufferedWriter(new FileWriter(newUntrustedFileSemALinha));

									String lineToRemove = nomeDaPessoa;
									String currentLine;

									while((currentLine = reader.readLine()) != null) {
										String trimmedLine = currentLine.trim();
										if(trimmedLine.equals(lineToRemove)) continue;
										writer.write(currentLine + System.getProperty("line.separator"));
									}
									writer.close(); 
									reader.close(); 
									newUntrustedFileSemALinha.renameTo(untrustedFileDecifrado);


									//FICHEIRO JA NAO TEM A LINHA, TEMOS DE ENCRIPTAR O FICHEIRO DE NOVO

									AESSymmetricEncryption encriptarUntrustedFile = new AESSymmetricEncryption();
									String untrustedFilePath = newUntrustedFileSemALinha.getAbsolutePath();

									//ENCRIPTAR OUTRA VEZ O NOVO INPUTFILE

									FileInputStream fisUntrustedFile = new FileInputStream(newUntrustedFileSemALinha);
									byte [] bytesUntrustedFile = Files.readAllBytes(newUntrustedFileSemALinha.toPath());
									int numBytesUntrustedFile = fisUntrustedFile.read(bytesUntrustedFile);
									encriptarUntrustedFile.encryptClientFile(untrustedFilePath, numBytesUntrustedFile, bytesUntrustedFile);
									encriptarUntrustedFile.signFile(untrustedFileCifradoPath);
									untrustedFileDecifrado.delete();
									fisUntrustedFile.close();
								}
								else {
									resultado = false;
								}
							}
							outStream.writeObject(resultado);

							if (!resultado) {
								if (this.utilizador.getId().equals(nomeDaPessoa)) {
									outStream.writeObject("Impossivel adicionar o nome " + nomeDaPessoa +
											", eh igual ao seu!");
								}
								outStream.writeObject("O utilizador " + nomeDaPessoa + 
										" nao existe na sua lista de trustedUsers");
							}
						}
						break;

					case "download":
						String userIDDownload = (String) inStream.readObject();
						List<String> trustedUserIDDownload = null;
						AESSymmetricEncryption aesEncryption = new AESSymmetricEncryption();

						if (userIDDownload.equals(this.utilizador.getId())) {
							outStream.writeObject(false);
							outStream.writeObject("O utilizador eh igual ao seu!");
						}
						else {
							for (Utilizador utilizadorCerto : users) {
								if (utilizadorCerto.getId().equals(userIDDownload)) {
									trustedUserIDDownload = utilizadorCerto.getTrustedIDs();
								}							
							}
							if (trustedUserIDDownload == null) {
								outStream.writeObject(false);
								outStream.writeObject("O utilizador nao existe!");
							}
							else {
								if (trustedUserIDDownload.contains(this.utilizador.getId())) {
									String nomeFicheiroToDownload = (String) inStream.readObject() + ".cif";
									File ficheiroToDownloadEncriptado = new File(nomeFicheiroToDownload);

									if(!ficheiroToDownloadEncriptado.exists()) { 
										outStream.writeObject(false);
										outStream.writeObject("O ficheiro nao existe!");
									}
									else {
										outStream.writeObject(true);
										int tamanhoFicheiro = (int) ficheiroToDownloadEncriptado.length();
										outStream.writeObject(tamanhoFicheiro);
										byte[] myByteArray = new byte[tamanhoFicheiro];
										myByteArray = aesEncryption.decryptClientFile(nomeFicheiroToDownload, tamanhoFicheiro, myByteArray);

										outStream.write(myByteArray, 0, tamanhoFicheiro);
										outStream.flush();

										String pathNovoFicheiroCriado = pathAreaUserNoServer + userIDDownload	+ "/ficheirosArmazenados/" 
												+ nomeFicheiroToDownload;
										File novoFicheiroCriado = new File(pathNovoFicheiroCriado);

										novoFicheiroCriado.delete();
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
						if (userIDMsg.equals(this.utilizador.getId())) {
							outStream.writeObject(false);
							outStream.writeObject("O utilizador eh igual ao seu!");
						}
						else {
							List<String> trustedUserIDMsg = null;
							Utilizador amigo = null;
							for (Utilizador utilizadorCerto : users) {
								if (utilizadorCerto.getId().equals(userIDMsg)) {
									trustedUserIDMsg = utilizadorCerto.getTrustedIDs();
									amigo = utilizadorCerto;
								}							
							}
							if (trustedUserIDMsg == null) {
								outStream.writeObject(false);
								outStream.writeObject("O utilizador nao existe!");
							}
							else {
								if (trustedUserIDMsg.contains(this.utilizador.getId())) {

									outStream.writeObject(true);

									String caixaMensagensCifradaPath = pathAreaUserNoServer + this.utilizador.getId() 
									+ "/caixaMensagens.txt.cif";

									AESSymmetricEncryption caixaDeMensagensVerify = new AESSymmetricEncryption();
									caixaDeMensagensVerify.verifySign(caixaMensagensCifradaPath);

									AESSymmetricEncryption aesEncryptionMsg = new AESSymmetricEncryption();
									String mensagem = this.utilizador.getId() + " disse: " + (String) inStream.readObject() + "\n" ;
									String mensagemEncriptada = aesEncryptionMsg.encryptMsg(mensagem);
									amigo.addMsg(mensagemEncriptada);

									//FICHEIRO JA TEM A LINHA, TEMOS DE ENCRIPTAR O FICHEIRO DE NOVO

									AESSymmetricEncryption encriptarCaixaMensagens = new AESSymmetricEncryption();
									String caixaMensagensDecifradaPath = pathAreaUserNoServer + this.utilizador.getId() 
									+ "/caixaMensagens.txt";
									File caixaMensagensDecifrada = new File(caixaMensagensDecifradaPath);

									//ENCRIPTAR OUTRA VEZ O NOVO INPUTFILE

									FileInputStream fisCaixaMensagens = new FileInputStream(caixaMensagensDecifradaPath);
									byte [] bytesCaixaMensagens = Files.readAllBytes(caixaMensagensDecifrada.toPath());
									int numBytesCaixaMensagens = fisCaixaMensagens.read(bytesCaixaMensagens);
									encriptarCaixaMensagens.encryptClientFile(caixaMensagensDecifradaPath, numBytesCaixaMensagens, bytesCaixaMensagens);
									encriptarCaixaMensagens.signFile(caixaMensagensCifradaPath);
									caixaMensagensDecifrada.delete();
									fisCaixaMensagens.close();

								}
								else {
									outStream.writeObject(false);
									outStream.writeObject("Nao eh amigo deste utilizador!");
								}
							}
						}
						break;
					case "collect":
						try {
							String ficheiroCaixaMensagensCifrado = pathAreaUserNoServer + this.utilizador.getId() + "/caixaMensagens.txt.cif";

							AESSymmetricEncryption aesEncryptionCaixaMensagens = new AESSymmetricEncryption();

							if (aesEncryptionCaixaMensagens.verifySign(ficheiroCaixaMensagensCifrado)) {

								String ficheiroCaixaMensagensDecifrado = pathAreaUserNoServer + this.utilizador.getId() + "/caixaMensagens.txt";


								BufferedReader reader = new BufferedReader(new FileReader(ficheiroCaixaMensagensDecifrado));
								String decryptedMsg = null;
								String msg = reader.readLine();
								if(msg == null) {
									outStream.writeObject(false);
									outStream.writeObject("\nA caixa de mensagens estah vazia!\n");				
								}
								else {
									outStream.writeObject(true);
									AESSymmetricEncryption aesEncryptionFirstMensagem = new AESSymmetricEncryption();
									decryptedMsg = aesEncryptionFirstMensagem.decryptMsg(msg); 
									outStream.writeObject(decryptedMsg);
									while ((msg = reader.readLine()) != null) {
										AESSymmetricEncryption aesEncryptionMensagem = new AESSymmetricEncryption();
										decryptedMsg = aesEncryptionMensagem.decryptMsg(msg); 
										outStream.writeObject(true);
										outStream.writeObject(decryptedMsg);
									}
									outStream.writeObject(false);
									outStream.writeObject("\nNao tem mais mensagens para ler!\n");
								}
								reader.close();

								File inputFile = new File(ficheiroCaixaMensagensCifrado);
								File tempFile = new File(pathAreaUserNoServer + this.utilizador.getId() 
								+ pathNovoTempFile);
								tempFile.createNewFile();
								inputFile.delete();
								tempFile.renameTo(inputFile);
							}

						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					case "quit":
						alive = false;
						outStream.writeObject(true);
						System.out.println("Utilizador " + "\"" + this.utilizador.getId() + "\"" + " terminou a sessao");
						break;
					default:
						System.out.println("Comando Invalido.");
						break;
					}
				}
				outStream.close();
				inStream.close();
				socket.close();
			}   catch (SocketException e) {
				System.out.println("Cliente " + this.utilizador.getId() + " desconectado abruptamente");
				System.out.println("A aceitar ligacoes");
			}
			catch (IOException | ClassNotFoundException | GeneralSecurityException e) {
				e.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
				Thread.currentThread().interrupt();
			} 

		}
	}
}
