package Repositories;

import classes.persoane.Admin;
import classes.persoane.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminRepository extends Repository<Admin>{

    @Override
    protected void insert(Admin admin) {

        String text = "";
        text += "insert into admini (nume, parola, varsta, tip) values (";
        text += String.format("'%s', '%s', %d, '%s'", admin.getNume(), admin.getParola(), admin.getVarsta(), admin.getTip());
        text += ");";
        executeStatement(text);
    }

    @Override
    protected void delete(int id) {

        String text = String.format("delete from admini where id = %d ;", id);
        executeStatement(text);
    }

    @Override
    protected ResultSet allResultSet() {

        executeStatement("select * from admini;");
        try{

            return statement.getResultSet();
        }catch (SQLException e){

            System.out.println("eroare la executeStatement;");
            return null;
        }
    }

    @Override
    protected List<Admin> all() {
        ResultSet set = allResultSet();
        List<Admin> admini = new ArrayList<>();

        try{

            while(set.next()){

                Admin admin = new Admin(set.getString(2), set.getString(3), set.getInt(4), set.getString(5));
                admin.setId(set.getInt(1));
                admini.add(admin);
            }
            return admini;
        }catch (Exception e){

            System.out.println("eroare la select * from admini");
            return null;
        }
    }

    @Override
    protected void update(Admin admin) {

        String text = "";
        text += String.format("update admini set nume = '%s', parola = '%s', varsta = %d, tip = '%s' where id = %d ;", admin.getNume(), admin.getParola(), admin.getVarsta(), admin.getTip(), admin.getId());
        executeStatement(text);
    }

    @Override
    protected boolean existsID(int id) {
        executeStatement(String.format("select * from admini where id = %d", id));

        try{

            ResultSet set = statement.getResultSet();

            if(set.next()){

                return true;
            }
        }catch (Exception e){

            System.out.println("eroare la existsID");
        }
        return false;
    }


    public Admin getAdminByID(int id){

        executeStatement(String.format("select * from admini where id = %d", id));
        try{

            ResultSet set = statement.getResultSet();
            if(set.next()){

                Admin admin = new Admin(set.getString(2), set.getString(3), set.getInt(4), set.getString(5));
                admin.setId(set.getInt(1));

                return admin;
            }
        }catch (Exception e){

            System.out.println("eroare la getClientByID");
        }

        return null;
    }
}