package br.com.caelum.cadastro;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaSyncEvent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.modelo.Aluno;

public class FormularioActivity extends Activity {

	private Button gravar;
	private FormularioHelper helper;
	
	private int TIRA_FOTO = 101;
	private String caminhoArquivo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);

		helper = new FormularioHelper(this);

		final Aluno alunoParaSerAlterado = (Aluno) getIntent().getSerializableExtra("alunoSelecionado");

		if (alunoParaSerAlterado != null) {
			helper.colocaAlunoNoFormulario(alunoParaSerAlterado);
		}

		gravar = (Button) findViewById(R.id.gravar);

		gravar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Aluno aluno = new Aluno();
				AlunoDAO dao = new AlunoDAO(FormularioActivity.this);

				if (helper.pegarDadosAluno() != null) {
					gravar.setText("Atualizar");
					aluno = helper.pegarDadosAluno();

					if (alunoParaSerAlterado != null) {
						aluno.setId(alunoParaSerAlterado.getId());
						dao.atualizar(aluno);
					} else {
						dao.insere(aluno);
					}

					dao.close();
					finish();
				} else {
					message("Atenção!", "Favor preencher todos os campos!");
				}
			}

		});
		
		ImageView foto = helper.getIvFoto();
		
		foto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				
				caminhoArquivo = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".png";
				File arquivo = new File(caminhoArquivo);
				
				Uri localFoto = Uri.fromFile(arquivo);
				irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);
				
				startActivityForResult(irParaCamera, TIRA_FOTO);
			}
		});

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	
		if(requestCode == TIRA_FOTO){
			if(resultCode == Activity.RESULT_OK){
				helper.carregaImage(caminhoArquivo);
			} else {
				caminhoArquivo = null;
			}
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void message(String titulo, String msg) {

		AlertDialog.Builder caixa = new AlertDialog.Builder(FormularioActivity.this);
		caixa.setTitle(titulo);
		caixa.setMessage(msg);
		caixa.setNeutralButton("OK", null);
		caixa.show();

	}
	
}

























