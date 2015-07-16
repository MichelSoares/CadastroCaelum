package br.com.caelum.cadastro;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import br.com.caelum.cadastro.adapter.AlunoAdapter;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.modelo.Aluno;

public class ListaAlunosActivity extends Activity {

	private ListView lista; 
	private Aluno aluno;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listagem_alunos);

		lista = (ListView) findViewById(R.id.lista);

		registerForContextMenu(lista);

		// final String[] alunos = {"Michel", "Marlene", "Rosângela"};

		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int posicao, long id) {

				Aluno alunoParaSerAlterado = (Aluno) adapter.getItemAtPosition(posicao);
				Intent irParaOFormulario = new Intent(ListaAlunosActivity.this,FormularioActivity.class);
				irParaOFormulario.putExtra("alunoSelecionado",alunoParaSerAlterado);
				
				startActivity(irParaOFormulario);

			}
		});

		lista.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int posicao, long id) {

				aluno = (Aluno) adapter.getItemAtPosition(posicao);

				return false;
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		this.carregarLista();
	}

	public void carregarLista() {
		AlunoDAO dao = new AlunoDAO(this);
		List<Aluno> alunos = dao.getLista();
		AlunoAdapter adapter = new AlunoAdapter(alunos, this);
		lista.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.menu_lista_alunos, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.novo:

			startActivity(new Intent(ListaAlunosActivity.this,
					FormularioActivity.class));
			break;

		case R.id.discador:

			String numero = "";
			startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel: "
					+ numero)));
			Toast.makeText(ListaAlunosActivity.this,
					"Digite o número DESEJADO!", Toast.LENGTH_SHORT).show();

		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		MenuItem ligar = menu.add("Ligar");

		ligar.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem arg0) {

				Intent ligarParaContato = new Intent(Intent.ACTION_CALL);

				Uri telefoneDoAluno = Uri.parse("tel: " + aluno.getTelefone());
				ligarParaContato.setData(telefoneDoAluno);

				startActivity(ligarParaContato);

				return false;
			}
		});

		MenuItem sms = menu.add("Enviar SMS");

		sms.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem arg0) {

				Intent mandarSMS = new Intent(Intent.ACTION_VIEW);

				Uri smsDoAluno = Uri.parse("sms: " + aluno.getTelefone());
				mandarSMS.setData(smsDoAluno);

				startActivity(mandarSMS);

				return false;
			}
		});

		MenuItem mapa = menu.add("Achar no Mapa");

		mapa.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {

				Intent acharNoMapa = new Intent(Intent.ACTION_VIEW);
				
				Uri localizacaoAluno = Uri.parse("geo:0,0?z=14&q=" + aluno.getEndereco());
				acharNoMapa.setData(localizacaoAluno);
				
				startActivity(acharNoMapa);
				
				return false;
			}
		});

		MenuItem site = menu.add("Navegar no site");

		Intent irParaSiteAluno = new Intent(Intent.ACTION_VIEW);
		Uri siteAluno = Uri.parse("http:// " + aluno.getSite());
		irParaSiteAluno.setData(siteAluno);
		site.setIntent(irParaSiteAluno);
		

		MenuItem deletar = menu.add("Deletar");

		deletar.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {

				AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
				dao.deletar(aluno);
				dao.close();

				carregarLista();
				return false;
			}
		});

		menu.add("Enviar E-mail");

		super.onCreateContextMenu(menu, v, menuInfo);
	}
}
