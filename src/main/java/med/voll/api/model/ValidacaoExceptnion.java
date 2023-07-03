package med.voll.api.model;

public class ValidacaoExceptnion extends RuntimeException {
	
	private static final long serialVersionUID = 5064052374492517958L;

	public ValidacaoExceptnion(String mensagem) {
		super(mensagem);
	}
}
