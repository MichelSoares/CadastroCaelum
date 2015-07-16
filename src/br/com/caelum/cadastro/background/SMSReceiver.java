package br.com.caelum.cadastro.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.gsm.SmsMessage;
import android.widget.Toast;
import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.dao.AlunoDAO;

public class SMSReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context ctx, Intent intent) {
		Object[] mensagens = (Object[]) intent.getExtras().get("pdus");
		byte[] mensagem = (byte[]) mensagens[0];
		
		SmsMessage sms = SmsMessage.createFromPdu(mensagem);
		
		String telefone = sms.getOriginatingAddress();
		
		boolean smsEhDeAluno = new AlunoDAO(ctx).isAluno(telefone);
		
		if(smsEhDeAluno){
			MediaPlayer musica = MediaPlayer.create(ctx, R.raw.games_of_thrones);
			musica.start();
		}
		
		Toast.makeText(ctx, "SMS do aluno: " + telefone, Toast.LENGTH_SHORT).show();
	}

}
