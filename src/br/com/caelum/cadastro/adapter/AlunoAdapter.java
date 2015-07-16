package br.com.caelum.cadastro.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.modelo.Aluno;

public class AlunoAdapter extends BaseAdapter{

	private final List<Aluno> alunos;
	private Activity activity;
	
	public AlunoAdapter(List<Aluno> alunos, Activity activity){
		this.alunos = alunos;
		this.activity = activity;
		
	}
	@Override
	public int getCount() {
		return alunos.size();
	}

	@Override
	public Object getItem(int posicao) {
		return alunos.get(posicao);
	}

	@Override
	public long getItemId(int posicao) {
		return alunos.get(posicao).getId();
	}

	@Override
	public View getView(int posicao, View contentView, ViewGroup parent) {
		
		Aluno aluno = alunos.get(posicao);
		
		LayoutInflater inflater = activity.getLayoutInflater();
		
		View linha = inflater.inflate(R.layout.item, null);
		
		if(posicao % 2 == 0){
			linha.setBackgroundColor(activity.getResources().getColor(R.color.linha_par));
		}
		
		TextView nome = (TextView) linha.findViewById(R.id.nome);
		nome.setText(aluno.getNome().toString());
		
		ImageView foto = (ImageView) linha.findViewById(R.id.foto);

		if(aluno.getCaminhoFoto() != null){
			Bitmap imagem = BitmapFactory.decodeFile(aluno.getCaminhoFoto());
			Bitmap imagemReduzida = Bitmap.createScaledBitmap(imagem, 100, 100, true);
			foto.setImageBitmap(imagemReduzida);
		} else {
			foto.setImageResource(R.drawable.ic_no_image);
		}
		
		return linha;
	}

}

















