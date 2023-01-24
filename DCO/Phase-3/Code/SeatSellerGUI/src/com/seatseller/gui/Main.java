package com.seatseller.gui;
	
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.seatseller.SeatSeller;
import com.seatseller.api.ICriarGrelhaHandler;
import com.seatseller.api.IReservarLugarHandler;
import com.seatseller.api.ISeatSeller;
import com.seatseller.api.ISessao;
import com.seatseller.api.exceptions.DoesNotExistException;
import com.seatseller.api.exceptions.InvalidCreditCardException;
import com.seatseller.api.exceptions.NonUniqueException;
import com.seatseller.api.wrappers.Combinacao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	private static Logger LOGGER = Logger.getLogger(Main.class.getName());
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		ISeatSeller app = new SeatSeller();
		
		startUp(app);
		createWindow(primaryStage, app);
	}
	
	private void createWindow(Stage primaryStage, ISeatSeller servicos) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root,400,400);

			LoginMenuController lm = fxmlLoader.getController();
			lm.setServicos(servicos);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error loading fxml", e);
		}

		
	}

	private void startUp(ISeatSeller servicos) {
		criarContas(servicos);
		ISessao serv = servicos.autenticar("admin", "admin").get();
		criarTiposDeLugar(serv);
		criarGrelha2(serv);
		criarReserva(serv);
	}
	
	private void criarContas(ISeatSeller servicos) {
		
		servicos.getRegistarUtilizadorHandler().registarAdministrador("admin", "admin");
		servicos.getRegistarUtilizadorHandler().registarFuncionario("zacarias", "zacarias", LocalTime.parse("13:00"), LocalTime.parse("20:00"));
		servicos.getRegistarUtilizadorHandler().registarFuncionario("serafim", "serafim", LocalTime.parse("00:00"), LocalTime.parse("23:59"));
		
		servicos.getRegistarUtilizadorHandler().registarClienteFinal("ana", "ana");
		servicos.getRegistarUtilizadorHandler().registarClienteFinal("maria", "maria");
		LOGGER.info("Utilizadores criados");
	}

	private void criarTiposDeLugar(ISessao serv) {
		serv.getCriarTipoDeLugarHandler().criarTipoDeLugar("Lugar Normal", "Lugar típico na sala de cinema", 7.00, false);
		serv.getCriarTipoDeLugarHandler().criarTipoDeLugar("Lugar VIP", "Lugar com cadeira reclinavel", 10.00, false);
		serv.getCriarTipoDeLugarHandler().criarTipoDeLugar("Lugar Love Seat", "Lugar sem divisória de um dos lados", 8.00, false);
		serv.getCriarTipoDeLugarHandler().criarTipoDeLugar("Lugar Acessível", "Lugar adaptado a cadeiras de rodas", 2.00, false);
		LOGGER.info("Tipos de lugares criados");
	}
	
	private void criarGrelha2(ISessao serv)  {
		ICriarGrelhaHandler cgh = serv.getCriarGrelhaHandler();
		try {
			cgh.iniciarGrelha("Sala VIP", 1.2);
		} catch (NonUniqueException e) {
			LOGGER.log(Level.SEVERE, "Grelha já existe", e);
		}
		Optional<String> tipoDeLugarPadrao = cgh.indicarDimensao(5,10);
		
		if (!tipoDeLugarPadrao.isPresent()) {
			try {
				cgh.indicarTipoPadrao("Lugar VIP");
				for(int i=0; i<10; i++) {
					cgh.indicarTipoLugar(4, i, "Lugar Acessível");
				}
			} catch (DoesNotExistException e) {
				LOGGER.log(Level.SEVERE, "Tipo de Lugar não existe", e);
			}
		}
		cgh.terminar();
	}
	
	private void criarReserva(ISessao serv) {
		IReservarLugarHandler rlh = serv.getReservarLugarHandler();
		try {
			
			if (!serv.isClienteFinal()) {
				rlh.indicarCliente("ana");
			}
			LocalDate date = LocalDate.of(2022, 11, 24);
			LocalTime time = LocalTime.of(17, 00);
			Iterable<Combinacao> combs = rlh.indicarDataHora(date, time);
			
			Combinacao c0 = combs.iterator().next();
			String l = rlh.indicarCombinacao(c0.getGrelha(), c0.getTipoDeLugar());
			LOGGER.info("Atribuido lugar " + l);
			
			rlh.terminarLugares();
			
			double preco = -1.0;
			try {
				preco = rlh.indicarCC("1994132412342221", 123, 7, 2020);
			} catch (InvalidCreditCardException e) {
				LOGGER.log(Level.SEVERE, "Cartão de Crédito Inválido: ", e);
			}
			LOGGER.info("Preco: " + preco);
			String cod = rlh.confirmarReserva();
			LOGGER.info("Confirmada Reserva: " + cod);
			
		} catch (DoesNotExistException e) {
			LOGGER.log(Level.SEVERE, "Erro: ", e);
		}
	}

}
