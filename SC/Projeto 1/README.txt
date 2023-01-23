/***************************************************************************
 *
 *  Seguranca e Confiabilidade 2018/19
 *  Grupo SegC-017
 *  Diogo Nogueira 49435
 *  Filipe Silveira 49506
 *  Filipe Capela 50296
 *
 ***************************************************************************/
 
O Nosso projeto consegue permite a troca de mensagens e ficheiros através dum canal seguro tls/ssl, 
Utiliza criptografia assimétrica e simétrica, isto é consegue encriptar ficheiros com chaves as simétricas pretendidas ou
com as assimétricas. 
 
Para executar o nosso programa é necessário executar os seguintes passos:

1 - Executar o UserManager, tudo o que irá ser usado por este programa é pedido em execução e portanto não são necessários quaisquer argumentos
de entrada. Este é utilizado para criar os utilizadores todos que se encontram regitados no servidor.

2 - Iniciar o programa servidor (MsgFileServer) utilizando os comandos necessários para o efeito (i.e. MsgFileServer 12313).

3 - Após a primeira execução do programa servidor, serão criados automaticamente vários diretórios, nomeadamente a pasta "clientes" e a pasta "servidor", 
correspondendo respetivamente à zona onde são simuladas as áreas locais dos utilizadores e à pasta de armazenamento dos ficheiros na cloud (servidor). 
 
4 - Iniciar o programa cliente (MsgFile) utilizando os comandos necessários para o efeito (i.e. MsgFile 127.1.1.1:12313 utilizador palavrapass).

5 - Após o registo de um utilizador "XXXX" serão criadas pastas e ficheiros para o utilizador, nomeadamente o dirétorio "clientes/area-XXXX" e "servidor/area-XXXX", 
correspondendo então à área local do utilizador "XXXX" e à pasta de armazenamento de ficheiros do utilizador "XXXX" no servidor.

6 - Pedimos então que introduza ficheiros dentro da área local do cliente em questão, ou seja, dentro da pasta "clientes/area-XXXX". 
Apenas deste modo podemos executar o comando "store" e consequentemente o "remove".
Opcionalmente, fornecemos uma pasta denominada "Ficheiros de Teste", onde se encontram vários ficheiros para testar o programa.

7 - Após seguir estes passos pode seguir as indicações passadas pelo terminal para utilizar o nosso programa.

//     //
//NOTAS//
//     //

NOTA1: Sempre que for criado um novo utilizador, é necessário copiar os ficheiros para a área local do mesmo ("clientes/area-XXXX") se se quiser testar.
NOTA2: Onde se lê "XXXX" temos sempre o nome do utilizador corrente.
NOTA3: Os ficheiros criados após execução do comando "download" serão armazenados na pasta "clientes/area-XXXX/ficheirosArmazenados".
NOTA4: Sempre que for executado o comando "store" ou "remove", é favor de indicar a extensão do ficheiro passado como argumento, 
	caso contrário o programa assumirá que o ficheiro não existe.
NOTA5: Tamanho máximo de ficheiro a ser enviado para o servidor ou transferido do mesmo: 8192 bytes.
NOTA6: Se, para efeitos de teste, terminar a execução do servidor enquanto existirem clientes ligados, estes apenas apresentaram sinal da disconexão 
	se lhe for passado um comando, p.ex. store, list, etc...
NOTA7: Tentámos executar os ficheiros com os respetivos ficheiros ".policy" mas os mesmos não conseguiam ser executados. Enviamos portanto os ficheiros 
       .policy mas não é possivel passá-los como argumentos da VM.
