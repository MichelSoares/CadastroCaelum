package br.com.caelum.cadastro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import br.com.caelum.cadastro.modelo.Aluno;

public class FormularioHelper {

	private EditText etNome;
	private EditText etSite;
	private EditText etEndereco;
	private EditText etTelefone;
	private Aluno aluno;
	private ImageView ivFoto;
	private RatingBar rbNota;

	public FormularioHelper(FormularioActivity activity) {

		aluno = new Aluno();

		etNome = (EditText) activity.findViewById(R.id.etNome);
		etSite = (EditText) activity.findViewById(R.id.etSite);
		etEndereco = (EditText) activity.findViewById(R.id.etEndereco);
		etTelefone = (EditText) activity.findViewById(R.id.etTelefone);
		rbNota = (RatingBar) activity.findViewById(R.id.nota);
		ivFoto = (ImageView) activity.findViewById(R.id.foto);
		

	}

	public Aluno pegarDadosAluno() {

		if (etNome.getText().toString().isEmpty()
				|| etSite.getText().toString().isEmpty()
				|| etEndereco.getText().toString().isEmpty()
				|| etTelefone.getText().toString().isEmpty()) {
			return null;
		} else {

			aluno.setNome(etNome.getText().toString());
			aluno.setSite(etSite.getText().toString());
			aluno.setEndereco(etEndereco.getText().toString());
			aluno.setTelefone(etTelefone.getText().toString());
			aluno.setNota(rbNota.getProgress());

			return aluno;

		}
	}

	public void colocaAlunoNoFormulario(Aluno alunoParaSerAlterado) {

		aluno = alunoParaSerAlterado;

		etNome.setText(alunoParaSerAlterado.getNome());
		etSite.setText(alunoParaSerAlterado.getSite());
		etEndereco.setText(alunoParaSerAlterado.getEndereco());
		etTelefone.setText(alunoParaSerAlterado.getTelefone());
		rbNota.setProgress(alunoParaSerAlterado.getNota());
		
		if(aluno.getCaminhoFoto() != null) {
			carregaImage(aluno.getCaminhoFoto());
		}

	}

	public ImageView getIvFoto() {
		return ivFoto;
	}

	public void carregaImage(String caminhoArquivo) {
		aluno.setCaminhoFoto(caminhoArquivo);

		Bitmap imagem = BitmapFactory.decodeFile(caminhoArquivo);
		Bitmap imagemReduzida = Bitmap.createScaledBitmap(imagem, 100, 100, true);

		ivFoto.setImageBitmap(imagemReduzida);

	}
}






















