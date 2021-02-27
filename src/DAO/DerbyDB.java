package DAO;
import java.util.ArrayList;

import Mediator.Player;
import Strategy.Board;
import Strategy.Proprieta;
import Model.StateGame;
import java.sql.*;
/**
 *
 * @version n.n (24-02-2021)
 * @author Giosue' Orefice
 */

public class DerbyDB implements DAO{

	private String jdbcURL; // stringa per collegarsi al db
	private String sql;
	private Connection connection;
	private Statement statement;
	private static DerbyDB instance; //Singleton 

	private DerbyDB() {	
		jdbcURL = "jdbc:derby:salesdb;create=true";
		
		try { //Crea la tabella Game
			connection = DriverManager.getConnection(jdbcURL);
			statement = connection.createStatement();
			sql = "Create Table Game (id_G int not null,"+
					"N int, i int, numGiocatori int,winner int, primary key(id_G))"; 

			statement.executeUpdate(sql);
		} catch (SQLException e) {}

		try { //Crea la tabella Player
		sql = "Create Table Player (id int not null generated always as identity,"+
				"Nome varchar(25), Location int, Money int, Prigione boolean, TurniPrigione int,"
				+ "checkCostruire boolean,"
				+ "Game int, primary key(id), foreign key(Game) references Game(id_G))";	
		
			statement.executeUpdate(sql);
		} catch (SQLException e) {}

		try { //Crea la tabella Proprieta
		sql = "Create Table Proprieta (id_P int not null generated always as identity,"+
				"Nome varchar(25), NumCase int, Albergo boolean, Player int,Game int, primary key(id_P), "
				+ "foreign key(Game) references Game(id_G))";

		
			statement.executeUpdate(sql);
		} catch (SQLException e) {}


	}

	public static DerbyDB getInstance()  {
		if (instance == null) 
			instance = new DerbyDB();
		return instance;
	}

	@Override
	public void insert(StateGame state) {
		int rows;
		try {
			ResultSet result;
			int idGame =0;
			sql = "Select * from Game";
			result= statement.executeQuery(sql);
			
			while (result.next()) 
				idGame = result.getInt(1);

			idGame+=1;
			//Inserisci nella tabella game
			sql = "Insert into Game (id_G,N,i,numGiocatori,winner) values (" + idGame +"," +
					state.getN() + "," + state.getCurrentPlayer() + "," + state.getNumGiocatoriInGara() + "," + state.getWinner() + ")";
			rows = statement.executeUpdate(sql);
			if(rows !=1)
				System.out.println("error");

			//Inserisci nella tabella Player
			Player[] players = state.getPlayers();
			for(int j=0;j<state.getN();j++) {
				sql = "Insert into Player (Nome,Location,Money,Prigione,TurniPrigione,checkCostruire,Game) values"
						+ "('" +  players[j].getName() + "'," + players[j].getLocation() + "," + players[j].getMoney() + 
						"," + players[j].isPrigione() + "," + players[j].getTurniPrigione() + "," + players[j].isCostruzione() 
						+"," + idGame + ")";
				rows = statement.executeUpdate(sql);
				if(rows !=1)
					System.out.println("error");
			}

			//Inserisci nella tabella Proprieta
			Board board = state.getBoard();
			int index=-1;
			for(int j=0;j<12;j++) {
				if(board.getSquare(j) instanceof Proprieta) {
					Proprieta p = (Proprieta) board.getSquare(j);
					if(p.getPlayer()!=null) { //controlla se la proprieta è stata acquista 
						for(int z=0;z<state.getN();z++) { 
							if(players[z].getName().equals(p.getPlayer().getName()))
								index=z;
						}
					}
					else index=-1;
					sql = "Insert into Proprieta (Nome,NumCase,Albergo,Player,Game) values"
							+ "('" + p.getName() +"'," + p.getNumCase() + "," + p.isAlbergo() + "," + index +"," + idGame +")" ;

					rows = statement.executeUpdate(sql);
					if(rows !=1)
						System.out.println("error");
				}


			}

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public ArrayList<Player> findAllWinner(){
		ResultSet result;
		ArrayList<Player> players = new ArrayList<Player>();
		sql = "Select * from Game";
		try {
			result= statement.executeQuery(sql);
			//scorri tutte le righe della tabella ed ottieni i vincitori
			while(result.next()) 
				players.add(players.size(), new Player("Player" + result.getInt(5)));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return players;
	}

	@Override
	public StateGame findGame(int id){
		@SuppressWarnings("unused")
		StateGame state;
		Player[] players;
		boolean check=false;
		int i=-1,n=0,numGiocatori=0,winner=-1;
		try { //trova il game con l'id di input
			ResultSet result;
			sql = "Select * from Game";
			result= statement.executeQuery(sql);
			while(result.next()) {
				if(result.getInt(1) == id) {
					i = result.getInt(3);
					n= result.getInt(2);
					numGiocatori = result.getInt(4);
					winner = result.getInt(5);
					players = new Player[n];
					check=true;
				}
			}
			if(!check) //se non lo trova 
				return null;

			players = new Player[n];
			//trova i giocatori della partita
			sql = "Select * from Player";
			result= statement.executeQuery(sql);
			int z=0;
			while(result.next()) {
				if(result.getInt(8) == id) {
					players[z] = new Player(result.getString(2));
					players[z].setLocation(result.getInt(3));
					players[z].setMoney(result.getInt(4));
					players[z].setPrigione(result.getBoolean(5));
					players[z].setTurniPrigione(result.getInt(6));
					players[z].setCostruzione(result.getBoolean(7));
					z++;
				}
			}
			
			//trova le proprieta della partita
			sql = "Select * from Proprieta";
			result= statement.executeQuery(sql);
			Board  board = new Board(n);
			z=0;
			while(result.next()) {
				if(result.getInt(6) == id) {
					while(!(board.getSquare(z) instanceof Proprieta)) 
						z++;
					Proprieta p = (Proprieta) board.getSquare(z);
					p.setNumCase(result.getInt(3));
					p.setAlbergo(result.getBoolean(4));
					int index =result.getInt(5);
					if(index!=-1)
						p.setPlayer(players[result.getInt(5)]);
					z++;
				}
			}
			return new StateGame(n,winner, players, numGiocatori, i,board);

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}



}
