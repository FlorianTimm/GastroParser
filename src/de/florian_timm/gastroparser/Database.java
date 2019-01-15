package de.florian_timm.gastroparser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import org.sqlite.SQLiteConfig;

import de.florian_timm.gastroparser.entity.Artikel;
import de.florian_timm.gastroparser.entity.Garantiepreis;
import de.florian_timm.gastroparser.entity.Lieferant;
import de.florian_timm.gastroparser.entity.Produkt;
import de.florian_timm.gastroparser.entity.Rechnung;
import de.florian_timm.gastroparser.entity.Rechnungsposten;
import de.florian_timm.gastroparser.ordner.LieferantOrdner;
import de.florian_timm.gastroparser.ordner.ProduktOrdner;

public class Database {
	static Database db = null;
	Connection conn = null;

	private Database() {
		createNewDatabase();
		loadLieferanten();
		loadRechnungen();
		loadProdukt();
		loadArtikel();
		loadRechnungsPosten();
	}

	public static Database get() {
		if (db == null) {
			db = new Database();
		}
		return db;
	}
	
	public ResultSet execute(String sql) {
		Statement stmt;
		try {
			stmt = conn.createStatement();
			return stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void createNewDatabase() {

		String url = "jdbc:sqlite:gastroData.db";

		try {

			Class.forName("org.sqlite.JDBC");

			SQLiteConfig config = new SQLiteConfig();
			config.setReadOnly(false);

			conn = DriverManager.getConnection(url, config.toProperties());

			Statement stmt = conn.createStatement();
			// create new tables
			stmt.execute("CREATE TABLE IF NOT EXISTS lieferanten (lid integer primary key,name text,"
					+ "ustId text, regDate text, regDatePos integer, regDateOrder text,regRechNr text, "
					+ "regRechNrPos int,regRechPos text,regRechPosOrder text);");

			stmt.execute("CREATE TABLE IF NOT EXISTS produkte (pid integer primary key,bezeichnung text,"
					+ "einheit text, mwst real);");

			stmt.execute("CREATE TABLE IF NOT EXISTS rechnungen (rid integer primary key,"
					+ "datumUhrzeit text,lieferant integer,rechnungsnummer text,"
					+ "FOREIGN KEY(lieferant) REFERENCES lieferanten(lid));");

			stmt.execute("CREATE TABLE IF NOT EXISTS artikel (" + "aid integer primary key,artikelnr text,"
					+ "ean long,menge real,produkt integer," + "FOREIGN KEY(produkt) REFERENCES produkte(pid));");

			stmt.execute("CREATE TABLE IF NOT EXISTS rechnungsposten (" + "rpid integer primary key,"
					+ "artikel integer,menge real,preis real,rechnung integer,"
					+ "FOREIGN KEY(artikel) REFERENCES artikel(aid),"
					+ "FOREIGN KEY(rechnung) REFERENCES rechnungen(rid));");

		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	
	public long insert(Rechnungsposten posten) {
		try {
			String sql = "INSERT INTO rechnungsposten (artikel, menge, preis, rechnung) VALUES (?,?,?,?)";
			String[] id = { "pid" };
			PreparedStatement ppstmt = this.conn.prepareStatement(sql, id);

			ppstmt.setLong(1, posten.getArtikel().getId());
			ppstmt.setDouble(2, posten.getMenge());
			ppstmt.setDouble(3, posten.getPreis());
			ppstmt.setLong(4, posten.getRechnung().getId());
			
			ppstmt.execute();
			ResultSet rs = ppstmt.getGeneratedKeys();

			if (rs.next()) {
				return rs.getLong(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	private void loadRechnungsPosten() {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT rpid, artikel, menge, preis, rechnung FROM rechnungsposten;");
			while (rs.next()) {
				long rpid = rs.getLong(1);
				long artikelId = rs.getLong(2);
				double menge = rs.getDouble(3);
				double preis = rs.getDouble(4);
				long rechnungId = rs.getLong(5);
				
				Artikel artikel = Artikel.getArtikel(artikelId);
				Rechnung rechnung = Rechnung.getRechnung(rechnungId);
				
				new Rechnungsposten(rpid, artikel, menge, preis, rechnung);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public long insert(Artikel artikel) {
		try {
			String sql = "INSERT INTO artikel (artikelnr,ean,menge,produkt) VALUES (?,?,?,?)";
			String[] id = { "pid" };
			PreparedStatement ppstmt = this.conn.prepareStatement(sql, id);

			ppstmt.setString(1, artikel.getArtikelnr());
			ppstmt.setLong(2, artikel.getEan());
			ppstmt.setDouble(3, artikel.getMenge());
			ppstmt.setLong(4, artikel.getProdukt().getId());
			
			ppstmt.execute();
			ResultSet rs = ppstmt.getGeneratedKeys();

			if (rs.next()) {
				return rs.getLong(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	private void loadArtikel() {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT aid,artikelnr,ean,menge,produkt FROM artikel;");
			while (rs.next()) {
				long aid = rs.getLong(1);
				String artikelnr = rs.getString(2);
				long ean = rs.getLong(3);
				double menge = rs.getDouble(4);
				long produktId = rs.getLong(5);
				
				Produkt produkt = ProduktOrdner.getInstanz().getProdukt(produktId);
				
				new Artikel(aid, artikelnr, ean, menge, produkt);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
public long insert(Produkt produkt) {
		try {
			String sql = "INSERT INTO produkte (bezeichnung,einheit,mwst) VALUES (?,?,?)";
			String[] id = { "pid" };
			PreparedStatement ppstmt = this.conn.prepareStatement(sql, id);

			ppstmt.setString(1, produkt.getBezeichnung());
			ppstmt.setString(2, produkt.getEinheit());
			ppstmt.setDouble(3, produkt.getMwst());
			
			ppstmt.execute();
			ResultSet rs = ppstmt.getGeneratedKeys();

			if (rs.next()) {
				return rs.getLong(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	private void loadProdukt() {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT pid, bezeichnung,einheit,mwst FROM produkte;");
			while (rs.next()) {
				long pid = rs.getLong(1);
				String bezeichnung = rs.getString(2);
				String einheit = rs.getString(3);
				double mwst = rs.getDouble(4);
				
				new Produkt(pid, bezeichnung, einheit, mwst);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public long insert(Rechnung rechnung) {
		
		
		try {
			String sql = "INSERT INTO rechnungen (datumUhrzeit,lieferant,rechnungsnummer) VALUES (?,?,?)";
			String[] id = { "rid" };
			PreparedStatement ppstmt = this.conn.prepareStatement(sql, id);

			ppstmt.setString(1, rechnung.getDatumUhrzeit().toString());
			ppstmt.setLong(2, rechnung.getLieferant().getId());
			ppstmt.setString(3, rechnung.getRechnungsnummer());
			
			ppstmt.execute();
			ResultSet rs = ppstmt.getGeneratedKeys();

			if (rs.next()) {
				return rs.getLong(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	private void loadRechnungen() {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT rid, datumUhrzeit,lieferant,rechnungsnummer FROM rechnungen;");
			while (rs.next()) {
				long rid = rs.getLong(1);
				LocalDateTime datumUhrzeit = LocalDateTime.parse(rs.getString(2));
				Long lieferantId = rs.getLong(3);
				String rechnungsnummer = rs.getString(4);
				
				Lieferant lieferant = LieferantOrdner.getInstanz().getLieferant(lieferantId);
				new Rechnung(rid, datumUhrzeit, lieferant, rechnungsnummer);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public long insert(Lieferant lieferant) {
		try {
			String sql = "INSERT INTO lieferanten (name, ustId,regDate, regDatePos, regDateOrder,regRechNr,"
					+ "regRechNrPos,regRechPos,regRechPosOrder) VALUES (?,?,?,?,?,?,?,?,?)";
			String[] id = { "lid" };
			PreparedStatement ppstmt = this.conn.prepareStatement(sql, id);

			ppstmt.setString(1, lieferant.getName());
			ppstmt.setString(2, lieferant.getUstId());
			ppstmt.setString(3, lieferant.getRegDate());
			ppstmt.setInt(4, lieferant.getRegDatePos());
			ppstmt.setString(5, lieferant.getRegDateOrder());
			ppstmt.setString(6, lieferant.getRegRechNr());
			ppstmt.setInt(7, lieferant.getRegRechNrPos());
			ppstmt.setString(8, lieferant.getRegRechPos());
			
			int[] rechPosOrder = lieferant.getRegRechPosOrder();
			String strArray[] = new String[rechPosOrder.length];
			for (int i = 0; i < rechPosOrder.length; i++)
				strArray[i] = String.valueOf(rechPosOrder[i]);

			ppstmt.setString(9, String.join(",", strArray));
			
			ppstmt.execute();
			ResultSet rs = ppstmt.getGeneratedKeys();

			if (rs.next()) {
				return rs.getLong(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	private void loadLieferanten() {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT lid, name,ustId,regDate, "
					+ "regDatePos,regDateOrder,regRechNr,regRechNrPos,regRechPos,regRechPosOrder "
					+ "FROM lieferanten;");
			while (rs.next()) {
				long lid = rs.getLong(1);
				String name = rs.getString(2);
				String ustId = rs.getString(3);
				String regDate = rs.getString(4);
				int regDatePos = rs.getInt(5);
				String regDateOrder = rs.getString(6);
				String regRechNr = rs.getString(7);
				int regRechNrPos = rs.getInt(8);
				String regRechPos= rs.getString(9);
				String[] rRPO = rs.getString(10).split(",");
				int[] regRechPosOrder = new int[rRPO.length];
				for (int i = 0; i < rRPO.length; i++)
					regRechPosOrder[i] = Integer.parseInt(rRPO[i]);
				new Lieferant(lid, name, ustId, regDate, regDatePos,
						regDateOrder, regRechPos, regRechPosOrder, regRechNr, regRechNrPos);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public long insert(Garantiepreis garantiepreis) {
		try {
			String sql = "INSERT INTO garantiepreis (produkt, lieferant, preis, von, bis) VALUES (?,?,?,?,?)";
			String[] id = { "pid" };
			PreparedStatement ppstmt = this.conn.prepareStatement(sql, id);

			ppstmt.setLong(1, garantiepreis.getProdukt().getId());
			ppstmt.setLong(2, garantiepreis.getLieferant().getId());
			ppstmt.setDouble(3, garantiepreis.getPreis());
			ppstmt.setString(4, garantiepreis.getVon().toString());
			ppstmt.setString(5, garantiepreis.getBis().toString());
			
			ppstmt.execute();
			ResultSet rs = ppstmt.getGeneratedKeys();

			if (rs.next()) {
				return rs.getLong(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

}
