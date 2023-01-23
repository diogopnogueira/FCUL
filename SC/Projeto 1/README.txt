/***************************************************************************
 *
 *  Seguranca e Confiabilidade 2018/19
 *  Grupo SegC-017
 *  Diogo Nogueira 49435
 *  Filipe Silveira 49506
 *  Filipe Capela 50296
 *
 ***************************************************************************/
 
O Nosso projeto consegue permite a troca de mensagens e ficheiros atrav�s dum canal seguro tls/ssl, 
Utiliza criptografia assim�trica e sim�trica, isto � consegue encriptar ficheiros com chaves as sim�tricas pretendidas ou
com as assim�tricas. 
 
Para executar o nosso programa � necess�rio executar os seguintes passos:

1 - Executar o UserManager, tudo o que ir� ser usado por este programa � pedido em execu��o e portanto n�o s�o necess�rios quaisquer argumentos
de entrada. Este � utilizado para criar os utilizadores todos que se encontram regitados no servidor.

2 - Iniciar o programa servidor (MsgFileServer) utilizando os comandos necess�rios para o efeito (i.e. MsgFileServer 12313).

3 - Ap�s a primeira execu��o do programa servidor, ser�o criados automaticamente v�rios diret�rios, nomeadamente a pasta "clientes" e a pasta "servidor", 
correspondendo respetivamente � zona onde s�o simuladas as �reas locais dos utilizadores e � pasta de armazenamento dos ficheiros na cloud (servidor). 
 
4 - Iniciar o programa cliente (MsgFile) utilizando os comandos necess�rios para o efeito (i.e. MsgFile 127.1.1.1:12313 utilizador palavrapass).

5 - Ap�s o registo de um utilizador "XXXX" ser�o criadas pastas e ficheiros para o utilizador, nomeadamente o dir�torio "clientes/area-XXXX" e "servidor/area-XXXX", 
correspondendo ent�o � �rea local do utilizador "XXXX" e � pasta de armazenamento de ficheiros do utilizador "XXXX" no servidor.

6 - Pedimos ent�o que introduza ficheiros dentro da �rea local do cliente em quest�o, ou seja, dentro da pasta "clientes/area-XXXX". 
Apenas deste modo podemos executar o comando "store" e consequentemente o "remove".
Opcionalmente, fornecemos uma pasta denominada "Ficheiros de Teste", onde se encontram v�rios ficheiros para testar o programa.

7 - Ap�s seguir estes passos pode seguir as indica��es passadas pelo terminal para utilizar o nosso programa.

//     //
//NOTAS//
//     //

NOTA1: Sempre que for criado um novo utilizador, � necess�rio copiar os ficheiros para a �rea local do mesmo ("clientes/area-XXXX") se se quiser testar.
NOTA2: Onde se l� "XXXX" temos sempre o nome do utilizador corrente.
NOTA3: Os ficheiros criados ap�s execu��o do comando "download" ser�o armazenados na pasta "clientes/area-XXXX/ficheirosArmazenados".
NOTA4: Sempre que for executado o comando "store" ou "remove", � favor de indicar a extens�o do ficheiro passado como argumento, 
	caso contr�rio o programa assumir� que o ficheiro n�o existe.
NOTA5: Tamanho m�ximo de ficheiro a ser enviado para o servidor ou transferido do mesmo: 8192 bytes.
NOTA6: Se, para efeitos de teste, terminar a execu��o do servidor enquanto existirem clientes ligados, estes apenas apresentaram sinal da disconex�o 
	se lhe for passado um comando, p.ex. store, list, etc...
NOTA7: Tent�mos executar os ficheiros com os respetivos ficheiros ".policy" mas os mesmos n�o conseguiam ser executados. Enviamos portanto os ficheiros 
       .policy mas n�o � possivel pass�-los como argumentos da VM.
