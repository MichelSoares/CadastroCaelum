package br.com.caelum.cadastro.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.caelum.cadastro.modelo.Aluno;

public class AlunoDAO extends SQLiteOpenHelper {

	private static final String DATABASE = "CadastroCaelum";
	private static final int VERSION = 1;
	private static final String TABELA = "alunos";

	public AlunoDAO(Context context) {
		super(context, DATABASE, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {

		String sql = "CREATE TABLE " + TABELA + "("
				+ "id INTEGER PRIMARY KEY, " + "nome TEXT, " + "site TEXT, "
				+ "endereco TEXT, " + "telefone TEXT, " + "nota REAL, "
				+ "caminhoFoto TEXT " + ");";

		database.execSQL(sql);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		
		String sql = "DROP TABLE IF EXISTS " + TABELA;
		database.execSQL(sql);
		onCreate(database);
	
	}
	
	public void insere(Aluno aluno){
		
		ContentValues cv = new ContentValues();
		
		cv.put("nome", aluno.getNome());
		cv.put("site", aluno.getSite());
		cv.put("endereco", aluno.getEndereco());
		cv.put("telefone", aluno.getTelefone());
		cv.put("nota", aluno.getNota());
		cv.put("caminhoFoto", aluno.getCaminhoFoto());
		
		getWritableDatabase().insert(TABELA, null, cv);
		
	}
	
	public List<Aluno> getLista(){
		
		List<Aluno> alunos = new ArrayList<Aluno>();
		
		String sql = "SELECT * FROM " + TABELA + ";";
		Cursor c = getReadableDatabase().rawQuery(sql, null);
	
		while(c.moveToNext()){
			
			Aluno aluno = new Aluno();
			
			aluno.setId(c.getLong(c.getColumnIndex("id")));
			aluno.setNome(c.getString(c.getColumnIndex("nome")));
			aluno.setSite(c.getString(c.getColumnIndex("site")));
			aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
			aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
			aluno.setNota(c.getInt(c.getColumnIndex("nota")));
			aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));
			
			alunos.add(aluno);
			
		}
		
		return alunos;
		
	}

	public void deletar(Aluno aluno) {

		String[] ids = {aluno.getId().toString()};
		getWritableDatabase().delete(TABELA, "id=?", ids);
	}

	public void atualizar(Aluno aluno) {
		
		ContentValues cv = new ContentValues();
		
		cv.put("nome", aluno.getNome());
		cv.put("site", aluno.getSite());
		cv.put("endereco", aluno.getEndereco());
		cv.put("telefone", aluno.getTelefone());
		cv.put("nota", aluno.getNota());
		cv.put("caminhoFoto", aluno.getCaminhoFoto());
		
		String[] ids = {aluno.getId().toString()};
		getWritableDatabase().update(TABELA, cv, "id=?" , ids );
		
	}

	public boolean isAluno(String telefone) {
		
		String sql = "SELECT id FROM " + TABELA + " WHERE telefone = ?";
		String[] args = {telefone};
		Cursor cursor = getReadableDatabase().rawQuery(sql, args);
		
		boolean existeUmPrimeiro = cursor.moveToFirst();
		
		return existeUmPrimeiro;
	}

}











































